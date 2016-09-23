package game_player.utilities;

import java.util.List;
import game_engine.properties.Position;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class PolygonUtility {
    
    public static Shape generateUnion (List<Polygon> polygons) {
        Shape first = polygons.get(0);
        if (polygons.size() == 1) {
            return first;
        }
        for (int i = 1; i < polygons.size(); i++) {
            first = Shape.union(first, polygons.get(i));
        }
        return first;
    }

    public static Shape subtractBounds (Shape first, List<Polygon> bounds) {
        for (int i = 0; i < bounds.size(); i++) {
            first = Shape.subtract(first, bounds.get(i));
        }
        return first;
    }

    public static Polygon fillPolygonWithPoints (Polygon polygon, List<Position> points) {
        for (Position p : points) {
            polygon.getPoints().addAll(new Double[] { p.getX(), p.getY() });
        }
        return polygon;
    }

}
