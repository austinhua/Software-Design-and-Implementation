package game_engine.affectors;

import game_engine.game_elements.Branch;
import game_engine.game_elements.Unit;
import game_engine.properties.Movement;
import game_engine.properties.Position;


/*
 * This class modifies units based on their set speed, as well as the path that they are supposed to
 * follow.
 * This is done by calling the apply method repeatedly based on their speed, and changing the
 * position of the unit
 * based on a sampled version of the path that has been drawn out for them.
 * 
 */

// TODO: Discuss whether this class is necessary. If so, update range path follow position as well.

public class PathFollowPositionMoveAffector extends PathFollowAffector {

    public PathFollowPositionMoveAffector (AffectorData data) {
        super(data);
    }

    @Override
    public void apply (Unit u) {
        super.apply(u);
    }

    public Position getNextPosition (Unit u) {
        Movement move = u.getProperties().getMovement();
        Branch currentBranch = move.getCurrentBranch();
        if (currentBranch == null) {
            return null;
        }
        Position next = move.getNextPosition();
        return next == null ? null : next;
    }

}