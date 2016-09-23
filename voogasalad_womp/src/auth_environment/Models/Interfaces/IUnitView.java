package auth_environment.Models.Interfaces;

import game_engine.game_elements.Unit;

/**
 * Created by BrianLin on 4/20/16
 * Team member responsible: Brian
 *
 * The Auth Environment's way of displaying a Unit. 
 * Contains both the Unit and an ImageView to be displayed. 
 * 
 * A class that implements this should extend ImageView.
 */

public interface IUnitView {
	
	public void setUnit(Unit unit);
	
	public Unit getUnit();

}
