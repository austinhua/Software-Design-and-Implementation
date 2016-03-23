package graphics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;




import simulation.*;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import config.*;
	
public class Graphics {
	public static final double DEFAULT_SPEED = 1.0;
    public static final double SCREEN_WIDTH = 800;
    public static final double SCREEN_HEIGHT = 715; // with .75 grid_width_percentage, 8:6.5 ratio for square package
    private static final double SCREEN_BORDER = .075; 
    private static final double GRID_WIDTH_PERCENTAGE = .75; // Percent of the horizontal space on the screen the grid takes up
    private static final double GRAPH_HEIGHT_PERCENTAGE = .2;
    
    private String fileName;
    private Scene myScene;
    private Group root;
    private Stage myStage;
    private Simulation mySim;
    private AbstractGridGraphics gridGraphic;
    private Grapher graph;
    private Group gridGroup;
    private Group guiGroup;
    private Group graphGroup;
    private Map<Integer, String> colorMap;
    
	private double gridWidth;
	private double gridHeight;
	private boolean gridOutlineEnabled;
	
	private double animationSpeed;
	private Timeline animation;
	private int timeStep;
	
	public Graphics(Simulation sim, Stage stage, String fileName, String cellShape, boolean gridOutlineEnabled) {
		mySim = sim;
		myStage = stage;
		this.fileName = fileName;
		this.gridOutlineEnabled = gridOutlineEnabled;
		colorMap = mySim.getColorMap();
		
		root = new Group();
		initGrid();
		initGUI();
		initGraph();
		myScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.ANTIQUEWHITE);
        myScene.setOnMouseClicked(e -> handleMousePressed(e.getX() - gridGroup.getLayoutX(), e.getY() - gridGroup.getLayoutY()));
		gridGraphic = chooseGridGraphics(cellShape);
		
		timeStep = 0;
		setUpAnimations();
	}
	
	public Scene init() {
		updateGridGraphics();
		graph.addData(timeStep, mySim.getGrid());
		return myScene;
	}
	
	private void updateGridGraphics() {
		gridGraphic.draw(mySim, colorMap, gridOutlineEnabled);
	}
	
	private AbstractGridGraphics chooseGridGraphics(String cellShape) {
		switch(cellShape) {
		case Main.SQUARE_CELLS:
			return new RectangularGridGraphics(mySim, gridWidth, gridHeight, gridGroup);
		case Main.TRIANGULAR_CELLS:
			return new TriangularGridGraphics(mySim, gridWidth, gridHeight, gridGroup);
		case Main.HEXAGONAL_CELLS:
		default:
			return new RectangularGridGraphics(mySim, gridWidth, gridHeight, gridGroup);
		}
	}

	private void setUpAnimations() {
		animationSpeed = DEFAULT_SPEED;	
		animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.millis(1000/animationSpeed), e -> step());
        animation.getKeyFrames().add(frame);
	}

	/**
	 * Positions and attaches the grid relative to the scene and returns the group of the grid
	 */
	private void initGrid() {
		gridGroup = new Group();
		gridGroup.relocate(SCREEN_HEIGHT * SCREEN_BORDER, SCREEN_HEIGHT * SCREEN_BORDER);
		gridWidth = SCREEN_WIDTH * (GRID_WIDTH_PERCENTAGE - SCREEN_BORDER);
		gridHeight = SCREEN_HEIGHT * (1 - 2.5*SCREEN_BORDER - GRAPH_HEIGHT_PERCENTAGE);
		root.getChildren().add(gridGroup);
	}
	/**
	 * Positions and attaches the GUI relative to the scene and returns the group of the GUI
	 */
	private void initGUI() {
		guiGroup = new Group();
		double guiXStart = SCREEN_WIDTH * (GRID_WIDTH_PERCENTAGE + SCREEN_BORDER);
		guiGroup.relocate(guiXStart, SCREEN_HEIGHT * SCREEN_BORDER);
		double guiWidth = SCREEN_WIDTH * (1 - SCREEN_BORDER) - guiXStart;
		double guiHeight = SCREEN_HEIGHT * (1 - 2*SCREEN_BORDER - GRAPH_HEIGHT_PERCENTAGE);
		root.getChildren().add(guiGroup);
		new GUI(this, guiWidth, guiHeight, guiGroup);
	}
	/**
	 * Positions and attaches the graph relative to the scene and returns the group of the graph
	 */
	private void initGraph() {
		graphGroup = new Group();
		graphGroup.relocate(SCREEN_WIDTH * SCREEN_BORDER, SCREEN_HEIGHT * (1 - (SCREEN_BORDER + GRAPH_HEIGHT_PERCENTAGE) ) );
		double graphWidth = SCREEN_WIDTH * (1 - 2 * SCREEN_BORDER);
		double graphHeight = SCREEN_HEIGHT * GRAPH_HEIGHT_PERCENTAGE;
		root.getChildren().add(graphGroup);
		graph = new Grapher(this, graphGroup, graphWidth, graphHeight);
	}
	
	private void handleMousePressed(double x, double y) {
		Point2D cellCoord = gridGraphic.findCellCoordPressed(x, y);
		if (!mySim.getGrid().containsKey(cellCoord)) {return;}
		Cell cellPressed = mySim.getGrid().get(cellCoord);
		int cState = cellPressed.getState();
		int newState = (cState + 1 == mySim.getNumStates()) ? 0 : cState + 1;
		cellPressed.setState(newState);
		updateGridGraphics();
	}
	
	public void setColorMap(Map<Integer, String> colorMap) { this.colorMap = colorMap; }
	public Map<Integer, String> getColorMap() { return colorMap; }
	
	//// BUTTONS
	public void step() {
		timeStep++;
		mySim.step();
		graph.addData(timeStep, mySim.getGrid());
		updateGridGraphics();
	}
	public void start() {
		animation.play();
	}
	public void pause() {
		animation.pause();
	}
	public void reset() {
		animation.pause();
		tryToStartSim(fileName);
	}
	public void saveSim() {
		try {
			new XMLWriter(mySim, colorMap, gridOutlineEnabled);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void changeSpeed(double newSpeed) {
		animation.setRate(newSpeed / DEFAULT_SPEED);
	}
	public void loadFile() {
		// animation.pause();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Configuration File");
		
		File fileChosen = fileChooser.showOpenDialog(myStage);
		if (fileChosen != null) {
			fileName = fileChosen.getPath();
			tryToStartSim(fileName);
		}
	}
	
	public void tryToStartSim(String fileName) {
		try {
			Main.startSimulation(fileName);
		} catch (Exception e) {
			printError("Error Opening File", fileName);
			e.printStackTrace();
		}
	}


	
	private void printError(String headerText, String fileName) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(headerText);
		alert.setContentText(fileName);

		alert.showAndWait();
	}
}
