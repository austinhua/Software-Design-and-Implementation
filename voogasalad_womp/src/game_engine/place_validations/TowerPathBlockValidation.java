package game_engine.place_validations;

import java.util.Arrays;
import java.util.List;

import exceptions.ExceptionDialog;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Unit;
import game_engine.properties.Position;

/**
 * This class ensures towers do not block paths that Path following (non-AI) Units are to follow.
 * 
 * @author adamtache
 *
 */

public class TowerPathBlockValidation extends PlaceValidation {

	@Override
	public boolean validate(Unit unit, double posX, double posY) {
		Unit copy = unit.copyShallowUnit();
		copy.getProperties().setPosition(new Position(posX, posY));
		return blocksPath(unit);
	}

	private boolean blocksPath(Unit tower){
		List<Unit> pathFollowUnits = null;
		try {
			pathFollowUnits = getEngine().getLevelController().getActiveUnitsWithAffector(Class.forName("PathFollowPositionMoveAffector"));
		} catch (ClassNotFoundException e) {
			new ExceptionDialog("PathFollowPositionMoveAffector not found").displayMessage();
		}
		for(Unit pathFollower : pathFollowUnits){
			for (Branch b : pathFollower.getProperties().getMovement().getBranches()) {
				for (Position pos : b.getPositions()) {
					List<Position> enemyBounds =
							pathFollower.getProperties().getBounds().getUseableBounds(pos);
					if (getEngine().getCollisionDetector().simulatedObstacleCollisionCheck(enemyBounds, Arrays.asList(pathFollower))) {
						return true;
					}
				}
			}
		}
		return false;
	}

}