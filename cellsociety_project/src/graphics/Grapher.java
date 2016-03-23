package graphics;

import java.util.Map;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import simulation.Cell;

import java.util.HashMap;

public class Grapher {
	public static double LINE_WIDTH = 2.0; // default = 1.0
	private static int maxTimeSteps = 10; // maximum number of line points before line starts to compress
	
	private Map<Integer, Polyline> stateData;
	private Graphics myGraphics;
	private Group myParent;
	private double width;
	private double height;
	private int mostRecentTimeStep;
	
	private double xIncr;
	
	public Grapher(Graphics graphics, Group parent, double graphWidth, double graphHeight) {
		myGraphics = graphics;
		myParent = parent;
		width = graphWidth;
		height = graphHeight;
		mostRecentTimeStep = -1;
		Rectangle r = new Rectangle(graphWidth, graphHeight, Color.DARKGRAY);
		myParent.getChildren().add(r);
		stateData = new HashMap<Integer, Polyline>();
		xIncr = graphWidth / maxTimeSteps;
	}
	
	public void addData(int time, Map<Point2D, Cell> grid) {
		if (time <= mostRecentTimeStep) return;
		Map<Integer, Integer> stateCount = getStateCount(grid);
		int totalCellCount = getTotalCount(stateCount);
		for (int state : stateCount.keySet()) {
			Polyline polyline = stateData.containsKey(state) ? stateData.get(state) : makeNewPolyline(state);
			double xVal = time * xIncr;
			double yVal = (1 - stateCount.get(state)/(double)totalCellCount) * height;
			polyline.getPoints().addAll(xVal, yVal);

		}
		for(int state : stateData.keySet()){
			Polyline polyline = stateData.get(state);
			if (!stateCount.containsKey(state)) {
				polyline.getPoints().addAll(time * xIncr, height);
			}
			if (time > maxTimeSteps) {
				double scaleX = maxTimeSteps/(double)time;
				polyline.setScaleX(scaleX); 
				polyline.setTranslateX(polyline.getTranslateX()-polyline.getBoundsInParent().getMinX());
			}
		}
		
	}
	
	private int getTotalCount(Map<Integer, Integer> stateCount) {
		int totalCellCount = 0;
		for (int count : stateCount.values()) {
			totalCellCount += count;
		}
		return totalCellCount;
	}
	
	public Polyline makeNewPolyline(int state) {
		Polyline p = new Polyline();
		myParent.getChildren().add(p);
		p.setStrokeWidth(LINE_WIDTH);
		p.setStroke(Color.web(myGraphics.getColorMap().get(state)));
		stateData.put(state, p);
		return p;
	}
	
	public Map<Integer, Integer> getStateCount(Map<Point2D, Cell> grid) {
		Map<Integer, Integer> stateCount = new HashMap<Integer, Integer>();
		for (Cell c : grid.values()) {
			int state = c.getState();
			stateCount.put(state, stateCount.get(state) == null? 1 : stateCount.get(state) + 1);
		}
		return stateCount;
	}
	
}