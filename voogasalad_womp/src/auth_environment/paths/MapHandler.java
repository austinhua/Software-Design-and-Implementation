package auth_environment.paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import game_engine.game_elements.Branch;
import game_engine.handlers.PositionHandler;
import game_engine.properties.Position;

/**
 * The MapHandler allows for creation of a "map" on the authoring environment to be passed into the game engine.
 * A map is represented by a list of branches, goals, and spawn points for Units to interact with.
 * 
 * @author adamtache
 *
 */

public class MapHandler {

	private PathGraphFactory myPGF;
	private PositionHandler myPositionHandler;
	private List<Position> mySpawns;
	private List<Position> myBranches;
	private List<Position> myGoals;

	public MapHandler(){
		myPGF = new PathGraphFactory();
		myPositionHandler = new PositionHandler();
		this.mySpawns = new ArrayList<>();
		this.myGoals = new ArrayList<>();
	}

	public void processPositions(List<Position> positions){
		List<Position> interpolatedPositions = myPositionHandler.getInterpolatedPositions(positions, false);
		myPGF.insertBranch(interpolatedPositions);
	}

	public void addSpawn(Position spawn){
		Position validSpawn = filterValidPos(spawn, 20);
		if(validSpawn == null)
			return;
		this.mySpawns.add(validSpawn);
	}

	public void addGoal(Position goal){
		Position validGoal = filterValidPos(goal, 20);
		if(validGoal == null)
			return;
		this.myGoals.add(validGoal);
		processPositions(Arrays.asList(validGoal));
	}

	public void setInitGoal(Position pos) {
		myGoals.add(pos);
	}

	public void createGrid(){
		myPGF.insertGrid();
	}

	private Position filterValidPos(Position pos, double radius){
		if(myPGF.getEngineBranches() == null){
			return null;
		}
		boolean match = false;
		Position nearby = null;
		for(Branch b : this.myPGF.getPathLibrary().getEngineBranches()){
			for(Position branchPos : b.getEndPoints()){
				if(pos.equals(branchPos)){
					match = true;
				}
				if(pos.distanceTo(branchPos) < radius){
					nearby = branchPos;
				}
			}
		}
		if(match){
			return pos;
		}
		return nearby;
	}

	public PathGraphFactory getPGF(){
		return myPGF;
	}

	public List<Branch> getEngineBranches() {
		return this.myPGF.getPathLibrary().getEngineBranches();
	}

	public List<Branch> getAuthGrid() {
		return this.myPGF.getAuthGrid();
	}

	public List<Branch> getAuthBranches() {
		return this.myPGF.getAuthBranches();
	}

	public List<Position> getGoals() {
		return myGoals;
	}

	public List<Position> getSpawns() {
		return mySpawns;
	}

}