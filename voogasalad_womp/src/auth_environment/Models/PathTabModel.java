package auth_environment.Models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import auth_environment.Models.Interfaces.IPathTabModel;
import auth_environment.paths.MapHandler;
import auth_environment.view.BoundLine;
import game_data.IGameEnvironment;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Level;
import game_engine.game_elements.Unit;
import game_engine.game_elements.Wave;
import game_engine.properties.Position;

/**
 * Created by BrianLin on 4/20/16
 * Team member responsible: Brian
 *
 * This Tab is for placing Paths on the Map 
 */

public class PathTabModel implements IPathTabModel {

	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private IGameEnvironment myAuthData;  
	private MapHandler myMapHandler; 
	private List<Position> myCurrentBranch;

	private Map<BoundLine, Branch> myBranchMap; 

	private List<Level> myLevels;
	private Level currentLevel;
	private Wave currentWave; 
	private Unit myActiveUnit; 

	private double myPathWidth;

	public PathTabModel(IGameEnvironment auth) {
		myAuthData = auth; 
		myBranchMap = new HashMap<BoundLine, Branch>(); 
		myPathWidth = Double.parseDouble(myDimensionsBundle.getString("defaultPathWidth"));
		myCurrentBranch = new ArrayList<>(); 
		myMapHandler = auth.getMapHandler();
		myMapHandler.setInitGoal(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE));
		myLevels = auth.getLevels(); 
	}

	@Override
	public void refresh(IGameEnvironment auth) {
		myCurrentBranch.clear();
		myMapHandler = auth.getMapHandler();
		myLevels = auth.getLevels(); 
	}

	@Override
	public void setPathWidth(double width) {
		myPathWidth = width; 
	}

	@Override
	public double getPathWidth() {
		return myPathWidth;
	}

	@Override
	public void submitBranch() {
		myMapHandler.processPositions(myCurrentBranch);
		myCurrentBranch.clear();
		submit();
	}

	private void submit() {
		myAuthData.setMapHandler(myMapHandler);
	}

	@Override
	public void addNewPosition(double x, double y) {
		myCurrentBranch.add(new Position(x, y));
	}

	@Override
	public void continueFromLastPosition(double x, double y) {
		myCurrentBranch.add(new Position(x, y));
	}

	@Override
	public void createGrid() {
		myMapHandler.createGrid();
	}

	public List<String> getLevelNames() {
		return myLevels.stream().map(level -> level.getName()).collect(Collectors.toList());
	}

	public List<String> getWaveNames(String selectedLevel) {
		List<Level> levels =  myLevels.stream().filter(l -> l.getName().equals(selectedLevel)).collect(Collectors.toList());
		if (levels!=null && levels.size()>0) {
			currentLevel = levels.get(0); 
		}
		return currentLevel.getWaves().stream().map(wave -> wave.toString()).collect(Collectors.toList());
	}

	private void getCurrentWaveFromString(String selectedWave) {
		currentWave = currentLevel.getWaves().stream().filter(w -> w.toString().equals(selectedWave)).collect(Collectors.toList()).get(0);
	}

	@Override
	public List<Unit> getSpawningUnits(String selectedWave) {
		if(selectedWave == null) {
			return new ArrayList<>(); 
		}
		getCurrentWaveFromString(selectedWave); 
		return currentWave.getSpawningUnits();
	}

	@Override
	public List<Unit> getPlacingUnits(String selectedWave) {
		if(selectedWave == null) {
			return new ArrayList<>(); 
		}
		getCurrentWaveFromString(selectedWave); 
 		return currentWave.getPlacingUnits();
	}

	@Override
	public Branch reselectBranch(BoundLine line) {
		if (myActiveUnit!=null) {
			Branch b = myBranchMap.get(line);
			List<Branch> branches = myActiveUnit.getProperties().getMovement().getBranches();
			branches.add(b);
			myActiveUnit.getProperties().getMovement().setBranches(branches);
		}
		return myBranchMap.get(line); 
	}

	@Override
	public void saveBranch(BoundLine line, Branch branch) {
		myBranchMap.put(line, branch); 
	}

	@Override
	public Set<BoundLine> getBoundLines() {
		return myBranchMap.keySet();
	}

	@Override
	public void setActiveUnit(Unit unit) {
		myActiveUnit = unit; 
	}

	@Override
	public Unit getActiveUnit() {
		return myActiveUnit;
	}

	@Override
	public void addGoalToActiveLevel(Position goal) {
		if (currentLevel!=null) {
			currentLevel.addGoal(goal.copyPosition());
		}
	}

	@Override
	public void addSpawnToActiveLevel(Position spawn) {
		if (currentLevel!=null) {
			currentLevel.addSpawn(spawn.copyPosition());
		}
	}

	@Override
	public List<Branch> getEngineBranches() {
		return myMapHandler.getEngineBranches();
	}

	@Override
	public void addNewSpawn(double x, double y) {
		myMapHandler.addSpawn(new Position(x, y));
	}

	@Override
	public void addNewGoal(double x, double y) {
		myMapHandler.addGoal(new Position(x, y));
	}

	@Override
	public List<Position> getGoals() {
		return myMapHandler.getGoals();
	}

	@Override
	public List<Position> getSpawns() {
		return myMapHandler.getSpawns();
	}

	@Override
	public List<Branch> getAuthBranches() {
		return this.myMapHandler.getAuthBranches();
	}

	@Override
	public List<Branch> getAuthGrid() {
		return this.myMapHandler.getAuthGrid();
	}

}