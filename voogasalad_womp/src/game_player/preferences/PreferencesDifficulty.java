package game_player.preferences;

import java.util.ResourceBundle;

import game_player.GameDataSource;
import game_player.interfaces.IGameView;
import game_player.view.GUIComboBox;
import game_player.view.PlayerGUI;

public class PreferencesDifficulty extends GUIComboBox {
	
	public PreferencesDifficulty(ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
    	super(r, gameData, view, r.getString("DifficultyLabel"), r.getString("DifficultyOptions"), GUI);
    }
	
	public void performAction() {
		
	}

}
