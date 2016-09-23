package game_engine.affectors;

import java.util.List;
import java.util.stream.Collectors;
import game_engine.functions.Function;
import game_engine.game_elements.Unit;
import game_engine.properties.Property;


public class FiringChildrenAffector extends Affector {

    public FiringChildrenAffector (AffectorData data) {
        super(data);
    }

    @Override
    public void apply (List<Function> functions, Property property, Unit u) {
        if (getElapsedTime() % functions.get(0).evaluate(getElapsedTime()) == 0) {
            List<Unit> newChildren =
                    u.getChildren().stream().map(p -> p.copyUnit()).collect(Collectors.toList());
            newChildren.forEach(p -> {
                p.getProperties().setPosition(u.getProperties().getPosition().getX(),
                                              u.getProperties().getPosition().getY());
                getWorkspace().getUnitController().getPlacedUnits().add(p);
                p.addParent(u);
            });
        }

    }

}
