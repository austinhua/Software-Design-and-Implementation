package game_engine.store_elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import game_engine.affectors.Affector;
import game_engine.affectors.AffectorData;
import game_engine.affectors.BasicIncrementAffector;
import game_engine.game_elements.Unit;
import game_engine.interfaces.IStore;
import game_engine.factories.FunctionFactory;
import game_engine.functions.*;

public class Store implements IStore {

	private int myMoney;
	private Map<Unit, Integer> buyableUnits;
	private Map<String, List<Pair<Affector, Integer>>> upgrades;
	private Map<String, Unit> nameToOriginalInstance;
	private Map<Unit, Integer> items;

	public Store(int startMoney) {
		myMoney = startMoney;
		buyableUnits = new HashMap<Unit, Integer>();
		upgrades = new HashMap<String, List<Pair<Affector, Integer>>>();
		nameToOriginalInstance = new HashMap<String, Unit>();
		items = new HashMap<Unit, Integer>();
	}

	@Override
	public void clearBuyableUnits() {
		buyableUnits.clear();
	}

	@Override
	public void addBuyableUnit(Unit t, double cost) {
		buyableUnits.put(t, new Integer((int) cost));
		nameToOriginalInstance.put(t.toString(), t);
	}

	@Override
	public void addBuyableUnit(Collection<Pair<Unit, Integer>> listOfNewUnits) {
		for (Pair<Unit, Integer> p : listOfNewUnits) {
			Unit newUnit = p.getLeft();
			int cost = p.getRight();
			boolean contained = false;
			for (Unit u : buyableUnits.keySet()) {
				if (u.toString().equals(newUnit.toString())) {
					this.buyableUnits.put(u, cost);
					contained = true;
					break;
				}
			}
			if (contained) {
				continue;
			}
			buyableUnits.put(newUnit, cost);
		}
	}

	@Override
	public Unit purchaseUnit(String name) {
		Unit u = null;
		for (Unit unit : buyableUnits.keySet()) {
			if (unit.toString().equals(name) && myMoney >= buyableUnits.get(unit)) {
				myMoney -= buyableUnits.get(unit);
				u = unit;
				break;
			}
		}
		return u;
	}

	@Override
	public void sellUnit(Unit unit) {
	        System.out.println(unit);
		myMoney += buyableUnits.get(nameToOriginalInstance.get(unit.toString()));
	}

	public void applyItem(String name, List<Unit> applied) {
		for (Unit u : items.keySet()) {
			if (u.toString().equals(name) && myMoney >= items.get(u)) {
				for (Unit app : applied) {
					List<Affector> affectorsToApply = u.getAffectorsToApply().stream().map(p -> p.copyAffector())
							.collect(Collectors.toList());
					app.addAffectorsToApply(affectorsToApply);
				}
				myMoney -= items.get(u);
			}
		}
	}

	@Override
	public List<Unit> getTowerList() {
		ArrayList<Unit> ret = new ArrayList<Unit>();
		for (Unit t : buyableUnits.keySet()) {
			ret.add(t);
		}
		return ret;
	}

	public void addMoney(int amount) {
		myMoney += amount;
	}

	@Override
	public void addUpgrade(Unit upgradedUnit, Affector upgrade, int cost) {
		Pair<Affector, Integer> affectorPair = new Pair<>(upgrade, cost);
		if (upgrades.get(upgradedUnit.toString()) == null) {
			upgrades.put(upgradedUnit.toString(), new ArrayList<>());
		}
		upgrades.get(upgradedUnit.toString()).add(affectorPair);
	}

	@Override
	public List<Affector> getUpgrades(Unit upgradedUnit) {
		List<Affector> affectors = new ArrayList<>();
		Unit found = findUnit(upgradedUnit);
		if (found == null) {
			return new ArrayList<>();
		}
		for (Pair<Affector, Integer> p : upgrades.get(found.toString())) {
			affectors.add(p.getLeft());
		}
		return affectors;
	}

	@Override
	public void buyUpgrade(Unit upgradedUnit, Affector affector) {
		Unit found = findUnit(upgradedUnit);
		if (found == null) {
			throw new RuntimeException("This unit could not be found");
		}
		for (Pair<Affector, Integer> p : upgrades.get(found.toString())) {
			if (p.getLeft().getClass().getSimpleName().equals(affector.getClass().getSimpleName())
					&& myMoney >= p.getRight()) {
				myMoney -= p.getRight();
				p.getLeft().apply(upgradedUnit);
			}
		}
	}

	public Unit findUnit(Unit upgradedUnit) {
		Unit found = null;
		for (String u : upgrades.keySet()) {
			if (u.equals(upgradedUnit.toString())) {
				found = upgradedUnit;
			}
		}
		return found;
	}

	@Override
	public int getMoney() {
		return myMoney;
	}

}
