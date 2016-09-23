package auth_environment.view.tabs;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ResourceBundle;

import auth_environment.Models.AnimationTabModel;
import auth_environment.Models.Interfaces.IAnimationTabModel;
import auth_environment.delegatesAndFactories.FileChooserDelegate;
import auth_environment.delegatesAndFactories.NodeFactory;
import game_engine.game_elements.Unit;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnimationLoaderTab{

	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);

	private NodeFactory myNodeFactory;
	
	private Stage myStage;

	private HBox myHBox;

	private IAnimationTabModel myModel; 

	// TODO: create abstract borderpane tab class
	public AnimationLoaderTab() {
		this.myNodeFactory = new NodeFactory();
		this.myModel = new AnimationTabModel(); 
		this.myHBox = new HBox();
		this.setUpScene();
	}
	
	public void setUnit(Unit unit){
		this.myModel.setUnit(unit);
		saveImages();
	}
	
	private void setUpScene(){
		BorderPane bp = this.setupBorderPane();
		Scene scene = new Scene(bp);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	myStage = stage;
	}
	
	public void show(){
    	myStage.show();
	}

	private BorderPane setupBorderPane() {
		BorderPane myBorderPane = new BorderPane();
		myBorderPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneWidth")),
				Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneHeight")));
		myBorderPane.setCenter(this.buildCenter());
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(myHBox);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		myBorderPane.setBottom(scrollPane);
		
		return myBorderPane;
	}

	private HBox makeDoneButton() {
		Button done = this.myNodeFactory.buildButton(this.myNamesBundle.getString("doneImageButton"));
		done.setOnAction(e -> this.closeScene());
		return this.myNodeFactory.centerNode(done);
	}

	private void saveImages(){
		this.myModel.saveFiles();
	}
	
	private void closeScene(){
		myStage.hide();
	}

	private HBox makeAddImageButton() {
		Button addImage = this.myNodeFactory.buildButton(this.myNamesBundle.getString("addImageButton"));
		addImage.setOnAction(e -> this.addImage());
		return this.myNodeFactory.centerNode(addImage); 
	}

	private void addImage() {
		FileChooserDelegate chooser = new FileChooserDelegate(); 
		File file = chooser.chooseImage(this.myNamesBundle.getString("imageChooserTitle"));
		this.myModel.addFile(file);
	
		try {
			Image image = new Image(file.toURI().toURL().toString());
			myHBox.getChildren().add(new ImageView (image));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private Node buildCenter() {
		VBox center = myNodeFactory.buildVBox(Double.parseDouble(myDimensionsBundle.getString("defaultVBoxSpacing")), 
				Double.parseDouble(myDimensionsBundle.getString("defaultVBoxPadding")));
		center.getChildren().addAll(this.makeAddImageButton(), 
				this.makeDoneButton()); 
		return center; 
	}

}
