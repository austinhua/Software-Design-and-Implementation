package game_engine.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Health is a unit property representing hit-points or life-points of a Unit for taking damage and staying alive.
 * 
 *
 */

public class Health extends Property {

    private double myHealth;
    private double initialHealth;

    public Health (double health) {
        this.initialHealth = health;
        this.myHealth = health;
    }

    public double getValue () {
        return myHealth;
    }

    public void setValue (double health) {
        myHealth = health;
    }

    public double getInitialValue () {
        return initialHealth;
    }

    public Health copyHealth () {
        return new Health(this.getValue());
    }

    @Override
    public List<Double> getValues () {
        return Arrays.asList(myHealth);
    }
    
    @Override
    public void setValues (List<Double> values) {
        myHealth = values.get(0);
    }

	@Override
	public boolean isBaseProperty() {
		return false;
	}

}
