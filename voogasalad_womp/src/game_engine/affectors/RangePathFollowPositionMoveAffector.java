package game_engine.affectors;

import game_engine.game_elements.Unit;

public class RangePathFollowPositionMoveAffector extends SingleTrackRangeAffector {

    PathFollowPositionMoveAffector affector;
    
    public RangePathFollowPositionMoveAffector (AffectorData data) {
        super(data);
        affector = new PathFollowPositionMoveAffector(data);
    }

    @Override
    public void firstApply (Unit u, Unit tracked) {
        affector.pathFollow(u); 
    }

    @Override
    public void futureApply (Unit u, Unit tracked) {
        firstApply(u, tracked);
    }

}
