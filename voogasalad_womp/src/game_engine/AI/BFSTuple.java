package game_engine.AI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import game_engine.game_elements.Branch;
import game_engine.handlers.BranchHandler;
import game_engine.properties.Position;

public class BFSTuple implements SearchTuple {

	private List<Branch> myEngineBranches;
	private HashSet<Position> myVisited;
	private HashMap<Position, Position> myEdges;
	private HashMap<Position, Integer> myDistances;
	private SearchHandler mySearchHandler;

	public BFSTuple(List<Branch> engineBranches, HashSet<Position> visited,
			HashMap<Position, Position> edges, HashMap<Position, Integer> distances) {
		this.myEngineBranches = engineBranches;
		this.myVisited = visited;
		this.myEdges = edges;
		this.myDistances = distances;
		this.mySearchHandler = new SearchHandler(myEngineBranches);
	}

	public List<Branch> getPathTo(Position goal, Branch currBranch, Position currPos){
		if(!myVisited.contains(goal)){
			return null;
		}
		Stack<Position> pathStack = new Stack<>();
		Position current;
		for(current = goal; myDistances.get(current) != 0; current = myEdges.get(current)){
			pathStack.push(current);
		}
		pathStack.push(current);
		List<Branch> path = mySearchHandler.createPath(pathStack, currBranch, currPos);
		return new BranchHandler().processBranches(path);
	}

	public boolean hasPathTo(Position pos) {
		return this.myVisited.contains(pos);
	}

}