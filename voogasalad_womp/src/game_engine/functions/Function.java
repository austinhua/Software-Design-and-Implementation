package game_engine.functions;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a discrete function based on any number of independent variable as arguments.
 * A function is made up of Terms (see Term.java), which are themselves made up of constants and variables.
 * @author adamtache
 *
 */
public class Function {
	private static final double DEFAULT_DX = 0.001;
	private List<Term> myTerms;
	private String myName;
	private double[] domain;
	private double dx;
	
	public Function(String type, String strength){
		myTerms = new ArrayList<>();
		domain = new double[2];
		dx = DEFAULT_DX;
	}
	
	public Function(String name, List<Term> terms){
		this.myName = name;
		this.myTerms = terms;
		domain = new double[2];
	}
	public void setDomain(int start, int end){
		domain[0] = start;
		domain[1] = end;
	}
	public void setDX(double newDX){
		dx = newDX;
	}
	public boolean atDomainEnd(double x){
		return  (Math.abs(x-domain[1]) < 0.00000001);
	}
	public double getDomainEnd(){
		return domain[1];
	}
	public double getDX(){
		return dx;
	}
	public Function(String equation){
		myTerms = new ArrayList<>();
		domain = new double[2];
	}
	
	public Function(List<Term> terms){
		this.myTerms = terms;
		domain = new double[2];
	}
	
	public double evaluate(int index){
		double evaluation = 0;
		for(Term term : myTerms){
			evaluation += term.evaluate(index);
		}
		return evaluation;
	}
	
	public double evaluate(double time, double currValue) {
	    return 10;
	}
	
	public String getName(){
		return myName;
	}
	
	public List<Term> getTerms(){
		return myTerms;
	}
	
	public String toString(){
		String str = "";
		for(Term term : myTerms){
			List<Variable> variables = term.getVariables();
			for(Variable var : variables){
				str += var+"*";
			}
			List<Constant> constants = term.getConstants();
			for(Constant c : constants){
				str += c+"*";
			}
		}
		return str;
	}

	public void setFunction(Function fun) {
		myTerms = fun.getTerms();
	}
	
}