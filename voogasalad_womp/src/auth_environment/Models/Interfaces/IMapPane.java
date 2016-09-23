package auth_environment.Models.Interfaces;

import auth_environment.Models.MapEditorTabModel;
import auth_environment.Models.UnitView;
import javafx.scene.layout.Pane;

public interface IMapPane {
	
	public void adjustUnitViewScale(UnitView uv);
	public void adjustUnitViewXY(UnitView uv, double x, double y);
	
	public MapEditorTabModel getModel();
	
	
	public void addToPane(UnitView uv);
	
	public Pane getRoot();
}
