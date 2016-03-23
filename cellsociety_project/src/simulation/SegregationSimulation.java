package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

public class SegregationSimulation extends Simulation {
	
	private final int EMPTY = 0;
	
	private final int UNSATISFIED = 1;
	
	private double satisfactionThreshold;


	public int  getNumStates() 					{return 3;}

	Grid gridObj;
	public Grid getGridObj()                   {return gridObj;}
	public void setGridObj(Grid g) 			   {gridObj = g   ;}

	Map<Point2D, Cell> grid;
	public Map<Point2D, Cell> getGrid()         {return grid;}
	public void setGrid(Map<Point2D, Cell> g)   {grid = g;}
	
	public SegregationSimulation(Map<String, Double> configMap, Map<Point2D, Map<String, Double>> cellMap, String gt, String ct) {
		initializeSim(configMap, cellMap, gt, ct);
		satisfactionThreshold = configMap.get("satisfactionThreshold");
		setGrid(getGridObj().getGrid());
	}

	@Override
	public int stateLogic(Cell cell, List<Point2D> checkedNeighbors) {
		int currentState = cell.getState();
		
		int likeAgentCounter = 0;
		int totalAgentCounter = 0;
		if(currentState == EMPTY){
			cell.setSatisfaction(UNSATISFIED);
			return EMPTY;
		}
		for(Point2D agent:checkedNeighbors){
			if(getGrid().get(agent).getState() == currentState){
				likeAgentCounter++;
				totalAgentCounter++;
			}
			else if(getGrid().get(agent).getState() != EMPTY){
				totalAgentCounter++;
			}
		}
		double t = likeAgentCounter/(double)totalAgentCounter;
		if(t < satisfactionThreshold){
			cell.setSatisfaction(UNSATISFIED);
			return currentState;
		}
			cell.setSatisfaction(0);
	
			return currentState;
	}
	
	public void move(Map<Point2D, Cell> grd){
		List<Cell> unsatisfiedCells = new ArrayList<Cell>();
		List<Cell> emptyCells = new ArrayList<Cell>();
		
		for(Point2D pt : grd.keySet()){
			if(grd.get(pt).getSatisfaction() == UNSATISFIED){
				if(grd.get(pt).getState() == EMPTY){
					emptyCells.add(grd.get(pt));
				}else{
					unsatisfiedCells.add(grd.get(pt));
				}
			}
			grd.get(pt).setSatisfaction(0);
		}
		Collections.shuffle(unsatisfiedCells);
		Collections.shuffle(emptyCells);
		for(int i = 0; i < (Math.min(unsatisfiedCells.size(), emptyCells.size())) ; i++ ){
			int ns = unsatisfiedCells.get(i).getState();
			emptyCells.get(i).setNextState(ns);
			unsatisfiedCells.get(i).setNextState(EMPTY);
		}
		
	}
	
	@Override
	public String getType() {
		return "segregation";
	}

}
