package game_engine.score_updates;

import java.util.stream.Collectors;
import game_engine.game_elements.Level;
import game_engine.GameEngineInterface;


public class EnemyWinScoreUpdate extends ScoreUpdate {

    @Override
    public void updateScore (GameEngineInterface engineWorkspace, Level currentLevel) {
        updateScoreFromUnitList(engineWorkspace.getUnitController().getUnitType("Enemy")
                .stream().filter(e -> currentLevel.isEnemyAtGoal(e)).collect(Collectors.toList()),
                                engineWorkspace);
        updateLivesFromUnitList(engineWorkspace.getUnitController().getUnitType("Enemy")
                .stream().filter(e -> !e.isVisible() && !currentLevel.isEnemyAtGoal(e))
                .collect(Collectors.toList()), engineWorkspace);
        removeAllInvisibleEnemies(engineWorkspace, currentLevel);
    }
}
