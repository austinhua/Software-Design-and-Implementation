package config;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import simulation.*;
import graphics.*;


/**
 * @author Jiangzhen Yu
 *
 */
public class Main extends Application{

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	private static Stage myStage;
	private static final String DEFAULT_FILENAME = "resources/saved/gameOfLifeSpecial.xml";
	public static final String SQUARE_CELLS = "square";
	public static final String TRIANGULAR_CELLS = "triangle";
	public static final String HEXAGONAL_CELLS = "hexagon";
	
	
	public static void startSimulation(String fileName) throws Exception {
		Simulation sim;
		XMLParser parser = new XMLParser(fileName);
		
		String simType = parser.getSimType();
		Map<Integer, String> stateCol = parser.getStateColor();
		Map<String, Double> configMap = parser.getConfigMap();
		Map<Point2D, Map<String, Double>> cellMap = parser.getCellMap();
		String gridType = parser.getGridType();
		String cellType = parser.getCellType();
		
		switch (simType) {
		case "segregation":
			sim = new SegregationSimulation(configMap, cellMap, gridType, cellType);
			break;
		case "fire":
			sim = new FireSimulation(configMap, cellMap, gridType, cellType);
			break;
		case "gameOfLife":
			sim = new GameOfLifeSimulation(configMap, cellMap, gridType, cellType);
			break;
		case "predator":
			sim = new PredatorSimulation(configMap, cellMap, gridType, cellType);
			break;
		case "slimeMold":
			sim = new SlimeMoldSimulation(configMap, cellMap, gridType, cellType);
			break;
		default:
			sim = new SegregationSimulation(configMap, cellMap, gridType, cellType);
		}

		Graphics g = new Graphics(sim, myStage, fileName, parser.getCellShape(), parser.getOutlined());
		g.setColorMap(stateCol);
		myStage.setScene(g.init());	
        myStage.setTitle(simType.toUpperCase());       
	}
	
    @Override
    public void start(Stage stage) throws Exception {
    	myStage = stage;
        startSimulation(DEFAULT_FILENAME);
		myStage.show();
    }
    
	public static void main(String[] args) {
		launch(args);
	}

}
