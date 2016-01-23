import java.awt.Point;

public class Unit extends MapElement{
	public final static int DEFAULT_HEALTH = 100;
	public final static int DEFAULT_SPEED = 2; // Number of moves that can be taken each step
	public final static int DEFAULT_DAMAGE = 20;
	
	private int health;
	private int speed;
	private int damage;
	private Point destination;
	private boolean friendly;
	
	public Unit(int x, int y, boolean friend) {
		super(x, y);
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		damage = DEFAULT_DAMAGE;
		destination = this.position();
		this.friendly = friend;
	}
	
	// Getter Methods
	public int getHealth() { return health; }
	public int getSpeed() { return speed; }
	public int getDamage() { return damage; }
	public boolean isFriendly() { return friendly; }
	
	// Adds change to current health. If a unit it taking damage, change should be negative. Returns unit's new health.
	public int changeHealth(int change) {
		health += change;
		return health;
	}
	
	public void setDestination(Point dest) {
		destination = dest;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
}
