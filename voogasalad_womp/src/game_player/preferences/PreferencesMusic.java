package game_player.preferences;

import java.util.ResourceBundle;

import game_player.GameDataSource;
import game_player.interfaces.IGameView;
import game_player.view.GUIComboBox;
import game_player.view.PlayerGUI;

public class PreferencesMusic extends GUIComboBox{
	private PlayerGUI myGUI;
	
	public PreferencesMusic(ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
    	super(r, gameData, view, r.getString("MusicLabel"), r.getString("MusicOptions"), GUI);
        myGUI = GUI;
    }
	
	public void performAction() {
		myGUI.getMusic().dispose();
		myGUI.setMusic(super.getComboBox().getValue());
	}
}
