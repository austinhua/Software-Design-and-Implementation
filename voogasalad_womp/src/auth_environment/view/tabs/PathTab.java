package auth_environment.view.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import game_engine.game_elements.Branch;
import game_engine.properties.Position;
import auth_environment.Models.PathTabModel;
import auth_environment.Models.UnitView;
import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.Models.Interfaces.IPathTabModel;
import auth_environment.delegatesAndFactories.BrowserWindowDelegate;
import auth_environment.delegatesAndFactories.DragDelegate;
import auth_environment.delegatesAndFactories.NodeFactory;
import auth_environment.view.BoundLine;
import auth_environment.view.PathPoint;
import auth_environment.view.UnitPicker;
import game_data.IGameEnvironment;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PathTab extends Tab implements IWorkspace {

	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);

	private static final String URLS_PACKAGE = "auth_environment/properties/urls";
	private ResourceBundle myURLSBundle = ResourceBundle.getBundle(URLS_PACKAGE);

	private NodeFactory myNodeFactory;

	private BorderPane myBorderPane;
	private TextField myPathWidthField;
	private Pane myPathPane;
	private ComboBox<String> myLevelComboBox;
	private ComboBox<String> myWaveComboBox; 
	private UnitPicker mySpawningUnitPicker; 
	private UnitPicker myPlacingUnitPicker; 
	private List<UnitView> myTerrains;
	private Button toggleGrid; 

	private IPathTabModel myPathTabModel;
	private IGameEnvironment myAuthEnvironment;
	private IAuthModel myAuthModel;
	private int drawingIndex;
	private List<Position> currentBranch;


	public PathTab(String name, IAuthModel auth) {
		super(name); 
		myAuthModel = auth;
		myAuthEnvironment = auth.getIAuthEnvironment();
		init(); 
	}

	private void init() {
		myPathTabModel = new PathTabModel(myAuthEnvironment); 
		myNodeFactory = new NodeFactory(); 
		myTerrains = new ArrayList<>();
		myPathPane = new Pane();
		setupBorderPane();
		currentBranch = new ArrayList<>();
		drawMap();
		setContent(getRoot());
	}

	private void refresh() {
		clearComboBoxes();
		myAuthEnvironment = myAuthModel.getIAuthEnvironment();
		myPathTabModel.refresh(myAuthEnvironment);
		buildLevelComboBox();
		drawMap();
	}

	private void setupBorderPane() {
		myBorderPane = new BorderPane(); 
		setOnSelectionChanged(e -> refresh());
		myBorderPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneWidth")),
				Double.parseDouble(myDimensionsBundle.getString("defaultBorderPaneHeight")));
		myBorderPane.setCenter(buildCenter());
		myBorderPane.setRight(buildRight());
	}

	private Node buildCenter() {
		VBox center = buildDefaultVBox(); 
		center.getChildren().addAll(
				buildTextInput(),
				myPathPane); 
		addClickHandlers();
		return center; 
	}

	private Node buildRight() {
		VBox right = buildDefaultVBox(); 
		right.getChildren().addAll(buildComboBoxes());
		return right; 
	}

	private Node buildComboBoxes() {
		VBox vb = buildDefaultVBox(); 
		myLevelComboBox = new ComboBox<String>();
		myWaveComboBox = new ComboBox<String>(); 
		mySpawningUnitPicker = new UnitPicker(myNamesBundle.getString("spawnUnitsLabel")); 
		myPlacingUnitPicker = new UnitPicker(myNamesBundle.getString("placeUnitsLabel"));
		buildLevelComboBox();
		vb.getChildren().addAll(
				myLevelComboBox, 
				myWaveComboBox, 
				mySpawningUnitPicker.getRoot(),
				myPlacingUnitPicker.getRoot()
				); 
		return vb; 
	}

	private void clearComboBoxes() {
		myLevelComboBox.getItems().clear();
		myWaveComboBox.getItems().clear();
	}

	@SuppressWarnings("unchecked")
	private void buildLevelComboBox() {
		myLevelComboBox.getItems().clear();
		if (!myAuthModel.getIAuthEnvironment().getLevels().isEmpty()) {
			myLevelComboBox.getItems().addAll(myPathTabModel.getLevelNames());
			myLevelComboBox.setOnAction(event -> {
				String selectedItem = ((ComboBox<String>)event.getSource()).getSelectionModel().getSelectedItem();
				buildWaveComboBox(selectedItem);
				event.consume();
			});
		}
		else {
			myLevelComboBox.getItems().add(myNamesBundle.getString("noLevelsLabel")); 
		}
	}

	@SuppressWarnings("unchecked")
	private void buildWaveComboBox(String levelName) {
		myWaveComboBox.getItems().clear();
		if (myPathTabModel.getWaveNames(levelName)!=null) {
			myWaveComboBox.getItems().addAll(myPathTabModel.getWaveNames(levelName));
			myWaveComboBox.setOnAction(event -> {
				String selectedItem = ((ComboBox<String>)event.getSource()).getSelectionModel().getSelectedItem();
				if (selectedItem!=null) {
					buildUnitPicker(selectedItem);
				}
				event.consume();
			});
		}
	}

	private void buildUnitPicker(String waveName) {
		mySpawningUnitPicker.setUnits(myPathTabModel.getSpawningUnits(waveName));
		myPlacingUnitPicker.setUnits(myPathTabModel.getPlacingUnits(waveName));
	}

	private VBox buildDefaultVBox() {
		return myNodeFactory.buildVBox(Double.parseDouble(myDimensionsBundle.getString("defaultVBoxSpacing")),
				Double.parseDouble(myDimensionsBundle.getString("defaultVBoxPadding")));
	}

	private HBox buildTextInput() {
		VBox vb = buildDefaultVBox(); 
		vb.getChildren().addAll(buildFirstRowButtons(), buildSecondRowButtons());
		return myNodeFactory.centerNode(vb); 
	}

	private HBox buildFirstRowButtons() {
		HBox hb0 = myNodeFactory.buildHBox(Double.parseDouble(myDimensionsBundle.getString("defaultHBoxSpacing")), 0);
		myPathWidthField = myNodeFactory.buildTextFieldWithPrompt(myNamesBundle.getString("pathWidthPrompt"));
		myPathWidthField.setOnAction(e -> submitPathWidth(myPathWidthField));
		Button submitBranchButton = myNodeFactory.buildButton(myNamesBundle.getString("submitBranchButtonLabel"));
		submitBranchButton.setOnAction(e -> submitBranch());
		Button help = myNodeFactory.buildButtonWithEventHandler(myNamesBundle.getString("openHelpItem"), 
				e -> {
					BrowserWindowDelegate browser = new BrowserWindowDelegate(); 
					browser.openWindow(myNamesBundle.getString("helpMenuLabel"),
							myURLSBundle.getString("helpURL"),
							Double.parseDouble(myDimensionsBundle.getString("helpWidth")),
							Double.parseDouble(myDimensionsBundle.getString("helpHeight"))
							);
				});
		hb0.getChildren().addAll(
				myPathWidthField, 
				submitBranchButton,
				help
				);
		return hb0; 
	}

	private HBox buildSecondRowButtons() {
		HBox hb1 = myNodeFactory.buildHBox(Double.parseDouble(myDimensionsBundle.getString("defaultHBoxSpacing")), 0);
		Button drawPathButton = myNodeFactory.buildButton(myNamesBundle.getString("drawPath"));
		drawPathButton.setOnAction(e -> updateDrawIndex(0));
		Button drawGoalButton = myNodeFactory.buildButton(myNamesBundle.getString("drawGoal"));
		drawGoalButton.setOnAction(e -> updateDrawIndex(1));
		Button drawSpawnButton = myNodeFactory.buildButton(myNamesBundle.getString("drawSpawn"));
		drawSpawnButton.setOnAction(e -> updateDrawIndex(2));
		toggleGrid = myNodeFactory.buildButtonWithEventHandler("Grid", e -> {
			this.myPathTabModel.createGrid();
			refresh(); 
			toggleGrid.setDisable(true);
		});
		hb1.getChildren().addAll(
				drawPathButton, 
				drawSpawnButton,
				drawGoalButton,
				toggleGrid
				);
		return hb1;
	}

	private void updateDrawIndex(int index) {
		drawingIndex = index;
	}

	private void submitPathWidth(TextField input) {
		if (checkValidInput(input)) {
			myPathTabModel.setPathWidth(Double.parseDouble(input.getText()));
			input.clear();
		}
	}

	private void submitBranch() {
		myPathTabModel.submitBranch();
		currentBranch.clear();
		drawMap();
	}

	private boolean checkValidInput(TextField input) {
		return input.getText().length() > 0; 
	}

	private void drawBranch(Branch branch) {
		displayEndPoint(branch.getFirstPosition());
		displayEndPoint(branch.getLastPosition());
		Position lastPosDrawn = branch.getFirstPosition();
		for(Position currPos : branch.getPositions()) {
			addBoundLine(
					lastPosDrawn.getX(), 
					lastPosDrawn.getY(), 
					currPos.getX(), 
					currPos.getY(),
					branch);
			lastPosDrawn = currPos;
			displayPoint(currPos);
		}
	}

	private void clearMap() {
		myPathPane.getChildren().clear();
		myTerrains.clear();
	}

	private void drawMap() {
		clearMap();
		drawTerrains();
		drawBranches();
		drawSpawns();
		drawGoals();
		drawCurrentBranch();
	}

	private void drawTerrains() {
		if(!myAuthEnvironment.getPlacedUnits().isEmpty()) {
			myAuthEnvironment.getPlacedUnits().stream().forEach(e -> {
				UnitView temp = new UnitView (e, e.toString() + myNamesBundle.getString("defaultImageExtension"));
				myTerrains.add(temp);
			});
			myPathPane.getChildren().addAll(myTerrains);
		}
	}

	private void drawBranches() {
		myPathTabModel.getAuthBranches().stream().forEach(b -> drawBranch(b));
		myPathTabModel.getAuthGrid().stream().forEach(b -> drawBranch(b));
	}

	private void drawSpawns() {
		myPathTabModel.getSpawns().forEach(s -> displaySpawnPoint(s));
	}

	private void drawGoals() {
		myPathTabModel.getGoals().forEach(s -> displayGoalPoint(s));
	}

	private void addBoundLine(double startX, double startY, double endX, double endY, Branch branch) {
		BoundLine b = new BoundLine(
				new SimpleDoubleProperty(startX),
				new SimpleDoubleProperty(startY),
				new SimpleDoubleProperty(endX),
				new SimpleDoubleProperty(endY));
		myPathTabModel.saveBranch(b, branch);
		b.setOnMouseClicked(e -> myPathTabModel.reselectBranch(b)); 
		myPathPane.getChildren().add(b); 
	}

	private void addClickHandlers() {
		myPathPane.setOnMouseClicked(e -> {
			if (e.isControlDown()) {
				if(drawingIndex == 0) {
					addPosition(e.getX(), e.getY());
					currentBranch.add(new Position(e.getX(), e.getY()));
				}
				else if (drawingIndex == 1) {
					addGoalPoint(e.getX(), e.getY());
				}
				else if (drawingIndex == 2) {
					addSpawnPoint(e.getX(), e.getY());
				}
				drawMap();
			}
		});
	}

	private void drawCurrentBranch() {
		currentBranch.forEach(p -> displayClickedPoint(p));
	}

	private void addPosition(double x, double y) {
		myPathTabModel.addNewPosition(x, y);
	}

	private void addSpawnPoint(double x, double y) {
		myPathTabModel.addNewSpawn(x, y);
	}

	private void addGoalPoint(double x, double y) {
		myPathTabModel.addNewGoal(x, y);
	}

	private void displayEndPoint(Position p) {
		PathPoint point = new PathPoint(p, myPathTabModel.getPathWidth()); 
		point.getCircle().setStroke(Color.BLACK);
		point.getCircle().setFill(Color.GREY);
		point.getCircle().setOnMouseClicked(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				if(e.getClickCount() == 2) {
					displayClickedPoint(p);
					addPosition(point.getPosition().getX(), point.getPosition().getY());
					currentBranch.add(point.getPosition());
				}
			}
		});
		myPathPane.getChildren().add(point.getCircle());
	}

	private void displayClickedPoint(Position p) {
		PathPoint point = new PathPoint(p, myPathTabModel.getPathWidth()); 
		point.getCircle().setStroke(Color.BLACK);
		point.getCircle().setFill(Color.RED);
		myPathPane.getChildren().add(point.getCircle());
	}

	private void displaySpawnPoint(Position spawn) {
		PathPoint point = new PathPoint(spawn, myPathTabModel.getPathWidth()); 
		point.getCircle().setStroke(Color.BLACK);
		point.getCircle().setFill(Color.BLUE);
		DragDelegate drag = new DragDelegate();
		drag.setUpNodeTarget(point, mySpawningUnitPicker, myPlacingUnitPicker, myPathTabModel);
		myPathPane.getChildren().add(point.getCircle());
	}

	private void displayGoalPoint(Position goal) {
		PathPoint point = new PathPoint(goal, myPathTabModel.getPathWidth()); 
		point.getCircle().setStroke(Color.BLACK);
		point.getCircle().setFill(Color.GREEN);
		myPathTabModel.addGoalToActiveLevel(point.getPosition());
		myPathPane.getChildren().add(point.getCircle());
	}

	private void displayPoint(Position p) {
		PathPoint point = new PathPoint(p, 1.0);
		point.getCircle().setStroke(Color.BLACK);
		myPathPane.getChildren().add(point.getCircle()); 
	}

	@Override
	public Node getRoot() {
		return myBorderPane;
	}

}