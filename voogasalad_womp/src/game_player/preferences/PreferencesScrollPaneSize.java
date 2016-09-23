package game_player.preferences;

import java.util.ResourceBundle;

import game_player.GameDataSource;
import game_player.interfaces.IGUIObject;
import game_player.interfaces.IGameView;
import game_player.view.GameCanvas;
import game_player.view.PlayerGUI;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PreferencesScrollPaneSize implements IGUIObject{
	private static final int PANEL_SPACING = 10;
	
	private ResourceBundle myResources;
	private IGameView myView;
	private GameCanvas myCanvas;
	private TextField widthField;
	private TextField heightField;
	
	public PreferencesScrollPaneSize(ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
        myResources = r;
        myView = view;
        myCanvas = myView.getCanvas();
    }

	@Override
	public Node createNode() {
		VBox box = new VBox(PANEL_SPACING);
		widthField = new TextField(myResources.getString("Width"));
		heightField = new TextField(myResources.getString("Height"));
		Button changeDimensions = new Button();
		changeDimensions.setOnAction(e -> changeScrollPane());
		box.getChildren().addAll(widthField, heightField, changeDimensions);
		box.setAlignment(Pos.CENTER);
		return box;
	}

	@Override
	public void updateNode() {

	}
	
	private void changeScrollPane() {
		myCanvas.configureScrollPane(Double.valueOf(widthField.getText()),
				Double.valueOf(heightField.getText()));
	}
}
