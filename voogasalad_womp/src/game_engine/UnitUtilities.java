package game_engine;

import java.util.List;
import java.util.stream.Collectors;

import game_engine.game_elements.Unit;

public class UnitUtilities {

    public List<Unit> getUnitsWithType (List<Unit> unitList, String type) {
    	return unitList.stream().filter(u -> u.toString().contains(type))
                .collect(Collectors.toList());
    }

    public List<Unit> getUnitsWithoutType (List<Unit> unitList, String type) {
        return unitList.stream().filter(u -> !u.toString().contains(type))
        	.collect(Collectors.toList());
    }
    
}