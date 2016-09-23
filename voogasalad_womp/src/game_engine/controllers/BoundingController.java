package game_engine.controllers;

import java.util.List;
import java.util.PriorityQueue;
import game_engine.game_elements.Unit;

/**
 * This class handles Unit bounding, and specifically, maximum bounding.
 * Maximum bounding is the max bounding distance of the largest currently active Unit on the map.
 * Max bounding is used by tower placement in order to avoid placing towers to avoid enemy collisions within this bounding distance.
 * 
 * @author adamtache
 *
 */

public class BoundingController {

	private PriorityQueue<Unit> maxBoundingQueue;
	private EnemyController myEnemyController;
	
	public BoundingController(EnemyController enemyController){
		this.myEnemyController = enemyController;
		this.maxBoundingQueue = new PriorityQueue<Unit>(myEnemyController.getBoundingEnemies().size(), new BoundsComparator());
		setupBoundingQueue();
	}
	
	/*
	 * Max bounding is the maximum bounding of the largest currently active Unit on the map.
	 * @return	double, the max bounding
	 */
	public double getMaxBounding(){
		Unit unit = maxBoundingQueue.peek();
		if (unit==null) {
			return 0.0; 
		}
		while(!unit.isAlive() || !unit.isVisible()){
			maxBoundingQueue.remove();
			unit = maxBoundingQueue.peek();
			if (unit==null) {
				return 0.0; 
			}
		}
		return unit.getProperties().getBounds().getMaxBoundingDistance();
	}
	
	private void setupBoundingQueue(){
		List<Unit> boundingEnemies = myEnemyController.getBoundingEnemies();
		for(Unit unit : boundingEnemies){
			this.maxBoundingQueue.add(unit);
		}
	}
	
}