package game_engine.physics;

import game_engine.properties.Position;

/**
 * CollisionChecker checks for collisions of units based on positions
 * 
 * @author Paul
 *
 */

public class CollisionChecker {

    public static boolean segmentOverlap (Position p, Position q, Position r) {
        return (q.getX() <= Math.max(p.getX(), r.getX()) &&
                q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) &&
                q.getY() >= Math.min(p.getY(), r.getY()));
    }

    public static boolean intersect (Position p1, Position q1, Position p2, Position q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);
        boolean special =
                (o1 == 0 && segmentOverlap(p1, p2, q1)) ||
                          (o2 == 0 && segmentOverlap(p1, q2, q1)) ||
                          (o3 == 0 && segmentOverlap(p2, p1, q2)) ||
                          (o4 == 0 && segmentOverlap(p2, q1, q2));
        return ((o1 != o2 && o3 != o4) || special);
    }

    public static int orientation (Position p, Position q, Position r) {
        double orient =
                (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                        (q.getX() - p.getX()) * (r.getY() - q.getY());
        return orient == 0 ? 0 : (orient > 0 ? 1 : 2);
    }

}