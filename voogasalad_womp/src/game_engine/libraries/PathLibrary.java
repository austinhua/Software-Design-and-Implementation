package game_engine.libraries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import auth_environment.paths.PathGraph;
import game_engine.game_elements.Branch;
import game_engine.properties.Position;

/**
 * This class represents a library or database of map objects.
 * This includes branches stored in a path graph, goals, spawns, and authoring environment branches.
 * Authoring environment filtered visuals and grid visual lists are used to reduce computational complexity of displaying all grid branches.
 * These are used if the user wishes to start with a pre-made default grid.
 * 
 * @author adamtache
 *
 */

public class PathLibrary {

	private PathGraph myPathGraph;
	private List<Branch> myAuthFilteredVisuals;
	private List<Branch> myAuthGridVisuals;

	public PathLibrary(){
		this.myPathGraph = new PathGraph();
		this.myAuthFilteredVisuals = new ArrayList<>();
		this.myAuthGridVisuals = new ArrayList<>();
	}
	
	public List<Branch> getEngineBranches(){
		return this.myPathGraph.getBranches();
	}
	
	public List<Branch> getAuthBranches(){
		return myPathGraph.getBranches().stream().filter(b -> !myAuthFilteredVisuals.contains(b)).collect(Collectors.toList());
	}
	
	public List<Branch> getAuthGrid(){
		return myAuthGridVisuals;
	}
	
	public PathGraph getPathGraph(){
		return this.myPathGraph;
	}

	public void setAuthVisualFilters(List<Branch> gridBranches) {
		this.myAuthFilteredVisuals = gridBranches;
	}

	public void setGridVisualNodes(List<Position> gridVisualNodes) {
		for(Position gridVisual : gridVisualNodes){
			this.myAuthGridVisuals.add(new Branch(Arrays.asList(gridVisual)));
		}
	}

}