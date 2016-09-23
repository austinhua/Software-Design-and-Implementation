package game_engine.handlers;

import game_engine.properties.Position;

/**
 * This class handles directions between positions with the correct orientation.
 * 
 * @author andy
 *
 */

public class DirectionHandler {
    
    public static double getDirection(Position from, Position to) {
    	double dx = getdx(from, to);
        double newDir = getActualDirection(from, to);
        return dx < 0 ? 270 - Math.toDegrees(newDir) : 90 - Math.toDegrees(newDir);
    }
    
    public static double getActualDirection(Position from, Position to){
    	return Math.atan((getdy(from, to)) / getdx(from, to));
    }
    
    public static double getdx(Position from, Position to) {
        return to.getX() - from.getX();
    }
    
    public static double getdy(Position from, Position to) {
        return to.getY() - from.getY();
    }

}
