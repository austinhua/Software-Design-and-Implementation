package game_engine.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import game_engine.affectors.Affector;
import game_engine.game_elements.Branch;
import game_engine.game_elements.Level;
import game_engine.game_elements.Unit;
import game_engine.interfaces.ILevelDisplayer;

/**
 * This class is a controller for the levels in games.
 * 
 * @author andy
 *
 */

public class LevelController implements ILevelDisplayer {

    private List<Level> myLevels;
    private Level myCurrentLevel;
    private double score;
    private boolean paused;
    
    public LevelController(List<Level> levels, double score, int currentLevelIndex) {
        this.myLevels = levels;
        this.score = score;
        this.paused = false;
        this.myCurrentLevel = this.myLevels.get(0);
    }
    
    public Level getCurrentLevel() {
        return myCurrentLevel;
    }
    
    public void setCurrentLevel(Level currentLevel) {
        this.myCurrentLevel = currentLevel;
    }
    
    public void setCurrentWave(int waveIndex) {
    	if (myCurrentLevel.getWaves().size() > waveIndex) {
    		myCurrentLevel.setCurrentWave(waveIndex);
    	}
    }
    
    public String getGameStatus () {
        if (myCurrentLevel.getMyLives() <= 0) {
            return "Waves remaining: " + myCurrentLevel.wavesLeft() + ", Lives remaining: " + "0";
        }
        return "Waves remaining: " + myCurrentLevel.wavesLeft() +
               ", Lives remaining: " + myCurrentLevel.getMyLives();
    }
    
    public int getLives () {
        return myCurrentLevel.getMyLives();
    }
    
    public void addLevel (Level level) {
        myLevels.add(level);
    }
    
    public void decrementLives (int lives) {
        myCurrentLevel.decrementLives(lives);
    }
    

    public void playLevel (int levelNumber) {
        myCurrentLevel = myLevels.get(levelNumber);
        paused = false;
    }

    public void continueWaves () {
        myCurrentLevel.playNextWave();
        paused = false;
    }
    
    public boolean isGameOver () {
        return isGameWon() || isGameLost();
    }
    
    public boolean isGameWon() {
    	return myLevels.get(myLevels.size() - 1).wavesLeft() == 1
				&& myCurrentLevel.getCurrentWave().getSpawningUnits().stream().filter(u -> u.isVisible()).count() == 0;
    }
    
    public boolean isGameLost() {
    	return myCurrentLevel.getMyLives() <= 0;
    }
    
    public List<Level> getLevels () {
        return myLevels;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public List<Unit> getActiveUnitsWithAffector(Class<?> cls){
		HashSet<Unit> units = new HashSet<>();
		List<Unit> activeEnemies = myCurrentLevel.getCurrentWave().getSpawningUnitsLeft();
		List<Unit> allEnemies = myCurrentLevel.getCurrentWave().getSpawningUnits();
		for(Unit e : allEnemies){
			if(e.isAlive() && e.isAlive() && e.isVisible() && !activeEnemies.contains(e)){
				activeEnemies.add(e);
			}
		}
		for(Unit e : activeEnemies){
			for(Affector a : e.getAffectors()){
				if(a.getClass().equals(cls)){
					units.add(e);
				}
			}
		}
		return new ArrayList<>(units);
	}
    
}