package game_player.view;

import java.util.ResourceBundle;

import game_player.GameDataSource;
import game_player.interfaces.IGUIObject;
import game_player.interfaces.IGameView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public abstract class GUIComboBox implements IGUIObject {
	
	private static final int PANEL_SPACING = 5;
	
	private ResourceBundle myResources;
	private ComboBox<String> comboBox;
	private Button comboBoxButton;
	private String promptText;
	private String optionsString;
	private ObservableList<String> options;
	
	public GUIComboBox (ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
        myResources = r;
    }
	
	public GUIComboBox (ResourceBundle r,
			GameDataSource gameData,
			IGameView view,
			String text,
			String options,
			PlayerGUI GUI) {
        this(r, gameData, view, GUI);
        promptText = text;
        optionsString = options;
    }
	
	@Override
	public Node createNode() {
        HBox difficultyBox = new HBox(PANEL_SPACING);
        options = FXCollections.observableArrayList();
        populateOptions();
        comboBox = new ComboBox<>(options);
        configureComboBox();
        comboBoxButton = new Button(myResources.getString("GoText"));
        comboBoxButton.setOnAction(event -> performAction());
        difficultyBox.getChildren().addAll(comboBox, comboBoxButton);
        return difficultyBox;
	}

	@Override
	public void updateNode() {
		
	}
	
	public void configureComboBox () {
        comboBox.setVisibleRowCount(Integer.valueOf(myResources.getString("VisibleRowCount")));
        comboBox.setPrefWidth(Double.valueOf(myResources.getString("ComboBoxWidth")));
        comboBox.setPromptText(promptText);
    }
	
	public void populateOptions() {
		String[] difficulties = optionsString.trim().split(",");
		for (String s: difficulties) {
			options.add(s);
		}
	}
	
	public abstract void performAction();
	
	public ComboBox<String> getComboBox() {
		return comboBox;
	}

}
