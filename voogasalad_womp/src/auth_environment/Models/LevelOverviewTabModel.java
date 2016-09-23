 package auth_environment.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auth_environment.Models.Interfaces.ILevelOverviewTabModel;
import exceptions.ExceptionDialog;
import game_data.IGameEnvironment;
import game_engine.game_elements.Level;
import game_engine.game_elements.Wave;

public class LevelOverviewTabModel implements ILevelOverviewTabModel{
	
	private IGameEnvironment myAuthEnvironment; 
	
    private Map<String, Wave> myCreatedWaves;
    private List<Level> myCreatedLevels;
    private int myCurrentLevelIndex;
    
    public LevelOverviewTabModel(IGameEnvironment authEnv){
    	this.myAuthEnvironment = authEnv; 
    	this.myCreatedLevels = new ArrayList<>(); 
        this.myCreatedWaves = new HashMap<String, Wave>(); // ex. Level1Wave1
        this.myCurrentLevelIndex = 0; 
    }
    
    public void refresh(IGameEnvironment authEnv) {
    	this.clear();
    	this.myAuthEnvironment = authEnv; 
    	this.myCreatedLevels = authEnv.getLevels();
        this.myCreatedLevels.stream().forEach(level -> this.addWavesToLevel(level));
    }
    
    private void clear() {
    	this.myCreatedLevels = new ArrayList<>();//.clear();
    	this.myCreatedWaves.clear();
        this.myCurrentLevelIndex = 0; 
    }
    
    private void addWavesToLevel(Level level) {
    	String levelNum = Integer.toString(this.myCreatedLevels.indexOf(level)); 
    	for (int i=0; i<level.getWaves().size(); i++) {
    		String label =  levelNum + " " + Integer.toString(i); 
    		this.myCreatedWaves.put(label, level.getWaves().get(i));
    	}
    }
    
    private boolean checkBounds(int levelNum){
        return (levelNum >= 0) && (levelNum < this.myCreatedLevels.size());
    }
    
    @Override
    public void changeEditedLevel(int editLevel){
        if(!this.checkBounds(editLevel-1)){
        	new ExceptionDialog("Out of bounds").displayMessage();
        }
        this.myCurrentLevelIndex = editLevel-1;
    }
    
    @Override
    public void addLevel(String name, int numLives){
        this.myCreatedLevels.add(new Level(name, numLives));
    }
    
    @Override
    public void addLevels(String name, int numLives, int numLevelsToAdd){
        for(int i = 1; i <= numLevelsToAdd; i++){
            this.addLevel(name + i, numLives);
        }
    }
    
    @Override
    public void addWaveToCurrentLevel(int waveIndex){
        Wave w = this.myCreatedWaves.get(waveIndex);
        this.myCreatedLevels.get(this.myCurrentLevelIndex).addWave(w);
    }
    
    @Override
    public List<String> getLevelNames(){
        List<String> levelNames = new ArrayList<>();
        this.myCreatedLevels.forEach(l -> levelNames.add(l.getName()));
        return levelNames;
    }
    
    @Override
    public List<String> getCurrentLevelWaveNames(){
        List<String> names = new ArrayList<>();
        this.myCreatedLevels.get(this.myCurrentLevelIndex).getWaves().forEach(w -> names.add(w.getName()));
        return names;
    }
    
    @Override
    public List<Level> getCreatedLevels(){
        return new ArrayList<>(this.myCreatedLevels);
    }
    
    // example of levelPlusWaveName: Level 1 Wave 1 
    public void addToCreatedWaves(String levelPlusWaveName, Wave wave) {
        this.myCreatedWaves.put(levelPlusWaveName, wave);
        int levelNum = Integer.parseInt(levelPlusWaveName.split(" ")[0]); 
        this.getCreatedLevels().get(levelNum-1).addWave(wave);
        this.submit();
    }

	@Override
	public void submit() {
		this.myAuthEnvironment.setLevels(this.myCreatedLevels);
	}
    
}