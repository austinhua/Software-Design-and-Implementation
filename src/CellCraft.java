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
 * Main body of the game's code.
 * 
 * @author Austin Hua
 */
class CellCraft {
    public static final String TITLE = "CellCraft";
    private Scene myScene;
    private Group root;
    
    private GameMap myMap;
    private GraphicsContext gc;
    private Point pointPressed;
    private Point pointReleased;
    private List<Unit> curSelected = new ArrayList<Unit>();
    private List<MapElement> mapElements = new ArrayList<MapElement>();
	private MapElement[][] mapGrid = new MapElement[Main.NUMCOLS][Main.NUMROWS]; // No modifier for ease of use within package because calls must frequently be made (similar to Java.awt.Point)
	
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
    	myMap = map;
    	addToMap(new Unit(23, 23, mapGrid, true));
        // Create a scene graph to organize the scene
        root = new Group();
        // Create a place to see the shapes
        myScene = new Scene(root, map.width(), map.height(), Color.GREY);
        // Add canvas object
        Canvas canvas = new Canvas(map.width(), map.height());
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        // draw the map
        map.drawMap(gc, mapElements, root);
        
//        // Make some shapes and set their properties
//        Image image = new Image(getClass().getClassLoader().getResourceAsStream("duke.gif"));
//        myBouncer = new ImageView(image);
//        
//        // x and y represent the top left corner, so center it
//        myBouncer.setX(map.width() / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
//        myBouncer.setY(map.height() / 2  - myBouncer.getBoundsInLocal().getHeight() / 2);
//        myTopBlock = new Rectangle(map.width() / 2 - 25, map.height() / 2 - 100, 50, 50);
//        myTopBlock.setFill(Color.RED);
//        // order added to the group is the order in which they are drawn
//        root.getChildren().add(myBouncer);

        
        setUpInputResponses(myScene);
        
        return myScene;
    }

    // Return true if successfully added, false otherwise (ex: already a unit at that position)
	private boolean addToMap(Unit u) {
		if (checkBounds(u.position()) && (mapGrid[u.x()][u.y()] == null || mapGrid[u.x()][u.y()].isFree())) {
			mapGrid[u.x()][u.y()] = u;
			mapElements.add(u);
			return true;
		}
		return false;
	}
	
	private boolean removeFromMap(Unit u) {
		if (mapGrid[u.x()][u.y()].equals(u)) {
			mapElements.remove(u);
			mapGrid[u.x()][u.y()] = null;
			return true;
		}
		return false;
	}
    
    public void setUpInputResponses(Scene myScene) {
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMousePressed(e -> handleMousePressed(e.getX(), e.getY()));
        myScene.setOnMouseReleased(e -> handleMouseReleased(e.getX(), e.getY()));
    }

    public void step (double elapsedTime) {
    	System.out.println("step");
//        // update attributes
//        myTopBlock.setRotate(myBottomBlock.getRotate() - 1);
//        
//        // check for collisions
//        if (intersect.getBoundsInLocal().getWidth() != -1) {
//            myTopBlock.setFill(Color.MAROON);
//        }
    	for (MapElement m : mapElements) {
    		m.takeAction();
    	}
    	myMap.drawMapElements(gc, mapElements, root);
    
    }


    private void handleKeyInput (KeyCode code) {
        switch (code) {
//            case RIGHT:
//                myTopBlock.setX(myTopBlock.getX() + KEY_INPUT_SPEED);
//                break;
//            case LEFT:
//                myTopBlock.setX(myTopBlock.getX() - KEY_INPUT_SPEED);
//                break;
//            case UP:
//                myTopBlock.setY(myTopBlock.getY() - KEY_INPUT_SPEED);
//                break;
//            case DOWN:
//                myTopBlock.setY(myTopBlock.getY() + KEY_INPUT_SPEED);
//                break;
            case ESCAPE:
            	deselectAll();
            	break;
            default:
                // do nothing
        }
    }

    private void handleMousePressed (double x, double y) {
    	pointPressed = getCell(x, y);
    	System.out.println("Pressed: (" + x + ", " + y + ")");
    }
    
    private void handleMouseReleased (double x, double y) {
    	pointReleased = getCell(x, y);
    	System.out.println("Released: (" + x + ", " + y + ")");
    	if (curSelected.isEmpty()) {
    		findFriendlyUnitsInRange(pointPressed, pointReleased);
    	}
    	else { // Whichever grid cell the mouse is released on is the one chosen as the destination
    		setDestinationForSelected(pointReleased);
    	}
    }
    
    private boolean checkBounds(Point p) {
    	return (p.x > 0 && p.y > 0 && p.x < myMap.numCols() && p.y < myMap.numRows());
    }
    
    // Converts screen coordinates to cell coordinates
    private Point getCell(double x, double y) {
    	int xVal = (int)(x/myMap.cellWidth());
    	int yVal = (int)(y/myMap.cellHeight());
    	
    	if (xVal < 0) xVal = 0;
    	if (xVal > myMap.numCols() - 1) xVal = myMap.numCols() - 1;
    	if (yVal < 0) yVal = 0;
    	if (yVal > myMap.numRows() - 1) yVal = myMap.numRows() - 1;
    	
    	return new Point(xVal, yVal);
    }
    
    // Look in the range specified for your units
    private void findFriendlyUnitsInRange(Point p1, Point p2) {
    	int lowerX = Math.min(p1.x, p2.x);
    	int higherX = Math.max(p1.x, p2.x);
    	int lowerY = Math.min(p1.y, p2.y);
    	int higherY = Math.max(p1.y, p2.y);
    	for (int i = lowerX; i <= higherX; i++ ) {
    		for (int j = lowerY; j <= higherY; j++) {
    			if (mapGrid[i][j] instanceof Unit && ((Unit)mapGrid[i][j]).isFriendly()) {
    				Unit selection = (Unit)mapGrid[i][j];
    				selection.select();
    				curSelected.add(selection);			
    			}
    		}
    	}
    }
    
    private void setDestinationForSelected(Point dest) {
    	for (Unit u : curSelected) {
    		u.setDestination(dest);
    	}
    	deselectAll();
    }
    
    private void deselectAll() {
    	for (Unit u : curSelected) {
    		u.deselect();
    	}
    	curSelected.clear();
    }
}
