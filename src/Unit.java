import java.awt.Point;
import java.util.*;
import java.lang.Math;

/**
 * Code masterpiece file
 * The purpose of this class was to create a generic extension of the MapElements class for combat units. 
 * I think it is well designed because it allows for a lot of polymorphism in its methods, 
 * such as takeAction(), findOpenMove(), and findNearbyEnemies(). This allows for different behaviors to be implemented by different extensions of Unit,
 * still allowing for them all to be called with the same few methods.
 * I made the attributes of instances of Unit protected because they needed to be frequently added by subclasses. 
 * I felt that the ease of use of being able to access the multitude of fields directly instead of needing to call a getter/setter method
 * for each and every one of the fields outweighed the considerations for making the fields private such as preventing 
 * unintended behavior or for greater encapsulation.
 * 
 * @author Austin Hua
 */

public class Unit extends MapElement{
	public final static int DEFAULT_HEALTH = 100;
	public final static double DEFAULT_SPEED = 1; // Number of moves that can be taken each step, can be <1 to take 1 move every 2+ steps
	public final static int DEFAULT_DAMAGE = 20;
	public final static int DEFAULT_RANGE = 1;
	
	protected int health;
	protected double speed;
	protected int damage;
	protected int range;
	protected Point destination;
	protected boolean friendly;
	protected boolean selected;
	protected double curAvailableMoves;
	protected String imageFile;
	
	public Unit(int x, int y, MapElement[][] mapGrid, boolean friend, CellCraft game) {
		super(x, y, mapGrid, game);
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		damage = DEFAULT_DAMAGE;
		range = DEFAULT_RANGE;
		this.friendly = friend;
		if (friendly) {
			destination = this.position();
			imageFile = Main.DEFAULT_FRIENDLY_IMAGE;

		}
		else { // give enemy units a random destination on the left half of the map
			destination = new Point((int)(Math.random() * mapGrid.length * .5), 
							(int)(Math.random() * mapGrid[0].length * .5)); 
			imageFile = Main.DEFAULT_ENEMY_IMAGE;
		}
		selected = false;
		curAvailableMoves = 0;
	}
	
	// Getter Methods
	public int getHealth() { return health; }
	public double getSpeed() { return speed; }
	public int getDamage() { return damage; }
	public boolean isFriendly() { return friendly; }
	public boolean isSelected() { return selected; }
	public String getImageFile() { return imageFile; }
	
	public void setDestination(Point dest) {
		destination = dest;
	}
	
	@Override
	public void takeAction() {
		curAvailableMoves += speed;
		while(curAvailableMoves >= 1) {
			curAvailableMoves--;
			List<Unit> attackableEnemies = findNearbyEnemies(range);
			if (!attackableEnemies.isEmpty()) {
				attackEnemy(attackableEnemies);
				return;
			}
			else {
				move();
			}
		}
	}
	
	/** Attempts to move one "movement point" towards the destination point. 
	 *  Cannot move if there is a nearby enemy it can attack.
	 *  Returns a boolean indicating whether it moved or not.
	 */
	private boolean move() {
		int dx = destination.x - x();
		int dy = destination.y - y();
		
		Point movement = findOpenMove(dx, dy);
		if (movement.x == 0 && movement.y == 0) return false;
		moveByAmount(movement);
		return true;
	}

	private void moveByAmount(Point movement) {
		int newX = x() + movement.x;
		int newY = y() + movement.y;
		myGrid[newX][newY] = this;
		myGrid[x()][y()] = null;
		setPosition(new Point(newX, newY));
	}
	
	
	/** Return a point representing the available move from current location or (0, 0) if no available moves.
	 *  Only searches in directions that wouldn't result in going backwards.
	 *  Note: Only straight moves are valid; Diagonal moves are not permitted. */
	private Point findOpenMove(int dx, int dy) {
		if (dx == 0 && dy == 0) return new Point(0, 0);
		int xDir = (int)Math.signum(dx);
		int yDir = (int)Math.signum(dy);
		int[] xOptions;
		int[] yOptions;
		
		if (dx == 0) {
			xOptions = new int[]{0, 1, -1};
			yOptions = new int[]{yDir, 0, 0};
		}
		else if (dy == 0) {
			xOptions = new int[]{xDir, 0, 0};
			yOptions = new int[]{0, 1, -1};
		}
		else {
			if (Math.abs(dx) >= Math.abs(dy)) {
				xOptions = new int[]{xDir, 0};
				yOptions = new int[]{0, yDir};
			}
			else {
				xOptions = new int[]{0, xDir};
				yOptions = new int[]{yDir, 0};
			}
		}
		for (int i = 0; i < xOptions.length; i++) {
			int xVal = x() + xOptions[i];
			int yVal = y() + yOptions[i];
			if (xVal >= 0 && xVal < myGrid.length && yVal >= 0 && yVal < myGrid[0].length) {
				MapElement option = myGrid[xVal][yVal];
				if (option == null || option.isFree() ) {
					return new Point(xOptions[i], yOptions[i]);
				}
			}
		}
		return new Point(0, 0);
	}
	
	public List<Unit> findNearbyEnemies(int rangeToCheck) {
		List<Unit> nearbyEnemies = new ArrayList<Unit>();
		for (int i = x() - rangeToCheck; i <= x() + rangeToCheck; i++) {
			for (int j = y() - rangeToCheck; j <= y() + rangeToCheck; j++) {
				if (i >= 0 && i < myGrid.length && j >= 0 && j < myGrid[0].length) {
					MapElement e = myGrid[i][j];
					if (e != null && e instanceof Unit && (((Unit)e).isFriendly() != this.friendly) && !((Unit)e).isDead()) {
						nearbyEnemies.add((Unit)e);
					}
				}
			}
		}
		return nearbyEnemies;
	}
	
	public void attackEnemy(List<Unit> attackableEnemies) {
		Unit enemy = attackableEnemies.get(0);
		enemy.changeHealth(-damage);
	}
	
	/** Adds change to current health. 
	 * If a unit it taking damage, change should be negative. 
	 * Returns unit's new health. */
	public int changeHealth(int change) {
		if (myGame.isInvincible() && friendly) return health;
		health += change;
		return health;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void select() {
		selected = true;
	}
	public void deselect() {
		selected = false;
	}
}
