package game_engine.game_elements;

import java.util.ArrayList;
import java.util.List;


public class Wave extends GameElement {

    private List<Unit> mySpawningUnits;
    private List<Unit> myPlacingUnits;
    private List<Integer> mySpawnTimes;
    private int myCurrentUnit;
    private int timeSinceLastSpawn;
    private int timeBeforeWave;

    public Wave (String name, int time) {
        super(name);
        timeBeforeWave = time;
        mySpawningUnits = new ArrayList<>();
        myPlacingUnits = new ArrayList<>();
        mySpawnTimes = new ArrayList<>();
        myCurrentUnit = 0;
        timeSinceLastSpawn = 0;
    }

    public Wave (String name,
                 List<Unit> spawning,
                 List<Unit> placing,
                 List<Integer> spawnTimes,
                 int timeBeforeWave) {
        super(name);
        this.timeBeforeWave = timeBeforeWave;
        mySpawningUnits = spawning;
        myPlacingUnits = placing;
        mySpawnTimes = spawnTimes;
        myCurrentUnit = 0;
        timeSinceLastSpawn = 0;
    }

    public int getTimeBeforeWave () {
        return timeBeforeWave;
    }

    public List<Unit> getSpawningUnitsLeft () {
        List<Unit> unitsLeft = new ArrayList<>();
        for (Unit e : mySpawningUnits) {
            if (e.isVisible()) {
                unitsLeft.add(e);
            }
        }
        return unitsLeft;
    }

    public void addSpawningUnit (Unit e, int spawnTime) {
        mySpawningUnits.add(e);
        mySpawnTimes.add(spawnTime);
    }

    public void addPlacingUnit (Unit e) {
        myPlacingUnits.add(e);
    }

    public void removePlacingUnit (int index) {
        myPlacingUnits.remove(index);
    }

    public void removeSpawningUnit (int index) {
        mySpawningUnits.remove(index);
        mySpawnTimes.remove(index);
    }

    public List<Unit> getSpawningUnits () {
        return mySpawningUnits;
    }

    public List<Unit> getPlacingUnits () {
        return myPlacingUnits;
    }

    public Unit tryToSpawnUnit () {
        timeSinceLastSpawn++;
        if (myCurrentUnit < mySpawningUnits.size() &&
            timeSinceLastSpawn >= mySpawnTimes.get(myCurrentUnit)) {
            Unit unit = mySpawningUnits.get(myCurrentUnit++);
            timeSinceLastSpawn = 0;
            return unit;
        }
        return null;
    }

}
