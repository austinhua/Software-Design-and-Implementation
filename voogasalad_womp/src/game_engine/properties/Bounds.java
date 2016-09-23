package game_engine.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import game_engine.handlers.BoundsHandler;

/**
 * Bounds is a unit property made up of a list of positions representing the outside of a Unit.
 * Bounds are used for collisions and encapsulations within the engine. 
 * 
 * Bounds are generally centered around 0,0, whereas useable bounds are centered around the current position of the Unit.
 *
 */

public class Bounds extends Property {

    private List<Position> myPositions;
    private double maxBoundingDistance;

    public Bounds (List<Position> positions) {
        this.myPositions = positions;
        setBoundingDistance();
    }

    public List<Position> getPositions () {
        return myPositions;
    }

    public void setPositions (List<Position> positions) {
        this.myPositions = positions;
        setBoundingDistance();
    }

    public double getMaxBoundingDistance () {
        return maxBoundingDistance;
    }

    public Bounds copyBounds () {
        List<Position> newPositions =
                this.getPositions().stream().map(p -> p.copyPosition())
                        .collect(Collectors.toList());
        return new Bounds(newPositions);
    }

    public String toString () {
        String str = "";
        for (Position pos : myPositions) {
            str += pos.getX() + " " + pos.getY() + " ";
        }
        return str;
    }

    public List<Position> getUseableBounds (Position pos) {
        List<Position> newBounds = new ArrayList<Position>();
        for (Position p : this.getPositions()) {
            Position newP = new Position(p.getX() + pos.getX(), p.getY() + pos.getY());
            newBounds.add(newP);
        }
        return newBounds;
    }

    @Override
    public List<Double> getValues () {
        List<Double> values = new ArrayList<>();
        myPositions.stream().forEach(p -> values.addAll(p.getValues()));
        return values;
    }

    @Override
    public void setValues (List<Double> values) {
        myPositions.clear();
        for (int i = 0; i < values.size(); i+=2) {
            myPositions.add(new Position(values.get(i), values.get(i+1)));
        }
    }

    private void setBoundingDistance () {
        maxBoundingDistance = BoundsHandler.getMaxBoundingDistance(myPositions);
    }

    @Override
    public boolean isBaseProperty () {
        return false;
    }

}
