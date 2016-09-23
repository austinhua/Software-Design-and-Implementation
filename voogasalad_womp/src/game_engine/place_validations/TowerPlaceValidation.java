package game_engine.place_validations;

import game_engine.game_elements.Unit;
import game_engine.properties.Position;

/**
 * This class first checks for valid tower placement using an AIsearcher to ensure a valid search is available from AI units to goals after the tower is placed.
 * This is done using visibility graphs to filter out the obstacle from the search problem.
 * Then, if a valid search is available, it performs a simulated path following obstacle collision test to ensure the largest enemy wouldn't collide if the tower is placed.
 * 
 * This uses maximum bounding distance from the largest current active Unit to ensure no collision would happen.
 * 
 * 
 * @author adamtache
 *
 */

public class TowerPlaceValidation extends PlaceValidation {

    public boolean validate (Unit unit, double posX, double posY) {
        Unit copy = unit.copyShallowUnit();
        copy.getProperties().setPosition(new Position(posX, posY));
        return getEngine().getAIController().getAISimulator().simulateTowerPlacement(copy);
    }

}