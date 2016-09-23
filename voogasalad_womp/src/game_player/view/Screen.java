package game_player.view;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen {
	
	private ResourceBundle myResources;
	private PlayerGUI myGUI;
	private BorderPane myRoot;
	private String screenImage;
	private String screenText;
	private Stage loadStage;
	
	public Screen(ResourceBundle r, PlayerGUI GUI) {
		myResources = r;
		myGUI = GUI;
	}
	
	public Screen(ResourceBundle r, PlayerGUI GUI, String text, String image) {
		this(r, GUI);
		screenText = text;
		screenImage = image;
	}
	
	protected void displayScreen() {
		createRoot();
		loadStage = new Stage();
		Scene scene = new Scene(myRoot);
		loadStage.setScene(scene);
		loadStage.show();
	}
	
	private void createRoot() {
		myRoot = new BorderPane();
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		Text winMessage = new Text(screenText);
		ImageView winImage = new ImageView(new Image(screenImage));
		Button loadNextLevel = new Button(myResources.getString("NextLevelButton"));
		loadNextLevel.setOnAction(e -> loadNextLevel());
		box.getChildren().addAll(winMessage, winImage, loadNextLevel);
		
		myRoot.setCenter(box);
	}
	
	private void loadNextLevel() {
		myGUI.loadNewTab();
		loadStage.hide();
	}
}