package auth_environment.Models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import auth_environment.Models.Interfaces.IAnimationTabModel;
import auth_environment.delegatesAndFactories.StateImageSaver;
import game_engine.game_elements.Unit;

public class AnimationTabModel implements IAnimationTabModel {
	private static final String SUFFIX = ".png";
	private static final String URLS_PACKAGE = "auth_environment/properties/urls";
	private ResourceBundle myURLSBundle = ResourceBundle.getBundle(URLS_PACKAGE);
	
	private Unit myUnit;

	private List<File> myFiles; 
	
	private StateImageSaver mySaver;
	
	public AnimationTabModel() {
		this.mySaver = new StateImageSaver(this.myURLSBundle.getString("saveImageLocation"), SUFFIX); 
		this.myFiles = new ArrayList<File>();
	}

	@Override
	public void addFile(File file) {
		this.myFiles.add(file);
	}
	
	public void setUnit(Unit unit){
		this.myUnit = unit;
	}
	
	public void saveFiles(){
		this.mySaver.saveFiles(this.myUnit.getName(), "", this.myFiles);
	}
}