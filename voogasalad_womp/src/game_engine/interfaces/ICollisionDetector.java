package game_engine.interfaces;

import java.util.List;

import game_engine.game_elements.Unit;
import game_engine.properties.Position;

public interface ICollisionDetector {
	/*
	 * Detects enemy collisions with projectiles, and performs the corresponding updates. This 
	 * can include killing the unit, applying new affectors depending on the projectile, and 
	 * damaging enemy health.
	 * 
	 * @param	List<Unit> myProjectiles is the list of Unit objects that should be considered 
	 * 			as projectiles during the collisions detection. The collisions detection is 
	 * 			performed between each enemy and each Unit in this list.
	 */
	void resolveEnemyCollisions (List<Unit> myProjectiles);
	
	/*
	 * Performs a collision detection between a theoretical Unit and a list of obstacles. Returns 
	 * a value based on whether or not the Unit can be placed in a location.
	 * 
	 * @param	enemyBounds is a List<Position> that represents the bounds of the Unit being placed.
	 * @param	obstacles is a List<Unit> that contains all units that should not be collided with.
	 * @return	boolean value based on whether the unit can be placed
	 */
	boolean simulatedObstacleCollisionCheck (List<Position> enemyBounds,
            List<Unit> obstacles);
}
