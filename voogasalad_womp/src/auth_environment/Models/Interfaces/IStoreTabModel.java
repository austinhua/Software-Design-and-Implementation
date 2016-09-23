package auth_environment.Models.Interfaces;

import java.util.List;

public interface IStoreTabModel {
	
	/*
	 * Adds a purchasble unit in the back-end with the name of the unit
	 * and it's cost.
	 * 
	 * @param name refers to the unit's name, cost refers to it's purchase cost
	 */
	void addBuyableUnit(String name, int cost);
	
	/*
	 * Adds a list of purchaseable units in the backend with a list of 
	 * respective costs.
	 * 
	 * @param names refers to a list of unit names, prices refers to a list of 
	 * respective purchase costs.
	 */
	void addBuyableUnits(List<String> names, List<Integer> prices);
	
	/*
	 * Adds an upgrade to a given unit with a given cost. 
	 * 
	 * @param unitName refers to the name of the unit being given the upgrade (names must
	 * be the same). upgradeName refers to the name of the upgrade, and cost refers to the 
	 * upgrade cost.
	 */
	void addBuyableUpgrade(String unitName, String upgradeName, int cost);
	
	/*
	 * Adds a list of upgrades to a list of units with a list of given costs. 
	 * 
	 * @param names refers to a list of names of units that are being given upgrades, upgradeNames
	 * is a list of names of upgrades, costs is a list of costs for each upgrade
	 */
	void addBuyableUpgrades(List<String> names, List<String> upgradeNames, List<Integer> costs);
	
}
