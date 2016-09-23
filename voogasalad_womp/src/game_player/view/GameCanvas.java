package game_player.view;

import java.util.ResourceBundle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class GameCanvas {
	
    private static final String FRONTEND_RESOURCE = "game_player/resources/FrontEnd";
	private static final String AUTH_ENVIRONMENT_PROPERTIES_DIMENSIONS = "auth_environment/properties/dimensions";
	private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 500;

    private Canvas myCanvas;
    private GraphicsContext myGC;
    private ResourceBundle myResources;
    private ResourceBundle dimensionsResource;
    private Pane myRoot;
    private ScrollPane myScrollPane;
    private double paneWidth;
    private double paneHeight;

    public GameCanvas (ResourceBundle r) {
        myResources = r;
        myRoot = new Pane();
        dimensionsResource = ResourceBundle.getBundle(AUTH_ENVIRONMENT_PROPERTIES_DIMENSIONS);
        paneWidth = Double.valueOf(dimensionsResource.getString("canvasWidth"));
        paneHeight = Double.valueOf(dimensionsResource.getString("canvasHeight"));
        myRoot.setPrefSize(paneWidth, paneHeight);
    }

    public ScrollPane createCanvas () {
    	myScrollPane = new ScrollPane();
        myCanvas = new Canvas(paneWidth, paneHeight);
        myGC = myCanvas.getGraphicsContext2D();
        myGC.drawImage(new Image(ResourceBundle.getBundle(FRONTEND_RESOURCE).getString("Background")), 0, 0);
        myRoot.getChildren().add(myCanvas);
        myScrollPane.setContent(myRoot);
        configureScrollPane(CANVAS_WIDTH, CANVAS_HEIGHT);
        return myScrollPane;
    }

    public void updateNode () {

    }
    
    public void configureScrollPane(double width, double length) {
    	myScrollPane.setPrefSize(width, length);
    	myScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    	myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    	myScrollPane.setPannable(true);
    }

    public Canvas getCanvas () {
        return myCanvas;
    }

    public Pane getRoot () {
        return myRoot;
    }
    
    public double getScrollPaneWidth() {
    	return myScrollPane.getWidth();
    }
    
    public double getScrollPaneHeight() {
    	return myScrollPane.getHeight();
    }
}
