package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

public class ToroGrid extends Grid {

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


	public ToroGrid(Simulation sim, int numCols, int numRows, Map<Point2D, Map<String, Double>> cellMap, String ct) {
		setGrid(createGrid(sim, numRows, numCols, cellMap, ct));
	}
	
	
	public List<Point2D> checkPositions(List<Point2D> neighbors, int nR, int nC, Map<Point2D, Cell> grid) {
		List<Point2D> newNeighbors = new ArrayList<Point2D>();
		
		for(Point2D p : neighbors){
			double newX = p.getX();
			double newY = p.getY();
			
			while( (newX < 0) ){
//				newX = nC-1;
				newX += nC;
			}
			while( (newY < 0) ){
//				newY = nR-1;
				newY += nR;
			}
			while(  newX>=nC){
//				neyX = 0;
				newX -= nC;
			}
			while(  newY>=nR){
//				newY = 0;
				newY -= nR;
			}
			
			Point2D newP = new Point2D(newX,newY);
			newNeighbors.add(newP);
		}
		return newNeighbors;
	}

}
	

