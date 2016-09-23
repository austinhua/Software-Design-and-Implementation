package game_player;

import java.util.HashMap;
import java.util.Map;

public class GameDataSource implements IGameDataSource{
	
	private Map<String, Double> doubleValues;
	
	public GameDataSource() {
		doubleValues = new HashMap<String, Double>();
	}
	
    @Override
    public void setDoubleValue (String key, double value) {
        doubleValues.put(key, value);
    }

    @Override
    public double getDoubleValue (String key) {
        return doubleValues.get(key);
    }

}
