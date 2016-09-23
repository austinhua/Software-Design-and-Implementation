package game_engine.functions;

/**
 * This class represents a Constant for a Function and can include a power.
 * For example, a Constant 5 with power 3 evaluates to 5^3 = 125.
 * @author adamtache
 *
 */
public class Constant {

	private double myValue;
	private double myPower;
	private String myName;
	
	public Constant(double value, double power){
		this.myValue = value;
		this.myPower = power;
	}
	
	public Constant(String name, double value){
		this.myName = name;
		this.myValue = value;
		this.myPower = 1;
	}
	
	// Default power of 1
	public Constant(double value){
		this.myValue = value;
		this.myPower = 1;
	}
	
	public double evaluate(){
		return Math.pow(myValue, myPower);
	}
	
	public boolean isNamedConstant(){
		return myName != null;
	}
	
	public void setValue(double value){
		this.myValue = value;
	}
	
	public double getValue(){
		return myValue;
	}
	
	public double getPower(){
		return myPower;
	}
	
	public String getName(){
		return myName;
	}
	
	public String toString(){
		return ""+myValue;
	}
	
}