package graphics;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;


import simulation.*;

class RectangularGridGraphics extends AbstractGridGraphics{
	double cellWidth;
	double cellHeight;
	
	public RectangularGridGraphics(Simulation sim, double gridWidth, double gridHeight, Group parent) {
		super(sim, gridWidth, gridHeight, parent);
	}

	public void drawCell(int xCoord, int yCoord, Color stateColor, boolean gridOutlineEnabled) {
		double xPos = xCoord * cellWidth;
		double yPos = yCoord * cellHeight;
		gc.setFill(stateColor);
		gc.fillRect(xPos, yPos, cellWidth, cellHeight);
		if (gridOutlineEnabled) {
			gc.strokeRect(xPos, yPos, cellWidth, cellHeight);
		}
	}

	protected double[] getXPoints(int xCoord, int yCoord) { 
		double[] xPoints = new double[4];
		double xStart = xCoord * cellWidth;
		xPoints[0] = xStart;
		xPoints[1] = xStart + cellWidth;
		xPoints[2] = xStart + cellWidth;
		xPoints[3] = xStart;

		return xPoints;
	}

	protected double[] getYPoints(int xCoord, int yCoord) {
		double[] yPoints = new double[4];
		double yStart = yCoord * cellHeight;
		yPoints[0] = yStart;
		yPoints[1] = yStart;
		yPoints[2] = yStart +  cellHeight;
		yPoints[3] = yStart +  cellHeight;
		return yPoints;
	}
	
	@Override
	protected void updateCellSizeCalculations() {
		cellWidth = getGridWidth() / getNumCols();
		cellHeight = getGridHeight() / getNumRows();
	}

	@Override
	public Point2D findCellCoordPressed(double xLoc, double yLoc) {
		int shiftedXCoord = (int)(xLoc / cellWidth);
		int shiftedYCoord = (int)(yLoc / cellHeight);
		int trueXCoord = shiftedXCoord - getXShift();
		int trueYCoord = shiftedYCoord - getYShift();
		return new Point2D(trueXCoord, trueYCoord);
	}
}
