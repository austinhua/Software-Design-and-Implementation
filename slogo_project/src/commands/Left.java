package commands;

import value.Value;
import turtle.Agent;

public class Left extends TurtleCommand{

	public Left(){
		setNumParams(1);
	}

	@Override
	public double doCommand(Agent a) {
		Value rotation = (Value) getParams().get(0);
		a.turn(-rotation.getValue()); //default is clockwise
		super.setValue(rotation.getValue());
		return rotation.getValue();
	}


}
