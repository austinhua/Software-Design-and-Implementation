package auth_environment.view.tabs;

import java.util.*;

import auth_environment.Models.ElementTabModel;
import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.Models.Interfaces.IElementTabModel;
import auth_environment.view.UnitPicker;
import game_engine.factories.UnitFactory;
import game_engine.game_elements.Unit;
import javafx.scene.control.*;
import javafx.scene.layout.*;
public class UnitTab extends ElementTab{
	 
	private Map<String, TextField> strTextMap;
	private Map<String, ComboBox<String>> strComboMap;
	private List<ComboBox<String>> affectorsToUnit;
	private List<ComboBox<String>> affectorsToApply;
	private List<ComboBox<String>> myProjectiles;
	private List<String> affectorNames;
	private List<String> unitNames;
	private AnimationPane myAnimationPane;
	
	private Button childButton;
	private Button affectorButton;
	private Button applyButton;

	private IElementTabModel myElementTabModel;
	private IAuthModel myAuthModel;

	public UnitTab(String name, IAuthModel authModel){
		super(name);
		this.myAuthModel = authModel;
		this.myElementTabModel = new ElementTabModel(authModel.getIAuthEnvironment()); 
		addRefresh();
		setUp();
	}
	
	public void refresh(){
        setIndex(1);
		refreshLists();
		
		init();
	}
	
	private void init(){
		strComboMap = new HashMap<String, ComboBox<String>>();
		strTextMap = new HashMap<String, TextField>();
		affectorsToUnit = new ArrayList<>();
		affectorsToApply = new ArrayList<>();
		myProjectiles = new ArrayList<>();
		myAnimationPane = new AnimationPane();
	}
	
	private void addRefresh(){
		this.setOnSelectionChanged(l -> setUp());
	}
	
	public UnitPicker setUpUnitPicker(){
	    UnitPicker up = new UnitPicker(getLabelsBundle().getString("editLabel"),
	    		this.myElementTabModel.getUnitFactory().getUnitLibrary().getUnits());
	    up.setClickable(this);
	    return up;
	}
	
	public void setUpAnimation(BorderPane gp){
		gp.setTop(myAnimationPane.getRoot());
	}
	
	private void addTextFields(GridPane gp){
    	addText(getLabelsBundle().getString("unitTextProperties"), gp, strTextMap);
	}
	
	private void addComboFields(GridPane gp){
    	addCombo(gp, getLabelsBundle().getString("unitComboProperties"), strComboMap);
	}

	public void createNewElement() {
		try{
			Map<String, List<Double>> strToDoubleMap = new HashMap<String, List<Double>>();	
			String name = strTextMap.remove(getLabelsBundle().getString("typeText")).getText();

			for(String str: strTextMap.keySet()){
				List<Double> val = new ArrayList<>();  		
				String[] strings = strTextMap.get(str).getText().trim().split(getLabelsBundle().getString("regex"));
				Arrays.asList(strings).stream().forEach(s -> val.add(Double.parseDouble(s)));
				strToDoubleMap.put(str, val);
			}
    	
			ComboBox<String> cb = strComboMap.get(getLabelsBundle().getString("stateText"));
			List<Double> list = new ArrayList<Double>();
			list.add((double)cb.getItems().indexOf(cb.getValue()));
			strToDoubleMap.put(getLabelsBundle().getString("stateText"), list);
  	
			createUnit(name, strToDoubleMap, comboListToStringList(myProjectiles),
    			comboListToStringList(affectorsToUnit), comboListToStringList(affectorsToApply));
			setUp();
		}
		catch(NumberFormatException e){
			return;
		}
	}
	
	private void createUnit(String name, Map<String, List<Double>> map, List<String> projectiles, List<String> atu, List<String> ata){
			UnitFactory myUnitFactory = this.myElementTabModel.getUnitFactory();
			Unit unit = myUnitFactory.createUnit(name,
	    		strComboMap.get(getLabelsBundle().getString("unitTypeText")).getValue(), map, projectiles, atu, ata);
			myAnimationPane.getAnimationLoaderTab().setUnit(unit);
			myUnitFactory.getUnitLibrary().addUnit(unit);
	}

	public void addFields(GridPane gp) {
		addComboFields(gp);
		addTextFields(gp);
		childButton = addTextComboButtonTrio(gp, unitNames, myProjectiles,
				getLabelsBundle().getString("childButton"), getLabelsBundle().getString("childText"));
		affectorButton = addTextComboButtonTrio(gp, affectorNames, affectorsToUnit,
				getLabelsBundle().getString("affectorsButton"), getLabelsBundle().getString("affectorsText"));
		applyButton =addTextComboButtonTrio(gp, affectorNames, affectorsToApply,
				getLabelsBundle().getString("applyButton"), getLabelsBundle().getString("applyText"));		
	}
	
