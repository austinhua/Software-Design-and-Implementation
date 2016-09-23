package game_engine.functions;

/**
 * This class represents an (independent) Variable for a Function and can include a power.
 * For example, a Variable x with power 3 is equivalent to x^3.
 * Variables are evaluated by index, which plugs in the index as the variable value.
 * @author adamtache
 *
 */
public class Variable {
	
	private String myVariable;
	private double myPower;
	
	public Variable(String variable, double power){
		this.myVariable = variable;
		this.myPower = power;
	}
	
	public Variable(String variable){
		this.myVariable = variable;
		this.myPower = 1;
	}

	public double evaluate(int index) {
		return Math.pow(index, myPower);
	}
	
	public String getVariable(){
		return myVariable;
	}

	public String toString(){
		return myVariable;
	}
	
}