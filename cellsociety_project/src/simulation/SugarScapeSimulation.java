package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

public class SugarScapeSimulation extends Simulation {

	private final int EMPTY = 0 ;
	private final int AGENT = 1;
	
	private final int SATISFIED = 1;
	

	public int  getNumStates() 					{return 2;}

	Grid gridObj;
	public Grid getGridObj()                   {return gridObj;}
	public void setGridObj(Grid g) 			   {gridObj = g   ;}

	Map<Point2D, Cell> grid;
	public Map<Point2D, Cell> getGrid()         {return grid;}
	public void setGrid(Map<Point2D, Cell> g)   {grid = g;}
	
	
	public SugarScapeSimulation(Map<String, Double> configMap, Map<Point2D, Map<String, Double>> cellMap, String gt, String ct) {
		initializeSim(configMap, cellMap, gt, ct);
		setGrid(getGridObj().getGrid());
	}

	@Override
	public void move(Map<Point2D, Cell> grid2) {
		// TODO Auto-generated method stub

	}

	@Override
	public int stateLogic(Cell cell, List<Point2D> arrayList) {
		int currentState = cell.getState();
		List<Cell> cardinalPoints = getCardinalCells(arrayList);
		if(cell.getSatisfaction() == SATISFIED){
			return cell.getNextState();
		}
		if(currentState == AGENT){
			if( cell.getProperty("sugar")<=0){  
				cell.setSatisfaction(SATISFIED);
				return EMPTY;
			}else{
				Cell c = getRandomNeighbor(getMaxVals(cardinalPoints, "sugar"));
				cell.setNextState(EMPTY);
				int sugarAmount = (int)Math.round(c.getProperty("sugar"));
				
				cell.setProperty("sugar",(c.getProperty("sugar")));
				c.setProperty("sugar", 0.0);
				cell.setProperty("sugar", (cell.getProperty("sugar")-cell.getProperty("metabolism")));
			}
		}
		return 0; //have to figure out what to return here. 
	}
	
	private List<Cell> getCardinalCells(List<Point2D> plist){
		List<Cell> cardinalPoints = new ArrayList<Cell>();
		for(Point2D p : plist){
			if(p.getX()!=p.getY()){
				cardinalPoints.add(getGrid().get(p));
			}
		}
		return cardinalPoints;
	}
	
	@Override
	public String getType() {
		return "sugarScape";
	}

}
