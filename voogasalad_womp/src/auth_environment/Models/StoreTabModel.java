package auth_environment.Models;

import java.util.List;

import auth_environment.Models.Interfaces.IAuthModel;
import game_data.IGameEnvironment;
import game_engine.factories.StoreFactory;

public class StoreTabModel {

	private IGameEnvironment myAuthEnvironment;
	private StoreFactory myStoreFactory;

	public StoreTabModel(IAuthModel authModel) {
		this.myAuthEnvironment = authModel.getIAuthEnvironment();
		this.myStoreFactory = new StoreFactory(authModel.getIAuthEnvironment().getUnitFactory().getUnitLibrary(),
		                                       authModel.getIAuthEnvironment().getAffectorFactory().getAffectorLibrary());
	}

	public void addBuyableUnit(String name, int cost) {
		this.myStoreFactory.addBuyableUnit(name, cost);
	}

	public void addBuyableUnits(List<String> names, List<Integer> prices) {
		this.myStoreFactory.addBuyableUnits(names, prices);
	}

	public void addBuyableUpgrade(String unitName, String upgradeName, int cost) {
		this.myStoreFactory.addUpgrade(unitName, upgradeName, cost);
	}

	public void addBuyableUpgrades(List<String> names, List<String> upgradeNames, List<Integer> costs) {
		this.myStoreFactory.addUpgrades(names, upgradeNames, costs);
	}
	
	public StoreFactory getStoreFactory() {
	    return myStoreFactory;
	}

}
