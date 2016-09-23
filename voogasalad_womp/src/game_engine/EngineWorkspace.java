package game_engine;

import java.util.ArrayList;
import java.util.List;

import game_data.IDataConverter;
import game_data.IGameEnvironment;
import game_data.Serializer;
import game_engine.affectors.Affector;
import game_engine.controllers.AIController;
import game_engine.controllers.EnemyController;
import game_engine.controllers.LevelController;
import game_engine.controllers.UnitController;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Level;
import game_engine.game_elements.Unit;
import game_engine.interfaces.AIWorkspace;
import game_engine.interfaces.ICollisionDetector;
import game_engine.interfaces.IEncapsulationDetector;
import game_engine.interfaces.ILevelDisplayer;
import game_engine.interfaces.IStore;
import game_engine.physics.CollisionDetector;
import game_engine.physics.EncapsulationDetector;
import game_engine.place_validations.PlaceValidation;
import game_engine.properties.Position;
import game_engine.score_updates.EnemyDeathScoreUpdate;
import game_engine.score_updates.EnemyWinScoreUpdate;
import game_engine.score_updates.ScoreUpdate;
import game_engine.store_elements.Store;
import game_engine.wave_goals.WaveGoal;


public class EngineWorkspace implements GameEngineInterface, AIWorkspace {

    private int nextWaveTimer;
    private List<Branch> myBranches;
    private List<Affector> myAffectors;
    private LevelController myLevelController;
    private ICollisionDetector myCollider;
    private IEncapsulationDetector myEncapsulator;
    private UnitController myUnitController;
    private EnemyController myEnemyController;
    private AIController myAIController;
    private WaveGoal waveGoal;
    private ScoreUpdate scoreUpdate;
    private List<Unit> unitsToRemove;
    private Position cursorPos;
    private IGameEnvironment myData;

    public void setUpEngine (IGameEnvironment data) {
        unitsToRemove = new ArrayList<>();
        waveGoal = data.getWaveGoal();
        scoreUpdate = data.getScoreUpdate();
        myBranches = data.getBranches();
        myCollider = new CollisionDetector(this);
        myEncapsulator = new EncapsulationDetector(this);
        myLevelController =
                new LevelController(data.getLevels(), data.getScore(), data.getCurrentLevelIndex());
        List<PlaceValidation> myPlaceValidations = data.getPlaceValidations();
        myPlaceValidations.stream().forEach(pv -> pv.setEngine(this));
        myUnitController =
                new UnitController(data.getPlacedUnits(), myPlaceValidations, data.getStore(),
                                   unitsToRemove);
        myEnemyController = new EnemyController(myLevelController, myUnitController);
        setWorkspaceForAffectors(data.getPlacedUnits(), data.getLevels());
        myAIController = new AIController(this);
    }

    @Override
    public void update () {
        Level myCurrentLevel = myLevelController.getCurrentLevel();
        IStore myStore = myUnitController.getStore();
        List<Unit> placingUnits = myCurrentLevel.getCurrentWave().getPlacingUnits();
        myUnitController.getStore().clearBuyableUnits();
        placingUnits.stream()
                .forEach(u -> myStore.addBuyableUnit(u, u.getProperties().getPrice().getValue()));
        nextWaveTimer++;
        waveProgression(myCurrentLevel);
        myUnitController.getUnitType("Projectile").forEach(p -> p.update());
        myUnitController.getUnitType("Projectile").removeIf(p -> !p.isVisible());
        myUnitController.getUnitType("Terrain").forEach(t -> t.update());
        scoreUpdate.updateScore(this, myCurrentLevel);
        unitsToRemove.stream().forEach(r -> r.setInvisible());
        unitsToRemove.clear();
    }

    public void waveProgression (Level myCurrentLevel) {
        if (!myLevelController.isPaused() && !myLevelController.isGameOver()) {
            myUnitController.getUnitType("Tower").forEach(t -> t.update());
            myUnitController.getUnitType("Enemy").forEach(e -> e.update());
            myCollider.resolveEnemyCollisions(myUnitController.getUnitType("Projectile"));
            myEncapsulator.resolveEncapsulations(myUnitController.getUnitType("Terrain"));
            Unit newE = myCurrentLevel.update();
            if (newE != null) {
                myUnitController.getPlacedUnits().add(newE);
            }
        }
        if (myCurrentLevel.getNextWave() != null && waveGoal.reachedGoal(this)) {
            nextWaveTimer = 0;
            myLevelController.continueWaves();
        }
        if (myUnitController.getUnitType("Enemy").size() == 0) {
            myUnitController.clearProjectiles();
        }
    }

    public void saveGame () {
        Level currentLevel = myLevelController.getCurrentLevel();
        myData.setCurrentLevelIndex(myLevelController.getLevels().indexOf(currentLevel));
        myData.setCurrentWaveIndex(currentLevel.getWaves().indexOf(currentLevel.getCurrentWave()));
        IDataConverter<IGameEnvironment> writer = new Serializer<IGameEnvironment>();
        writer.saveElement(myData);
    }

    public void setWorkspaceForAffectors (List<Unit> placedUnits, List<Level> levels) {
        List<Unit> allUnits = new ArrayList<>(placedUnits);
        levels.stream().forEach(l -> {
            l.getWaves().forEach(w -> {
                allUnits.addAll(w.getPlacingUnits());
                allUnits.addAll(w.getSpawningUnits());
            });
        });
        allUnits.stream().forEach(u -> setWorkspaceForUnit(u));
    }

    public void setWorkspaceForUnit (Unit unit) {
        unit.getChildren().stream().forEach(c -> {
            c.getAffectors().stream().forEach(a -> setWorkspaceForChild(c));
        });
        setWorkspaceForChild(unit);
    }

    public void setWorkspaceForChild (Unit child) {
        child.getAffectors().stream().forEach(a -> a.setWorkspace(this));
        child.getAffectorsToApply().stream().forEach(a -> a.setWorkspace(this));
    }

    public List<Affector> getAffectors () {
        return myAffectors;
    }

    @Override
    public List<Branch> getBranches () {
        return myBranches;
    }

    @Override
    public int getNextWaveTimer () {
        return nextWaveTimer;
    }

    @Override
    public void setCursorPosition (double x, double y) {
        cursorPos = new Position(x, y);
    }

    public Position getCursorPosition () {
        return cursorPos;
    }

    @Override
    public UnitController getUnitController () {
        return myUnitController;
    }

    @Override
    public LevelController getLevelController () {
        return myLevelController;
    }

    @Override
    public ILevelDisplayer getLevelDisplay () {
        return myLevelController;
    }

    @Override
    public AIController getAIController () {
        return myAIController;
    }

    @Override
    public EnemyController getEnemyController () {
        return myEnemyController;
    }

    @Override
    public ICollisionDetector getCollisionDetector () {
        return myCollider;
    }

    @Override
    public IEncapsulationDetector getEncapsulationDetector () {
        return myEncapsulator;
    }


}
