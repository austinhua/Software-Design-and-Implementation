package game_engine.physics;

import java.util.List;
import java.util.stream.Collectors;
import game_engine.GameEngineInterface;
import game_engine.affectors.Affector;
import game_engine.game_elements.Unit;
import game_engine.interfaces.IEncapsulationDetector;
import game_engine.properties.Bounds;

/**
 * This class detects encapsulations based on useable bounds, which are shifted bounds of a Unit based on its current position.
 * 
 * @author adamtache
 *
 */

public class EncapsulationDetector implements IEncapsulationDetector{

    private GameEngineInterface myEngine;

    public EncapsulationDetector (GameEngineInterface engine) {
        myEngine = engine;
    }

    public void resolveEncapsulations (List<Unit> terrains) {
        myEngine.getUnitController().getUnitType("Enemy").forEach(t -> terrainHandling(t, terrains));
        myEngine.getUnitController().getUnitType("Tower").forEach(t -> terrainHandling(t, terrains));
    }

    public boolean encapsulates (Unit inner, Unit outer) {
        return EncapsulationChecker.encapsulates(inner.getProperties().getBounds()
                .getUseableBounds(inner.getProperties().getPosition()),
                                                 outer.getProperties().getBounds()
                                                         .getUseableBounds(outer.getProperties()
                                                                 .getPosition()));
    }

    public boolean encapsulates (Unit inner, Bounds range) {
        return EncapsulationChecker.encapsulates(inner.getProperties().getBounds()
                .getUseableBounds(inner.getProperties().getPosition()),
                                                 range.getPositions());
    }

    private void terrainHandling (Unit unit, List<Unit> terrains) {
        for (int i = 0; i < terrains.size(); i++) {
            if ((!(unit == terrains.get(i)) && encapsulates(unit, terrains.get(i)))) {
                if (unit.isVisible()) {
                    List<Affector> newAffectorsToApply =
                            terrains.get(i).getAffectorsToApply().stream()
                                    .map(a -> a.copyAffector()).collect(Collectors.toList());
                    unit.addAffectors(newAffectorsToApply);
                }
            }
        }
    }

}