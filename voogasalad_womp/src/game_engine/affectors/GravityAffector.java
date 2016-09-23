package game_engine.affectors;

import java.util.List;

import game_engine.functions.Function;
import game_engine.game_elements.Unit;
import game_engine.properties.Property;

public class GravityAffector extends Affector {
	private static final int GRAV_CONSTANT = 100;
	public GravityAffector(AffectorData data) {
		super(data);
	}

	@Override
	public void apply(List<Function> functions, Property property, Unit u) {
		double x = u.getProperties().getPosition().getX();
		double y = u.getProperties().getPosition().getY();
		y -= 0.5*GRAV_CONSTANT*1*1;
		u.getProperties().setPosition(x, y);
	}
	

}
