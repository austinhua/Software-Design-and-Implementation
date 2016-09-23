package game_engine.libraries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import exceptions.ExceptionDialog;
import game_engine.game_elements.Unit;
import game_engine.properties.UnitProperties;

public class UnitLibrary {

	private List<Unit> myUnits;
	private HashMap<String, UnitProperties> myProperties;
	
	public UnitLibrary(){
		this.myUnits = new ArrayList<>();
		myProperties = new HashMap<>();
	}
	
	public List<Unit> getUnits(){
		return myUnits;
	}
	
	public void addUnit(Unit unit){
		this.myUnits.add(unit);
	}
	
	public List<String> getUnitNames() {
	    return myUnits.stream().map(m -> m.toString()).collect(Collectors.toList());
	}
	
	public Unit getUnitByName(String name){
		for(Unit unit : myUnits){
			if(unit.getName().equals(name)){
				return unit.copyUnit();
			}
		}
		return null;
	}
	
	public HashMap<String, UnitProperties> getProperties(){
		return myProperties;
	}
	
	public UnitProperties getPropertyByType(String type){
		return myProperties.get(type);
	}


	public void changePropertiesType(String type, UnitProperties newProperties) throws ExceptionDialog{
		if(!myProperties.keySet().contains(type)){
			throw new ExceptionDialog("UnitProperties type does not exist");
		}
		this.myProperties.put(type, newProperties);
	}
	

	
}