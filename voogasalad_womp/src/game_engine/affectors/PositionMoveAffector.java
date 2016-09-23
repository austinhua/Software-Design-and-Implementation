package game_engine.affectors;

import java.util.List;
import game_engine.functions.Function;
import game_engine.game_elements.Unit;
import game_engine.properties.Property;

public class PositionMoveAffector extends Affector{
    
    public PositionMoveAffector(AffectorData data){
        super(data);
    }

    @Override
    public void apply (List<Function> functions, Property property, Unit u) {
        double speed = u.getProperties().getVelocity().getSpeed();
        double direction = u.getProperties().getVelocity().getDirection();
        double xDir = Math.sin(Math.toRadians(direction));
        double yDir = Math.cos(Math.toRadians(direction));
        u.getProperties().getPosition().addToXY(speed*xDir, speed*yDir);
    }
}
