package game_engine.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Unit;
import game_engine.interfaces.AIWorkspace;
import game_engine.physics.EncapsulationChecker;
import game_engine.properties.Position;


/**
 * This class is a visibility handler that allows access to visibility graphs and visibility
 * utilities of valid search problems.
 * A visibility graph is represented by branches valid after the consideration of obstacles.
 * 
 * @author adamtache
 *
 */

public class VisibilityHandler {

	private AIWorkspace myEngine;

	public VisibilityHandler (AIWorkspace engine) {
		myEngine = engine;
	}

	public List<Position> getVisibleNodes () {
		return getVisibleNodes(getCurrentObstacles());
	}

	public List<Position> getVisibleNodes (Unit obstacle) {
		List<Unit> obstacles = getUnitCopyList(getCurrentObstacles());
		obstacles.add(obstacle.copyShallowUnit());
		return getVisibleNodes(obstacles);
	}

	public List<Position> getVisibleNeighbors(Position curr, List<Branch> searchBranches, List<Position> visibleNodes) {
		List<Position> visibleNeighbors = new ArrayList<>();
		List<Position> neighborPos = getNeighborPositions(searchBranches, curr);
		for(Position neighbor : neighborPos){
			if(visibleNodes.contains(neighbor)){
				visibleNeighbors.add(neighbor);
			}
		}
		return visibleNeighbors;
	}

	private List<Unit> getCurrentObstacles(){
		return myEngine.getUnitController().getUnitType("Tower");
	}

	private List<Position> getVisibleNodes (List<Unit> obstacles) {
		List<Position> nodesToFilter = getNodesToFilter(obstacles);
		return getFilteredNodes(nodesToFilter);
	}

	private List<Position> getFilteredNodes (List<Position> nodesToFilter) {
		HashSet<Position> copyVisibleNodes = new HashSet<Position>(getPosCopyList(getEndPoints(myEngine.getBranches())));
		List<Position> nodes = new ArrayList<>(copyVisibleNodes);
		for(Position nodeToFilter : nodesToFilter){
			while(nodes.contains(nodeToFilter)){
				nodes.remove(nodeToFilter);
			}
		}
		return nodes;
	}

	private List<Position> getNodesToFilter (List<Unit> obstacles) {
		Set<Position> removalList = new HashSet<>();
		List<Branch> engineBranches = myEngine.getBranches();
		List<Position> copyPos = getPosCopyList(getEndPoints(engineBranches));
		List<Unit> copyObstacles = getUnitCopyList(obstacles);
		double boundsExtension = myEngine.getEnemyController().getMaxBoundingDistance();
		for (Unit o : copyObstacles) {
			for (Position pos : copyPos) {
				List<Position> useableBounds = o.getProperties()
						.getBounds().getUseableBounds(o.getProperties().getPosition());
				List<Position> extendedBounds = BoundsHandler.getExtendedBounds(useableBounds, boundsExtension);
				if (EncapsulationChecker.encapsulates(Arrays.asList(pos), extendedBounds)) {
					removalList.add(pos);
				}
			}
			for(Branch branch : engineBranches){
				for(Position pos : branch.getPositions()){
					if (midBranchObstacle(o, branch, pos)){
						removalList.add(branch.getFirstPosition());
						removalList.add(branch.getLastPosition());
					}
				}
			}
		}
		return new ArrayList<Position>(removalList);
	}

	private boolean midBranchObstacle(Unit o, Branch branch, Position pos){
		if(EncapsulationChecker.encapsulates(Arrays.asList(pos), o.getProperties()
				.getBounds().getUseableBounds(o.getProperties().getPosition()))){
			if(!EncapsulationChecker.encapsulates(Arrays.asList(branch.getFirstPosition()), o.getProperties()
					.getBounds().getUseableBounds(o.getProperties().getPosition()))){
				if(!EncapsulationChecker.encapsulates(Arrays.asList(branch.getLastPosition()), o.getProperties()
						.getBounds().getUseableBounds(o.getProperties().getPosition()))){
					return true;
				}
			}
		}
		return false;
	}

	private List<Position> getPosCopyList(List<Position> positions){
		return positions.stream().map(b -> b.copyPosition()).collect(Collectors.toList());
	}

	private List<Unit> getUnitCopyList(List<Unit> units){
		return units.stream().map(o -> o.copyShallowUnit()).collect(Collectors.toList());
	}

	private List<Position> getEndPoints(Branch b){
		return Arrays.asList(b.getFirstPosition(), b.getLastPosition());
	}

	private List<Position> getEndPoints(List<Branch> branches){
		HashSet<Position> pointSet = new HashSet<>();
		for(Branch b : branches){
			List<Position> endPoints = getEndPoints(b);
			for(Position endPoint : endPoints){
				pointSet.add(endPoint);
			}
		}
		return new ArrayList<>(pointSet);
	}

	private List<Position> getNeighborPositions(List<Branch> visibility, Position current){
		List<Branch> neighboringBranches = getVisibleBranchesAtPos(visibility, current);
		List<Position> neighborPos = new ArrayList<>();
		for(Branch n : neighboringBranches){
			if(n.getFirstPosition().equals(current)) {
				neighborPos.add(n.getLastPosition());
			} else if(n.getLastPosition().equals(current)) {
				neighborPos.add(n.getFirstPosition());
			} else {
				neighborPos.add(n.getLastPosition());
				neighborPos.add(n.getFirstPosition());
			}
		}
		return neighborPos;
	}

	private List<Branch> getVisibleBranchesAtPos(List<Branch> visibility, Position current){
		List<Branch> branchesAtPos = new ArrayList<>();
		for(Branch b : visibility){
			if(b.getPositions().contains(current))
				branchesAtPos.add(b);
		}
		return branchesAtPos;
	}

}