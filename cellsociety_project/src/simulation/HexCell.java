package simulation;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

public class HexCell extends Cell {
	
	public List<Point2D> getNeighbors(int x,int y) {
		List<Point2D> neighbors = new ArrayList<Point2D>();
		int[] points;
		//i is the col number 
		if(y%2 ==0 ){ //if the col number is even
			points = new int[]{
					0,-1,
					0, 1,
					1, 0,
				   -1, 0,
				   -1,-1, 
				    1,-1,
					};
		}else{
			points = new int[]{
					0,-1,
					0, 1,
					1, 0,
				   -1, 0, 
				    1, 1,
				   -1, 1, 
					};
		}
		for(int i = 0 ; i < (points.length - 1); i+=2){
			Point2D pnt = new Point2D(points[i]+x,points[(i+1)]+y);
			neighbors.add(pnt);
		}
		return neighbors; //these are all possible neighbors...they are unchecked for position yet
	}

}
