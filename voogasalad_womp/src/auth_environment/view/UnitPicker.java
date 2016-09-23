package auth_environment.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import auth_environment.Models.AffectorView;
import auth_environment.Models.UnitView;
import auth_environment.delegatesAndFactories.DragDelegate;
import auth_environment.view.tabs.AffectorTab;
import auth_environment.view.tabs.ElementTab;
import auth_environment.view.tabs.UnitTab;
import game_engine.affectors.Affector;
import game_engine.game_elements.Unit;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class UnitPicker{

	private TitledPane myEditPane;
	private FlowPane myEditInfo;
	private List<UnitView> myUnitViews;
	private List<AffectorView> myAffectors;
	
	private static final String LABEL_PACKAGE = "auth_environment/properties/unit_picker_labels";
	private static final ResourceBundle myLabelsBundle = ResourceBundle.getBundle(LABEL_PACKAGE);
	
	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/unit_picker_dimensions";
	private static final ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);
	
	public UnitPicker(String name){
		init(name);
		this.myUnitViews = new ArrayList<>();
		this.myAffectors = new ArrayList<>();
	}
	
	public UnitPicker(String name, List<Unit> units){
		init(name);
		this.myUnitViews = new ArrayList<>();
		this.myAffectors = new ArrayList<>();
		this.setUnits(units);
	}
	
	public void setName(String name){
		init(name);
	}
	
	private void init(String name){
		ScrollPane editScrollPane = new ScrollPane();
		myEditPane = new TitledPane();
		myEditInfo = new FlowPane();
		
		myEditPane.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("width")),Double.parseDouble(myDimensionsBundle.getString("height")));
		editScrollPane.setContent(myEditInfo);
		editScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		editScrollPane.setFitToWidth(true);
		
		myEditPane.setText(name);
		myEditPane.setContent(editScrollPane);
		myEditPane.setCollapsible(false);
	}
	
	public void setUnits(List<Unit> units) {
		this.myUnitViews.clear();
		this.myEditInfo.getChildren().clear();
		units.stream().forEach(s -> {
			UnitView temp = new UnitView(s, s.toString() + myLabelsBundle.getString("pngLabel"));
			temp.setFitHeight(50);
			temp.setFitWidth(50);
			myUnitViews.add(temp);
		});
		this.myEditInfo.getChildren().addAll(this.myUnitViews);
		setDragable();
	}
	
	public void setAffector(List<Affector> affectors){
		this.myAffectors.clear();
		this.myEditInfo.getChildren().clear();
		affectors.stream().forEach(s -> myAffectors.add(new AffectorView(s)));
		this.myEditInfo.getChildren().addAll(this.myAffectors);
	}
	
	public void addAffector(Affector s){
		myAffectors.add(new AffectorView(s));
	}
	
	public void setDragable(){
		myUnitViews.stream().forEach(e -> {
			DragDelegate drag = new DragDelegate();
			drag.addUnitViewSource(e);
		});
	}
	
	public void add(Unit unit, ElementTab elementTab){
		UnitView uv = new UnitView(unit, unit.toString() + myLabelsBundle.getString("pngLabel"));
		myEditInfo.getChildren().add(uv);
	}
	
	public void addContextMenu(UnitTab pane, UnitView uv){
		uv.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            if (e.getButton() == MouseButton.SECONDARY){ 
			            	ContextMenu myContextMenu = buildContextMenu(pane, uv);
			            	 myContextMenu.show(uv, e.getScreenX(), e.getScreenY());
			            }
			        }
			});
	}
	
	 private ContextMenu buildContextMenu(UnitTab target, UnitView uv){
	    	ContextMenu cm = new ContextMenu();
	    	MenuItem cmItem1 = new MenuItem(myLabelsBundle.getString("deleteLabel"));
	    	MenuItem cmItem2 = new MenuItem(myLabelsBundle.getString("editLabel"));
	    	cmItem1.setOnAction(e-> {target.removeUnit(uv.getUnit());
	    		myUnitViews.remove(uv);
	    	});
	    	cmItem2.setOnAction(e->target.updateMenu(uv.getUnit()));
	    	cm.getItems().addAll(cmItem1, cmItem2);
	    	return cm;
	    }

	 public void addAffectorContextMenu(AffectorTab pane, AffectorView uv){
			uv.addEventHandler(MouseEvent.MOUSE_CLICKED,
				    new EventHandler<MouseEvent>() {
				        @Override public void handle(MouseEvent e) {
				            if (e.getButton() == MouseButton.SECONDARY){ 
				            	ContextMenu myContextMenu = buildAffectorContextMenu(pane, uv);
				            	 myContextMenu.show(uv, e.getScreenX(), e.getScreenY());
				            }
				        }
				});
		}
		
		 private ContextMenu buildAffectorContextMenu(AffectorTab target, AffectorView uv){
		    	ContextMenu cm = new ContextMenu();
		    	MenuItem cmItem1 = new MenuItem(myLabelsBundle.getString("deleteLabel"));
		    	cmItem1.setOnAction(e-> {target.removeAffector(uv.getAffector());
		    		myAffectors.remove(uv);
		    	});
		    	cm.getItems().addAll(cmItem1);
		    	return cm;
		    }
		 
	public TitledPane getRoot(){
		return myEditPane;
	}

	public void setClickable(UnitTab elementTab) {
		myUnitViews.stream().forEach(e -> {
			this.addContextMenu(elementTab, e);
		});
		
	}
	public void setMenu(AffectorTab pane){
		myAffectors.stream().forEach(e -> {
			this.addAffectorContextMenu(pane, e);
		});
	}
}
