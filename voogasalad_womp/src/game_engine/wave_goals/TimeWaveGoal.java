package game_engine.wave_goals;

import game_engine.GameEngineInterface;


public class TimeWaveGoal extends WaveGoal {

    @Override
    public boolean reachedGoal (GameEngineInterface engineWorkspace) {
        return engineWorkspace.getLevelController().getCurrentLevel().getNextWave()
                .getTimeBeforeWave() <= engineWorkspace.getNextWaveTimer();
    }

}
