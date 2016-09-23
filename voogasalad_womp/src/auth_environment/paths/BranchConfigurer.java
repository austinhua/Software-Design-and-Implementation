package auth_environment.paths;

import java.util.ArrayList;
import java.util.List;

import game_engine.game_elements.Branch;
import game_engine.properties.Position;

/**
 * The BranchConfigurer allows for configurations of branches within path graphs.
 * This includes splitting branches into two different branches and updating the graph and neighbors accordingly.
 * 
 * @author adamtache
 *
 */

public class BranchConfigurer {

	/*
	 * Configures branch in path graph through insertion and updating necessary neighbors.
	 * 
	 * @param newBranch
	 * @param myPath
	 * 
	 * Calls configureBranchesWithEdgePos to insert branch into graph if its end point(s) coincide with edge of another branch.
	 * Calls cnfigureMidBranchSplits to insert branch into graph if its end point(s) coincide with middle of another branch.
	 * 
	 */
	public void configureBranch(Branch newBranch, PathGraph pathGraph){
		if(!pathGraph.getBranches().contains(newBranch)){
			Position startPos = newBranch.getFirstPosition();
			Position endPos = newBranch.getLastPosition();
			configureBranchesWithEdgePos(newBranch, pathGraph, startPos);
			if(startPos != null && endPos !=null && !startPos.equals(endPos)){
				configureBranchesWithEdgePos(newBranch, pathGraph, endPos);
			}
			configureMidBranchSplits(newBranch, pathGraph, startPos);
			if(startPos != null && endPos !=null && !startPos.equals(endPos)){
				configureMidBranchSplits(newBranch, pathGraph, endPos);
			}
		}
	}

	/*
	 * Configures branch in path with branches that have an end point coinciding with the end points of branch.
	 * 
	 * @param newBranch
	 * @param myPath
	 * 
	 */
	private void configureBranchesWithEdgePos(Branch newBranch, PathGraph pathGraph, Position pos){
		List<Branch> branchesAtPos = pathGraph.getBranchesByEdgePosition(pos);
		List<Branch> branchesChecked = new ArrayList<>();
		for(Branch branch : branchesAtPos){
			if(!branchesChecked.contains(branch)){
				branchesChecked.add(branch);
				if(!branch.equals(newBranch)){
					newBranch.addNeighbor(branch);
					branch.addNeighbor(newBranch);
				}
			}
		}
		if(!pathGraph.getBranches().contains(newBranch))
			pathGraph.addBranch(newBranch);
	}

	/*
	 * Configures branch in path with branches that have a mid point coinciding with the end points of branch.
	 * 
	 * @param newBranch
	 * @param myPath
	 * 
	 */
	private void configureMidBranchSplits(Branch newBranch, PathGraph pathGraph, Position pos){
		List<Branch> branchesToSplit = pathGraph.getBranchesByMidPosition(pos);
		branchesToSplit.stream().filter(b -> b.equals(newBranch));
		for(Branch b : branchesToSplit){
			List<Position> cutoffPositions = b.cutoffByPosition(pos);
			Position lastCutoff = cutoffPositions.get(cutoffPositions.size()-1);
			Branch newSplitBranch = new Branch();
			newSplitBranch.addPositions(cutoffPositions);
			newSplitBranch.addNeighbor(b);
			newSplitBranch.addNeighbor(newBranch);
			b.addNeighbor(newSplitBranch);
			b.addNeighbor(newBranch);
			List<Branch> cutoffConnectedBranches = pathGraph.getBranchesByEdgePosition(lastCutoff);
			for(Branch br : cutoffConnectedBranches){
				newSplitBranch.addNeighbors(b.removeNeighbors(br.getNeighbors()));
			}
			newBranch.addNeighbor(b);
			newBranch.addNeighbor(newSplitBranch);
			pathGraph.addBranch(newSplitBranch);
		}
	}

}