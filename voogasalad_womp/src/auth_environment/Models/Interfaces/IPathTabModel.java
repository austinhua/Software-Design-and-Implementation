package auth_environment.Models.Interfaces;

import java.util.List;
import java.util.Set;

import auth_environment.view.BoundLine;
import game_data.IGameEnvironment;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Unit;
import game_engine.properties.Position;

public interface IPathTabModel extends IWorkspaceModel {
	
	public void refresh(IGameEnvironment auth); 
	
	public void setPathWidth(double width);
	
	public double getPathWidth(); 
	
	public void submitBranch(); 
		
	public List<Branch> getEngineBranches();
	
	public void continueFromLastPosition(double x, double y);

	public void createGrid();

	public void addNewPosition(double x, double y);

	public void addNewSpawn(double x, double y);

	public void addNewGoal(double x, double y);

	public List<Position> getGoals();
	
	public List<Position> getSpawns();
	
	public List<String> getLevelNames();
	
	public List<String> getWaveNames(String selectedLevel); 
	
	public List<Unit> getSpawningUnits(String selectedWave); 
	
	public List<Unit> getPlacingUnits(String selectedWave); 
	
	public Branch reselectBranch(BoundLine line); 
	
	public void saveBranch(BoundLine line, Branch branch); 

	public Set<BoundLine> getBoundLines(); 
	
	public void setActiveUnit(Unit unit);
	
	public Unit getActiveUnit(); 
	
	public void addGoalToActiveLevel(Position goal); 
	
	public void addSpawnToActiveLevel(Position spawn);

	public List<Branch> getAuthBranches(); 
	
	public List<Branch> getAuthGrid();
	
}