package game_engine.interfaces;

import java.util.Collection;
import java.util.List;

import game_engine.affectors.Affector;
import game_engine.game_elements.Unit;
import game_engine.store_elements.Pair;

/*
 * Inner API defined to handle store interactions for each game. This can include buying/selling
 * upgrades, towers, different projectiles, and other game elements that the developer defines.
 */
public interface IStore {
	/*
	 * Clears the current list of units that can be purchased by the player, presumably so that 
	 * other units can be added for different levels/waves.
	 */
	void clearBuyableUnits();
	
	/*
	 * Adds a possible unit to the list of units that can be purchased by the player.
	 * 
	 * @param	Unit t is the unit options that will be added to the list
	 * @param	Integer cost is the cost of the corresponding unit
	 */
	void addBuyableUnit(Unit t, double d);
	
	/*
	 * Adds a list of possible options to the unit list available to the player
	 * 
	 * @param	listOfNewUnits contains pairs of Units and Integers. This represents the corresponding
	 * 			Units and their respective costs that will be added to the options available to the player
	 */
	void addBuyableUnit (Collection<Pair<Unit, Integer>> listOfNewUnits);
	
	/*
	 * Purchases a unit that can be interacted with in the game engine.
	 * 
	 * @param	String name is the name of the unit that is being purchased.
	 * @return 	Unit is the unit that was purchased by the player (assuming that is exists in the
	 * 			available options). If the name does not match any of the options available to the player,
	 * 			this method returns null.
	 */
	Unit purchaseUnit (String name);
	
	/*
	 * Sells a unit and refunds the cost to the player's money.
	 * 
	 * @param	Unit unit is the unit that is being refunded for full cost price.
	 */
	void sellUnit (Unit unit);
	
	/*
	 * Returns a List<Unit> of the towers that are available to the user. These are purchaseable
	 * by the player upon starting the game.
	 * 
	 * @return	List<Unit> list of units that the player can purchase during the game (this is also 
	 * 			considered a list of towers that is purchasable).
	 */
	List<Unit> getTowerList();
	
	/*
	 * Adds an upgrade as an option for a specific unit in the store.
	 * 
	 * @param	Unit upgradedUnit is the specific unit that this upgrade will be available for.
	 * @param	Affector upgrade represents the type of upgrade that is available (defines which
	 * 			characteristics are changed when the upgrade is applied to a unit).
	 * @param	int cost is the cost of the upgrade. 
	 */
	void addUpgrade (Unit upgradedUnit, Affector upgrade, int cost);
	
	/*
	 * Returns a list of the possible ugprades that are available to the player
	 * 
	 * @param	Unit upgradedUnit specifies the type of unit that the player is trying to upgrade
	 * @return	List<Affector> is a list of upgrades that are available to the player for that unit.
	 */
	List<Affector> getUpgrades (Unit upgradedUnit);
	
	/*
	 * Allows the player to purchase an upgrade from the store.
	 * 
	 * @param	Unit upgradedUnit is the specific unit that the upgrade will be applied to 
	 * @param	Affector affector is the upgrade that is being purchased.
	 */
	void buyUpgrade (Unit upgradedUnit, Affector affector);
	
	/*
	 * Returns the amount of money that the player has.
	 * 
	 * @return	int amount of money that the player has left to spend.
	 */
	int getMoney();
}
