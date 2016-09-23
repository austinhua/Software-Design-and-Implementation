package auth_environment.Models;

import java.util.ResourceBundle;

import auth_environment.Models.Interfaces.IMapPane;
import auth_environment.Models.Interfaces.IUnitView;
import auth_environment.delegatesAndFactories.FreeMapPane;
import auth_environment.delegatesAndFactories.NodeFactory;
import game_engine.game_elements.Unit;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by BrianLin on 4/20/16
 * Team member responsible: Brian
 * @author Brian, Xander
 * The Auth Environment's way of displaying a Unit. 
 * Contains both the Unit and an ImageView to be displayed.
 * 
 *  DO NOT write this to XML
 * 
 */

public class UnitView extends ImageView implements IUnitView {
	
	private ContextMenu myContextMenu;
	private NodeFactory myNodeFactory = new NodeFactory(); 
	private Unit myUnit;
	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);
	
	public UnitView() {
		super();
	}
	
	public UnitView(Image image) {
		super(image); 
	}
	
	public UnitView(String imageName) {
		this.setImage(this.myNodeFactory.buildImage(imageName));
	}
	
	public UnitView(Unit unit) {
		super();
		this.myUnit = unit;
		this.setImage(this.myNodeFactory.buildImage(unit.toString()));
		this.setXY();
	}
	
	public UnitView(Unit unit, Image image) {
		super(image); 
		this.myUnit = unit; 
		this.setXY();
	}
	
	public UnitView(Unit unit, String imageName) {
		this.myUnit = unit;
		this.setImage(this.myNodeFactory.buildImage(imageName));
		this.setXY();
	}
	
	public UnitView clone(){
		UnitView uv = new UnitView(myUnit.copyUnit(), this.getImage());
		uv.setXY();
		return uv;
	}
	
	@Override
	public void setUnit(Unit unit) {
		this.myUnit = unit; 
	}

	@Override
	public Unit getUnit() {
		return this.myUnit;
	}
	
	public void setScale(FreeMapPane target, int gridWidth){
		target.adjustUnitViewScale(this);
		this.setFitHeight(target.getHeight()/gridWidth-3);
		this.setFitWidth(target.getWidth()/gridWidth-3);
	}
	
	public void addContextMenu(IMapPane target, UnitView uv){
		this.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            if (e.getButton() == MouseButton.SECONDARY){ 
			            	myContextMenu = buildContextMenu(target);
			            	 myContextMenu.show(uv, e.getScreenX(), e.getScreenY());
			            }
			        }
			});
	}
	
	 private ContextMenu buildContextMenu(IMapPane target){
	    	ContextMenu cm = new ContextMenu();
	    	MenuItem cmItem1 = new MenuItem(myNamesBundle.getString("deleteItemLabel"));
	    	cmItem1.setOnAction(e-> {target.getRoot().getChildren().remove(this);
	    		target.getModel().deleteTerrain(this.getUnit());
	    	});
	    	cm.getItems().add(cmItem1);
	    	return cm;
	    }
	 
	 public void setXY(){
		 this.setX(this.getUnit().getProperties().getPosition().getX());
		 this.setY(this.getUnit().getProperties().getPosition().getY());
	 }
}
