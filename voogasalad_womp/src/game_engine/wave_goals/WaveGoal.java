package game_engine.wave_goals;

import game_engine.GameEngineInterface;
import game_engine.game_elements.Level;
import game_engine.game_elements.Unit;
import game_engine.properties.Position;


public abstract class WaveGoal {

    public WaveGoal () {
    };

    public abstract boolean reachedGoal (GameEngineInterface engineWorkspace);


}
