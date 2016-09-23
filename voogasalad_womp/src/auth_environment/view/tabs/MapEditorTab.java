package auth_environment.view.tabs;

import java.util.ResourceBundle;

import auth_environment.Models.MapEditorTabModel;
import auth_environment.Models.UnitView;
import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.Models.Interfaces.IMapPane;
import auth_environment.delegatesAndFactories.DragDelegate;
import auth_environment.delegatesAndFactories.GridMapPane;
import auth_environment.delegatesAndFactories.NodeFactory;
import auth_environment.delegatesAndFactories.FreeMapPane;
import auth_environment.view.UnitPicker;
import game_data.IGameEnvironment;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MapEditorTab extends Tab implements IWorkspace {	

	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);

	private BorderPane myBorderPane;
	private TitledPane myMapPane;
	private GridMapPane myGridMapPane;
	private FreeMapPane myFreeMapPane;

	private NodeFactory myNodeFactory; 
	private UnitPicker myPicker;
	private DragDelegate myDragDelegate;
	private MapEditorTabModel myModel;
	private IAuthModel myAuthModel;
	private IGameEnvironment myAuth;

	public MapEditorTab(String name, IAuthModel auth) {
		super(name);
		myAuthModel = auth;
		myAuth = auth.getIAuthEnvironment();
		myModel = new MapEditorTabModel(myAuth); 
		init();
	}
	
	private void init() {
		myNodeFactory = new NodeFactory(); 
		myDragDelegate = new DragDelegate();
		buildTerrainChooser();
		buildMapPane();
		buildGridMapPane();
		buildFreeMapPane();
		setupInitialBorderPane();
		setContent(getRoot());
	}
	
	private void setupInitialBorderPane(){
		myBorderPane = new BorderPane(); 
		myBorderPane.setOnMouseEntered(e-> refresh());
		myBorderPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneWidth")),
				Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneHeight")));
		myBorderPane.setRight(myPicker.getRoot());
		myBorderPane.setLeft(buildInitialHBox());
		myBorderPane.setBottom(buildSwitchHBox());
	}

	private void refresh(){
		myAuth = myAuthModel.getIAuthEnvironment();
		myModel.refresh(myAuth);
		myPicker.setUnits(myModel.getTerrains());
		refreshCanvasPane(myFreeMapPane);
		refreshCanvasPane(myGridMapPane);
	}

	public void buildTerrainChooser(){
		if(myModel.getTerrains().equals(null)){
			myPicker = new UnitPicker(myNamesBundle.getString("terrainsLabel"));
		}
		else{
			myPicker = new UnitPicker(myNamesBundle.getString("terrainsLabel"), myModel.getTerrains());
		}
	}

	private void buildMapPane(){
		myMapPane = new TitledPane();
	}

	private void updateMapPane(IMapPane mapPane, String name){
		myMapPane.setText(name);
		myMapPane.setContent(mapPane.getRoot());
		myBorderPane.setLeft(myMapPane);
	}

	private void buildGridMapPane(){
		myGridMapPane = new GridMapPane(myModel, 
				Integer.parseInt(myDimensionsBundle.getString("mapEditorCols")), 
				Integer.parseInt(myDimensionsBundle.getString("mapEditorRows"))
				);
		myGridMapPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("canvasWidth")), 
				Double.parseDouble(myDimensionsBundle.getString("canvasHeight")));
		myDragDelegate.setUpNodeTarget(myGridMapPane, myPicker);
	}

	private void buildFreeMapPane(){
		myFreeMapPane = new FreeMapPane(myModel, Integer.parseInt(myDimensionsBundle.getString("mapEditorCols")));
		myFreeMapPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("canvasWidth")), 
				Double.parseDouble(myDimensionsBundle.getString("canvasHeight")));
		myDragDelegate.setUpNodeTarget(myFreeMapPane, myPicker);
	}

	private Button buildClearButton() {
		Button clear = new Button(myNamesBundle.getString("clearButtonLabel"));
		clear.setOnAction(e -> {
			myFreeMapPane.getChildren().clear();
			myGridMapPane.getChildren().clear();
			myModel.clear();
		});
		return clear;
	}

	private CheckBox buildGridSwitchBox(){
		CheckBox gridSwitch = new CheckBox(myNamesBundle.getString("gridLineLabel"));
		gridSwitch.setTextFill(Color.WHITE);
		gridSwitch.setSelected(true);
		gridSwitch.setOnAction(e -> myGridMapPane.setGridLinesVisible(!myGridMapPane.getRoot().isGridLinesVisible()));
		return gridSwitch;
	}

	private HBox buildInitialHBox() {
		HBox hbox = buildDefaulHBox();

		Button gridOption = buildGridOptionButton();
		Button freeOption = buildFreeOptionButton();

		hbox.getChildren().addAll(gridOption, freeOption);
		return hbox;
	}

	private Button buildGridOptionButton(){
		Button gridOption = new Button("Grid");
		gridOption.setOnAction(e -> updateMapPane(myGridMapPane, myNamesBundle.getString("gridLabel")));
		return gridOption;
	}

	private Button buildFreeOptionButton(){
		Button freeOption = new Button("Free");
		freeOption.setOnAction(e -> updateMapPane(myFreeMapPane, myNamesBundle.getString("freeLabel")));
		return freeOption;
	}

	private HBox buildDefaulHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(myNodeFactory.getInsetsFromProperties(myDimensionsBundle.getString("mapEditorInsets"), " "));
		hbox.setSpacing(Double.parseDouble(myDimensionsBundle.getString("defaultHBoxPadding")));
		return hbox; 
	}

	private HBox buildSwitchHBox() {
		HBox hbox = buildDefaulHBox();

		Button clearButton = buildClearButton();
		CheckBox gridSwitchBox = buildGridSwitchBox();

		hbox.getChildren().addAll(clearButton,gridSwitchBox);
		return hbox;
	}

	public Node getRoot() {
		return myBorderPane;
	}

	public Node getFreeMapPane(){
		return myFreeMapPane;
	}
	
	public void refreshCanvasPane(IMapPane tempMapPane){
		if(!this.myModel.getPlacedUnits().isEmpty()) {
			tempMapPane.getRoot().getChildren().clear();
			this.myModel.getPlacedUnits().stream().forEach(e -> {
				UnitView temp = new UnitView (e, e.toString() + myNamesBundle.getString("defaultImageExtensions"));
				temp.addContextMenu(tempMapPane, temp);
				tempMapPane.addToPane(temp);
			});
		}
	}
}