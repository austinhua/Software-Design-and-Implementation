package auth_environment.view.tabs;

import java.util.*;

import auth_environment.FrontEndCreator;
import auth_environment.view.UnitPicker;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public abstract class ElementTab extends Tab{
        
        private static final String LABEL_PACKAGE = "auth_environment/properties/creation_tab_labels";
        private static final ResourceBundle myLabelsBundle = ResourceBundle.getBundle(LABEL_PACKAGE);
        
        private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/creation_tab_dimensions";
        private static final ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);
        
        private int index;
        private FrontEndCreator myCreator;
        private BorderPane myPane;

        public ElementTab(String name) {
                super(name);
                init();
        }
        
        private void init(){
        	this.myPane = new BorderPane();
        	this.setContent(myPane);
        	myCreator = new FrontEndCreator();
        	this.setClosable(false);
        }
        
        public void setUp(){
                reset();
                refresh();
                index = 1;
            myPane.setRight(setUpUnitPicker().getRoot());
            
                BorderPane newBorderPane = new BorderPane();
            myPane.setLeft(setUpTitledPane(newBorderPane));
            setUpAnimation(newBorderPane);
        setUpTableInfo(newBorderPane);
        setUpBottomPane(newBorderPane);
        }
        
        private void setUpTableInfo(BorderPane bp){
        GridPane newTableInfo = setUpGridPane(myLabelsBundle.getString("newTablePaneDim"));
                bp.setLeft(newTableInfo);
                addPropertiesTitle(newTableInfo);
        addFields(newTableInfo);        
        }
        
        private void setUpBottomPane(BorderPane bp){
                GridPane bottomInfo = setUpPane(myLabelsBundle.getString("bottomInfoDim"));
                bp.setBottom(bottomInfo);
                addOkButton(bottomInfo);
        }
        
        private void addPropertiesTitle(GridPane gp){
        Text propertiesTitle = new Text(myLabelsBundle.getString("propertiesText"));
        propertiesTitle.setFont(new Font(20));
        gp.add(propertiesTitle, 0, 0);
        }
        
        private void addOkButton(GridPane gp){
                Button ok = myCreator.createButton(gp, myLabelsBundle.getString("okText"), 0, 2);
                ok.setOnAction(e -> createNewElement());
        }
        
        public abstract void refresh();
        
        public abstract void setUpAnimation(BorderPane bp);
        
        public abstract void addFields(GridPane gp);
        
        public void reset(){
                myPane.getChildren().clear();
        }
        
        public FrontEndCreator getCreator(){
                return myCreator;
        }
        
        public void iterateIndex(){
                index++;
        }
        
        public int getIndex(){
                return index;
        }
        
        public void setIndex(int val){
                index = val;
        }
        
        public ResourceBundle getLabelsBundle(){
                return myLabelsBundle;
        }
        
        public ResourceBundle getDimensionsBundle(){
                return myDimensionsBundle;
        }

        public abstract void createNewElement();
        
        public abstract UnitPicker setUpUnitPicker();
        
        public GridPane setUpGridPane(String s){
        GridPane gridPane = setUpPane(s);
        gridPane.getRowConstraints().addAll(new RowConstraints(Double.parseDouble(myDimensionsBundle.getString("rowConstraintSize"))));
        gridPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("newTableWidth")),
                        Double.parseDouble(myDimensionsBundle.getString("newTableHeight")));    
                return gridPane;
        }
        
        public TitledPane setUpTitledPane(BorderPane bp){
                ScrollPane sp = new ScrollPane();
                TitledPane titledPane= new TitledPane();
                titledPane.setText(myLabelsBundle.getString("newLabel"));
                titledPane.setContent(sp);
                titledPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("newPaneWidth")),
                                Double.parseDouble(myDimensionsBundle.getString("newPaneHeight")));
                titledPane.setCollapsible(false);
                sp.setContent(bp);
                return titledPane;
        }
        
        public GridPane setUpPane(String s){
                GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString(s))),
        		new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString(s+0))),
        		new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString(s+1))),
        		new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString(s+2))));
		return gridPane;
	}	
        
    public void addText(String value, GridPane gp, Map<String, TextField> strTextMap){
        for(String s: Arrays.asList(value.split(getLabelsBundle().getString("regex")))){
            myCreator.createTextLabels(gp, s, getIndex(), 1, Double.parseDouble(getDimensionsBundle().getString("rowConstraintSize")));
            strTextMap.put(s, myCreator.createTextField(gp, getIndex(), 2));
            iterateIndex();
        }
    }
    
    public void addCombo(GridPane gp, String value, Map<String, ComboBox<String>> strComboMap){
		for(String s: Arrays.asList(value.split(getLabelsBundle().getString("regex")))){
			myCreator.createTextLabels(gp, s , getIndex(), 1,
					Double.parseDouble(getDimensionsBundle().getString("rowConstraintSize")));
			ComboBox<String> cb = myCreator.createStringComboBox(gp, Arrays.asList(getLabelsBundle()
					.getString(s).split(getLabelsBundle().getString("regex"))), getIndex(), 2);
			strComboMap.put(s, cb);
			iterateIndex();
		}
    }
	
	public List<String> comboListToStringList(List<ComboBox<String>> comboList){
		List<String> list = new ArrayList<String>();
		comboList.stream().forEach(s-> {if(s.getValue() != null)list.add(s.getValue());});
		return list;
	}
	
	public List<Double> textFieldListToStringList(List<TextField> textFieldList){
		List<Double> list = new ArrayList<>();
    	textFieldList.stream().forEach(s -> {if(!s.getText().equals(""))list.add(Double.parseDouble(s.getText()));});
    	return list;
	}
}