package game_engine.game_elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import game_engine.affectors.Affector;
import game_engine.properties.UnitProperties;


/**
 * This class is the superclass for game units and implements GameElement.
 * It represents any physical game unit and holds its ID, UnitProperties, and list of current
 * Affectors to be applied.
 * 
 *
 */
public class Unit extends GameElement {

    private List<Unit> parents;
    private UnitProperties myProperties;
    private UnitProperties myBaseProperties;
    private List<Affector> myAffectors;
    private List<Affector> myAffectorsToApply;
    private int TTL;
    private boolean setToDeath;
    private boolean hasCollided;
    private int deathDelay;
    private int elapsedTime;
    private int numFrames;
    private List<Unit> myChildren;

    public Unit (String name, List<Affector> affectors, int numFrames) {
        super(name);
        initialize();
        myProperties = new UnitProperties();
        setMyBaseProperties(myProperties.copyUnitProperties());
        elapsedTime = 0;
        this.numFrames = numFrames;
        addAffectors(affectors);
        myChildren = new ArrayList<>();
        parents = new ArrayList<>();
    }
    public Unit (String name, UnitProperties unitProperties, int numFrames) {
        super(name);
        initialize();
        myProperties = unitProperties;
        setMyBaseProperties(unitProperties.copyUnitProperties());
        elapsedTime = 0;
        this.numFrames = numFrames;
        this.myChildren = new ArrayList<>();
    }

    public Unit (String name, int numFrames) {
        super(name);
        initialize();
        myProperties = new UnitProperties();
        setMyBaseProperties(myProperties.copyUnitProperties());
        elapsedTime = 0;
        this.numFrames = numFrames;
        myChildren = new ArrayList<>();
        parents = new ArrayList<>();
    }
    
    public String getType(){
    	return this.getName().split("\\s+")[0];
    }
    
    public Unit copyUnit () {
        Unit copy = this.copyShallowUnit();
        List<Unit> copiedChildren =
                this.getChildren().stream().map(u -> u.copyUnit()).collect(Collectors.toList());
        copy.setChildren(copiedChildren);
        return copy;
    }
    
    public Unit copyShallowUnit () {
        Unit copy = new Unit(this.toString(), this.getNumFrames());
        List<Affector> copyApplyAffectors =
                this.getAffectorsToApply().stream().map(a -> a.copyAffector())
                        .collect(Collectors.toList());
        copy.setAffectorsToApply(copyApplyAffectors);
        List<Affector> copyAffectors =
                this.getAffectors().stream().map(a -> a.copyAffector())
                        .collect(Collectors.toList());
        copy.setAffectors(copyAffectors);
        copy.setProperties(this.getProperties().copyUnitProperties());
        copy.setDeathDelay(this.getDeathDelay());
        copy.setTTL(this.getTTL());
        copy.setHasCollided(this.hasCollided);
        copy.elapsedTime = 0;
        return copy;
    }

    private void initialize () {
        this.setHasCollided(false);
        if (myAffectors == null) {
            myAffectors = new ArrayList<>();
        }
        if (myAffectorsToApply == null) {
            myAffectorsToApply = new ArrayList<>();
        }
    }

    public void update () {
        myChildren.stream().forEach(p -> p.incrementElapsedTime(1));
        if (isVisible()) {
            elapsedTime++;
            myAffectors.removeIf(a -> a.getTTL() <= a.getElapsedTime());
            myAffectors.forEach(a -> a.apply(this));
        }
        if (!isAlive()) {
            setElapsedTimeToDeath();
            setToDeath = true;
        }
    }

    public boolean isAlive () {
        if (myProperties.getHealth() == null)
            return false;
        return myProperties.getHealth().getValue() > 0;
    }

    public void setInvisible () {
        this.setElapsedTime(this.getTTL());
    }

    public UnitProperties getProperties () {
        return myProperties;
    }

    public void setProperties (UnitProperties properties) {
        this.myProperties = properties;
    }

    public int getTTL () {
        return TTL;
    }

    public int getElapsedTime () {
        return elapsedTime;
    }

    public void setTTL (int TTL) {
        this.TTL = TTL;
    }

    public void setDeathDelay (int deathDelay) {
        this.deathDelay = deathDelay;
    }

    public int getDeathDelay () {
        return deathDelay;
    }

    public void incrementTTL (int increment) {
        this.TTL += increment;
    }

    public void setElapsedTimeToDeath () {
        if (!setToDeath) {
            this.setElapsedTime(this.getTTL() - deathDelay);
            this.getProperties().getState().changeState(5);
            setToDeath = true;
        }
    }

    public boolean isVisible () {
        if(this.getElapsedTime() == Integer.MAX_VALUE) {
            return false;
        }
        return this.getTTL() > this.getElapsedTime();
    }

    public void incrementElapsedTime (int i) {
        this.elapsedTime += 1;
    }

    public void setElapsedTime (int newTime) {
        elapsedTime = newTime;
    }

    public boolean hasCollided () {
        return hasCollided;
    }

    public void setHasCollided (boolean hasCollided) {
        this.hasCollided = hasCollided;
    }

    public int getNumFrames () {
        return numFrames;
    }

    public double getSellPrice () {
        return getProperties().getPrice().getValue();
    }


    public List<Unit> getChildren () {
        return myChildren;
    }

    public void setChildren (List<Unit> children) {
        this.myChildren = children;
    }

    public void addChild (Unit child) {
        this.myChildren.add(child);
    }

    public List<Unit> getParents () {
        return parents;
    }

    public void addParent (Unit parent) {
        this.parents.add(parent);
    }

    public void setParents (List<Unit> parents) {
        this.parents = parents;
    }

    public void addChildren (List<Unit> children) {
        this.myChildren.addAll(children);
    }

    public void addAffectors (List<Affector> affectors) {
    	affectors.stream().forEach(t -> this.addAffector(t));
    }

    public void addAffectorsToApply (List<Affector> affectorsToApply) {
        this.myAffectorsToApply.addAll(affectorsToApply);
    }

    public void addAffector (Affector affector) {
        this.myAffectors.add(0, affector);
    }

    public void addAffectorToApply (Affector affectorToApply) {
        this.myAffectorsToApply.add(affectorToApply);
    }

    public List<Affector> getAffectors () {
        return myAffectors;
    }

    public List<Affector> getAffectorsToApply () {
        return myAffectorsToApply;
    }

    public void setAffectors (List<Affector> affectors) {
        myAffectors = affectors;
    }

    public void setAffectorsToApply (List<Affector> affectorsToApply) {
        myAffectorsToApply = affectorsToApply;
    }

    public void removeAffectorsByName (String name) {
        for (int x = 0; x < myAffectors.size(); x++) {
            if (myAffectors.get(x).getClass().equals(name)) {
                myAffectors.remove(x);
                x--;
            }
        }
    }
    
	public UnitProperties getMyBaseProperties() {
		return myBaseProperties;
	}
	public void setMyBaseProperties(UnitProperties myBaseProperties) {
		this.myBaseProperties = myBaseProperties;
	}

}