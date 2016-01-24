import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.*;


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
	
	private String DEFAULT_FRIENDLY_IMAGE = "HumanTCell.png";
	private String DEFAULT_ENEMY_IMAGE = "PurpleHumanTCell.png";
	
	public GameMap(int width, int height, int numCols, int numRows) {
		this.width = width;
		this.height = height;
		this.numCols = numCols;
		this.numRows = numRows;
		cellWidth = (double)width/numCols;
		cellHeight = (double)height/numRows;
	}
	
	public void drawMap(GraphicsContext gc, List<MapElement> mapElements, Group root) {
        makeBackground(gc);
        drawGridLines(gc);
        drawMapElements(gc, mapElements, root);
	}

	// Make a solid color background
	public void makeBackground(GraphicsContext gc) {
		gc.setFill( new Color(1.0, 0.4, 0.31, 1) );
        gc.fillRect(0,0, width, height);
	}

	public void drawGridLines(GraphicsContext gc) {
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
	
	public void drawMapElements(GraphicsContext gc, List<MapElement> mapElements, Group root) {
		root.getChildren().remove(mapElementsGroup);
		mapElementsGroup = new Group();
		root.getChildren().add(mapElementsGroup);
		for (MapElement m : mapElements) {
			// More specific ones should go first
			if (m instanceof Unit) {
				if (((Unit) m).isFriendly()) {
					placeImageAtCell(m, DEFAULT_FRIENDLY_IMAGE, mapElementsGroup);
				}
				else {
					placeImageAtCell(m, DEFAULT_ENEMY_IMAGE, mapElementsGroup);
				}
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
			image.setOpacity(u.getHealth()/100. > .5? u.getHealth()/100. : .5);
		}
		image.setFitHeight(cellHeight*1.25);
		image.setFitWidth(cellWidth*1.25);
		image.setX(m.x()*cellWidth - .125*cellWidth);
		image.setY(m.y()*cellHeight - .125*cellHeight);
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
