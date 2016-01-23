import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


// Handles the graphics 
public class GameMap {
	private int width;
	private int height;
	private int numCols;
	private int numRows;
	private double cellWidth;
	private double cellHeight;
	
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
	public void makeBackground(GraphicsContext gc) {
		gc.setFill( new Color(1.0, 0.4, 0.31, .85) );
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
	
	public int width() { return width; }
	public int height() { return height; }
	public int numCols() { return numCols; }
	public int numRows() { return numRows; }
	public double cellWidth() { return cellWidth; }
	public double cellHeight() { return cellHeight; }
}
