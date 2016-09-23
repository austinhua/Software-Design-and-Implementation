package auth_environment.view;

import game_engine.properties.Position;
import javafx.scene.shape.Circle;

public class PathPoint {
	
	private Position myPosition;
	private Circle myCircle; 
	
	public PathPoint(Position pos, double radius) {
		this.myPosition = pos;
		this.myCircle = new Circle(radius);
		this.relocate(radius);
	}
	
	private void relocate(double shift) {
		this.myCircle.relocate(myPosition.getX() - shift, myPosition.getY() - shift);
	}
	
	public Circle getCircle() {
		return this.myCircle;
	}
	
	public Position getPosition() {
		return this.myPosition;
	}

}
