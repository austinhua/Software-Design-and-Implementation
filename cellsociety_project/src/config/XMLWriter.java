package config;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.geometry.Point2D;
import simulation.*;

public class XMLWriter {
	private Simulation mySim;
	
	private Document doc;
	private Element rootElement;
	private Element stateSetElement;
	private Element configSetElement;
	private Element cellSetElement;
	
	private String type;
	private String version = "Default";
	
	public static final String CELL_SHAPE_ATTR = "cellShape";
	public static final String SIM_TYPE_ATTR = "simType";
	public static final String GRID_TYPE_ATTR = "gridType";
	public static final String CELL_TYPE_ATTR = "cellType";
	public static final String ATTR = "name";
	public static final String OUTLINED_ATTR = "outlined";
	
	
	private static final String SIM_TAG = "simulation";
    private static final String STATE_SET_TAG = "stateSet";
    private static final String CONFIG_SET_TAG = "configSet";
    private static final String CELL_SET_TAG = "cellSet";
    
    public static final String STATE_TAG = "state";
    public static final String CONFIG_TAG = "config";
    public static final String CELL_TAG = "cell";
    public static final String DELIM = ",";
    public static final String PROP_TAG = "cellProperty";
    
    
    private static final String RED = "#be5056";
    private static final String GREEN = "#56be50";
    private static final String YELLOW = "#ffd812";
    private static final String PURPLE = "#955fff";
    private static final String BLACK = "#000000";
    private static final String WHITE = "#ffffff";
    
    public void setVersion(String newVersion) {
    	version = newVersion;
    }
    
    
    private XMLWriter() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory =
        DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = 
           dbFactory.newDocumentBuilder();
        doc = dBuilder.newDocument();
        // root element
        rootElement = doc.createElement(SIM_TAG);
        doc.appendChild(rootElement);	
        
        
        stateSetElement = attachChildElement(rootElement, STATE_SET_TAG);
    	configSetElement = attachChildElement(rootElement, CONFIG_SET_TAG);
    	cellSetElement = attachChildElement(rootElement, CELL_SET_TAG);
    	
	}
    
   public XMLWriter(Simulation sim, Map<Integer, String> colorMap, boolean gridLinesEnabled) throws ParserConfigurationException, TransformerException {
	   XMLWriter w = new XMLWriter();
	   mySim = sim;
	   w.saveCellMap(mySim.getGrid());
	   w.saveMap(STATE_TAG, colorMap);
	   w.saveMap(CONFIG_TAG, mySim.getConfigMap());
	   w.setVersion("Save");
	   w.setType(SIM_TYPE_ATTR, sim.getType());
	   w.setType(OUTLINED_ATTR, gridLinesEnabled? "1" : "0");
	   w.generateXML();
   }
    
    private Element attachChildElement(Element parent, String childTagName) {
        Element childElement = doc.createElement(childTagName);
        parent.appendChild(childElement);
        return childElement;
    }
    
    public void setType(String attrName, String attrType) {
        Attr attr = doc.createAttribute(attrName);
        attr.setValue(attrType);
        rootElement.setAttributeNode(attr);
        if (attrName.equals(SIM_TYPE_ATTR)) {
        	type = attrType;
        }
    }
    
    public void saveCellMap(Map<Point2D, Cell> cellMap) {
        for (Point2D point: cellMap.keySet()) {
        	Cell cell = cellMap.get(point);
        	Element cellElement = createCellElement(cell);

        	cellSetElement.appendChild(cellElement);
        }
    }    
    
    
    //Save stateCol, or configMap
    public void saveMap(String tagName, Map<?, ?> map) {
    	Element parent;    	
    	if (tagName.equals(STATE_TAG)) {
    		parent = stateSetElement;
    	} else {   		
    		parent = configSetElement;
    	}
        for (Object key: map.keySet()) {
            Element element = createLeaf(tagName, key.toString(), map.get(key));
            parent.appendChild(element);
        }
    }
    
    
    
    private Element createElement(String tagName, String attrVal) {
        Element element = doc.createElement(tagName);
        Attr attrType = doc.createAttribute(ATTR);
        attrType.setValue(attrVal);
        element.setAttributeNode(attrType);
        return element;
    }
    
    
    private Element createLeaf(String tagName, String attrVal, Object elementVal) {
    	Element element = createElement(tagName, attrVal);
    	String valString = String.valueOf(elementVal);
    	element.appendChild(doc.createTextNode(valString));
    	return element;
    }
    
    
    /*
     * Create a cell node with its state and properties as subnode
     */
    private Element createCellElement(Cell cell) {
    	String coorString = cell.getX() + DELIM + cell.getY();
    	Element cellElement = createElement(CELL_TAG, coorString);
    	Element propLeaf = createLeaf(PROP_TAG, STATE_TAG, cell.getState());
    	cellElement.appendChild(propLeaf);
    	Map<String, Double> map = cell.getProperties();
    	for (String key: map.keySet()) {
    		propLeaf = createLeaf(PROP_TAG, key, map.get(key));
    		cellElement.appendChild(propLeaf);
    	}
    	return cellElement;
    }
    

	private void generateXML() throws TransformerException {
        TransformerFactory transformerFactory =
        TransformerFactory.newInstance();
        Transformer transformer =
        transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result =
        new StreamResult(new File("resources/saved/"+ type + version + ".xml"));
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult =
        new StreamResult(System.out);
        transformer.transform(source, consoleResult);		
	}
