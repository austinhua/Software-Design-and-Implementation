package game_engine.AI;

import java.util.List;

import game_engine.game_elements.Branch;
import game_engine.properties.Position;

public interface SearchTuple {
	
	public List<Branch> getPathTo(Position goal, Branch currBranch, Position currPos);
	
	public boolean hasPathTo(Position pos);
	
}