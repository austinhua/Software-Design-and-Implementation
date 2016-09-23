package game_engine.affectors;

import java.util.List;
import game_engine.functions.Function;
import game_engine.game_elements.Unit;
import game_engine.handlers.DirectionHandler;
import game_engine.properties.Position;
import game_engine.properties.Property;
import game_engine.properties.UnitProperties;


public class CursorDirectionAffector extends Affector {

    private boolean firstApplication;
    private PositionMoveAffector positionMoveAffector;
    private PositionHomingAffector positionHomingAffector;

    public CursorDirectionAffector (AffectorData data) {
        super(data);
        firstApplication = true;
        positionMoveAffector = new PositionMoveAffector(data);
        positionHomingAffector = new PositionHomingAffector(data);
    }

    @Override
    public void apply (List<Function> functions, Property property, Unit u) {
        UnitProperties properties = u.getProperties();
        if (firstApplication) {
            Position mouse = getWorkspace().getCursorPosition();
            Position currPos = properties.getPosition();
            positionHomingAffector.updatePositionAndMove(u, currPos, mouse);
            u.getParents().get(0).getProperties().getVelocity()
                    .setDirection(DirectionHandler.getDirection(currPos, mouse));
            firstApplication = false;
        }
        else {
            positionMoveAffector.apply(functions, property, u);
        }
    }
}
