package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

public class FireSimulation extends Simulation {
	
	private final int EMPTY   = 0;
	private final int TREE    = 1;
	private final int BURNING = 2;
	@Override
	public int  getNumStates() 					{return 3;}

	private double probCatch;
	
	public FireSimulation(Map<String, Double> configMap, Map<Point2D, Map<String, Double>> cellMap, String gt, String ct) {
		initializeSim(configMap, cellMap, gt, ct);
		probCatch = configMap.get("probCatch");
		setGrid(getGridObj().getGrid());
	}

	@Override
	public int stateLogic(Cell cell, List<Point2D> checkedNeighbors) {
		int currentState = cell.getState();

		if(currentState == EMPTY){
			return EMPTY;
		}
		if(currentState == BURNING){
			return EMPTY;
		}
		if(currentState == TREE){
			for(Point2D agent:checkedNeighbors){
				if(getGrid().get(agent).getState() == BURNING){
					double random = Math.random();
					if(random < probCatch){
						return BURNING;
					}
					
				}
			}
		}
		return currentState;
	}
	
	public Map<Integer,String> getColorMap(){
		Map<Integer,String> colorMap = new HashMap<Integer,String>();
		colorMap.put(0, "#e6ac00");
		colorMap.put(TREE, "#29a329");
		colorMap.put(BURNING, "#ff0000");
		return colorMap;
	}
	public void move(Map<Point2D, Cell> grid2){}

	@Override
	public String getType() {
		return "fire";
	}


}
