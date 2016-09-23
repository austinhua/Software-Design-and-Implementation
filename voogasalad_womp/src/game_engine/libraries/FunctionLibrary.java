package game_engine.libraries;

import java.util.HashMap;
import java.util.List;

import game_engine.factories.FunctionFactory;
import game_engine.functions.Constant;
import game_engine.functions.Function;
import game_engine.functions.Term;

/**
 * This class represents a library or database of functions for a single instance of a game.
 * The library holds pre-created default functions but can be expanded in-game by the player.
 * @author adamtache
 *
 */
public class FunctionLibrary {

	private HashMap<String, Function> myFunctions;
	private HashMap<String, List<Term>> myFunctionTypes;
	private HashMap<String, Constant> myStrengths;
	private HashMap<String, Constant> mySpecialConstants;
	private FunctionFactory myFactory;
	
	public FunctionLibrary(FunctionFactory myFactory){
		myFunctions = new HashMap<>();
		myFunctionTypes = new HashMap<>();
		myStrengths = new HashMap<>();
		mySpecialConstants = new HashMap<>();
		this.myFactory = myFactory;
	}

	public List<Term> getTerms(String type, String strength) {
		return myFunctions.get(myFactory.getName(type, strength)).getTerms();
	}

	public Function addFunction(Function function) {
		return myFunctions.put(function.getName(), function);
	}
	
	public void addStrength(String strength, Constant constant){
		myStrengths.put(strength, constant);
	}
	
	public void addFunctionType(String type, List<Term> terms){
		myFunctionTypes.put(type, terms);
	}
	
	public void addSpecialConstant(Constant constant){
		mySpecialConstants.put(constant.getName(), constant);
	}
	
	public Function getFunction(String name){
		return myFunctions.get(name);
	}
	
	public List<Term> getFunctionType(String type){
		return myFunctionTypes.get(type);
	}
	
	public Constant getStrength(String str){
		return myStrengths.get(str);
	}
	
}