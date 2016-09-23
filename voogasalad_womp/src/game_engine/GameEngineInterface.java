package game_engine;

import java.util.List;

import game_data.IGameEnvironment;
import game_engine.controllers.AIController;
import game_engine.controllers.EnemyController;
import game_engine.controllers.LevelController;
import game_engine.controllers.UnitController;
import game_engine.game_elements.Branch;
import game_engine.interfaces.ICollisionDetector;
import game_engine.interfaces.IEncapsulationDetector;
import game_engine.interfaces.ILevelDisplayer;
import game_engine.properties.Position;


/**
 * This interface is the external API for the game player module. It facilitates
 * user actions to the game engine. Throws exception when wrong files are passed
 * to setUpEngine or towers cannot be found/modified. Throws exception when games
 * cannot be saved/loaded correctly.
 * 
 * @author ownzandy
 *
 */

public interface GameEngineInterface {

	public void saveGame ();

	public void update ();

	public void setUpEngine (IGameEnvironment data);

	public UnitController getUnitController ();

	public LevelController getLevelController ();
	
	public AIController getAIController();
	
	public EnemyController getEnemyController ();

	public List<Branch> getBranches ();

	public int getNextWaveTimer ();

	public void setCursorPosition (double x, double y);

	public Position getCursorPosition ();

	public ILevelDisplayer getLevelDisplay();

	public ICollisionDetector getCollisionDetector ();
	
	public IEncapsulationDetector getEncapsulationDetector ();


}