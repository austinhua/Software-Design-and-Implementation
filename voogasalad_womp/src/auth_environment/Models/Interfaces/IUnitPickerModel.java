package auth_environment.Models.Interfaces;

import java.util.List;

import auth_environment.Models.UnitView;
import game_engine.game_elements.Unit;

public interface IUnitPickerModel {

	public List<UnitView> setUnits(List<Unit> units);
	
	public UnitView getUnitViewWithHash(int hash);
	
}
