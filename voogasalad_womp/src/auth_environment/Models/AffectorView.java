package auth_environment.Models;

import game_engine.affectors.Affector;
import javafx.scene.control.Button;

public class AffectorView extends Button{

	private Affector myAffector;
	
	public AffectorView(Affector affector){
		super(affector.getName());
		this.myAffector = affector;
	}
	public AffectorView(String text, Affector affector) {
		super(text);
		this.myAffector = affector;
	}
	
	public Affector getAffector(){
		return myAffector;
	}
	
	
}
