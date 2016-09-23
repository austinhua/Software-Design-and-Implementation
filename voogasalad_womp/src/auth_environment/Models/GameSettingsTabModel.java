package auth_environment.Models;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.Models.Interfaces.IGameSettingsTabModel;
import exceptions.ExceptionDialog;
import game_data.GameEnvironment;
import game_data.IDataConverter;
import game_data.IGameEnvironment;
import game_data.Serializer;
import game_engine.place_validations.PlaceValidation;
import game_engine.score_updates.ScoreUpdate;
import game_engine.wave_goals.WaveGoal;

/**
 * Created by BrianLin on 4/19/16
 * Team member responsible: Brian
 *
 * This Tab is for customizing Global Game settings + Saving/Loading. Will need the entire IEngineWorkspace passed
 * in so that it can be saved/loaded. 
 */

public class GameSettingsTabModel implements IGameSettingsTabModel {
	
	private static final String SETTINGS_PACKAGE = "auth_environment/properties/gameSettings";
	private ResourceBundle mySettingsBundle = ResourceBundle.getBundle(SETTINGS_PACKAGE);
	
	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);
	
	private IAuthModel myAuthModel;
	
	private IDataConverter<IGameEnvironment> writer = new Serializer<IGameEnvironment>();

	public GameSettingsTabModel(IAuthModel authModel) {
		this.myAuthModel = authModel;
	}

	@Override
	public void saveToFile() {
		writer.saveElement(this.myAuthModel.getIAuthEnvironment()); 
	}

	@Override
	public void loadFromFile() {
		GameEnvironment temp = (GameEnvironment) writer.loadElement();
		this.myAuthModel.getIAuthEnvironment().setAffectorFactory(temp.getAffectorFactory());
		this.myAuthModel.getIAuthEnvironment().setUnitFactory(temp.getUnitFactory());
	}

	@Override
	public void setGameName(String name) {
		this.myAuthModel.getIAuthEnvironment().setGameName(name);
	}

	@Override
	public String getGameName() {
		return this.myAuthModel.getIAuthEnvironment().getGameName();
	}
	
	@Override
	public void setSplashFile(String name) {
		this.myAuthModel.getIAuthEnvironment().setSplashScreen(name);
	}

	@Override
	public String getSplashFile() {
		return this.myAuthModel.getIAuthEnvironment().getSplashScreen();
	}

	@Override
	public List<String> getScoreUpdateNames() {
		return Arrays.asList(mySettingsBundle.getString("scoreUpdateTypes").split(" "));
	}

	@Override
	public List<String> getWaveGoalNames() {
		return Arrays.asList(mySettingsBundle.getString("waveGoalTypes").split(" ")); 
	}

	@Override
	public List<String> getPlaceValidationNames() {
		return Arrays.asList(mySettingsBundle.getString("placeValidationTypes").split(" ")); 
	}

	@Override
	public void chooseScoreUpdate(String selectedItem) {
		try {
			myAuthModel.getIAuthEnvironment().setScoreUpdate( (ScoreUpdate) Class.forName("game_engine.score_updates." + selectedItem + "ScoreUpdate").getConstructor().newInstance());
		} catch (Exception e) {
			new ExceptionDialog(myNamesBundle.getString("scoreUpdateError")).displayMessage();
		}
	}

	@Override
	public void chooseWaveGoal(String selectedItem) {
		try {
			myAuthModel.getIAuthEnvironment().setWaveGoal( (WaveGoal) Class.forName("game_engine.wave_goals." + selectedItem + "WaveGoal").getConstructor().newInstance());
		} catch (Exception e) { 
			new ExceptionDialog(myNamesBundle.getString("waveGoalError")).displayMessage();
		}
	}

	@Override
	public void choosePlaceValidation(String selectedItem) {
		try {
			myAuthModel.getIAuthEnvironment().getPlaceValidations().add( (PlaceValidation) Class.forName("game_engine.place_validations." + selectedItem + "PlaceValidation").getConstructor().newInstance());
		} catch (Exception e) {
			new ExceptionDialog(myNamesBundle.getString("placeValidationError")).displayMessage();
		}
		
	}
}
