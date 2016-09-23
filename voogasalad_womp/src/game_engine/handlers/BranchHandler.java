package game_engine.handlers;

import java.util.Collections;
import java.util.List;

import game_engine.game_elements.Branch;
import game_engine.interfaces.IBranchHandler;
import game_engine.properties.Position;

/**
 * The branch handler is responsible for manipulating position and neighbor information for Unit bounds.
 * This includes utility functions such as reversing the branch to ensure proper branching for units.
 * 
 * @author adamtache
 *
 */

public class BranchHandler implements IBranchHandler {
	
	public Branch updateBranch(List<Position> newPos, Branch branch){
		Branch copyBranch = branch.copyBranch();
		branch = new Branch(newPos);
		branch.addNeighbors(copyBranch.getNeighbors());
		return branch;
	}
	
	public Branch reverseBranch(Branch branch){
		Branch branchCopy = branch.copyBranch();
		List<Position> posList = branchCopy.getPositions();
		Collections.reverse(posList);
		branch = new Branch(posList);
		branch.addNeighbors(branchCopy.getNeighbors());
		return branch;
	}
	
	public Branch getPartialBranch(Branch currBranch, Branch newCurrBranch, Position currPos){
		if(!currBranch.equals(newCurrBranch)){
			Position newFirstPos = newCurrBranch.getFirstPosition();
			boolean trimRight = currBranch.getPositions().indexOf(newFirstPos) == 0;
			return trimBranchAtIndex(currBranch.getPositions().indexOf(currPos), trimRight, currBranch);
		}
		return null;
	}
	
	public List<Branch> processBranches(List<Branch> path){
		for(int x=0; x<path.size()-1; x++){
			Branch currBranch = path.get(x);
			Branch nextBranch = path.get(x+1);
			if(nextBranch.getLastPosition().equals(currBranch.getLastPosition())){
				path.set(x+1, reverseBranch(nextBranch));
			}
			else if(nextBranch.getFirstPosition().equals(currBranch.getFirstPosition())){
				path.set(x, reverseBranch(currBranch));
			}
			
		}
		return path;
	}

	private Branch trimBranchAtIndex(int trimIndex, boolean trimRight, Branch branch){
		Branch copyBranch = branch.copyBranch();
		List<Position> copyPos = copyBranch.getPositions();
		List<Position> trimmedPos = trimRight ? copyPos.subList(0, trimIndex+1): copyPos.subList(trimIndex, copyPos.size());
		if(trimRight){
			Collections.reverse(trimmedPos);
		}
		return updateBranch(trimmedPos, branch);
	}
	
}