package game_engine.properties;

import java.util.Arrays;
import java.util.List;


public class Velocity extends Property {

    private double mySpeed;
    private double myDirection;

    public Velocity (double speed, double direction) {
        this.mySpeed = speed;
        this.myDirection = direction;
    }

    public double getSpeed () {
        return mySpeed;
    }

    public void setSpeed (double speed) {
        this.mySpeed = speed;
    }

    public void setDirection (double direction) {
        this.myDirection = direction;
    }

    public double getDirection () {
        return myDirection;
    }

    public Velocity copyVelocity () {
        return new Velocity(this.getSpeed(), this.getDirection());
    }

    @Override
    public List<Double> getValues () {
        return Arrays.asList(mySpeed, myDirection);
    }

    @Override
    public void setValues (List<Double> values) {
        mySpeed = values.get(0);
        myDirection = values.get(1);
    }

	public void setVelocity(Velocity velocity) {
		this.mySpeed = velocity.getSpeed();
		this.myDirection = velocity.getDirection();
	}

	@Override
	public boolean isBaseProperty() {
		return true;
	}


}
