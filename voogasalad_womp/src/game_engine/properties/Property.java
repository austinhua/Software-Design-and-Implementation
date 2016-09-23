package game_engine.properties;

import java.util.List;

public abstract class Property {

	public abstract List<Double> getValues();

	public abstract void setValues(List<Double> values);
	
	public abstract boolean isBaseProperty();
	
}