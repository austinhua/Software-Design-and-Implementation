package game_engine.affectors;

import java.util.List;
import game_engine.functions.Function;
import game_engine.game_elements.Unit;
import game_engine.handlers.DirectionHandler;
import game_engine.properties.Position;
import game_engine.properties.Property;


public class PositionHomingAffector extends Affector {

    public PositionHomingAffector (AffectorData data) {
        super(data);
    }

    @Override
    public void apply (List<Function> functions, Property property, Unit u) {
        updatePositionAndMove(u, u.getProperties().getPosition(),
                              new Position(functions.get(0).evaluate(getElapsedTime()),
                                           functions.get(1).evaluate(getElapsedTime())));
    }

    public void updatePositionAndMove (Unit u, Position from, Position to) {
        double speed = u.getProperties().getVelocity().getSpeed();
        double dx = DirectionHandler.getdx(from, to);
        double dy = DirectionHandler.getdy(from, to);
        double num = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        u.getProperties().getVelocity().setDirection(DirectionHandler.getDirection(from, to));
        u.getProperties().getPosition().addToXY(speed * dx / num,
                                                speed * dy / num);
    }

}
