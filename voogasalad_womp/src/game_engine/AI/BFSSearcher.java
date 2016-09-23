package game_engine.AI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import game_engine.game_elements.Branch;
import game_engine.handlers.VisibilityHandler;
import game_engine.interfaces.AIWorkspace;
import game_engine.properties.Position;

/**
 * This class is a utility Artificial Intelligence searcher that allows for BFS search problems.
 * 
 * @author adamtache
 *
 */

public class BFSSearcher implements AISearcher {

	private AIWorkspace myEngine;
	private VisibilityHandler myVisibility;

	public BFSSearcher(AIWorkspace engine) {
		this.myEngine = engine;
		this.myVisibility = new VisibilityHandler(engine);
	}

	public List<Branch> getPath(Position current) {
		List<Position> visibleNodes = myVisibility.getVisibleNodes();
		System.out.println(visibleNodes);
		return getPath(current, visibleNodes);
	}

	public List<Branch> getPath(Position current, List<Position> visibleNodes){
		SearchTuple tuple = getTuple(getGoals(), visibleNodes);
		List<Branch> path = tuple.getPathTo(current, null, current);
		if(path != null){
			return path;
		}
		return null;
	}

	public SearchTuple getTuple(List<Position> sources, List<Position> visibleNodes) {
		Queue<Position> queue = new LinkedList<>();
		HashMap<Position, Integer> distances = new HashMap<>();
		HashMap<Position, Position> edges = new HashMap<>();
		HashSet<Position> visited = new HashSet<>();
		for(Position source : sources){
			visited.add(source);
			distances.put(source, 0);
			queue.add(source);
		}
		while(!queue.isEmpty()){
			Position next = queue.poll();
			for(Position adjacent : myVisibility.getVisibleNeighbors(next, myEngine.getBranches(), visibleNodes)){
				if(!visited.contains(adjacent)){
					edges.put(adjacent, next);
					distances.put(adjacent, distances.get(next) + 1);
					visited.add(adjacent);
					queue.add(adjacent);
				}
			}
		}
		return new BFSTuple(myEngine.getBranches(), visited, edges, distances);
	}

	public boolean isValidSearch(SearchTuple myTuple) {
		List<Position> spawns = myEngine.getLevelController().getCurrentLevel().getSpawns();
		for(Position spawn : spawns){
			if(!myTuple.hasPathTo(spawn)){
				return false;
			}
		}
		return true;
	}

	private List<Position> getGoals() {
		return myEngine.getLevelController().getCurrentLevel().getGoals();
	}

}