package auth_environment.Models.Interfaces;

import java.util.List;

import game_data.IGameEnvironment;
import game_engine.game_elements.Level;
import game_engine.game_elements.Wave;

public interface ILevelOverviewTabModel {
    /*
     * Changes the specific level in the back-end that is being edited.
     * 
     * @param    editLevel refers to the level number that is to be edited 
     *             (This should start at level 1)
     */
    void changeEditedLevel(int editLevel);
    /*
     * Creates a single new level
     * 
     * @param    name specifies the name of the level (user inputs a name)
     * @param    numLives specifies the number of lives that the player gets during this level
     */
    void addLevel(String name, int numLives);
    /*
     * Creates multiple new levels. The number of levels to be created is specified by the caller.
     * 
     * @param    name represents the name of the levels (different levels with be differentiated by
     *             a level number that is appended (Level1, Level2, etc).
     * @param    numLives is the number of lives that each of the levels will be instantiated with
     * @param    numLevelsToAdd is the number of levels that the caller wants to add.
     */
    void addLevels(String name, int numLives, int numLevelsToAdd);
    /*
     * From the list of created waves, adds a wave to the current, editable level.
     * 
     * @param    waveIndex specifies which wave in the list of waves that is going to be added
     *             to the level.
     */
    void addWaveToCurrentLevel(int waveIndex);
    /*
     * Method used in order to display the names of all levels the user has created thus far.
     * 
     * @return    List<String> return type is a list of all names for the levels
     */
    List<String> getLevelNames();
    /*
     * Returns a list of strings that directly correlate with the names of each of the waves 
     * created for a specific editable level.
     * 
     * @return    List<String> list of wave names for the current level
     */
    List<String> getCurrentLevelWaveNames();
    /*
     * creates a shallow copy of the created levels and returns that list
     * 
     * @return    List<Level> is the shallow copy of the list of levels created by the users.
     */
     List<Level> getCreatedLevels();
     
    void addToCreatedWaves(String level, Wave wave);
    
    void refresh(IGameEnvironment auth);
    
    void submit(); 
}