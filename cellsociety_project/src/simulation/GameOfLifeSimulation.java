package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;

public class GameOfLifeSimulation extends Simulation {
	
	private final int DEAD = 0;
	private final int LIVE = 1;
	
	public int  getNumStates() 					{return 2;}
	
	Grid gridObj;
	public Grid getGridObj()                   {return gridObj;}
	public void setGridObj(Grid g) 			   {gridObj = g   ;}

	Map<Point2D, Cell> grid;
	public Map<Point2D, Cell> getGrid()         {return grid;}
	public void setGrid(Map<Point2D, Cell> g)   {grid = g;}
	
	public GameOfLifeSimulation(Map<String, Double> configMap, Map<Point2D, Map<String, Double>> cellMap, String gt, String ct) {
		initializeSim(configMap, cellMap, gt, ct);
		setGrid(getGridObj().getGrid());
	}

	public int stateLogic(Cell cell, List<Point2D> checkedNeighbors) {
		int currentState = cell.getState();
		
		int liveCount = 0;
		
		for(Point2D agent:checkedNeighbors){
			if(getGrid().get(agent).getState() == LIVE){
				liveCount++;				
			}
		}
		//System.out.printf("Live Count: %d%n", liveCount);
		if(currentState==LIVE && ( (liveCount < 2)||(liveCount > 3) )){
			return DEAD;
		}
		else if(currentState==LIVE && ( (liveCount == 2)||(liveCount == 3) )){
			return LIVE;
		}
		else if(currentState == DEAD && (liveCount == 3)){
			return LIVE;
		}else{	
			return DEAD;
		}
	}
	
	public Map<Integer,String> getColorMap(){
		Map<Integer,String> colorMap = new HashMap<Integer,String>();
		colorMap.put(DEAD, "#000000");
		colorMap.put(LIVE, "#40ff00");
		return colorMap;
	}
	public void move(Map<Point2D, Cell> grid2){}
	
	@Override
	public String getType() {
		return "gameOfLife";
	}

}








