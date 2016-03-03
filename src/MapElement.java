import java.awt.Point;

/** 
 * The purpose of this class is to define a generic element that appears in the game in one of the grids.
 * 
 * @author Austin Hua
 *
 */

public class MapElement {
	private Point position;
	protected CellCraft myGame;
	protected MapElement[][] myGrid;
	
	public MapElement(int x, int y, MapElement[][] mapGrid, CellCraft game) {
		position = new Point(x, y);
		myGrid = mapGrid;
		myGame = game;
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
