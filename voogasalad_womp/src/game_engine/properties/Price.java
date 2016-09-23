package game_engine.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Price is a unit property representing the value of a unit in the store and in general.
 * 
 *
 */

public class Price extends Property {

    private double myPrice;

    public Price (double price) {
        this.myPrice = price;
    }

    public double getValue () {
        return myPrice;
    }

    public void setPrice (double price) {
        this.myPrice = price;
    }

    public Price copyPrice () {
        return new Price(this.getValue());
    }

    @Override
    public List<Double> getValues () {
        return Arrays.asList(myPrice);
    }

    @Override
    public void setValues (List<Double> values) {
        this.myPrice = values.get(0);
    }

	@Override
	public boolean isBaseProperty() {
		return true;
	}

}
