package auth_environment.Models;

import java.util.List;
import java.util.stream.Collectors;
import auth_environment.Models.Interfaces.ILevelOverviewTabModel;
import auth_environment.Models.Interfaces.IWaveWindowModel;

import java.util.ArrayList;
import game_engine.game_elements.Unit;
import game_engine.game_elements.Wave;
import game_engine.libraries.UnitLibrary;

public class WaveWindowModel implements IWaveWindowModel {
	
    private Wave myWave;
    private UnitLibrary myLibrary;
    private ILevelOverviewTabModel levelOverview;
    
    public WaveWindowModel(UnitLibrary lib, ILevelOverviewTabModel levelOverview) {
    	this.myLibrary = lib;
    	this.levelOverview = levelOverview;
    }
    
    public WaveWindowModel(UnitLibrary lib, String name, int spawnTime, ILevelOverviewTabModel levelOverview){
        this.myWave = new Wave(name, spawnTime);
        this.myLibrary = lib;
        this.levelOverview = levelOverview;
    }
    
    @Override 
    public Wave createWave(String name, String levelPlusWaveName, List<String> spawningNames, List<Integer> spawningTimes, List<String> placingNames, int timeBeforeWave) {
        Wave w = new Wave(name, unitsFromNames(spawningNames), unitsFromNames(placingNames), spawningTimes, timeBeforeWave);
        levelOverview.addToCreatedWaves(levelPlusWaveName, w);
        return w;
    }
    
    public List<Unit> unitsFromNames(List<String> names) {
        return names.stream().map(n -> myLibrary.getUnitByName(n)).collect(Collectors.toList());    
    }
    
    @Override
    public void addSpawningUnit(String name, int time){
        Unit u = myLibrary.getUnitByName(name);
        if(u != null){
            this.myWave.addSpawningUnit(u, time);
        }
        // TODO: throw an error if the unit cannot be found
    }

    @Override
    public List<String> getEnemyInfo() {
        List<String> ret = new ArrayList<>();
        for(Unit u : this.myWave.getSpawningUnits()){
            ret.add(u.getName()); // TODO: maybe some other statistics?
        }
        return ret;
    }

    @Override
    public void addSpawningUnits(List<String> names, List<Integer> times) {
        for(int i = 0;i < names.size();i++){
            this.addSpawningUnit(names.get(i), times.get(i));
        }
    }

    @Override
    public void removeSpawningUnit(int index) {
        this.myWave.removeSpawningUnit(index);
    }

    @Override
    public void addSpawningUnit(String name) {
        this.addSpawningUnit(name, DEFAULT_TIME);
    }

    @Override
    public void addPlacingUnit(String name) {
        Unit u = this.myLibrary.getUnitByName(name);
        if(u != null){
            this.myWave.addPlacingUnit(u);
        }
    }

    @Override
    public void addPlacingUnits(List<String> names) {
        names.forEach(name -> this.addPlacingUnit(name));
    }

    @Override
    public void removePlacingUnit(int index) {
        this.myWave.removePlacingUnit(index);
    }
}