/*
	public static void main(String argv[]) {
		try {
			XMLWriter w = new XMLWriter();
			w.setType(SIM_TYPE_ATTR, "segregation");
			w.setType(GRID_TYPE_ATTR, "finite");
			w.setType(CELL_TYPE_ATTR, Main.SQUARE_CELLS);
			w.setType(CELL_SHAPE_ATTR, Main.SQUARE_CELLS);
			w.setType(OUTLINED_ATTR, "1");
			
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "0", RED));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "1", GREEN));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "2", YELLOW));
	    	
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfRows", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfCols", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "satisfactionThreshold", "0.3"));

		    w.generateXML();  
		    
		    
			w = new XMLWriter();
			w.setType(SIM_TYPE_ATTR, "fire");
			w.setType(GRID_TYPE_ATTR, "finite");
			w.setType(CELL_TYPE_ATTR, Main.SQUARE_CELLS);
			w.setType(CELL_SHAPE_ATTR, Main.SQUARE_CELLS);
			w.setType(OUTLINED_ATTR, "0");
			
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "0", RED));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "1", YELLOW));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "2", PURPLE));
	    	
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfRows", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfCols", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numStates", "3"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "probCatch", ".3"));

	    	
		    w.generateXML(); 

		    
			w = new XMLWriter();
			w.setType(SIM_TYPE_ATTR, "predator");
			w.setType(GRID_TYPE_ATTR, "finite");
			w.setType(CELL_TYPE_ATTR, Main.SQUARE_CELLS);
			w.setType(CELL_SHAPE_ATTR, Main.SQUARE_CELLS);
			w.setType(OUTLINED_ATTR, "1");
			
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "0", WHITE));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "1", YELLOW));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "2", GREEN));
	    	
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfRows", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfCols", "50"));

	    	
		    w.generateXML(); 
	
		    
			w = new XMLWriter();
			w.setType(SIM_TYPE_ATTR, "gameOfLife");
			w.setType(GRID_TYPE_ATTR, "finite");
			w.setType(CELL_TYPE_ATTR, Main.SQUARE_CELLS);
			w.setType(CELL_SHAPE_ATTR, Main.SQUARE_CELLS);
			w.setType(OUTLINED_ATTR, "1");
			
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "0", WHITE));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "1", BLACK));
	    	
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfRows", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfCols", "50"));

	    	
		    w.generateXML();
		    
		    
			w = new XMLWriter();
			w.setType(SIM_TYPE_ATTR, "slimeMold");
			w.setType(GRID_TYPE_ATTR, "toro");
			w.setType(CELL_TYPE_ATTR, Main.HEXAGONAL_CELLS);
			w.setType(CELL_SHAPE_ATTR, Main.SQUARE_CELLS);
			w.setType(OUTLINED_ATTR, "0");
			
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "0", RED));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "1", GREEN));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "2", YELLOW));
	    	
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfRows", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfCols", "50"));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "cAMPAmount", "0.3"));
	    	Element  propLeaf;
	    	for (int i = 0; i < 50; i++) {
	    		for (int j = 0; j < 50; j++) {
	    			String coorString = i + DELIM + j;
	    	    	Element cellElement = w.createElement(CELL_TAG, coorString);
	    	    	    int fullAmount = (int)(Math.random() * 10 + 1);
	    	    	    propLeaf = w.createLeaf(PROP_TAG, "fullcAMPCapacity", fullAmount);
	    	    	    cellElement.appendChild(propLeaf);
	    	    	    int cAmount = (int)(Math.random() * fullAmount);
	    	    	    propLeaf = w.createLeaf(PROP_TAG, "cAMPAmount", cAmount);
	    	    		cellElement.appendChild(propLeaf);
	    	    		w.cellSetElement.appendChild(cellElement);
	    		}
	    	}
		    
		    w.generateXML(); 
		    
			w = new XMLWriter();
			w.setType(SIM_TYPE_ATTR, "sugerScape");
			w.setType(GRID_TYPE_ATTR, "finite");
			w.setType(CELL_TYPE_ATTR, Main.SQUARE_CELLS);
			w.setType(CELL_SHAPE_ATTR, Main.SQUARE_CELLS);
			w.setType(OUTLINED_ATTR, "0");
			
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "0", RED));
	    	w.stateSetElement.appendChild(w.createLeaf(STATE_TAG, "1", YELLOW));
	    	
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfRows", 51));
	    	w.configSetElement.appendChild(w.createLeaf(CONFIG_TAG, "numOfCols", 51));


	    	
		    w.generateXML(); 

		} catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
*/
}
