package game_data;

import java.util.List;

import auth_environment.paths.MapHandler;
import game_engine.game_elements.Level;
import game_engine.affectors.Affector;
import game_engine.factories.AffectorFactory;
import game_engine.factories.StoreFactory;
import game_engine.factories.UnitFactory;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Unit;
import game_engine.place_validations.PlaceValidation;
import game_engine.score_updates.ScoreUpdate;
import game_engine.store_elements.Store;
import game_engine.wave_goals.WaveGoal;

public interface IGameData {
	public List<Level> getLevels();
	public void setLevels(List<Level> levels);

	public List<Branch> getBranches();

	public List<Unit> getPlacedUnits();
	public void setPlacedUnits(List<Unit> units);

	public AffectorFactory getAffectorFactory();
	public void setAffectorFactory(AffectorFactory affectorFactory);
	public default List<Affector> getAffectors() {
		return getAffectorFactory().getAffectorLibrary().getAffectors();
	}

	public List<PlaceValidation> getPlaceValidations();
	public void setPlaceValidations(List<PlaceValidation> placeValidations);

	public WaveGoal getWaveGoal();
	public void setWaveGoal(WaveGoal waveGoal);

	public ScoreUpdate getScoreUpdate();
	public void setScoreUpdate(ScoreUpdate scoreUpdate);

	public double getScore();
	public void setScore(double score);    

	public UnitFactory getUnitFactory();
	public void setUnitFactory(UnitFactory unitFactory);

	public MapHandler getMapHandler();
	public void setMapHandler(MapHandler mapHandler);

	public StoreFactory getStoreFactory(); 
	public default Store getStore() {
		return getStoreFactory().getStore();
	}

	public int getCurrentWaveIndex();
	public void setCurrentWaveIndex(int currentWaveIndex);
	public int getCurrentLevelIndex();
	public void setCurrentLevelIndex(int currentLevelIndex);
}