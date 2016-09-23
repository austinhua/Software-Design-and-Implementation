package game_engine.affectors;

import java.util.List;
import game_engine.functions.Function;
import game_engine.game_elements.Unit;
import game_engine.physics.EncapsulationChecker;
import game_engine.properties.Position;
import game_engine.properties.Property;
import game_engine.properties.UnitProperties;


public abstract class SingleTrackRangeAffector extends Affector {

    private Unit trackedUnit;
    private boolean firstApplication;

    public SingleTrackRangeAffector (AffectorData data) {
        super(data);
        firstApplication = true;
    }

    public void apply (List<Function> functions, Property property, Unit u) {
        if (firstApplication) {
            trackedUnit = findTrackedUnit(u.getProperties());
            if (trackedUnit != null) {
                firstApply(u, trackedUnit);
                firstApplication = false;
            }
            else {
                u.setInvisible();
                u.incrementElapsedTime(1);
            }
        }
        else {
            futureApply(u, trackedUnit);
        }
    }

    public abstract void firstApply (Unit u, Unit tracked);

    public abstract void futureApply (Unit u, Unit tracked);

    public Unit findTrackedUnit (UnitProperties properties) {
        Unit closestEnemy = findClosestEnemy(properties.getPosition());
        if (closestEnemy == null) {
            return null;
        }
        return EncapsulationChecker.encapsulates(closestEnemy.getProperties().getBounds()
                .getUseableBounds(closestEnemy.getProperties().getPosition()),
                                                 properties.getRange()
                                                         .getUseableBounds(properties
                                                                 .getPosition())) ? closestEnemy
                                                                                  : null;
    }

    public Unit findClosestEnemy (Position myPos) {
        double closestDiff = Double.MAX_VALUE;
        Unit closestEnemy = null;
        for (int i = 0; i < getWorkspace().getUnitController().getUnitType("Enemy").size(); i++) {
            double currDiff;
            Unit currEnemy = getWorkspace().getUnitController().getUnitType("Enemy").get(i);
            if (currEnemy.isVisible()) {
                Position currPos = currEnemy.getProperties().getPosition();
                if ((currDiff =
                        Math.abs(myPos.getX() - currPos.getX()) +
                                Math.abs(myPos.getY() - currPos.getY())) < closestDiff &&
                    currEnemy.isVisible()) {
                    closestDiff = currDiff;
                    closestEnemy = currEnemy;
                }
            }
        }
        return closestEnemy;
    }
}
