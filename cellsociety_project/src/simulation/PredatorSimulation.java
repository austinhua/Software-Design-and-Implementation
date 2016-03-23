package simulation;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredatorSimulation extends Simulation {

	private final int EMPTY = 0;
	private final int FISH = 1;
	private final int SHARK = 2;
	
	private final int SATISFIED = 1;


	public int  getNumStates() 					{return 3;}

	Grid gridObj;
	public Grid getGridObj()                   {return gridObj;}
	public void setGridObj(Grid g) 			   {gridObj = g   ;}

	Map<Point2D, Cell> grid;
	public Map<Point2D, Cell> getGrid()         {return grid;}
	public void setGrid(Map<Point2D, Cell> g)   {grid = g;}

	
	public PredatorSimulation(Map<String, Double> configMap, Map<Point2D, Map<String, Double>> cellMap, String gt, String ct) {
		initializeSim(configMap, cellMap, gt, ct);
		setGrid(getGridObj().getGrid());
	}
	

	public int stateLogic(Cell cell, List<Point2D> checkedNeighbors) {
		int currentState = cell.getState();
		List<Cell> allToChange = new ArrayList<Cell>();
		List<Cell> sharks = new ArrayList<Cell>();
		List<Cell> fish = new ArrayList<Cell>();
		List<Cell> empty = new ArrayList<Cell>();
				
		if(cell.getSatisfaction() == SATISFIED){
			return cell.getNextState();
		}
		
		for(Point2D p:checkedNeighbors){
			if(getGrid().get(p).getSatisfaction() != SATISFIED){
				allToChange.add(getGrid().get(p));
			}
		}
		
		empty  = createStateList(EMPTY , allToChange);
		fish   = createStateList(FISH  , allToChange);
		sharks = createStateList(SHARK , allToChange);

		if(currentState == FISH){
			if( (empty.isEmpty()) ){
				cell.setSatisfaction(SATISFIED);
			}else if( sharks.isEmpty()){
				cell.setSatisfaction(SATISFIED);
			}
			return FISH;
		}
		else if(currentState == SHARK){
			if( !(fish.isEmpty()) ){
				return process(cell, fish, SHARK, EMPTY);
			}else if( !(empty.isEmpty()) ){
				return process(cell, empty, SHARK, EMPTY);
			}
			cell.setSatisfaction(SATISFIED);
			return SHARK;
		}else{
			return EMPTY;
		}
		
	}

	
	private int process(Cell cell, List<Cell> typeList, int changeTo, int returnState) {
		Cell s = getRandomNeighbor(typeList);
		s.setNextState(changeTo);
		cell.setSatisfaction(SATISFIED);
		s.setSatisfaction(SATISFIED);
		return returnState;
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
	
	public void move(Map<Point2D, Cell> grd){
		for(Point2D pt: grd.keySet()){
			grd.get(pt).setSatisfaction(0);
		}
	}
	

	public Map<Integer,String> getColorMap(){
		Map<Integer,String> colorMap = new HashMap<Integer,String>();
		colorMap.put(0, "#ffffff");
		colorMap.put(1, "#add8e6");
		colorMap.put(2, "#551a8b");
		return colorMap;
	}
	
	@Override
	public String getType() {
		return "predator";
	}

}
