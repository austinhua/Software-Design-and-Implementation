package game_engine.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Team is a unit property representing the team value of the unit.
 * 
 *
 */

public class Team extends Property {

    private double myTeam;

    public Team (double team) {
        this.myTeam = team;
    }

    public double getTeam () {
        return myTeam;
    }

    public void setTeam (double team) {
        this.myTeam = team;
    }

    public boolean checkTeam (double teamName) {
        return myTeam == teamName;
    }

    public Team copyTeam () {
        return new Team(this.getTeam());
    }

    @Override
    public List<Double> getValues () {
        return Arrays.asList(new Double[] { myTeam });
    }

    @Override
    public void setValues (List<Double> values) {
        this.myTeam = values.get(0);

    }

	@Override
	public boolean isBaseProperty() {
		return true;
	}
}
