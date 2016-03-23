package simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

public class SlimeMoldSimulation extends Simulation {

	private final int EMPTY = 0 ;
	private final int cAMP = 1;
	private final int SLIME = 2;
	
	private final int fullcAMP = 3;
	private final int SATISFIED = 1;

	public int getNumStates() 					{return 3;}

	Grid gridObj;
	public Grid getGridObj()                   {return gridObj;}
	public void setGridObj(Grid g) 			   {gridObj = g   ;}

	Map<Point2D, Cell> grid;
	public Map<Point2D, Cell> getGrid()         {return grid;}
	public void setGrid(Map<Point2D, Cell> g)   {grid = g;}
	
	
	public SlimeMoldSimulation(Map<String, Double> configMap, Map<Point2D, Map<String, Double>> cellMap, String gt, String ct) {
		initializeSim(configMap, cellMap, gt, ct);
		setGrid(getGridObj().getGrid());
	}

	@Override
	public int stateLogic(Cell cell, List<Point2D> arrayList) {
		int currentState = cell.getState();
		List<Cell> allToChange = new ArrayList<Cell>(); 
		for(Point2D p:arrayList){
			if(getGrid().get(p).getSatisfaction() != SATISFIED){
				allToChange.add(getGrid().get(p));
			}
		}
		List<Cell> empty = createStateList(EMPTY, allToChange);
		List<Cell> cAmp = createStateList(cAMP, allToChange);
		List<Cell> slime = createStateList(SLIME, allToChange);
		
		if(currentState == cAMP){
			if(cell.getProperty("cAMPAmount")==0.0){
				return EMPTY;
			}
			for(Cell c : allToChange){
				if( !slime.contains(c)){
					c.setProperty("cAMPAmount",c.getProperty("cAMPAmount")+1);
				}
			}
			cell.setProperty("cAMPAmount", (cell.getProperty("cAMPAmount")-1));
			return cAMP;
		}
		else if(currentState == SLIME){
			if(!cAmp.isEmpty()){
				Cell c = getRandomNeighbor(getMaxVals(cAmp));
				c.setNextState(SLIME);
				cell.setNextState(cAMP);
				cell.setProperty("cAMPAmount", cell.getProperty("fullcAMPCapacity"));
				cell.setSatisfaction(SATISFIED);
				c.setSatisfaction(SATISFIED);
				return cAMP;
			}else if(!empty.isEmpty()){
				Cell e = getRandomNeighbor(empty);
				e.setNextState(SLIME);
				cell.setNextState(cAMP);
				cell.setProperty("cAMPAmount", cell.getProperty("fullcAMPCapacity"));
				cell.setSatisfaction(SATISFIED);
				e.setSatisfaction(SATISFIED);
				return cAMP;
				
			}
			cell.setSatisfaction(SATISFIED);
			return SLIME;
		}
		
		return EMPTY;
	}
	
	public List<Cell> getMaxVals(List<Cell> list){
		int maxVal = 0;
		List<Cell> maxCellVals  = new ArrayList<Cell>();
		for(Cell c:list){
			if(c.getProperty("cAMPAmount")>=maxVal){
				maxVal = c.getProperty("cAMPAmount").intValue();
			}
		}
		for(Cell c:list){
			if(c.getProperty("cAMPAmount")>=maxVal){
				maxCellVals.add(c);
			}
		}
		return maxCellVals;
	}
	public void move(Map<Point2D, Cell> grid2){};
		
	@Override
	public String getType() {
		return "slimeMold";
	}
}
