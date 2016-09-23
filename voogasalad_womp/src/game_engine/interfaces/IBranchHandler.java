package game_engine.interfaces;

import java.util.List;

import game_engine.game_elements.Branch;
import game_engine.properties.Position;

public interface IBranchHandler {
	/*
	 * Changes a branch to reflect a new set of points on the map connected by paths.
	 * 
	 * @param	newPos is a List<Position> that reflects the new path in the branch
	 * @param	branch is a Branch object that is to be updated.
	 * @return	returns a new Branch object that reflects the new path added to the branch.
	 */
	Branch updateBranch(List<Position> newPos, Branch branch);
	
	/*
	 * Creates a new branch with the same neighbors as the previous branch, but with a reversed order 
	 * of points that will be visited along the branch.
	 * 
	 * @param	Branch branch is the branch that is to be reversed.
	 * @return 	returns a Branch object that is the same as the original input, but with the positions 
	 * 			reversed.
	 */
	Branch reverseBranch(Branch branch);
	
	/*
	 * Truncates a branch to only include some of the points.
	 * 
	 * @param	currBranch is the point to the branch that is to be truncated
	 * @param	newCurrBranch is the pointer to the new branch that is being created
	 * @param	currPos is the Position at which to split the branch
	 * @return	Branch returns the new branch that is the truncated version of the original input.
	 */
	Branch getPartialBranch(Branch currBranch, Branch newCurrBranch, Position currPos);
	
	/*
	 * Handles switching branches by checking overlapping positions, and connecting
	 * neighboring branches.
	 * 
	 * @param	List<Branch> path is the list of Branches that are being processed 
	 * @return	List<Branch> is a list of the branches with updated neighbors.
	 */
	List<Branch> processBranches(List<Branch> path);
}
