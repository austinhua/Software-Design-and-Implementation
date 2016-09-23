package auth_environment.Models;
import java.util.List;

import auth_environment.Models.Interfaces.IMapEditorTabModel;
import game_data.IGameEnvironment;
import game_engine.game_elements.Unit;


/*
 * This class serves as a controller between MapEditorTab and the backend data.
 * @Author: Alexander Tseng
 */
public class MapEditorTabModel implements IMapEditorTabModel{

	private IGameEnvironment myAuthData; 

	public MapEditorTabModel(IGameEnvironment auth) {
		myAuthData = auth;
	}
	
	public void refresh(IGameEnvironment auth) {
		myAuthData = auth; 
	}
	
	public void addTerrain(double xPos, double yPos, Unit element){
		element.getProperties().setPosition(xPos, yPos);
		myAuthData.getPlacedUnits().add(element);
	}
	
	public void deleteTerrain(Unit element){
		myAuthData.getPlacedUnits().remove(element);
	}
	
	public List<Unit> getPlacedUnits(){
		return myAuthData.getPlacedUnits();
	}
	
	public List<Unit> getTerrains(){
		return myAuthData.getUnitFactory().getUnitLibrary().getUnits();
	}
	
	public void clear(){
		myAuthData.getPlacedUnits().clear();
	}


}
