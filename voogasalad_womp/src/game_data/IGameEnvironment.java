package game_data;

import java.util.List;

import game_engine.game_elements.Unit;
import game_engine.properties.Position;
import game_engine.factories.FunctionFactory;


public interface IGameEnvironment extends IGameData {
		
	public String getGameName(); 
	public void setGameName(String name);
		
	public String getSplashScreen(); 
	public void setSplashScreen(String fileName); 	
		
	public List<Unit> getTowers(); 
	public void setTowers(List<Unit> towers); 
	
	public List<Unit> getTerrains(); 
	public void setTerrains(List<Unit> terrains); 
		
	public List<Unit> getEnemies(); 
	public void setEnemies(List<Unit> enemies); 	
	
    public List<Unit> getProjectiles();
	public void setProjectiles(List<Unit> projectiles); 
	
	public List<Position> getGoals();
	public void setGoals(List<Position> goals);

	public List<Position> getSpawns();
	public void setSpawns(List<Position> spawns);

	public FunctionFactory getFunctionFactory();
	public void setFunctionFactory(FunctionFactory factory); 
	
}