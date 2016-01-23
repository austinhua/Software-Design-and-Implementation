import java.util.*;
import java.awt.Point;
import java.lang.Math;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Austin Hua
 */
class CellCraft {
    public static final String TITLE = "CellCraft";
    public static final int KEY_INPUT_SPEED = 5;
    private static final double GROWTH_RATE = 1.1;
    private static final int BOUNCER_SPEED = 30;

    private Scene myScene;
    private ImageView myBouncer;
    private Rectangle myTopBlock;
    private Rectangle myBottomBlock;
    
    private GameMap map;
    private Point pointPressed;
    private Point pointReleased;
    private List<Unit> curSelected = new ArrayList<Unit>();

	//private Map<Point, Unit> unitList = new HashMap<Point, Unit>();
	MapElement[][] mapGrid = new MapElement[Main.NUMCOLS][Main.NUMROWS]; // No modifier for ease of use within package because calls must frequently be made (similar to Java.awt.Point)
	
    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (GameMap map) {
    	this.map = map;
        // Create a scene graph to organize the scene
        Group root = new Group();
        // Create a place to see the shapes
        myScene = new Scene(root, map.width(), map.height(), Color.GREY);
        // Add canvas object
        Canvas canvas = new Canvas(map.width(), map.height());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        // draw the map
        map.drawMap(gc);
        
        // Make some shapes and set their properties
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("duke.gif"));
        myBouncer = new ImageView(image);
        
        // x and y represent the top left corner, so center it
        myBouncer.setX(map.width() / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(map.height() / 2  - myBouncer.getBoundsInLocal().getHeight() / 2);
        myTopBlock = new Rectangle(map.width() / 2 - 25, map.height() / 2 - 100, 50, 50);
        myTopBlock.setFill(Color.RED);
        myBottomBlock = new Rectangle(map.width() / 2 - 25, map.height() / 2 + 50, 50, 50);
        myBottomBlock.setFill(Color.BISQUE);
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBouncer);
        root.getChildren().add(myTopBlock);
        root.getChildren().add(myBottomBlock);
        
        setUpInputResponses(myScene);
        
        return myScene;
    }
    
    public void setUpInputResponses(Scene myScene) {
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMousePressed(e -> handleMousePressed(e.getX(), e.getY()));
        myScene.setOnMouseReleased(e -> handleMouseReleased(e.getX(), e.getY()));
    }

    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
        // update attributes
        myBouncer.setX(myBouncer.getX() + BOUNCER_SPEED * elapsedTime);
        myTopBlock.setRotate(myBottomBlock.getRotate() - 1);
        myBottomBlock.setRotate(myBottomBlock.getRotate() + 1);
        
        // check for collisions
        // with shapes, can check precisely
        Shape intersect = Shape.intersect(myTopBlock, myBottomBlock);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            myTopBlock.setFill(Color.MAROON);
        }
        else {
            myTopBlock.setFill(Color.RED);
        }
        // with images can only check bounding box
        if (myBottomBlock.getBoundsInParent().intersects(myBouncer.getBoundsInParent())) {
            myBottomBlock.setFill(Color.BURLYWOOD);
        }
        else {
            myBottomBlock.setFill(Color.BISQUE);
        }
    }


    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                myTopBlock.setX(myTopBlock.getX() + KEY_INPUT_SPEED);
                break;
            case LEFT:
                myTopBlock.setX(myTopBlock.getX() - KEY_INPUT_SPEED);
                break;
            case UP:
                myTopBlock.setY(myTopBlock.getY() - KEY_INPUT_SPEED);
                break;
            case DOWN:
                myTopBlock.setY(myTopBlock.getY() + KEY_INPUT_SPEED);
                break;
            case ESCAPE:
            	curSelected.clear();
            	break;
            default:
                // do nothing
        }
    }

    private void handleMousePressed (double x, double y) {
    	pointPressed = getCell(x, y);
    }
    
    private void handleMouseReleased (double x, double y) {
    	pointReleased = getCell(x, y);
    	if (curSelected.isEmpty()) {
    		findFriendlyUnitsInRange(pointPressed, pointReleased);
    	}
    }
    
    // Converts screen coordinates to cell coordinates
    private Point getCell(double x, double y) {
    	int xVal = (int)(x/map.cellWidth());
    	int yVal = (int)(y/map.cellHeight());
    	
    	if (xVal < 0) xVal = 0;
    	if (xVal > map.numCols() - 1) xVal = map.numCols() - 1;
    	if (yVal < 0) yVal = 0;
    	if (yVal > map.numRows() - 1) yVal = map.numRows() - 1;
    	
    	return new Point(xVal, yVal);
    }
    
    // Look in the range specified for your units
    private void findFriendlyUnitsInRange(Point p1, Point p2) {
    	for (int i = p1.x; i <= p2.x; i += Math.signum(p2.x - p1.x)) {
    		for (int j = p1.y; j < p2.y; j += Math.signum(p2.y - p1.y)) {
    			if (mapGrid[i][j] instanceof Unit && ((Unit)mapGrid[i][j]).isFriendly()) {
    				curSelected.add((Unit)mapGrid[i][j]);
    			}
    		}
    	}
    }
}
