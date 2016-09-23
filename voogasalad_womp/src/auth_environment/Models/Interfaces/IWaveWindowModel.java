package auth_environment.Models.Interfaces;

import java.util.List;

import game_engine.game_elements.Wave;

public interface IWaveWindowModel {
    static final int DEFAULT_TIME = 240;
    
    /*
     * Adds a unit associated with a specific name and time. If the unit does not exist in the library,
     * nothing is added to the list of units and an error is thrown. Default time is used.
     * 
     * @param    name represents the name of the unit to be added
     */
    void addSpawningUnit(String name);
    
    Wave createWave(String name, String level, List<String> spawningNames, List<Integer> spawningTimes, List<String> placingNames, int timeBeforeWave);

    /*
     * Adds a unit associated with a specific name. If the unit does not exist in the library,
     * nothing is added to the list of units and an error is thrown.
     * 
     * @param    name represents the name of the unit to be added
     * @param    time represents how many ticks should occur between spawns
     */
    void addSpawningUnit(String name, int time);
    
    /*
     * adds a list of units into the wave, along with their corresponding times.
     * 
     * @param    names refers to the list of names for the units
     * @param    times refers to the corresponding time that each unit will spawn.
     */
    void addSpawningUnits(List<String> names, List<Integer> times);
    /*
     * Returns a list of information on the enemies that are currently added to the wave
     * 
     * @return    List<String> contains strings related to the information of each unit in the wave.
     */
    List<String> getEnemyInfo();
    
    /*
     * Removes a specified spawning unit from the wave. Unit to be removed is specified by an index
     * 
     * @param    index is the index of the unit to be removed from the wave (position in the list)
     */
    void removeSpawningUnit(int index);
    
    /*
     * Adds a unit to the list of place-able units for the wave.
     * 
     * @param    name is the name of the unit that is to be added.
     */
    void addPlacingUnit(String name);
    
    /*
     * Adds a list of place-able units to the wave. Units are specified by String names
     * 
     * @param    names is the list of names for each of the units to be added as place-able units.
     */
    void addPlacingUnits(List<String> names);
    
    /*
     * Removes a place-able unit from the wave. Unit is specified by the name.
     * 
     * @param    name is the name of the place-able unit to be removed.
     */
    void removePlacingUnit(int index);
}