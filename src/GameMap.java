import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.*;

// Code masterpiece file
// The purpose of this class was to handle 


/** Handles drawing graphics.
 * 
 * @author Austin Hua
 *
 */
public class GameMap {
	private int width;
	private int height;
	private int numCols;
	private int numRows;
	private double cellWidth;
	private double cellHeight;
	private Group mapElementsGroup;
	
	
	public GameMap(int width, int height, int numCols, int numRows) {
		this.width = width;
		this.height = height;
		this.numCols = numCols;
		this.numRows = numRows;
		cellWidth = (double)width/numCols;
		cellHeight = (double)height/numRows;
		
	}
	
	public void drawMap(GraphicsContext gc) {
        makeBackground(gc);
        drawGridLines(gc);
	}

	// Make a solid color background
	private void makeBackground(GraphicsContext gc) {
		gc.setFill( new Color(1.0, 0.4, 0.31, 1) );
        gc.fillRect(0,0, width, height);
	}

	private void drawGridLines(GraphicsContext gc) {
		gc.setLineWidth(1.25);
		// Draw vertical lines
		for (double i = 0; i <= width; i += cellWidth) {
			gc.strokeLine(i, 0, i, height);
		}
		// Draw horizontal lines
		for (double j = 0; j <= height; j += cellHeight) {
			gc.strokeLine(0, j, width, j);
		}
	}
	
	public void drawMapElements(List<MapElement> mapElements, Group root) {
		root.getChildren().remove(mapElementsGroup);
		mapElementsGroup = new Group();
		root.getChildren().add(mapElementsGroup);
		for (MapElement m : mapElements) {
			// More specific ones should go first
			if (m instanceof Nanorobot) {
				placeImageAtCell(m, ((Nanorobot) m).getImageFile(), mapElementsGroup);
			}
			else if (m instanceof Unit) {
				placeImageAtCell(m, ((Unit) m).getImageFile(), mapElementsGroup);
			}
			else {
				Rectangle solidBlock = new Rectangle((m.x()) * cellWidth, (m.y()) * cellHeight, cellWidth, cellHeight);
				solidBlock.setFill(new Color(.4, 1.0, .7, 1.0));
				mapElementsGroup.getChildren().add(solidBlock);
			}
		}
	}
	
	public void placeImageAtCell(MapElement m, String imageName, Group parent) {
		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(imageName)));
		if (m instanceof Unit) {
			Unit u = (Unit)m;
			image.setOpacity(u.getHealth()/(double)u.DEFAULT_HEALTH > .5? u.getHealth()/(double)u.DEFAULT_HEALTH : .5);
		}
//		if (m instanceof Nanorobot) { //Nanorobot.png needs to be made bigger
//			image.setFitHeight(cellHeight*2);
//			image.setFitWidth(cellWidth*2);
//			image.setX(m.x()*cellWidth - .5*cellWidth);
//			image.setY(m.y()*cellHeight - .5*cellHeight);
//		}
		else {
			image.setFitHeight(cellHeight*1.25);
			image.setFitWidth(cellWidth*1.25);
			image.setX(m.x()*cellWidth - .125*cellWidth);
			image.setY(m.y()*cellHeight - .125*cellHeight);
		}
		parent.getChildren().add(image);
	}
	

	
	// Getter Methods
	public int width() { return width; }
	public int height() { return height; }
	public int numCols() { return numCols; }
	public int numRows() { return numRows; }
	public double cellWidth() { return cellWidth; }
	public double cellHeight() { return cellHeight; }
}
