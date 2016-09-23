package game_engine.affectors;

import game_engine.game_elements.Unit;
import game_engine.handlers.DirectionHandler;
import game_engine.properties.Position;


public class HomingMoveAffector extends SingleTrackRangeAffector {
    
    private PositionHomingAffector positionHomingAffector;
    
    public HomingMoveAffector (AffectorData data) {
        super(data);
        positionHomingAffector = new PositionHomingAffector(data);
    }

    public void futureApply (Unit u, Unit tracked) {
        Position trackedPos = tracked.getProperties().getPosition();
        Position currPos = u.getProperties().getPosition();
        positionHomingAffector.updatePositionAndMove(u, currPos, trackedPos);
        if (!tracked.isVisible()) {
            u.setElapsedTimeToDeath();
        }
    }
    
    public void firstApply (Unit u, Unit tracked) {
        u.getParents().get(0).getProperties().getVelocity()
                .setDirection(DirectionHandler.getDirection(u.getProperties().getPosition(),
                                                            tracked.getProperties().getPosition()));
    }

}
