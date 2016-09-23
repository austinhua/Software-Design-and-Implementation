package game_player.preferences;

import java.util.ResourceBundle;
import game_player.GameDataSource;
import game_player.interfaces.IGameView;
import game_player.view.GUIComboBox;
import game_player.view.PlayerGUI;


public class PreferencesColorTheme extends GUIComboBox {
	
    private IGameView myView;

    public PreferencesColorTheme (ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
    	super(r, gameData, view, r.getString("ColorThemeLabel"), r.getString("ColorThemeOptions"), GUI);
        myView = view;
    }
	
    public void performAction () {
        myView.setCSS(super.getComboBox().getValue());
    }

    @Override
    public void updateNode () {

    }

}
