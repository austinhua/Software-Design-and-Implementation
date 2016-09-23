package game_engine.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exceptions.ExceptionDialog;
import game_engine.functions.Constant;
import game_engine.functions.Function;
import game_engine.functions.Term;
import game_engine.functions.Variable;
import game_engine.libraries.FunctionLibrary;

/**
 * This class is a factory for construction of functions. 
 * More information on functions can be found in the Game_Engine/Functions package.
 * 
 * @author adamtache
 *
 */

public class FunctionFactory {

	private FunctionLibrary myFunctionLibrary;

	public FunctionFactory(){
		myFunctionLibrary = new FunctionLibrary(this);
		setupSpecialConstants();
		setupDefaultStrengths(0.0001, 0.00005, 0.000005);
		setupDefaultTypes();
	}
	
	public FunctionFactory(FunctionLibrary functionLibrary){
		myFunctionLibrary = functionLibrary;
		setupSpecialConstants();
		setupDefaultStrengths(0.0001, 0.00005, 0.000005);
		setupDefaultTypes();
	}

	public Function createFunction(String type, String str){
		String name = getName(type, str);
		if(myFunctionLibrary.getFunction(name) != null){
			return myFunctionLibrary.getFunction(name);
		}
		List<String> typeAndStr = new ArrayList<>();
		typeAndStr.add(type);
		typeAndStr.add(str);
		List<Term> terms = myFunctionLibrary.getFunctionType(type);
		if(terms == null){
			new ExceptionDialog("Function type not implemented yet").displayMessage();
			return null;
		}
		Constant strength = myFunctionLibrary.getStrength(str);
		if(strength == null){
			new ExceptionDialog("Strength type not implemented yet").displayMessage();
			return null;
		}
		for(Term term : terms){
			List<Constant> constants = copyConstantList(term.getConstants());
			for(Constant c: constants){
				if(!c.isNamedConstant()){
					c.setValue(strength.evaluate());
				}
			}
			term.setConstants(constants);
		}
		return myFunctionLibrary.addFunction(new Function(getName(type, str), terms));
	}


	public Function createConstantFunction(double constant){
		return new Function(Arrays.asList(new Term(new Constant(constant))));
	}

	public Function createExpIncrFunction(String type){
		return myFunctionLibrary.getFunction(getName("ExpIncr", type));
	}

	public Function createExpDecrFunction(String type){
		return myFunctionLibrary.getFunction(getName("ExpDecr", type));
	}

	public Function createRandomConstantFunction(int randomMax){
		return createConstantFunction(randomMax*Math.random());
	}

	public Function createSingleTermFunction(Constant constant, Variable variable){
		return new Function(Arrays.asList(new Term(constant, variable)));
	}

	public Function createLinearFunction(Constant constant, Term term){
		return new Function(Arrays.asList(new Term(constant), term));
	}

	public String getName(String type, String str){
		return type + " " + str;
	}

	private void setupSpecialConstants(){
		myFunctionLibrary.addSpecialConstant(new Constant("Gravity", -9.8));
		myFunctionLibrary.addSpecialConstant(new Constant("Pi", Math.PI));
	}

	private void setupDefaultTypes() {
		setupExpIncrFunction("Strong");
		setupExpIncrFunction("Moderate");
		setupExpIncrFunction("Weak");
		setupExpDecrFunction("Strong");
		setupExpDecrFunction("Moderate");
		setupExpDecrFunction("Weak");
	}

	public void setupDefaultStrengths(double strong, double moderate, double weak){
		myFunctionLibrary.addStrength("Strong", new Constant(strong));
		myFunctionLibrary.addStrength("Moderate", new Constant(moderate));
		myFunctionLibrary.addStrength("Weak", new Constant(weak));
	}

	private void setupExpFunction(String strength, int sign){
		List<Constant> expConsts = new ArrayList<>();
		Constant const_proportionality = new Constant(myFunctionLibrary.getStrength(strength).evaluate() * sign);
		expConsts.add(const_proportionality);
		List<Variable> expVars = new ArrayList<>();
		expVars.add(new Variable("t"));
		Term expTerm = new Term(expVars, expConsts);
		List<Term> expTerms = new ArrayList<>();
		expTerms.add(expTerm);
		String type = "";
		if(sign == 1)
			type = "ExpIncr";
		else if(sign == -1)
			type = "ExpDecr";
		else{
			new ExceptionDialog("Only exponentially increasing and decreasing functions supported").displayMessage();
		}
		myFunctionLibrary.addFunctionType(type, expTerms);
		if(sign == 1){
			Function expIncr = new Function(getName(type, strength), expTerms);
			myFunctionLibrary.addFunction(expIncr);
		}
		if(sign == -1){
			Function expDecr = new Function(getName(type, strength), expTerms);
			myFunctionLibrary.addFunction(expDecr);
		}

	}

	private void setupExpIncrFunction(String strength){
		setupExpFunction(strength, 1);
	}

	private void setupExpDecrFunction(String strength){
		setupExpFunction(strength, -1);
	}

	private List<Constant> copyConstantList(List<Constant> constants){
		List<Constant> constantsCopy = new ArrayList<Constant>();
		for(Constant c : constants){
			constantsCopy.add(new Constant(c.getValue(), c.getPower()));
		}
		return constantsCopy;
	}

	public FunctionLibrary getFunctionLibrary(){
		return myFunctionLibrary;
	}

}