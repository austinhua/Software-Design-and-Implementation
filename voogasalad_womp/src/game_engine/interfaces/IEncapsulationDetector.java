package game_engine.interfaces;

import java.util.List;

import game_engine.game_elements.Unit;
import game_engine.properties.Bounds;

public interface IEncapsulationDetector {
	/*
	 * Detects encapsulations between units and performs corresponding updates. More specifically,
	 * the encapsulation detection is performed between on screen units and a list of terrains.
	 * 
	 * @param	terrains is a List<Unit> of terrains that needs to be checked for encapsulation
	 * 			with other units so that the corresponding affectors can be updated and transfered.
	 */
	void resolveEncapsulations (List<Unit> terrains);
	
	/*
	 * Returns true or false depending on whether or not the outer Units encapsulates the inner. 
	 * Encapsulation is determined by having the bounds of one Unit completely inside the other.
	 * 
	 * @param	inner is the inner Unit that is being checked. The bounds for this Unit should be inside 
	 * 			the other unit in order for the method to return true.
	 * @param	outer is the outer Unit that is being checked. This Unit encapsulates the other when 
	 * 			the method returns true.
	 * @return	boolean value depending on whether or not an encapsulation is detected.
	 */
	boolean encapsulates (Unit inner, Unit outer);
	
	/*
	 * Returns true or false depending on whether or not the Unit inner is encapsulated by a set of bounds
	 * defined by range. 
	 * 
	 * @param	inner is the Unit that is being checked for encapsulation. This unit must be inside 
	 * 			the defined bounds for the method to return true.
	 * @param	range is a Bounds object that defines a polygon which must surround the Unit in order 
	 * 			for the method to return true.
	 * @return	boolean value depending on whether or not an encapsulation is detected.
	 */
	boolean encapsulates (Unit inner, Bounds range);
}
