import java.util.*;
import java.awt.Point;
import java.lang.Math;
import java.io.*;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.*;


/**
 * Main body of the game's code.
 * 
 * @author Austin Hua
 */
class CellCraft {
    public static final String TITLE = "CellCraft";
    private Scene myScene;
    private Group root = new Group();
    private GraphicsContext gc;
	private GraphicsContext selectionGC;
    private GameMap myMap;
    
    private Point pointPressed;
    private Point pointReleased;
    private List<Unit> curSelected = new ArrayList<Unit>();
    private List<MapElement> mapElements = new ArrayList<MapElement>();
	private MapElement[][] mapGrid; 
	private boolean invincibility = false;
	
	
    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (String mapFileName) {
    	myMap = readMapFromFile(mapFileName);
        myScene = new Scene(root, myMap.width(), myMap.height(), Color.GREY);
        gc = makeNewCanvas();
        selectionGC = makeNewCanvas();
        // draw the map
        myMap.drawMap(gc);
    	myMap.drawMapElements(mapElements, root);
        setUpInputResponses(myScene);
        return myScene;
    }
    
    /**
     * 
     */
    public Scene setUpSplashScreen(String imageName) {
    	Group startup = new Group();
    	Scene splashScreenScene = new Scene(startup, Main.WIDTH, Main.HEIGHT, Color.WHITE);
    	addStartupImage(imageName, startup);
		GraphicsContext ssGC = makeNewCanvas();
		//writeStartupText(ssGC);
		return splashScreenScene;
    }

	private void addStartupImage(String imageName, Group startup) {
		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(imageName)));
    	image.setFitHeight(Main.HEIGHT);
		image.setFitWidth(Main.WIDTH);
		startup.getChildren().add(image);
	}
	
//	private void writeStartupText(GraphicsContext gc) {
//		gc.setFill( Color.RED );
//	    gc.setStroke( Color.BLACK );
//	    gc.setLineWidth(2);
//	    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
//	    gc.setFont( theFont );
//	    gc.fillText( "Hello, World!", 60, 50 );
//	    gc.strokeText( "Hello, World!", 60, 50 );
//	}

	public void step (double elapsedTime) {
    	Iterator<MapElement> it = mapElements.iterator();
    	while (it.hasNext()) {
    		MapElement m = it.next();
    		if(m.isDead()) {
    			if (mapGrid[m.x()][m.y()].equals(m)) {
    				mapGrid[m.x()][m.y()] = null;
    				it.remove();
    			}
    		}
    		else {
    			m.takeAction();
    		}
    	}
    	myMap.drawMapElements(mapElements, root);
		selectionGC.clearRect(0, 0, myMap.width(), myMap.height());
		highlightSelected();
    
    }

    // Return true if successfully added, false otherwise (ex: already a unit at that position)
	private boolean addToGame(MapElement m) {
		if (checkBounds(m.position()) && (mapGrid[m.x()][m.y()] == null || mapGrid[m.x()][m.y()].isFree())) {
			mapGrid[m.x()][m.y()] = m;
			mapElements.add(m);
			return true;
		}
		System.out.printf(m.getClass().toString() + " failed %s" , "a");
		return false;
	}
	
    public boolean checkBounds(Point p) {
    	return (p.x >= 0 && p.y >= 0 && p.x < mapGrid.length && p.y < mapGrid[0].length);
    }
    
    public void setUpInputResponses(Scene myScene) {
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMousePressed(e -> handleMousePressed(e.getX(), e.getY()));
        myScene.setOnMouseReleased(e -> handleMouseReleased(e.getX(), e.getY()));
    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case ESCAPE:
            	deselectAll();
            	break;
            case I:
            	if (invincibility) System.out.println("Invincibility toggled off");
            	else System.out.println("Invincibility toggled on");
            	invincibility = !invincibility;
            	break;
            default:
                // do nothing
        }
    }
    
    public boolean isInvincible() {
    	return invincibility;
    }

    private void handleMousePressed (double x, double y) {
    	pointPressed = getCell(x, y);
    }
    
    private void handleMouseReleased (double x, double y) {
    	pointReleased = getCell(x, y);
    	if (curSelected.isEmpty()) {
    		findFriendlyUnitsInRange(pointPressed, pointReleased);
    	}
    	else { // Whichever grid cell the mouse is released on is the one chosen as the destination
    		setDestinationForSelected(pointReleased);
    	}
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
    				highlightSelected();
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
		selectionGC.clearRect(0, 0, myMap.width(), myMap.height());
    }
    
    private void highlightSelected(){
		selectionGC.setLineWidth(5.0);
		selectionGC.setStroke(Color.CORNFLOWERBLUE);
		for(MapElement m : curSelected) {
			selectionGC.strokeRect(m.x() * myMap.cellWidth(), m.y()*myMap.cellHeight(), myMap.cellWidth(), myMap.cellHeight());
		}
    }
    
    /** Adds a new canvas of the game's WIDTH AND HEIGHT to root and returns it's corresponding GraphicsContext */
	private GraphicsContext makeNewCanvas() {
		Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
        root.getChildren().add(canvas);
		return canvas.getGraphicsContext2D();
	}
    
    private GameMap readMapFromFile(String mapFileName) {
		List<String> lines = new ArrayList<String>();
    	try {
    		Scanner sc = new Scanner(new BufferedReader(new FileReader(mapFileName)));
            while(sc.hasNextLine()) {
            	lines.add(sc.nextLine());
            }         
    	}
    	catch(FileNotFoundException ex) { ex.printStackTrace(); }
    	int numCols = lines.get(0).length();
    	int numRows = lines.size();
    	mapGrid = new MapElement[numCols][numRows];
    	for (int j = 0; j < numRows; j++) {
    		char[] row = lines.get(j).toUpperCase().toCharArray();
    		for (int i = 0; i < row.length; i++) {
    			char c = row[i];
    			switch (c) {
                case 'A':
    				addToGame(new Unit(i, j, mapGrid, true, this)); // Default friendly Unit
                    break;
                case 'Z':
                	addToGame(new Unit(i, j, mapGrid, false, this)); // Default enemy Unit
                    break;
                case 'N':
                	addToGame(new Nanorobot(i, j, mapGrid, true, this)); // Friendly Nanorobot
                	break;
                case 'M':
                	addToGame(new Nanorobot(i, j, mapGrid, false, this)); // Enemy Nanorobot
                	break;
                case 'X':
                	addToGame(new MapElement(i, j, mapGrid, this)); // Obstacle
                	break;

                default:
                    // do nothing
    			}
    		}
    	}
    	return new GameMap(Main.WIDTH, Main.HEIGHT, numCols, numRows);
    }
}
