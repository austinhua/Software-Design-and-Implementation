package auth_environment.view;

import java.util.ResourceBundle;

import auth_environment.delegatesAndFactories.NodeFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.IMainView;

/*
 * Created by BrianLin on 4/6/16.
 * 
 * Team member responsible: Brian
 * 
 * First (interactive) screen displayed to the Developer. Asks for Game name. 
 * 
 */
// TODO: Allow for segue back to main screen. 

public class Welcome {

	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);

	private static final String URLS_PACKAGE = "auth_environment/properties/urls";
	private ResourceBundle myURLSBundle = ResourceBundle.getBundle(URLS_PACKAGE);

	private NodeFactory myNodeFactory = new NodeFactory();
	private Stage myStage = new Stage(); 
	private Scene welcomeScene; 
	private VBox myRoot;
	private IMainView myView; 

	public Welcome(IMainView view) {
		this.myView = view; 
		this.init();
	}

	private void init() {
		this.myRoot = myNodeFactory.buildVBox(Double.parseDouble(myDimensionsBundle.getString("defaultVBoxSpacing")),
				Double.parseDouble(myDimensionsBundle.getString("defaultVBoxPadding"))
				);
		this.welcomeScene = new Scene(this.myRoot);
		this.myRoot.getChildren().addAll(this.buildWompImage(), 
				this.buildSelectionButtons(),
				this.buildAnimation()
				);
		this.myRoot.setStyle("-fx-background-color: #292929;");
		this.myRoot.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneWidth")),
				Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneHeight")));
		this.welcomeScene.getStylesheets().add(myURLSBundle.getString("darkStylesheet"));
		this.myStage.setScene(this.welcomeScene);
		this.myStage.show();
		this.myStage.toFront();
	}

	private HBox buildWompImage() {
		return myNodeFactory.centerNode(myNodeFactory.buildImageView(myNamesBundle.getString("wompWelcomeImage")));
	}

	private HBox buildSelectionButtons() {	
		// TODO: refactor this 
		Button authButton = myNodeFactory.buildButton(myNamesBundle.getString("authButtonLabel"));
		authButton.setOnAction(e -> this.authButtonPressed());
		
		Button playButton = myNodeFactory.buildButton(myNamesBundle.getString("playButtonLabel"));
		playButton.setOnAction(e -> this.playButtonPressed());
		
		HBox hb = myNodeFactory.centerNode(authButton);
		hb.getChildren().add(playButton); 
		return hb;
	}
	
	private ImageView buildAnimation() {
		ImageView animation = myNodeFactory.buildImageView(myNamesBundle.getString("wompAnimation"));
		animation.setFitWidth(Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneWidth")));
		return animation;
	}
	
	private void authButtonPressed() {
		this.myStage.hide();
		this.myView.displayAuth();
	}
	
	private void playButtonPressed() {
		this.myStage.hide();
		this.myView.displayPlayer();
	}
}
