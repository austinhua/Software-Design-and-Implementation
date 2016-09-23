package game_engine.affectors;

import game_engine.Operator;

/**
 * A Basic Increment Affector increments a UnitProperty value by a certain value.
 * 
 */

public class BasicIncrementAffector extends BasicAffector {

    public BasicIncrementAffector (AffectorData data) {
        super(data, Operator.SUM);
    }
}
