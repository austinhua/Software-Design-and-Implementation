package game_engine.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import game_engine.properties.Position;

/**
 * This class handles unit bounds for tower placement and allows for extended bounds and max bounding distance access.
 * 
 * @author adamtache
 *
 */

public class BoundsHandler {
	
	/*
	 * Takes in a list of positions and returns the maximum bounding distance.
	 * The max bounding distance is the maximum distance between any two positions in a list of positions.
	 * 
	 * @param	positions is list of positions
	 * @return	the list of extended bounds positions
	 */
	public static double getMaxBoundingDistance(List<Position> positions){
		return getMaxDistanceBetween(positions, getBoundsCenter(positions));
	}

	/*
	 * Takes in a list of bounds and extension amount. Extends all positions based on extension in correct direction.
	 * 
	 * @param	bounds is list of positions to be extended
	 * @param	extension is distance for extension
	 * @return	the list of extended bounds positions
	 */
	public static List<Position> getExtendedBounds(List<Position> bounds, double extension){
		List<Position> copyPos = bounds.stream().map(p -> p.copyPosition()).collect(Collectors.toList());
		List<Position> extendedPos = new ArrayList<>();
		Position center = getBoundsCenter(bounds);
		for(Position pos : copyPos){
			double dx = DirectionHandler.getdx(center, pos);
			double dy = DirectionHandler.getdy(center, pos);
			extendedPos.add(getExtendedPos(pos, extension, dx, dy));
		}
		return extendedPos;
	}
	
	private static Position getExtendedPos(Position pos, double extension, double dx, double dy){
		return new Position(pos.getX() + extension * Math.signum(dx), pos.getY() + extension * Math.signum(dy));
	}

	public static Position getBoundsCenter(List<Position> positions){
		double minX = getMinX(positions);
		double maxX = getMaxX(positions);
		double minY = getMinY(positions);
		double maxY = getMaxY(positions);
		double centerX = (minX + maxX)/2;
		double centerY = (minY + maxY)/2;
		return new Position(centerX, centerY);
	}

	private static double getMaxDistanceBetween(List<Position> positions, Position center){
		double maxDistance = Integer.MIN_VALUE;
		for(Position pos : positions){
			double distance = getEuclideanDistance(center.getX(), center.getY(), pos);
			if(distance > maxDistance){
				maxDistance = distance;
			}
		}
		return maxDistance;
	}

	private static double getEuclideanDistance(double x, double y, Position pos){
		return Math.sqrt(Math.pow(getDx(pos, x), 2) + Math.pow(getDy(pos, y), 2));
	}

	private static double getDx(Position pos, double x){
		return Math.abs(pos.getX() - x);
	}

	private static double getDy(Position pos, double y){
		return Math.abs(pos.getY() - y);
	}

	private static double getMinX(List<Position> positions){
		double minX = Integer.MAX_VALUE;
		for(Position pos : positions){
			double currX = pos.getX();
			if(currX < minX){
				minX = currX;
			}
		}
		return minX;
	}

	private static double getMaxX(List<Position> positions){
		double maxX = Integer.MIN_VALUE;
		for(Position pos : positions){
			double currX = pos.getX();
			if(currX > maxX){
				maxX = currX;
			}
		}
		return maxX;
	}

	private static double getMinY(List<Position> positions){
		double minY = Integer.MAX_VALUE;
		for(Position pos : positions){
			double currY = pos.getY();
			if(currY < minY){
				minY = currY;
			}
		}
		return minY;
	}

	private static double getMaxY(List<Position> positions){
		double maxY = Integer.MIN_VALUE;
		for(Position pos : positions){
			double currY = pos.getY();
			if(currY > maxY){
				maxY = currY;
			}
		}
		return maxY;
	}

}