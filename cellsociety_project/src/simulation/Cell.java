package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

public abstract class Cell{
	/**
	 * Objects of this class represent actors in the simulations
	 * Cells in the grid have a position, next state, and current state
	 */
	public static final String SUGAR = "sugar";
	
	
	private int X;
	private int Y;
	private int State;
	private int nextState ;
	private int satisfaction;

	private Map<String, Double> properties;
	
	public Map<String, Double> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Double> props) {
		properties = props;
	}
	public Double getProperty(String propertyName) {
		return properties.get(propertyName) == null? Math.random() : properties.get(propertyName);
	}
	public void setProperty(String propertyName, Double value) {
		properties.put(propertyName, value);
	}
	
	public void setX(int i)                   	{ X = i; }
	public void setY(int j)                   	{ Y = j; }
	public int getX() 							{ return X; }
	public int getY() 							{ return Y; }
	public void setState(int r) 				{ State = r; }
	public int getState() 						{ return State; }
	public void setNextState(int ns) 			{ nextState = ns; }
	public int getNextState() 					{ return nextState; }
	public int getSatisfaction() 				{ return satisfaction;}
	public void setSatisfaction(int e) 			{ satisfaction = e;}
	
	
	public List<Point2D> getNeighbors(int x, int y, int vision) {
		List<Point2D> neighbors = new ArrayList<Point2D>();
		List<Integer> points = new ArrayList<Integer>();
		for(int i = 0; i<=vision; i++){
			if(i==0){
				points.add(0);
			}else{
				points.add(i);
				points.add(-i);
			}
		}
		List<Integer> allPoints = new ArrayList<Integer>();
		for(int p : points){
			for(int q: points){
				if(p == 0 && q == 0) continue;
				allPoints.add(p);
				allPoints.add(q);
			}
		}
		for(int i = 0 ; i < (allPoints.size() - 1); i+=2){
			Point2D pnt = new Point2D(allPoints.get(i)+x,allPoints.get(i+1)+y);
			neighbors.add(pnt);
		}
		return neighbors; //these are all possible neighbors...they are unchecked for position yet
	}
	
}