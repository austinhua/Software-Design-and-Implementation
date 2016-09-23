package game_engine.properties;

import java.util.List;

/**
 * Range is a unit property that sets the outer Bounds (list of positions) of where the unit can shoot.
 * 
 *
 */

public class Range extends Bounds{

    public Range (List<Position> positions) {
        super(positions);
    }

}