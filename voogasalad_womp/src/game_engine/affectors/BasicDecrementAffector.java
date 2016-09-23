package game_engine.affectors;

import game_engine.Operator;

/**
 * A Basic Increment Decrement decrements a UnitProperty value by a certain value.
 * 
 */

public class BasicDecrementAffector extends BasicAffector {

    public BasicDecrementAffector (AffectorData data) {
        super(data, Operator.DIFFERENCE);
    }

}
