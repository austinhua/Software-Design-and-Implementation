package game_engine.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import game_engine.properties.Position;

/**
 * This class controls interpolation of a list of positions by filling in too large of gaps between positions.
 * Too large of a gap is more than one "position unit" away. 
 * 
 * @author paul
 *
 */

public class PositionHandler {
	
	/*
	 * Takes in a list of positions, generates the interpolated points, and adds them to a new list
	 * 
	 * @param	positions is the list of positions to be interpolated
	 * @param	cycle determines whether or not the list of positions should eventually loop back around
	 * @return	the list of interpolated positions
	 */
	public List<Position> getInterpolatedPositions(List<Position> positions, boolean cycle){
		List<Position> newList = new ArrayList<>();
		HashMap<Position, Position> nextPositions = fillPositionGaps(positions, cycle);
		if(!positions.isEmpty()){
			Position start = positions.get(0);
			newList.add(new Position(start.getX(), start.getY()));
			Position curr = nextPositions.get(start);
			while(curr != null && !curr.equals(start) && !curr.equals(positions.get(0))){
				newList.add(new Position(curr.getX(), curr.getY()));
				curr = nextPositions.get(curr);
			}
		}
		return newList;
	}
	
	/*
	 * Takes in a list of positions, fills in between any two points more than 1 "unit" apart with more interpolated positions
	 * 
	 * @param	positions is the list of positions to be interpolated
	 * @param	cycle determines whether or not the list of positions should eventually loop back around
	 * @return	the list of interpolated positions
	 */
	private HashMap<Position, Position> fillPositionGaps(List<Position> positions, boolean cycle){
		HashMap<Position, Position> nextPositions = new HashMap<>();
		int size = cycle ? positions.size() : positions.size() - 1;
		if(positions.size() < 1){
			return nextPositions;
		}
		double x = positions.get(0).getX();	//X and Y stay as the coordinates of the first point
		double y = positions.get(0).getY();
		for(int i = 0;i < size;i++){
			Position p1 = positions.get(i);
			Position p2 = positions.get((i+1)%positions.size());
			double vx = p2.getX() - p1.getX();
			double vy = p2.getY() - p1.getY();
			double mag = Math.sqrt(vx*vx + vy*vy);
			vx /= mag;	//Get unit vector
			vy /= mag;
			//Loops through until the distance between p1 and p2 has been filled in by interpolated points 1 unit apart
			while((vx == 0 || (p2.getX() - x)/vx > 0 ) && (vy == 0 || (p2.getY() - y)/vy > 0) ){
				Position newPosition = new Position(x + vx, y + vy);
				nextPositions.put(new Position(x, y), newPosition);
				x += vx;
				y += vy;
			}
			nextPositions.put(new Position(x - vx, y - vy), p2);
			x = p2.getX();
			y = p2.getY();
		}
		return nextPositions;
	}
	
}