 package simulation;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Simulation{
	
	Map<Point2D,Cell> grid;
	Map<String, Double> configs;
	Map<Point2D,Integer> layout;
	int neighborhoodSize;
	int satisfaction;
	int numRows;
	int numCols;
	int startX = 0;
	int startY = 0;
	
	public Map<String, Double> getConfigMap()	{return configs;}
	public Map<Point2D,Cell> getGrid()			{return grid;}
	public void setGrid(Map<Point2D,Cell> g)	{grid =  g;}
	public int getNeighborHoodSize()			{return neighborhoodSize;}

	public void setStartX(int startX)			{this.startX = startX;}
	public void setStartY(int startY)			{this.startY = startY;}
	public int  getNumRows()                    {return numRows;}
	public void setNumRows(int nr)              {numRows = nr;}
	public int  getNumCols()                    {return numCols;}
	public void setNumCols(int nc)              {numCols = nc;}
	public abstract int getNumStates();
	public abstract String getType();
	
	public void  initializeSim(Map<String, Double> configMap, Map<Point2D, Map<String, Double>> cellMap, String gt, String ct){
		configs = configMap;
		setNumRows( (int) Math.round(configMap.get("numOfRows") ) );
		setNumCols( (int) Math.round(configMap.get("numOfCols") ) );
		setGridObj(createGridObject(gt,cellMap,ct));
	}
	
	
	public Grid getGridObj()                   {return gridObj;}
	public void setGridObj(Grid g) 			   {gridObj = g   ;}
	Grid gridObj;
	public Grid createGridObject(String gridType,Map<Point2D, Map<String, Double>> cellMap, String ct ){
		Grid g;
		switch (gridType) {
		case "toro":
			g = new ToroGrid(this, getNumCols(), getNumRows(), cellMap, ct);
			break;
		case "infinite":
			g = new InfiniteGrid(this, getNumCols(), getNumRows(), cellMap, ct);
			break;
		case "finite":
			g = new FiniteGrid(this, getNumCols(), getNumRows(), cellMap, ct);
			break;
		default:
			g = new FiniteGrid(this, getNumCols(), getNumRows(), cellMap, ct);
		}
		return g;
	}
	
	/**
	 * Primary method of the Simulation Class
	 * finds a list of neighbors for each Point2D and uses them to determine
	 * the next state value of the cell at that Point2D
	 * The updateStates method changes state = next state
	 * @return HashMap<Point2D,Cell> grid
	 */
	public Grid step(){
		for (int i = startY; i<getNumRows();i++){
			for (int j = startX; j<getNumCols(); j++){
				//setNumCols(getNumCols()+1);
				Point2D p = new Point2D(j,i); //j is the x value
				List<Point2D> neighbors = getGrid().get(p).getNeighbors(j,i,1);
				List<Point2D> inBoundsNeighbors = getGridObj().checkPositions(neighbors, getNumRows(), getNumCols(), getGrid());
				int nextState = stateLogic(getGrid().get(p),inBoundsNeighbors);
				getGrid().get(p).setNextState(nextState);	
			}
		}
		// have to go through loop again to repeat process for fish 
		//this is becasue every shark must go before any fish
		
		
		move(getGrid());
		updateStates(getGrid());
		//test(getGrid());
		//test2(getGrid());
		return getGridObj();
	}
		
	public abstract void move(Map<Point2D, Cell> grid2);
	
	private void updateStates(Map<Point2D, Cell> grid) {
		for(Point2D q:grid.keySet()){
			grid.get(q).setState(grid.get(q).getNextState());
		}
		if(getGridObj().isDecrementCols()){
			setStartX(startX-1);
		
		}
		if(getGridObj().isDecrementRows()){
			setStartY(startY-1);
		}
		if(getGridObj().isIncrementCols()){
			setNumCols(getNumCols()+1);
		}
		if(getGridObj().isIncrementRows()){
			setNumRows(getNumRows()+1);
		}
	}
	
	
	public Map<Integer,String> getColorMap(){
		Map<Integer,String> colorMap = new HashMap<Integer,String>();
		colorMap.put(0, "#000000");
		colorMap.put(1, "#32cd32");
		colorMap.put(2, "#ffffff");
		return colorMap;
	}
	
	public void test(Map<Point2D, Cell> tester){
		for(int i = 0 ; i<getNumRows() ; i++){
			for(int j = 0 ; j < getNumCols() ; j++){
				Point2D pls = new Point2D(j,i);
				System.out.print(tester.get(pls).getState());
			}
			System.out.print("\n");
		}
	}
	public void test2(Map<Point2D, Cell> tester){
		for(int i = 0 ; i<getNumRows() ; i++){
			for(int j = 0 ; j < getNumCols() ; j++){
				Point2D pls = new Point2D(j,i);
				System.out.print(tester.get(pls).getNextState());
			}
			System.out.print("\n");
		}
	}
	
	public List<Cell> createStateList(int State, List<Cell> allToChange){
		List<Cell> type = new ArrayList<Cell>();
		for(Cell agent:allToChange){	
			if(agent.getState()==State){
				type.add(agent);
			}
		}
		return type;
	}
	
	public Cell getRandomNeighbor(List<Cell> type){
		Collections.shuffle(type);
		return type.get(0);
	}
	public int getStartX(){
		return this.startX;
	}
	public int getStartY(){
		return this.startY;
	}
	
	public List<Cell> getMaxVals(List<Cell> list, String valueName){
		int maxVal = 0;
		List<Cell> maxCellVals  = new ArrayList<Cell>();
		for(Cell c:list){
			if(c.getProperty(valueName)>=maxVal){
				maxVal = (int) Math.round(c.getProperty(valueName));
			}
		}
		for(Cell c:list){
			if(c.getProperty(valueName)>=maxVal){
				maxCellVals.add(c);
			}
		}
		return maxCellVals;
	}

	public abstract int stateLogic(Cell cell, List<Point2D> arrayList);
	
}