package game_engine.game_elements;

import java.util.ArrayList;
import java.util.List;
import game_engine.properties.Position;


/*
 * Internal API that is used in order to represent levels within a game. More specifically,
 * this API will be responsible for dealing with transitions in between waves of enemies, as well as
 * keeping track of the order in which waves occur, and the initial conditions for waves.
 */
public class Level extends GameElement {

    private int myLives;
    private int startingLives;
    private Wave myCurrentWave;
    private List<Wave> myWaves;
    private List<Branch> myBranches;
    private List<Position> myGoals;
    private List<Position> mySpawns;

    public Level (String name, int myLives) {
        super(name);
        this.myLives = myLives;
        this.startingLives = myLives;
        myWaves = new ArrayList<>();
        myGoals = new ArrayList<>();
        mySpawns = new ArrayList<>();
    }

    /*
     * This API will allow the player to start a new wave. Returns true if next started, false if
     * not.
     */
    public void playNextWave () {
        Wave nextWave = getNextWave();
        if (nextWave != null) {
            myCurrentWave = nextWave;
        }
    }

    public Wave getCurrentWave () {
        return myCurrentWave;
    }

    public void setCurrentWave (int wave) {
        myCurrentWave = myWaves.get(wave);
    }

    public int wavesLeft () {
        int numWavesLeft = myWaves.size();
        for (Wave wave : myWaves) {
            if (wave == myCurrentWave) {
                break;
            }
            numWavesLeft--;
        }
        return numWavesLeft;
    }

    public void addWave (Wave newWave) {
        myWaves.add(newWave);
        if (myWaves.size() == 1) {
            myCurrentWave = newWave;
        }
    }

    public Unit update () {
        if (myCurrentWave == null)
            return null;
        return myCurrentWave.tryToSpawnUnit();
    }

    public Wave getNextWave () {
        for (int x = 0; x < myWaves.size() - 1; x++) {
            if (myWaves.get(x).equals(myCurrentWave)) {
                int nextWaveIndex = x + 1;
                return myWaves.get(nextWaveIndex);
            }
        }
        return null;
    }

    public String toString () {
        return String.valueOf(myWaves.size());
    }

    public int getMyLives () {
        return myLives;
    }

    public void setMyLives (int myLives) {
        this.myLives = myLives;
    }

    public int getStartingLives () {
        return startingLives;
    }

    public void decrementLife () {
        myLives--;
    }

    public void decrementLives (int lives) {
        myLives -= lives;
    }

    public List<Wave> getWaves () {
        return myWaves;
    }

    public void setGoals (List<Position> goals) {
        this.myGoals = goals;
    }

    public void setSpawns (List<Position> spawns) {
        this.mySpawns = spawns;
    }

    public void addSpawns (List<Position> spawns) {
        this.mySpawns.addAll(spawns);
    }

    public void addSpawn (Position spawn) {
        this.mySpawns.add(spawn);
    }

    public void addGoal (Position goal) {
        this.myGoals.add(goal);
    }

    public List<Position> getSpawns () {
        return mySpawns;
    }

    public void addGoals (List<Position> goals) {
        this.myGoals.addAll(goals);
    }

    public List<Position> getGoals () {
        return myGoals;
    }

    public void addWaves (List<Wave> waves) {
        this.myWaves.addAll(waves);
    }

    public boolean isGoal (Position goal) {
        if (myGoals == null)
            return false;
        return myGoals.contains(goal);
    }

    public boolean isEnemyAtGoal (Unit e) {
        if (myGoals == null)
            return false;
        for (Position p : this.myGoals) {
            if (e.getProperties().getPosition().roughlyEquals(p)) {
                return true;
            }
        }
        return false;
    }


}
