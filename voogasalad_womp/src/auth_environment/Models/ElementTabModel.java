package auth_environment.Models;

import auth_environment.Models.Interfaces.IElementTabModel;
import game_data.IGameEnvironment;
import game_engine.factories.AffectorFactory;
import game_engine.factories.UnitFactory;

public class ElementTabModel implements IElementTabModel {
	
	private UnitFactory myUnitFactory;
	private AffectorFactory myAffectorFactory; 
	
	public ElementTabModel(IGameEnvironment auth) {
		update(auth);
	}
	
	public void update(IGameEnvironment auth){
		this.myUnitFactory = auth.getUnitFactory(); 
		this.myAffectorFactory = auth.getAffectorFactory();
		this.myUnitFactory.setAffectorLibrary(auth.getAffectorFactory().getAffectorLibrary());
	}
	
	@Override
	public UnitFactory getUnitFactory() {
		return this.myUnitFactory;
	}

	@Override
	public AffectorFactory getAffectoryFactory() {
		return this.myAffectorFactory;
	}

}
