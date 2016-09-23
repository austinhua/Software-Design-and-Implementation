package game_engine.wave_goals;

import game_engine.GameEngineInterface;


public class EnemyNumberWaveGoal extends WaveGoal {

    @Override
    public boolean reachedGoal (GameEngineInterface engineWorkspace) {
        return engineWorkspace.getLevelController().getCurrentLevel().getCurrentWave().getSpawningUnits().stream()
                .filter(e -> e.isVisible()).count() == 0;
    }

}
