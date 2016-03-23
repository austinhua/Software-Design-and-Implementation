package graphics;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import simulation.Simulation;

class TriangularGridGraphics extends AbstractGridGraphics {
	private double baseLength;
	private double height;
	
	public TriangularGridGraphics(Simulation sim, double gridWidth, double gridHeight, Group parent) {
		super(sim, gridWidth, gridHeight, parent);
	}

	protected double[] getXPoints(int xCoord, int yCoord) {
		double[] xPoints = new double[3];
		double xStart = xCoord * .5*baseLength;
		if (isUpsideDown(xCoord, yCoord)) {
			xPoints[0] = xStart;
			xPoints[1] = xStart + baseLength;
			xPoints[2] = xStart + .5*baseLength;
		}
		else {
			xPoints[0] = xStart + .5*baseLength;
			xPoints[1] = xStart + baseLength;
			xPoints[2] = xStart;
		}
		return xPoints;
	}
	
	protected double[] getYPoints(int xCoord, int yCoord) {
		double[] yPoints = new double[3];
		double yStart = yCoord * height;
		if (isUpsideDown(xCoord, yCoord)) {
			yPoints[0] = yStart;
			yPoints[1] = yStart;
			yPoints[2] = yStart +  height;
		}
		else {
			yPoints[0] = yStart;
			yPoints[1] = yStart + height;
			yPoints[2] = yStart +  height;
		}
		return yPoints;
	}
	
	public boolean isUpsideDown(int xCoord, int yCoord) {
		Point2D shift = getShift();
		return (xCoord + yCoord + shift.getX() + shift.getY()) % 2 == 0;
	}
	
	@Override
	protected void updateCellSizeCalculations() {
		baseLength = getGridWidth()/((getNumCols() + 1.) /2);
		height = getGridHeight() / getNumRows();
	}

	@Override
	public Point2D findCellCoordPressed(double xLoc, double yLoc) {
		int shiftedYCoord = (int)(yLoc / height);
		
		int possibleXCoord = (int)(xLoc / (.5*baseLength));
		double yDistFromTopOfCell = yLoc - shiftedYCoord*height;
		double xDistFromLeftOfCell = xLoc - possibleXCoord * .5*baseLength;
		double triangleSlope = height / (.5*baseLength);

		double difference;
		if (isUpsideDown(possibleXCoord, shiftedYCoord)) {
			difference = triangleSlope * xDistFromLeftOfCell - yDistFromTopOfCell;
		}
		else {
			difference = yDistFromTopOfCell - (height - triangleSlope * xDistFromLeftOfCell);
		}
		if (difference < 0) {
			possibleXCoord--;
		}
		int shiftedXCoord = possibleXCoord;
		
		
		int trueXCoord = shiftedXCoord - getXShift();
		int trueYCoord = shiftedYCoord - getYShift();
		return new Point2D(trueXCoord, trueYCoord);
	}

}
