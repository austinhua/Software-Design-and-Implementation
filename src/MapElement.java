import java.awt.Point;

public class MapElement {
	private Point position;
	
	public MapElement(int x, int y) {
		position = new Point(x, y);
	}
	
	public Point position() { return position; }
	public int X() { return position.x; }
	public int Y() { return position.y; }
}
