package game_engine.affectors;

import java.util.List;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Unit;
import game_engine.properties.Movement;
import game_engine.properties.Position;

public class RandomPathFollowAffector extends PathFollowAffector{

	public RandomPathFollowAffector(AffectorData data) {
		super(data);
	}

	public Position getNextPosition(Unit u) {
		Movement move = u.getProperties().getMovement();
		Branch currentBranch = move.getCurrentBranch();
		if(currentBranch == null){
			return null;
		}
		Position next = move.getNextPosition();
		if(next == null){
			currentBranch = pickRandomBranch(u);
			if(currentBranch == null){
				return null;
			}
			u.getProperties().getMovement().setCurrentBranch(currentBranch);
			next = currentBranch.getFirstPosition();
		}
		return next;
	}

	private Branch pickRandomBranch(Unit u) {
		List<Branch> choices = getAllBranchChoices(u);
		if(choices.size() == 0)
			return null;
		int random = (int) (Math.random()*choices.size());
		return choices.get(random);
	}

}