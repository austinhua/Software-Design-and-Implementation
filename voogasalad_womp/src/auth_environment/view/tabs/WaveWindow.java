package auth_environment.view.tabs;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import auth_environment.Models.WaveWindowModel;
import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.Models.Interfaces.ILevelOverviewTabModel;
import auth_environment.Models.Interfaces.IWaveWindowModel;
import auth_environment.delegatesAndFactories.NodeFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WaveWindow {
	
	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);
	
	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private GridPane myLeftGridPane;
	private GridPane myRightGridPane;
	private BorderPane myBorderPane; 
	
	private List<ComboBox<String>> spawningNames;
	private List<ComboBox<String>> placingNames;
	private List<TextField> spawningTimes;
	private List<String> sn = new ArrayList<>();
	private List<Integer> st = new ArrayList<>();
	private List<String> pn = new ArrayList<>();
	
	private IAuthModel myAuthModel;
	private IWaveWindowModel myWaveWindowModel; 
	private ILevelOverviewTabModel myLevelOverviewTabModel; 

	private Stage stage;
	private Group root;
	private Scene newScene;
	private final int timeBeforeWave = 4;
	
	private NodeFactory myNodeFactory; 
		
	public WaveWindow(String level, String wave, IAuthModel authModel, ILevelOverviewTabModel levelOverview){
		this.myAuthModel = authModel;
		this.myLevelOverviewTabModel = levelOverview; 
		this.myWaveWindowModel = new WaveWindowModel(authModel.getIAuthEnvironment().getUnitFactory().getUnitLibrary(),
				this.myLevelOverviewTabModel); 
		init();
		String title = level + ", " + wave;
		showWindow(level, wave, title);
		int index = 0;
		Button dummyButton = new Button();
		ComboBox<String> dummyCBox = new ComboBox<>(); 
		dummyCBox.setValue(myNamesBundle.getString("waveDummyBox"));
		addNewElementSpace(index, myLeftGridPane, dummyButton, dummyCBox, true);
		addNewElementSpace(index, myRightGridPane, dummyButton, dummyCBox, false);
		Button ok = new Button(myNamesBundle.getString("waveOk"));
		myBorderPane.setBottom(ok);
		String levelNum = level.split(" ")[1]; 
		String waveNum = wave.split(" ")[1];
		ok.setOnAction(e -> createNewWave(title, levelNum + " " + waveNum));
	}
	
	private void showWindow(String level, String wave, String title){
		stage.setTitle(title);
		stage.show();
		centerStage(stage);
	}
	
	private void init(){
		this.myNodeFactory = new NodeFactory(); 
		this.spawningNames = new ArrayList<>();
		this.placingNames = new ArrayList<>();
		this.spawningTimes = new ArrayList<>();
		stage = new Stage();
		root = new Group();
		newScene = new Scene(root);
		stage.setScene(newScene);
		myLeftGridPane = new GridPane();
		myRightGridPane = new GridPane();
		myBorderPane = new BorderPane();
		myBorderPane.setLeft(myLeftGridPane);
		myBorderPane.setRight(myRightGridPane);
		root.getChildren().add(myBorderPane);
	}
	
	private void createNewWave(String title, String level) {
		spawningNames.stream().forEach(s -> sn.add(s.getValue()));
		placingNames.stream().forEach(s -> pn.add(s.getValue()));
		spawningTimes.stream().forEach(s -> st.add(Integer.parseInt(s.getText())));
		sn.removeIf(e -> e == null);
		pn.removeIf(e -> e == null);
		st.removeIf(e -> e == null);
		this.myWaveWindowModel.createWave(title, level, sn, st, pn, timeBeforeWave); 
	}

	private void centerStage(Stage stage){
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
		stage.setMinHeight(Double.parseDouble(myDimensionsBundle.getString("waveStageHeight")));
		stage.setMinWidth(Double.parseDouble(myDimensionsBundle.getString("waveStageWidth")));
	}
	
	private void addNewElementSpace(int index, GridPane newTableInfo, Button dButton, ComboBox<String> cBox, boolean makeInputField){
		if(cBox.getValue() != null){
			newTableInfo.getChildren().remove(dButton);
			ComboBox<String> newcbox = new ComboBox<String>();
			newcbox.getItems().addAll(this.myAuthModel.getIAuthEnvironment().getUnitFactory().getUnitLibrary().getUnitNames());
			newTableInfo.add(addSpawnTimeHBox(makeInputField, newcbox), 2, index);
			index++;
			Button newAffectorButton = new Button(myNamesBundle.getString("waveAddNewElement"));
			int num = index;
			newAffectorButton
			.setOnAction(e -> addNewElementSpace(num, newTableInfo, newAffectorButton,
					newcbox, makeInputField));
			newTableInfo.add(newAffectorButton, 2, index);
			if(makeInputField){
				spawningNames.add(newcbox);
			}
			else{
				placingNames.add(newcbox);
			}
		}
	}
	
	private Node addSpawnTimeHBox(boolean makeSTBox, ComboBox<String> cBox){
		if(makeSTBox){
			HBox hbox = new HBox();
			hbox.getChildren().add(cBox);
			TextField input = this.myNodeFactory.buildTextFieldWithPrompt("Delay");
			input.setMaxWidth(Double.parseDouble(myDimensionsBundle.getString("waveInputWidth")));
			input.setMinHeight(Double.parseDouble(myDimensionsBundle.getString("waveInputHeight")));
			hbox.setMinWidth(Double.parseDouble(myDimensionsBundle.getString("waveBoxWidth")));
			hbox.getChildren().add(input);
			spawningTimes.add(input);
			return hbox;
		}
		else{
			return cBox;
		}	
	}
	
}