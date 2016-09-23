package game_engine.AI;

import java.util.ArrayList;
import java.util.List;

import game_engine.game_elements.Branch;
import game_engine.handlers.BranchHandler;
import game_engine.interfaces.IBranchHandler;
import game_engine.properties.Position;

public class SearchHandler {
	
	private List<Branch> myEngineBranches;
	private IBranchHandler myBranchHandler;
	
	public SearchHandler(List<Branch> engineBranches){
		this.myEngineBranches = engineBranches;
		this.myBranchHandler = new BranchHandler();
	}

	public List<Branch> createPath(List<Position> posList, Branch currBranch, Position currPos){
		List<Branch> path = new ArrayList<>();
		for(int x=0; x<posList.size()-1; x++){
			Position endA = posList.get(x);
			Position endB = posList.get(x+1);
			path.add(myEngineBranches.stream().filter(b -> b.getPositions().contains(endA) && b.getPositions().contains(endB)).findFirst().get());
		}
		path = myBranchHandler.processBranches(path);
		if(path.size() > 1){
			if(currBranch != null){
				Branch partialBranch = myBranchHandler.getPartialBranch(currBranch, path.get(0), currPos);
				if(partialBranch != null){
					path.add(0, partialBranch);
				}
			}
		}
		return path;
	}
	
}