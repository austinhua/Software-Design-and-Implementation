import java.util.List;

public class Nanorobot extends Unit {
	
	public Nanorobot(int x, int y, MapElement[][] mapGrid, boolean friend, CellCraft game) {
		super(x, y, mapGrid, friend, game);
		health = DEFAULT_HEALTH * 3;
		speed = DEFAULT_SPEED * 2.5;
		damage = DEFAULT_DAMAGE * 2;
		imageFile = Main.NANOROBOT_IMAGE;
	}
}
