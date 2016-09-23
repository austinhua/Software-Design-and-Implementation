package game_engine.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import game_engine.affectors.Affector;
import game_engine.game_elements.Unit;
import game_engine.libraries.AffectorLibrary;
import game_engine.libraries.UnitLibrary;
import game_engine.properties.UnitProperties;

/**
 * This class is a factory for construction of Units, which are constructed based on types and UnitProperties and children.
 * 
 * @author adamtache
 *
 */

public class UnitFactory {

    private UnitLibrary myUnitLibrary;
    private AffectorLibrary myAffectorLibrary;

    public UnitFactory () {
        this.myUnitLibrary = new UnitLibrary();
    }

    public UnitFactory (UnitLibrary unitLibrary) {
        this.myUnitLibrary = unitLibrary;
    }

    public Unit createUnit (String type, String unitType, Map<String, List<Double>> inputs,
                            List<String> children,
                            List<String> affectors,
                            List<String> affectorsToApply) {
        UnitProperties newProperties = new UnitProperties();
        newProperties.getHealth().setValues(inputs.get("Health"));
        newProperties.getPrice().setValues(inputs.get("Price"));
        newProperties.getMass().setValues(inputs.get("Mass"));     
        newProperties.getState().setValues(inputs.get("State"));
        newProperties.getBounds().setValues(inputs.get("Bounds"));
        newProperties.getRange().setValues(inputs.get("Range"));
        List<Double> velocityList = new ArrayList<>();
        velocityList.addAll(inputs.get("Speed"));
        velocityList.addAll(inputs.get("Direction"));
        newProperties.getVelocity().setValues(velocityList);
        Unit unit = createUnit(getName(type, unitType), newProperties, inputs.get("NumFrames").get(0).intValue());
        unit.setTTL(inputs.get("TTL").get(0).intValue());
        unit.setChildren(getUnitsFromString(children));
        unit.setAffectors(getAffectorsFromString(affectors));
        unit.setAffectorsToApply(getAffectorsFromString(affectorsToApply));       
        unit.setDeathDelay(inputs.get("DeathDelay").get(0).intValue());
        return unit;
    }

    public List<Unit> getUnitsFromString (List<String> names) {
    	names.removeIf(n -> n == null);
        List<Unit> units = new ArrayList<>();
        names.stream().forEach(n -> units.add(myUnitLibrary.getUnitByName(n)));
        return units;
    }

    public List<Affector> getAffectorsFromString (List<String> names) {
    	names.removeIf(n -> n == null);
        List<Affector> affectors = new ArrayList<>();
        names.stream().forEach(n -> {
        	affectors.add(myAffectorLibrary.getAffector(n));
        });
        return affectors;
    }

    public UnitLibrary getUnitLibrary () {
        return myUnitLibrary;
    }

    private String getName (String type, String unitType) {
        return type + "_" + unitType;
    }

    private Unit createUnit (String name, UnitProperties unitProperties, int numFrames) {
        Unit unit = new Unit(name, unitProperties, numFrames);
        return unit;
    }

    public void setAffectorLibrary (AffectorLibrary library) {
        this.myAffectorLibrary = library;
    }

}
