package game_data;

import java.util.ArrayList;
import java.util.List;

import auth_environment.paths.MapHandler;
import game_data.IGameEnvironment;
import game_engine.affectors.Affector;
import game_engine.factories.AffectorFactory;
import game_engine.factories.FunctionFactory;
import game_engine.factories.StoreFactory;
import game_engine.factories.UnitFactory;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Level;
import game_engine.game_elements.Unit;
import game_engine.place_validations.PlaceValidation;
import game_engine.properties.Position;
import game_engine.score_updates.ScoreUpdate;
import game_engine.wave_goals.WaveGoal;


public class GameEnvironment implements IGameEnvironment {
	private String myName;
	private String mySplashScreenName;
	
	private List<Level> myLevels = new ArrayList<Level>();
	private List<Unit> myPlacedUnits = new ArrayList<Unit>(); 
	private List<PlaceValidation> myPlaceValidations = new ArrayList<PlaceValidation>();
	private ScoreUpdate myScoreUpdate;
	private WaveGoal myWaveGoal;
	private double myScore = 0;
	private int myCurrentWaveIndex = 0;
	private int myCurrentLevelIndex = 0;
	private MapHandler myMapHandler = new MapHandler();
	
	private FunctionFactory myFunctionFactory = new FunctionFactory();
	private AffectorFactory myAffectorFactory = new AffectorFactory(myFunctionFactory);
	private UnitFactory myUnitFactory = new UnitFactory();
	private StoreFactory myStoreFactory = new StoreFactory(myUnitFactory.getUnitLibrary(), myAffectorFactory.getAffectorLibrary()); 

	private List<Unit> myTowers = new ArrayList<Unit>();
	private List<Unit> myEnemies = new ArrayList<Unit>();
	private List<Unit> myTerrains = new ArrayList<Unit>();
	private List<Unit> myProjectiles = new ArrayList<Unit>();
	private List<Position> mySpawns = new ArrayList<Position>();
	private List<Position> myGoals = new ArrayList<Position>();

	@Override
	public String getGameName() {
		return this.myName; 
	}
	@Override
	public void setGameName(String name) {
		this.myName = name; 
	}

	public String getSplashScreen() {
		return mySplashScreenName;
	}
	public void setSplashScreen(String fileName) {
		mySplashScreenName = fileName;
	}
	
	@Override
	public List<Position> getGoals() {
		return myGoals;
	}
	@Override
	public void setGoals(List<Position> goals) {
		myGoals = goals;
	}
	
	@Override
	public List<Position> getSpawns() {
		return mySpawns;
	}
	@Override
	public void setSpawns(List<Position> spawns) {
		mySpawns = spawns;
	}
	
	@Override
	public List<Unit> getTowers() {
		return myTowers;
	}
	@Override
	public void setTowers(List<Unit> towers) {
		myTowers = towers;
	}
	
	@Override
	public List<Unit> getTerrains() {
		return myTerrains;
	}
	@Override
	public void setTerrains(List<Unit> terrains) {
		myTerrains = terrains;
	}
	
	@Override
	public List<Unit> getEnemies() {
		return myEnemies;
	}
	@Override
	public void setEnemies(List<Unit> enemies) {
		myEnemies = enemies;
	}
	
	@Override
	public List<Unit> getProjectiles() {
		return myProjectiles;
	}
	@Override
	public void setProjectiles(List<Unit> projectiles) {
		myProjectiles = projectiles;
	}

	@Override
	public FunctionFactory getFunctionFactory() {
		return myFunctionFactory;
	}
	@Override
	public void setFunctionFactory(FunctionFactory factory) {
		myFunctionFactory = factory;
	}
	
	@Override
	public List<Level> getLevels() {
		return myLevels;
	}
	@Override
	public void setLevels(List<Level> levels) {
		myLevels = levels;
	}
	
	@Override
	public List<Branch> getBranches() {
		return myMapHandler.getEngineBranches();
	}
	
	@Override
	public List<Unit> getPlacedUnits() {
		return myPlacedUnits;
	}
	@Override
	public void setPlacedUnits(List<Unit> units) {
		myPlacedUnits = units;
	}
	
	@Override
	public List<Affector> getAffectors() {
		return myAffectorFactory.getAffectorLibrary().getAffectors();
	}
	
	@Override
	public AffectorFactory getAffectorFactory() {
		return myAffectorFactory;
	}
	@Override
	public void setAffectorFactory(AffectorFactory affectorFactory) {
		myAffectorFactory = affectorFactory;
	}
	
	@Override
	public List<PlaceValidation> getPlaceValidations() {
		return myPlaceValidations;
	}
	@Override
	public void setPlaceValidations(List<PlaceValidation> placeValidations) {
		myPlaceValidations = placeValidations;
	}
	
	@Override
	public WaveGoal getWaveGoal() {
		return myWaveGoal;
	}
	@Override
	public void setWaveGoal(WaveGoal waveGoal) {
		myWaveGoal = waveGoal;
	}
	
	@Override
	public ScoreUpdate getScoreUpdate() {
		return myScoreUpdate;
	}
	@Override
	public void setScoreUpdate(ScoreUpdate scoreUpdate) {
		myScoreUpdate = scoreUpdate;
	}
	
	@Override
	public double getScore() {
		return myScore;
	}
	@Override
	public void setScore(double score) {
		myScore = score;
	}
	
	@Override
	public UnitFactory getUnitFactory() {
		return myUnitFactory;
	}
	@Override
	public void setUnitFactory(UnitFactory unitFactory) {
		myUnitFactory = unitFactory;
	}
	
	@Override
	public MapHandler getMapHandler() {
		return myMapHandler; 
	}
	@Override
	public void setMapHandler(MapHandler mapHandler) {
		myMapHandler = mapHandler;
	}

	@Override
	public StoreFactory getStoreFactory() {
		return myStoreFactory; 
	}

	@Override
	public int getCurrentWaveIndex() {
		return myCurrentWaveIndex;
	}
	@Override
	public void setCurrentWaveIndex(int currentWaveIndex) {
		myCurrentWaveIndex = currentWaveIndex;
	}
	@Override
	public int getCurrentLevelIndex() {
		return myCurrentLevelIndex;
	}
	@Override
	public void setCurrentLevelIndex(int currentLevelIndex) {
		myCurrentLevelIndex = currentLevelIndex;
	}
}
