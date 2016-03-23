package simulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;

public class InfiniteGrid extends Grid {
	

	 	boolean incrementCols = false;
		boolean incrementRows = false;
		boolean decrementCols = false;
		boolean decrementRows = false;
		

	
		public boolean isIncrementCols() {
			return incrementCols;
		}
		public void setIncrementCols(boolean incrementCols) {
			this.incrementCols = incrementCols;
		}
		public boolean isIncrementRows() {
			return incrementRows;
		}
		public void setIncrementRows(boolean incrementRows) {
			this.incrementRows = incrementRows;
		}
		public boolean isDecrementCols() {
			return decrementCols;
		}
		public void setDecrementCols(boolean decrementCols) {
			this.decrementCols = decrementCols;
		}
		public boolean isDecrementRows() {
			return decrementRows;
		}
		public void setDecrementRows(boolean decrementRows) {
			this.decrementRows = decrementRows;
		}


		private Map<Point2D, Cell> grid;
		private int numRows;
		private int numCols;
		private Map<String,Double> configs;
		
		public int getNumRows()                      { return numRows; }
		public int getNumCols()                      { return numCols; }
		public void setNumRows(int nr)               { numRows = nr  ; }
		public void setNumCols(int nc)               { numCols = nc  ; }
		public void setConfigs(Map<String,Double>c)  { configs = c   ; }
		public Map<Point2D, Cell> getGrid()          { return grid   ; }
		public void setGrid(Map<Point2D, Cell> g)    { grid = g      ; }
		public Map<String,Double> getConfigs()       { return configs; }


		public InfiniteGrid(Simulation sim, int numCols, int numRows, Map<Point2D, Map<String, Double>> cellMap, String ct) {
			setGrid(createGrid(sim, numRows, numCols, cellMap,ct));
		}
		
		
		public List<Point2D> checkPositions(List<Point2D> neighbors, int nR, int nC, Map<Point2D, Cell> grid) {
		 	boolean incrementCols = false;
			boolean incrementRows = false;
			boolean decrementCols = false;
			boolean decrementRows = false;
			for(Point2D p : neighbors){
				
				if( (p.getX() < 0) ){
					setDecrementCols(true);
				}
				if( (p.getY() < 0) ){
					setDecrementRows(true);
				}
				if(  p.getX()>=nC){
					setIncrementCols(true);
				}
				if(  p.getY()>=nR){
					setIncrementRows(true);
				}
				if( (p.getX() < 0)|| (p.getX()>=nC) || (p.getY()<0) ||(p.getY()>=nR) ){
					Point2D newP = new Point2D(p.getX(),p.getY());
					Cell c = createCell(getCellType());
					c.setState(0);
					c.setNextState(0);
					grid.put(newP, c);
				}
			}
			return neighbors;
		}

	
}
	
	


