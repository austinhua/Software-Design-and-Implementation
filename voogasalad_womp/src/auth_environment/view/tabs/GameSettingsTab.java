package auth_environment.view.tabs;

import java.util.ResourceBundle;
import auth_environment.Models.GameSettingsTabModel;
import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.Models.Interfaces.IGameSettingsTabModel;
import auth_environment.delegatesAndFactories.NodeFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.IMainView;
import utility.CloudStorageFrontend;

/**
 * Created by BrianLin on 3/31/16.
 * Team member responsible: Brian
 *
 * This Tab is for customizing Global Game settings + Saving/Loading. Will need the entire IEngineWorkspace passed
 * in so that it can be saved/loaded. 
 */

public class GameSettingsTab extends Tab implements IWorkspace {

	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);

	private NodeFactory myNodeFactory;
	private IMainView myMainView; 

	private BorderPane myBorderPane;
	private TextField myGameNameField;

	private IGameSettingsTabModel myGameSettingsTabModel;

	public GameSettingsTab(String name, IAuthModel authModel, IMainView mainView) {
		super(name); 
		myMainView = mainView; 
		myGameSettingsTabModel = new GameSettingsTabModel(authModel); 
		init(); 
	}

	private void init() {
		myNodeFactory = new NodeFactory(); 
		setupBorderPane();
	}

	private void setupBorderPane() {
		myBorderPane = new BorderPane(); 
		myBorderPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneWidth")),
				Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneHeight")));
		myBorderPane.setCenter(buildCenter());
		myBorderPane.setBottom(buildBottom());
		setContent(myBorderPane);
	}

	private Node buildCenter() {
		VBox center = myNodeFactory.buildVBox(Double.parseDouble(myDimensionsBundle.getString("defaultVBoxSpacing")), 
				Double.parseDouble(myDimensionsBundle.getString("defaultVBoxPadding")));
		center.getChildren().addAll(
				buildWompImage(),
				buildTextInput(),
				buildButtonRow()
				);
		return center; 
	}

	private Node buildBottom() {
		HBox bottom = myNodeFactory.buildHBox(Double.parseDouble(myDimensionsBundle.getString("defaultHBoxSpacing")),
				Double.parseDouble(myDimensionsBundle.getString("defaultHBoxPadding")));
		bottom.getChildren().addAll(buildChooseScore(), buildChooseWaveGoal(), buildChoosePlaceValidation());
		return bottom; 
	}

	private HBox buildWompImage() {
		return myNodeFactory.centerNode(myNodeFactory.buildImageView(myNamesBundle.getString("wompWelcomeImage")));
	}

	private HBox buildTextInput() {
		myGameNameField = myNodeFactory.buildTextFieldWithPrompt(myNamesBundle.getString("gameNamePrompt"));
		myGameNameField.setOnAction(e -> submitButtonPressed(myGameNameField));

		Button submitNameButton = myNodeFactory.buildButtonWithEventHandler(myNamesBundle.getString("submitButtonLabel"), 
				e -> submitButtonPressed(myGameNameField));

		HBox hb = myNodeFactory.buildHBox(Double.parseDouble(myDimensionsBundle.getString("defaultHBoxSpacing")),
				Double.parseDouble(myDimensionsBundle.getString("defaultHBoxPadding")));
		hb.getChildren().addAll(myGameNameField, submitNameButton);
		return myNodeFactory.centerNode(hb); 
	}

	private Node buildButtonRow() {
		HBox hb = new HBox();
		hb.getChildren().addAll(buildSaveButton(), buildLoadButton(), buildPlayButton(), buildCloudStorage());
		return myNodeFactory.centerNode(hb);
	}

	private Node buildCloudStorage() {
		Button cloud = myNodeFactory.buildButton(myNamesBundle.getString("cloudButtonLabel"));
		cloud.setOnAction(e -> {
			new CloudStorageFrontend(); 
		});
		return cloud;
	}

	private Node buildSaveButton() {
		return myNodeFactory.buildButtonWithEventHandler(myNamesBundle.getString("saveItemLabel"), 
				e -> myGameSettingsTabModel.saveToFile());
	}

	private Node buildLoadButton() {
		return myNodeFactory.buildButtonWithEventHandler(myNamesBundle.getString("loadItemLabel"), 
				e -> myGameSettingsTabModel.loadFromFile());
	}

	private Node buildPlayButton() {
		return myNodeFactory.buildButtonWithEventHandler(myNamesBundle.getString("playButtonLabel"), 
				e -> myMainView.displayPlayer());
	}

	@SuppressWarnings("unchecked")
	private Node buildChooseScore() {
		ComboBox<String> chooseScore = myNodeFactory.buildComboBoxWithEventHandler(myGameSettingsTabModel.getScoreUpdateNames(),
				event -> {
					String selectedItem = ((ComboBox<String>)event.getSource()).getSelectionModel().getSelectedItem();
					myGameSettingsTabModel.chooseScoreUpdate(selectedItem);
					event.consume();
				}); 
		return addToVBox(chooseScore, myNamesBundle.getString("scoreUpdateLabel"));
	}

	@SuppressWarnings("unchecked")
	private Node buildChooseWaveGoal() {
		ComboBox<String> chooseWaveGoal = myNodeFactory.buildComboBoxWithEventHandler(myGameSettingsTabModel.getWaveGoalNames(), event -> {
			String selectedItem = ((ComboBox<String>)event.getSource()).getSelectionModel().getSelectedItem();
			myGameSettingsTabModel.chooseWaveGoal(selectedItem);
			event.consume();});
		return addToVBox(chooseWaveGoal, myNamesBundle.getString("waveGoalLabel"));
	}

	@SuppressWarnings("unchecked")
	private Node buildChoosePlaceValidation() {
		ComboBox<String> choosePlaceValidation = myNodeFactory.buildComboBoxWithEventHandler(myGameSettingsTabModel.getPlaceValidationNames(),
				event -> {
					String selectedItem = ((ComboBox<String>)event.getSource()).getSelectionModel().getSelectedItem();
					myGameSettingsTabModel.choosePlaceValidation(selectedItem);
					event.consume();});
		return addToVBox(choosePlaceValidation, myNamesBundle.getString("placeValidationLabel"));
	}

	private VBox addToVBox(ComboBox<String> cbox, String name){
		VBox vb = new VBox(); 
		vb.getChildren().add(myNodeFactory.buildLabel(name));
		vb.getChildren().add(cbox);
		return vb;
	}

	private void submitButtonPressed(TextField input) {
		if (checkValidInput(input)) {
			myGameSettingsTabModel.setGameName(input.getText());
			input.clear();
		}
	}

	private boolean checkValidInput(TextField input) {
		return input.getText().length() > 0; 
	}

	@Override
	public Node getRoot() {
		return myBorderPane; 
	}
}