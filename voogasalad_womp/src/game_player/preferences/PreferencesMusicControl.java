package game_player.preferences;

import java.util.ResourceBundle;

import game_player.GameDataSource;
import game_player.interfaces.IGUIObject;
import game_player.interfaces.IGameView;
import game_player.view.PlayerGUI;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class PreferencesMusicControl implements IGUIObject {
	
	private static final int PANEL_SPACING = 10;
	
	private ResourceBundle myResources;
	private MediaPlayer myMusic;
	private PlayerGUI myGUI;
	
	public PreferencesMusicControl(ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
        myResources = r;
        myGUI = GUI;
    }

	@Override
	public Node createNode() {
		VBox box = new VBox(PANEL_SPACING);
		Label musicLabel = new Label(myResources.getString("Music"));
		Button switchMusic = new Button();
		switchMusic.setGraphic(new ImageView(new Image(myResources.getString("MusicNote"))));
		switchMusic.setOnAction(e -> switchGameMusic());
		box.getChildren().addAll(musicLabel, switchMusic);
		box.setAlignment(Pos.CENTER);
		return box;
	}

	@Override
	public void updateNode() {

	}
	
	private void switchGameMusic() {
		myMusic = myGUI.getMusic();
		if (myMusic.getStatus() == Status.PAUSED) {
			myMusic.play();
		} else {
			myMusic.pause();
		}
	}

}
