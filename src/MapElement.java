import java.awt.Point;

/** Defines a generic element that appears in the game in one of the grids.
 * 
 * @author Austin Hua
 *
 */

public class MapElement {
	private Point position;
	MapElement[][] myGrid;
	
	public MapElement(int x, int y, MapElement[][] mapGrid) {
		position = new Point(x, y);
		myGrid = mapGrid;
	}
	
	public Point position() { return position; }
	public int x() { return position.x; }
	public int y() { return position.y; }
	
	public void setPosition(Point p) {
		position = p;
	}
	
	// Override in subclasses if a unit can go on top of it
	public boolean isFree() {
		return false;
	}
	
	public boolean isDead() {
		return false;
	}
	
	public void takeAction() { }
}
