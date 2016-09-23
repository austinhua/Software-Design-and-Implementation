package game_engine.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import game_engine.game_elements.Branch;
import game_engine.handlers.DirectionHandler;

/**
 * Movement is a unit property that holds information on how a unit moves.
 * This includs its current position, branch, and the branches it moves on, as well as the position it's currently moving towards on its current branch.
 * 
 * @author adamtache
 *
 */

public class Movement {

	private List<Branch> myBranches;
	private Branch myCurrentBranch;
	private Position movingTowards;
	private Position myPosition;

	public Movement(List<Branch> branches, Position currentPosition){
		this.myBranches = branches;
		this.myPosition = currentPosition;
		if(branches.size() > 0){
			myCurrentBranch = myBranches.get(0);
			if(myCurrentBranch.getPositions().size() > 0){
				this.initializeMovingTowards();
			}
		}
	}

	public Movement(Position spawn){
		this.myBranches = new ArrayList<>();
		this.myPosition = spawn;
	}

	public List<Branch> getBranches(){
		return myBranches;
	}

	public void setBranches(List<Branch> branches){
		this.myBranches = branches;
		if(branches != null && branches.size() != 0){
			myCurrentBranch = branches.get(0);
			initializeMovingTowards();
		}
	}

	public Movement copyMovement(){
		Movement newMovement = new Movement(myBranches.stream().map(b -> b.copyBranch()).collect(Collectors.toList()), myPosition);
		newMovement.setMovingTowards(movingTowards);
		newMovement.setCurrentBranch(myCurrentBranch);
		return newMovement;
	}

	public Branch getCurrentBranch(){
		return myCurrentBranch;
	}

	public Branch getLastBranch(){
		if(myBranches.size() == 0)
			return null;
		return myBranches.get(myBranches.size()-1);
	}

	public Branch getNextBranch() {
		if(getLastBranch().equals(myCurrentBranch)){
			myCurrentBranch = null;
			return null;
		}
		int curentBranchIndex = myBranches.indexOf(myCurrentBranch);
		myCurrentBranch = myBranches.get(curentBranchIndex + 1);
		return myCurrentBranch;
	}

	public void setCurrentBranch(Branch branch) {
		myCurrentBranch = branch;
		initializeMovingTowards();
	}

	public Double getNextDirection () {
		Position nextPosition = getNextPosition();
		if(nextPosition == null){
			nextPosition = myPosition;
		}
		return DirectionHandler.getDirection(myPosition, nextPosition);
	}

	public Position getNextPosition(){
		Position next = myCurrentBranch.getNextPosition(myPosition, movingTowards);
		if(next == null){
			if(myBranches.get(myBranches.size()-1).equals(myCurrentBranch)){
				return null;
			}
			Branch nextBranch = myBranches.get(1 + myBranches.indexOf(myCurrentBranch));
			setCurrentBranch(nextBranch);
		}
		return next;
	}

	public void initializeMovingTowards(){
		if(myPosition != null && myCurrentBranch != null){
			if(myPosition.equals((myCurrentBranch.getFirstPosition()))){
				movingTowards = myCurrentBranch.getLastPosition();
			}
			else if(myPosition.equals(myCurrentBranch.getLastPosition())){
				movingTowards = myCurrentBranch.getFirstPosition();
			}
		}
	}

	public void setPosition(Position position){
		this.myPosition = position;
		initializeMovingTowards();
	}

	public void setPosition(double x, double y){
		this.myPosition = new Position(x, y);
		initializeMovingTowards();
	}

	public Position getPosition(){
		return myPosition;
	}

	public Position getMovingTowards(){
		return this.movingTowards;
	}

	public void setMovingTowards(Position pos){
		this.movingTowards = pos;
	}

}
