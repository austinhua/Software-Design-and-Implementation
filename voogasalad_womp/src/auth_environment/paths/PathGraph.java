package auth_environment.paths;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import game_engine.game_elements.Branch;
import game_engine.properties.Position;

/**
 * A path graph is a List of Branches, which in term have neighbor Branches. This class allows for accessing branches in various ways.
 * 
 * @author adamtache
 *
 */

public class PathGraph implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Branch> myBranches;

	public PathGraph(List<Branch> branches){
		this.myBranches = branches;
	}

	public PathGraph() {
		myBranches = new ArrayList<>();
	}

	public List<Branch> getBranches(){
		return myBranches;
	}

	public Branch getBranchByPos(Position pos){
		if(myBranches.size() == 0)
			return null;
		List<Branch> branchesAtEdgePos = getBranchesByEdgePosition(pos);
		if(branchesAtEdgePos.size() == 0)
			return null;
		return branchesAtEdgePos.get(0);
	}

	public Branch getBranch(Branch newBranch) {
		for(Branch b : myBranches){
			if(b.equals(newBranch)){
				return b;
			}
		}
		return null;
	}

	public void removeBranch(Branch b) {
		for(Branch neighbor : b.getNeighbors()){
			neighbor.removeNeighbor(b);
			b.removeNeighbor(neighbor);
		}
		myBranches.remove(b);
	}

	public void addBranch(Branch branch){
		myBranches.add(branch);
	}

	public List<Branch> getBranchNodes(Branch branch){
		List<Branch> nodes = branch.getNeighbors().stream().filter(n -> getBranchNodes(n) != null).collect(Collectors.toList());
		nodes.add(branch);
		return nodes;
	}

	public List<Branch> copyBranches(){
		return myBranches.stream().map(p -> new Branch(p.getPositions(), p.getNeighbors())).collect(Collectors.toList());
	}

	public List<Branch> getBranchesByEdgePosition(Position pos){
		return myBranches.stream().filter(
				n -> n.getPositions().size() > 0 && (n.getPositions().get(0).equals(pos) || n.getPositions().get(n.getPositions().size()-1).equals(pos)))
				.collect(Collectors.toList());
	}

	public List<Branch> getBranchesByMidPosition(Position pos){
		return myBranches.stream().filter(
				n-> n.getPositions().contains(pos) && !n.getPositions().get(0).equals(pos) && !n.getPositions().get(n.getPositions().size()-1).equals(pos)).collect(Collectors.toList());
	}

	public PathGraph copyGraph(){
		return new PathGraph(myBranches.stream().map(b -> b.copyBranch()).collect(Collectors.toList()));
	}

}