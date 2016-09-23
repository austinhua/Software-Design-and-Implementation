package game_engine.affectors;

import game_engine.Operator;

/**
 * A Basic Set Affector sets a UnitProperty value equal to a certain value.
 * 
 */

public class BasicSetAffector extends BasicAffector {

    public BasicSetAffector (AffectorData data) {
        super(data, Operator.EQUALS);
    }

}