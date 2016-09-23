package game_engine.properties;

import java.util.Arrays;
import java.util.List;

/**
 * State is a unit property representing the state of the unit.
 * States affect the front-end image and back-end representation of the unit's current state.
 * 
 *
 */

public class State extends Property{
    
    private String[] states = {"Stationary", "Disappearing", "Moving", "Initializing", "Receiving_Damage", "Dying"};
    private double state;
    
    public State(double state) {
        this.state = state;
    }
    
    public double getValue() {
        return state;
    }
    
    public String getString() {
        return states[(int) state];
    }
    
    public void changeState(double index) {
        this.state = index;
    }
    
    public State copyState() {
        return new State(state);
    }
    
    public void setStates(String[] states) {
        this.states = states;
    }
    
    public void setState(double state){
    	this.state = state;
    }

    @Override
    public List<Double> getValues () {
        return Arrays.asList(state);
    }

    @Override
    public void setValues (List<Double> values) {
        state = values.get(0);
    }

	@Override
	public boolean isBaseProperty() {
		return false;
	}
    
}
