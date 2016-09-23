package game_engine.libraries;

import java.util.HashMap;
import game_engine.game_elements.Unit;

public class TerrainLibrary {

	private HashMap<String, Unit> myTerrains;
	
	public TerrainLibrary() {
		myTerrains = new HashMap<>();
	}
	
	public Unit getTerrainByName(String name) {
		return myTerrains.get(name).copyUnit();
	}
	
	public void addTerrain(Unit terrain){
		myTerrains.put(terrain.toString(), terrain);
	}

}