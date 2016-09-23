package game_player.preferences;

import java.util.ResourceBundle;

import game_player.GameDataSource;
import game_player.interfaces.IGameView;
import game_player.view.GUIComboBox;
import game_player.view.PlayerGUI;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class PreferencesCursor extends GUIComboBox {
	private Scene myScene;
	
	public PreferencesCursor(ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
    	super(r, gameData, view, r.getString("CursorLabel"), r.getString("CursorOptions"), GUI);
        myScene = view.getScene();
    }
	
	public void performAction() {
		myScene.setCursor(new ImageCursor(new Image(super.getComboBox().getValue())));
	}
}
