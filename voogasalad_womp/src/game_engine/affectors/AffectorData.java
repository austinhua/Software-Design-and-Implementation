package game_engine.affectors;

import java.util.ArrayList;
import java.util.List;

import game_engine.functions.Function;

/**
 * AffectorData is a data structure holding functions and String representations of UnitProperties that these functions affect.
 * A List of List is used for both to ensure flexibility of affecting values.
 * 
 */

public class AffectorData {

	private List<List<Function>> myFunctions;
	private List<String> myUnitProperties;
	
	public AffectorData() {
	    myFunctions = new ArrayList<>();
	    myUnitProperties = new ArrayList<>();
	    myFunctions.add(null);
	    myUnitProperties.add(null);
	}
	
	public AffectorData(List<List<Function>> functions, List<String> unitProperies){
		myFunctions = functions;
		myUnitProperties = unitProperies;
	}
	
	public List<List<Function>> getFunctions(){
		return myFunctions;
	}
	
	public List<String> getUnitProperties(){
		return myUnitProperties;
	}
	
	public void setFunctions(List<List<Function>> functions){
		this.myFunctions = functions;
	}
	
	public void setUnitProperties(List<String> unitProperties){
		this.myUnitProperties = unitProperties;
	}
	
}