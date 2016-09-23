package auth_environment.paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import game_engine.game_elements.Branch;
import game_engine.handlers.PositionHandler;
import game_engine.libraries.PathLibrary;
import game_engine.properties.Position;

/**
 * This class is a factory for creating path graphs. Path graphs are a set of branches that represent a set of positions.
 * Branches have neighbors and end points are used as nodes in search problems. 
 * 
 * @author adamtache
 *
 */

public class PathGraphFactory {

	private PathLibrary myPathLibrary;
	private PositionHandler myPositionHandler;
	private BranchConfigurer myBranchHandler;

	public PathGraphFactory(){
		myPositionHandler = new PositionHandler();
		myPathLibrary = new PathLibrary();
		myBranchHandler = new BranchConfigurer();
	}

	/**
	 * @param newPath
	 * @param GraphID
	 * Inserts branch with newPath positions into path graph
	 */
	public void insertBranch(List<Position> branchPos){
		if(branchPos.size() == 0)
			return;
		Branch newBranch = new Branch(branchPos);
		Branch currentBranch = myPathLibrary.getPathGraph().getBranchByPos(branchPos.get(0));
		if(currentBranch != null){
			myBranchHandler.configureBranch(newBranch, myPathLibrary.getPathGraph());
		}
		else{
			currentBranch = myPathLibrary.getPathGraph().getBranchByPos(branchPos.get(branchPos.size()-1));
			if(currentBranch != null){
				myBranchHandler.configureBranch(newBranch, myPathLibrary.getPathGraph());
			}
		}
		if(myPathLibrary.getPathGraph().getBranch(newBranch) == null){
			if(branchPos.size() > 0){
				createNewBranch(newBranch);
			}
		}
	}
	
	public void insertGrid(){
		Position[][] positionGrid = createPosGrid(500, 500, 50);
		List<List<Position>> branchPosLists = createBranchPosLists(positionGrid);
		for(List<Position> branchPos : branchPosLists){
			insertBranchInPath(branchPos, myPathLibrary.getPathGraph());
		}
		List<Branch> gridBranches = myPathLibrary.getPathGraph().getBranches();
		myPathLibrary.setAuthVisualFilters(gridBranches);
		List<Position> gridVisualNodes = new ArrayList<>();
		for(Branch gridBranch : gridBranches){
			gridVisualNodes.addAll(gridBranch.getEndPoints());
		}
		myPathLibrary.setGridVisualNodes(gridVisualNodes);
	}

	private void insertBranchInPath(List<Position> branchPos, PathGraph path){
		Branch newBranch = new Branch(branchPos);
		myBranchHandler.configureBranch(newBranch, path);
	}

	private List<List<Position>> createBranchPosLists(Position[][] grid){
		List<List<Position>> branchPosLists = new ArrayList<>();
		for(int r=0; r<grid.length; r++){
			for(int c=0; c<grid[r].length; c++){
				Position pos = grid[r][c];
				if((r+1) < grid.length){
					Position neighbor1 = grid[r+1][c];
					List<Position> branchEndPos = Arrays.asList(pos, neighbor1);
					branchPosLists.add(myPositionHandler.getInterpolatedPositions(branchEndPos, false)); 
				}
				if((c+1) < grid[r].length){
					Position neighbor2 = grid[r][c+1];
					List<Position> branchEndPos = Arrays.asList(pos, neighbor2);
					branchPosLists.add(myPositionHandler.getInterpolatedPositions(branchEndPos, false)); 
				}
			}
		}
		return branchPosLists;
	}
	
	private Position[][] createPosGrid(double width, double length, double sideLength){
		int numSquareCols = (int)Math.floor(width/sideLength) + 1;
		int numSquareRows = (int)Math.floor(length/sideLength) + 1;
		Position[][] posGrid = new Position[numSquareCols][numSquareRows];
		for(int x=0; x<numSquareCols; x++){
			for(int y=0; y<numSquareRows; y++){
				posGrid[x][y] = new Position(x*sideLength, y*sideLength);
			}
		}
		return posGrid;
	}

	private void createNewBranch(Branch branch){
		myPathLibrary.getPathGraph().addBranch(branch);
	}

	public PathLibrary getPathLibrary() {
		return myPathLibrary;
	}
	
	public List<Branch> getEngineBranches(){
		return myPathLibrary.getEngineBranches();
	}
	
	public List<Branch> getAuthBranches(){
		return myPathLibrary.getAuthBranches();
	}
	
	public List<Branch> getAuthGrid(){
		return myPathLibrary.getAuthGrid();
	}
	
}