	private Button addTextComboButtonTrio(GridPane gp, List<String> listOfNames, List<ComboBox<String>> comboList, String buttonText, String labelText){
		getCreator().createTextLabels(gp, labelText, getIndex(), 1, Double.parseDouble(getDimensionsBundle().getString("rowConstraintSize")));
		ComboBox<String> cbox = getCreator().createStringComboBox(gp, listOfNames, getIndex(), 2);
		comboList.add(cbox);
		int currentInt = getIndex();
		iterateIndex();
		
		Button button = getCreator().createButton(gp, buttonText, getIndex(), 2);
		button.setOnAction(e-> addNewComboBox(currentInt, gp, button, cbox, 2, comboList, listOfNames));
		iterateIndex();
		
		return button;	
	}
	
	private void addNewComboBox(int row, GridPane gp, Button button, ComboBox<String> cbox, int col, List<ComboBox<String>> list, List<String> names) {
		if(cbox.getValue() != null){
			int newcol = col + 1;		
			gp.getRowConstraints().add(new RowConstraints(Double.parseDouble(getDimensionsBundle().getString("rowConstraintSize"))));
			gp.getColumnConstraints().add(new ColumnConstraints(150));
			ComboBox<String> newcbox = getCreator().createStringComboBox(gp, names, row, newcol);
			list.add(newcbox);
			button.setOnAction(e -> addNewComboBox(row, gp, button, newcbox, newcol, list, names));
		}
	}
	
	public void removeUnit(Unit u){
		this.myElementTabModel.getUnitFactory().getUnitLibrary().getUnits().remove(u);
		setUp();
	}
	
	private void refreshLists(){
		affectorNames = this.myElementTabModel.getAffectoryFactory().getAffectorLibrary().getAffectorNames();
		unitNames = this.myElementTabModel.getUnitFactory().getUnitLibrary().getUnitNames();	
		myElementTabModel.update(myAuthModel.getIAuthEnvironment());
	}
	
	public void updateMenu(Unit unit) {
		setUp();
		String[] name = unit.getType().split("_");
		strTextMap.get(getLabelsBundle().getString("typeText")).setText(name[0]);
		strComboMap.get(getLabelsBundle().getString("unitTypeText")).setValue(name[1]);
		strTextMap.get(getLabelsBundle().getString("deathDelayText")).setText(unit.getDeathDelay() + "");
		strTextMap.get(getLabelsBundle().getString("numFramesText")).setText(unit.getNumFrames()+"");
		strTextMap.get(getLabelsBundle().getString("speedText")).setText(unit.getProperties().getVelocity().getSpeed() +"");
		strTextMap.get(getLabelsBundle().getString("directionText")).setText(unit.getProperties().getVelocity().getDirection() + "");
		strTextMap.get(getLabelsBundle().getString("priceText")).setText(unit.getProperties().getPrice().getValue()+"");
		strComboMap.get(getLabelsBundle().getString("stateText")).setValue(
				strComboMap.get(getLabelsBundle().getString("stateText")).getItems().get((int)unit.getProperties().getState().getValue()));
		strTextMap.get(getLabelsBundle().getString("healthText")).setText(unit.getProperties().getHealth().getValue()+"");
		strTextMap.get(getLabelsBundle().getString("boundsText")).setText(unit.getProperties().getBounds().toString());
		strTextMap.get(getLabelsBundle().getString("rangeText")).setText(unit.getProperties().getRange().toString());
		strTextMap.get(getLabelsBundle().getString("ttlText")).setText(unit.getTTL()+"");
		strTextMap.get(getLabelsBundle().getString("massText")).setText(unit.getProperties().getMass().getMass()+"");
			
		updateComboBox(unit.getAffectors().toString(), affectorsToUnit, affectorButton);
		updateComboBox(unit.getAffectorsToApply().toString(), affectorsToApply, applyButton);
		updateComboBox(unit.getChildren().toString(), myProjectiles, childButton);
	}
	
	private void updateComboBox(String names, List<ComboBox<String>> comboList, Button button){
		if(!names.substring(1,names.length()-1).equals("")){
		String[] list = names.substring(1,names.length()-1).replaceAll(getLabelsBundle().getString("commaText"), "").split(getLabelsBundle().getString("regex"));
		for(int i = 0; i < list.length - 1; i++){
			comboList.get(i).setValue(list[i]);
			button.fire();
		}
		comboList.get(list.length-1).setValue(list[list.length-1]);
		}
	}
}