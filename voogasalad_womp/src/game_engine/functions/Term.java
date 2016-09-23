package game_engine.functions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Term for a Function. A Term is made up of a list of Constants and Variables that are all multiplied together to represent a Term.
 * An example of a Term is 5^2*x^3. In this example:
 * a) the list of Constants has one item: a 5 with a power of 2.
 * b) the list of Variables has one item: a x with a power of 3.
 * 
 * The Term class supports a function of any arity (any number of arguments of independent variables).
 * @author adamtache
 *
 */
public class Term {

	private List<Variable> myVariables;
	private List<Constant> myConstants;
	
	public Term(List<Variable> variables, List<Constant> constants){
		this.myVariables = variables;
		this.myConstants = constants;
	}
	
	public Term(Constant constant){
		this.myVariables = new ArrayList<>();
		myConstants = new ArrayList<>();
		myConstants.add(constant);
	}
	
	public Term(Constant constant, Variable variable){
		this.myVariables = new ArrayList<>();
		this.myConstants = new ArrayList<>();
		myConstants.add(constant);
		myVariables.add(variable);
	}
	
	public double evaluate(int index){
		double evaluation = 1;
		for(Variable var : myVariables){
			evaluation *= var.evaluate(index);
		}
		for(Constant c : myConstants){
			evaluation *= c.evaluate();
		}
		return evaluation;
	}
	
	public List<Variable> getVariables(){
		return myVariables;
	}
	
	public List<Constant> getConstants(){
		return myConstants;
	}
	
	public void setVariables(List<Variable> variables){
		myVariables = variables;
	}
	
	public void setConstants(List<Constant> constants){
		myConstants = constants;
	}
	
	public void addVariable(Variable var){
		myVariables.add(var);
	}
	
	public void addConstant(Constant c){
		myConstants.add(c);
	}
	
	public String toString(){
		String str = "";
		for(Variable vars : myVariables){
			str += vars.toString()+"*";
		}
		for(Constant cost : myConstants){
			str += cost.toString()+"*";
		}
		return str;
	}
	
}