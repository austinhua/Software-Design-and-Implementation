package game_engine.properties;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Position is a property that represents the position of a unit on the map.
 * Top left is 0,0 and x,y increases going right and down respectively.
 * 
 *
 */

public class Position extends Property implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private double myX;
	private double myY;

	public Position (double x, double y) {
		this.myX = x;
		this.myY = y;
	}
	
	public double getX () {
		return myX;
	}

	public double getY () {
		return myY;
	}

	public void setX (double x) {
		this.myX = x;
	}

	public void setY (double y) {
		this.myY = y;
	}

	public void addToX (double x) {
		this.myX += x;
	}

	public void addToY (double y) {
		this.myY += y;
	}

	public void addToXY (double x, double y) {
		addToX(x);
		addToY(y);
	}

	public Position copyPosition () {
		return new Position(this.getX(), this.getY());
	}

	public double distanceTo (Position other) {
		double dx = this.myX - other.myX;
		double dy = this.myY - other.myY;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public boolean roughlyEquals(Position o) {
	    return (o instanceof Position &&
                    (Math.abs(((Position) o).myX - this.myX)) < 5 &&
                    (Math.abs(((Position) o).myY - this.myY)) < 5) ||
                    (this == o);
	}

	@Override
	public boolean equals (Object o) {
		return (o instanceof Position &&
				(Math.abs(((Position) o).myX - this.myX)) < 0.0000001 &&
				(Math.abs(((Position) o).myY - this.myY)) < 0.0000001) ||
				(this == o);
	}

	@Override
	public String toString () {
		return "("+Double.toString(this.myX) + ", " + Double.toString(this.myY)+")";
	}

	@Override
	public void setValues(List<Double> values) {
		this.myX = values.get(0);
		this.myY = values.get(1);
	}

	@Override
	public List<Double> getValues () {
		return Arrays.asList(myX, myY);
	}

	public Position getPosition () {
		return copyPosition();
	}

	@Override
	public int hashCode(){
		return 15;
	}

	@Override
	public boolean isBaseProperty() {
		return false;
	}

}