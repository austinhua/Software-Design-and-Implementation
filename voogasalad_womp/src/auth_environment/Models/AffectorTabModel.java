package auth_environment.Models;

import auth_environment.Models.Interfaces.IAffectorTabModel;
import game_data.IGameEnvironment;
import game_engine.factories.AffectorFactory;

public class AffectorTabModel implements IAffectorTabModel {
	
	private AffectorFactory myAffectorFactory; 
	
	public AffectorTabModel(IGameEnvironment auth) {
		this.myAffectorFactory = auth.getAffectorFactory();
	}

	@Override
	public AffectorFactory getAffectorFactory() {
		return this.myAffectorFactory;
	}

}
