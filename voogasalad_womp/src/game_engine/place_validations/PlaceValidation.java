package game_engine.place_validations;

import game_engine.GameEngineInterface;
import game_engine.game_elements.Unit;

/**
 * This is an abstract class allowing for place validation checks of any kind.
 * 
 * @Andy
 *
 */

public abstract class PlaceValidation {

    private GameEngineInterface myEngine;

    public abstract boolean validate (Unit unit, double posX, double posY);

    public GameEngineInterface getEngine () {
        return myEngine;
    }
    
    public void setEngine(GameEngineInterface myEngine) {
        this.myEngine = myEngine;
    }

}