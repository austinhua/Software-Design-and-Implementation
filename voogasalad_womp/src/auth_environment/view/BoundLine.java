package auth_environment.view;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class BoundLine extends Line{
	// Source: http://stackoverflow.com/questions/19748744/javafx-how-to-connect-two-nodes-by-a-line
		public BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
			startXProperty().bind(startX);
			startYProperty().bind(startY);
			endXProperty().bind(endX);
			endYProperty().bind(endY);
			setStrokeWidth(20);
			setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
			setStrokeLineCap(StrokeLineCap.BUTT);
			getStrokeDashArray().setAll(10.0, 5.0);
		}
}
