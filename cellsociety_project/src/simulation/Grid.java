package simulation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import config.Main;
import config.XMLWriter;
import javafx.geometry.Point2D;

public abstract class Grid { 
	
	private Map<String,Double> configs;
	private Map<Point2D, Integer> layout;
	private Map<Point2D, Cell> grid;
	private int numRows;
	private int numCols;
 	boolean incrementCols = false;
	boolean incrementRows = false;
	boolean decrementCols = false;
	boolean decrementRows = false;
	private String cellType;
	
	public String getCellType(){
		return this.cellType;
	}
		
	public Map<Point2D, Cell> getGrid()          { return grid   ; }
	public void setGrid(Map<Point2D, Cell> g)    { grid = g      ; }
	public Map<String,Double> getConfigs()       { return configs; }
	public void setConfigs(Map<String,Double>c)  { configs = c   ; }
	public Map<Point2D, Integer> getLayout()     { return layout ; }
	public int getNumRows()                      { return numRows; }
	public int getNumCols()                      { return numCols; }
	public void setNumRows(int nr)               { numRows = nr  ; }
	public void setNumCols(int nc)               { numCols = nc  ; }
	
	public boolean isIncrementCols() {
		return incrementCols;
	}
	public boolean isIncrementRows() {
		return incrementRows;
	}
	public boolean isDecrementCols() {
		return decrementCols;
	}
	public boolean isDecrementRows() {
		return decrementRows;
	}
	

	
	public Map<Point2D, Cell> createGrid(Simulation sim, int numRows, int numCols, Map<Point2D, Map<String, Double>> cellMap, String cT){
		grid = new HashMap<Point2D, Cell>();
		this.cellType = cT;
		for (int i = 0; i<numRows;i++){
			for (int j = 0; j<numCols ; j++){
				Cell c = createCell(cellType);
				Point2D p = new Point2D(j,i);
				c.setX(j); 
				c.setY(i);			
				Map<String, Double> otherProperties = cellMap.get(p);
				c.setProperties(otherProperties == null? new HashMap<String, Double>() : otherProperties); // null check		
				if(cellMap.containsKey(p) && cellMap.get(p).containsKey(XMLWriter.STATE_TAG)){

					int state = (int) Math.round(cellMap.get(p).get(XMLWriter.STATE_TAG));

					c.setState(state);
				}else{
					int numState = sim.getNumStates();
					int curState = (int)(Math.random() * numState);
					c.setState(curState);
				}				
				grid.put(p, c);
			}
		}	
		return grid;
	}
	
	public Cell createCell(String cellType){
		Cell c;
		switch (cellType) {
		case Main.TRIANGULAR_CELLS:
			c = new TriangleCell();
			break;
		case Main.HEXAGONAL_CELLS:
			c = new HexCell();
			break;
		case Main.SQUARE_CELLS:
			c = new SquareCell();
			break;
		default:
			c = new SquareCell();;
		}
		return c;
	}
	
	public void setup(Map<String,Double> c, Map<Point2D, Integer> l){
		setConfigs(c);
		setNumRows((int) Math.round(c.get("numRows")));
		setNumCols((int) Math.round(c.get("numCols")));  
	}
	
	public abstract List<Point2D> checkPositions(List<Point2D> neighbors, int nR, int nC, Map<Point2D, Cell> grid);

}
