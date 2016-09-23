package game_engine.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Mass is a unit property representing the mass of a unit.
 * 
 *
 */

public class Mass extends Property{
	
	private double myMass;

	public Mass(double mass) {
		this.myMass = mass;
	}

	public List<Double> getValues() {
		return Arrays.asList(myMass);
	}

	public void setValues(List<Double> values) {
		myMass = values.get(0);
	}
	
	public double getMass(){
		return myMass;
	}
	
	public void setMass(double mass){
		this.myMass = mass;
	}
	
	public Mass copyMass(){
		return new Mass(myMass);
	}

	@Override
	public boolean isBaseProperty() {
		return true;
	}

}