package game_engine.physics;

import java.util.List;
import java.util.stream.Collectors;
import game_engine.GameEngineInterface;
import game_engine.affectors.Affector;
import game_engine.game_elements.Unit;
import game_engine.interfaces.ICollisionDetector;
import game_engine.properties.Position;

/**
 * CollisionDetector controls unit collisions based on bounds and positions.
 * 
 * @author Paul
 *
 */

public class CollisionDetector implements ICollisionDetector{

    private GameEngineInterface myEngine;

    public CollisionDetector (GameEngineInterface engine) {
        myEngine = engine;
    }

    public void resolveEnemyCollisions (List<Unit> myProjectiles) {
        myEngine.getUnitController().getUnitType("Enemy").forEach(t -> updateEnemies(t, myProjectiles));
    }

    private void updateEnemies (Unit unit, List<Unit> myProjectiles) {
        for (int i = 0; i < myProjectiles.size(); i++) {
            if (!(unit == myProjectiles.get(i)) &&
                collides(unit.getProperties().getBounds()
                        .getUseableBounds(unit.getProperties().getPosition()),
                         myProjectiles.get(i))) {
                if (!myProjectiles.get(i).hasCollided() && unit.isVisible()) {
                    List<Affector> affectorsToApply = myProjectiles.get(i).getAffectorsToApply()
                            .stream().map(a -> a.copyAffector()).collect(Collectors.toList());
                    unit.addAffectors(affectorsToApply);
                    myProjectiles.get(i).setHasCollided(true);
                    myProjectiles.get(i).setElapsedTimeToDeath();
                }
            }
        }
    }

    private boolean collides (List<Position> bounds, Unit b) {
        List<Position> bPos =
                b.getProperties().getBounds().getUseableBounds(b.getProperties().getPosition());
        for (int i = 0; i < bounds.size(); i++) {
            for (int j = 0; j < bPos.size(); j++) {
                if (CollisionChecker.intersect(bounds.get(i), bounds.get((i + 1) % bounds.size()),
                                               bPos.get(j),
                                               bPos.get((j + 1) % bPos.size()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean simulatedObstacleCollisionCheck (List<Position> enemyBounds,
                                                    List<Unit> obstacles) {
        for (Unit obstacle : obstacles) {
            if (collides(enemyBounds, obstacle)) {
                return true;
            }
        }
        return false;
    }

}