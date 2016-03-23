package graphics;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulation.*;

/**
 * Abstract implementation of an automatically resizing grid. Handles drawing of the grid and the cells in it.
 *
 */

public abstract class AbstractGridGraphics {
	public static final double GRID_LINE_WIDTH = 1.0;
	
	Canvas myCanvas;
	GraphicsContext gc;
	private Simulation mySim;
	private double gridWidth;
	private double gridHeight;
	private int numCols;
	private int numRows;
	private Point2D myShift; // Amount to shift coordinates by so that the grid starts at (0, 0)

	public AbstractGridGraphics(Simulation sim, double gridWidth, double gridHeight, Group parent) {
		mySim = sim;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		measureGrid();

		
		myCanvas = new Canvas(gridWidth, gridHeight);
		gc = myCanvas.getGraphicsContext2D();
		gc.setLineWidth(GRID_LINE_WIDTH);
		parent.getChildren().add(myCanvas);
	}



	public void draw(Simulation sim, Map<Integer, String> colorMap, boolean gridOutlineEnabled) {
		clearGrid();
		if (numCols != mySim.getNumCols() || numRows != mySim.getNumRows()) {
			measureGrid();
		}
		drawCells(sim.getGrid(), colorMap, gridOutlineEnabled);
	}

	public void clearGrid() {
		gc.clearRect(0, 0, gridWidth, gridHeight);
	}

	public void drawCells(Map<Point2D, Cell> cells, Map<Integer,String> colorMap, boolean gridOutlineEnabled) {
		for(Cell c : cells.values()) {
			int cState = c.getState();
			Color stateColor = Color.web(colorMap.get(cState));
			drawCell(c.getX() + getXShift(), c.getY() + getYShift(), stateColor, gridOutlineEnabled);
		}
	}
	
	public void drawCell(int xCoord, int yCoord, Color stateColor, boolean gridOutlineEnabled) {
		gc.setFill(stateColor);
		double[] xPoints = getXPoints(xCoord, yCoord);
		double[] yPoints = getYPoints(xCoord, yCoord);
		gc.fillPolygon(xPoints, yPoints, xPoints.length);
		if (gridOutlineEnabled) {
			gc.strokePolygon(xPoints, yPoints, xPoints.length);
		}
	}
	
	/** Returns x-coordinate points starting from upper left and going clockwise. */
	protected abstract double[] getXPoints(int xCoord, int yCoord);/**
	 /* Returns y-coordinate points starting from upper left and going clockwise. */
	protected abstract double[] getYPoints(int xCoord, int yCoord);
	
	
	public int getNumCols() { return numCols; }
	public int getNumRows() { return numRows; }
	public double getGridWidth() { return gridWidth; }
	public double getGridHeight() { return gridHeight; }
	public Point2D getShift() { return myShift; } 
	public int getXShift() { return (int) myShift.getX(); }
	public int getYShift() { return (int) myShift.getY(); }
	
	public void setNumCols(int numCols) { 
		this.numCols = numCols;
	}
	public void setNumRows(int numRows) { 
		this.numRows = numRows; 
	}
	
	private void measureGrid() {
		this.numCols = mySim.getNumCols();
		this.numRows = mySim.getNumRows();
		updateShift();
		updateCellSizeCalculations();
	}
	
	private void updateShift() {
		myShift = new Point2D(Math.min(0, Math.abs(mySim.getStartX())), Math.min(0, Math.abs(mySim.getStartY())));
	}

	protected abstract void updateCellSizeCalculations();
	
	public abstract Point2D findCellCoordPressed(double xLoc, double yLoc);

}