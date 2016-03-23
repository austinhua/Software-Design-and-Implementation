package config;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.geometry.Point2D;

/**
 * @author Jiangzhen Yu
 *
 */
public class XMLParser {
	

	private Map<Integer, String> stateCol = new HashMap<Integer, String>();
	private Map<String, Double> configMap = new HashMap<String, Double>();
	private Map<Point2D, Map<String, Double>> cellMap = new HashMap<Point2D, Map<String, Double>>();
	private static final int DIM = 2;
	private String simType, gridType, cellType, cellShape;
	private boolean outlined;
	
//	public static void main(String argv[]) 
//			throws Exception {
//		XMLParser parser = new XMLParser("resources/segregationDefault.xml");
//		parser.getConfigMap();
//		parser.getStateColor();
//	}
//	
    /**
     * Constructor of XMLParser
     * processes the states and configs of a simulation file
     * 
     * @param fileName
     *            the simulation file to parse
     * 
     */	
    public XMLParser(String fileName)
    		throws Exception {
        Document doc = createDocument(fileName);
        processType(doc);
        processSet(doc, XMLWriter.STATE_TAG); 
        processSet(doc, XMLWriter.CONFIG_TAG);
        processSet(doc, XMLWriter.CELL_TAG);
    }
    
    
    //-------------- PUBLIC GETTERS OF THE FIELDS-----------   
    public String getSimType() {
    	return simType;
    }
    
    public String getGridType() {
    	return gridType;
    }
 
    
    public String getCellType() {
    	return cellType;
    }

    public Map<Integer, String> getStateColor() {
        //print stateCol
    	/*
        for (String state: stateCol.keySet()) {      	
            String value = Arrays.toString(stateCol.get(state)); 
            //System.out.println(state + " " + value);  
        }
        */
        for (Integer state: stateCol.keySet()) {      	
            String value = stateCol.get(state); 
            //System.out.println(state + " " + value);  
        }
    	return stateCol;
    }
    
    public Map<String, Double> getConfigMap() {
    	
        for (String state: configMap.keySet()) {      	
            double value = configMap.get(state); 
            //System.out.println(state + " " + value);  
        }
        
    	return configMap;
    }
 
    public Map<Point2D, Map<String, Double>> getCellMap() {
    	return cellMap;
    }
    
    public String getCellShape() {
    	return cellShape;
    }
    public boolean getOutlined() {
    	return outlined;
    }
    
    //---------------   PARSER TOOLS ---------------------
    
   
    /**
     * Set the type field to the simulation's type
     * 
     * @param doc
     *            an XML simulation document to be parsed	
     */
    private void processType(Document doc) {
    	Element root = doc.getDocumentElement();
    	simType = root.getAttribute(XMLWriter.SIM_TYPE_ATTR);
    	gridType = root.getAttribute(XMLWriter.GRID_TYPE_ATTR);
    	cellType = root.getAttribute(XMLWriter.CELL_TYPE_ATTR);
    	cellShape = root.getAttribute(XMLWriter.CELL_SHAPE_ATTR);
        outlined = Integer.parseInt(root.getAttribute(XMLWriter.OUTLINED_ATTR)) == 1;
    }

    
    
    
    /**
     * Process all state, configuration settings of the simulation
     * 
     * @param doc
     * 			an XML simulation document to be parsed	
     * @param tag
     *          a string tag to be parsed
     */
 
    private void processSet(Document doc, String tag) throws Exception{
    	NodeList list = doc.getElementsByTagName(tag);
        for (int i = 0; i < list.getLength(); i++) {
        	Element curElem = (Element) list.item(i);
        	String elementName = curElem.getAttribute(XMLWriter.ATTR);
        	String elementVal = curElem.getTextContent();
        	if (tag == XMLWriter.STATE_TAG) {
        		int stateNum = Integer.parseInt(elementName);
        		stateCol.put(stateNum, elementVal);
        	}
        	else if (tag == XMLWriter.CONFIG_TAG || tag == XMLWriter.PROP_TAG) {
        		double configVal = Double.parseDouble(elementVal);
        		configMap.put(elementName, configVal);
        	}
        	else if (tag == XMLWriter.CELL_TAG) {
        		String[] tokens = elementName.split(XMLWriter.DELIM);
        		if (tokens.length != DIM) {
        			throw new Exception();
        		} 
        		int x = Integer.parseInt(tokens[0]);
        		int y = Integer.parseInt(tokens[1]);
        	    Point2D p = new Point2D(x, y);
        	    Map<String, Double> curCellMap = processCell(curElem, XMLWriter.PROP_TAG);
        		cellMap.put(p, curCellMap);
        	} 
        }   
    }
    
	
    private Map<String, Double> processCell(Element cellElement, String tag) throws Exception{
    	NodeList list = cellElement.getElementsByTagName(tag);
    	Map<String, Double> map = new HashMap<String, Double>(); 
    	
        for (int i = 0; i < list.getLength(); i++) {
        	Element curElem = (Element) list.item(i);
        	String elementName = curElem.getAttribute(XMLWriter.ATTR);
        	String elementVal = curElem.getTextContent();
        	double configVal = Double.parseDouble(elementVal);
        	map.put(elementName, configVal);
        } 
        return map;
    }
       
    

    /**
     * Opens the specified file and creates an XML document object from it which
     * can then be parsed/traversed.
     * 
     * @param fileName
     *            the file to parse
     * @return the XML document corresponding to the specified XML file
     * 
     * @throws FileNotFoundException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Document createDocument(String fileName)
            throws FileNotFoundException, ParserConfigurationException,
            SAXException, IOException {
        InputStream inputStream = new FileInputStream(fileName);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        return doc;
    } 
    
    
    /**
     * Get the number inside a leaf element
     * 
     * @param elem
     * 			an XML leaf element that contains a number
     * @return the number contained in the elem 
     */
    private double getNumber(Element elem) throws InputMismatchException {		
		String valString = elem.getTextContent();
		return Double.parseDouble(valString);
    }
    
    
}
