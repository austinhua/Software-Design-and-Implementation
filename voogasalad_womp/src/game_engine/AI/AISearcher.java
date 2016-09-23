package game_engine.AI;

import java.util.List;

import game_engine.game_elements.Branch;
import game_engine.properties.Position;

/**
 * AISearcher interface allows for Artificial Intelligence search problems.
 * Search problems operate based on nodes and edges.
 * A Branch is the data structure with end points acting as nodes, and intermediary points acting as edges.
 * AI Search problems operate on Search Tuples which hold search-specific data.
 * 
 * @author adamtache
 *
 */

public interface AISearcher {

	public List<Branch> getPath(Position current);
	
	public List<Branch> getPath(Position current, List<Position> visibleNodes);
	
	public boolean isValidSearch(SearchTuple myTuple);
	
}