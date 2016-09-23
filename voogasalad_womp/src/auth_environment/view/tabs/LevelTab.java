package auth_environment.view.tabs;

import java.util.ResourceBundle;

import auth_environment.Models.LevelTabModel;
import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.Models.Interfaces.ILevelOverviewTabModel;
import game_engine.game_elements.Level;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;

public class LevelTab extends Tab{
	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);

	private BorderPane myBorderPane;
	private IAuthModel myAuthModel;
	private String myName; 
	private LevelTabModel myLevelTabModel;
	private ILevelOverviewTabModel myLevelOverviewTabModel; 
	private Level level;
	private TextField lifeField;
	private final int COLUMN_INDEX = 2;
	
	
	public LevelTab(String name, int levelIndex, IAuthModel authModel, ILevelOverviewTabModel levelOverview){
		super(name);
		this.level = new Level(name, Integer.parseInt(myDimensionsBundle.getString("defaultLives")));
		this.myLevelTabModel = new LevelTabModel(levelIndex); 
		this.myAuthModel = authModel;
		this.myLevelOverviewTabModel = levelOverview; 
		this.myName = name;
		init();
	}
	
	private void init() {
		this.myBorderPane = new BorderPane();
		myBorderPane.setPadding(new Insets(Double.parseDouble(myDimensionsBundle.getString("waveListPaddingTop")), 
				Double.parseDouble(myDimensionsBundle.getString("waveListPaddingRight")), 
				Double.parseDouble(myDimensionsBundle.getString("waveListPaddingBottom")), 
				Double.parseDouble(myDimensionsBundle.getString("waveListPaddingLeft"))));
		Label lifeLabel = new Label(myNamesBundle.getString("lifeLabel") + " ");
		lifeField = new TextField();
		lifeField.setPromptText(myNamesBundle.getString("lifeLabelPrompt"));
		HBox lifeHB = new HBox();
		lifeHB.getChildren().addAll(lifeLabel, lifeField);
		lifeHB.setSpacing(Double.parseDouble(myDimensionsBundle.getString("lifeHBSpacing")));
		myBorderPane.setTop(lifeHB);
		this.setRefresh();
		this.createWaveList();
		this.setContent(myBorderPane);
	}
	
	private void setRefresh() {
		myBorderPane.setOnMouseEntered(e -> {
			refresh();
		});
		setOnSelectionChanged(e -> {
			refresh();
		});
	}
	
	private void refresh() {
		this.myLevelOverviewTabModel.changeEditedLevel(this.myLevelTabModel.getLevelIndex());
		if(lifeField.getText() != null && !lifeField.getText().equals("")){
			level.setMyLives(Integer.parseInt(lifeField.getText()));
		}
	}
	
	private void createWaveList() { 
		int index = 0;
		GridPane newTableInfo = new GridPane();
		newTableInfo.setPadding(new Insets(Double.parseDouble(myDimensionsBundle.getString("waveListPaddingTop")), Double.parseDouble(myDimensionsBundle.getString("waveListPaddingRight")), 
				Double.parseDouble(myDimensionsBundle.getString("waveListPaddingBottom")), Double.parseDouble(myDimensionsBundle.getString("waveListPaddingLeft"))));
		newTableInfo.setVgap(Double.parseDouble(myDimensionsBundle.getString("waveListVGap")));
		newTableInfo.getColumnConstraints().addAll(new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString("waveListConstraintsTop"))), 
				new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString("waveListConstraintsRight"))), 
				new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString("waveListConstraintsBottom"))), 
				new ColumnConstraints(Double.parseDouble(myDimensionsBundle.getString("waveListConstraintsLeft"))));
		newTableInfo.getRowConstraints().addAll(new RowConstraints(Double.parseDouble(myDimensionsBundle.getString("waveListConstraintsRow"))));
		newTableInfo.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("waveListPrefSizeX")), Double.parseDouble(myDimensionsBundle.getString("waveListPrefSizeY")));	//TODO: Avoid hard-coded values
		this.myBorderPane.setLeft(newTableInfo);
		Button dummyWaveButton = new Button();
		this.addNewWaveSpace(index, newTableInfo, dummyWaveButton);
	}
	
	private void addNewWaveSpace(int index, GridPane newTableInfo, Button waveButton) {
		newTableInfo.getChildren().remove(waveButton);
		int waveNum = index + 1;
		String waveName = myNamesBundle.getString("wave") + waveNum;
		Button wave = new Button(waveName);
		wave.setOnAction(e -> new WaveWindow(myName, waveName, myAuthModel, this.myLevelOverviewTabModel));
		newTableInfo.add(wave, COLUMN_INDEX, index);
		index++;
		Button newWaveButton = new Button(myNamesBundle.getString("levelAddWave"));
		int num = index;
		newWaveButton.setOnAction(e -> addNewWaveSpace(num, newTableInfo, newWaveButton));
		newTableInfo.add(newWaveButton, COLUMN_INDEX, index);
	}
	
	public int getIndex() {
		return myLevelTabModel.getLevelIndex();
	}
	
}
