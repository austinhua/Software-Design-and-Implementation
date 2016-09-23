package game_engine.affectors;
import java.util.List;
import game_engine.functions.Function;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Unit;
import game_engine.properties.Position;
import game_engine.properties.Property;


public abstract class PathFollowAffector extends Affector {

    public PathFollowAffector (AffectorData data) {
        super(data);
    }

    public void apply (List<Function> functions, Property property, Unit u) {
        pathFollow(u);
    }

    public void pathFollow (Unit u) {
        if (this.getElapsedTime() <= this.getTTL()) {
            double speed = u.getProperties().getVelocity().getSpeed();
            for (int i = 0; i < speed; i++) {
                Position next = getNextPosition(u);
                if (next != null) {
                    u.getProperties().setPosition(next);
                    u.getProperties().getVelocity().setDirection(getNextDirection(u));
                }
            }
            this.updateElapsedTime();
        }
    }

    public abstract Position getNextPosition (Unit u);

    public Double getNextDirection (Unit u) {
        return u.getProperties().getMovement().getNextDirection();
    }

    public List<Branch> getAllBranchChoices (Unit u) {
        return u.getProperties().getMovement().getCurrentBranch().getNeighbors();
    }

}