package graphics;
import simulation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Handles the user interface elements, such as buttons
 */

public class GUI {
	private static final String LOAD_FILE_BUTTON_TEXT = "Load File";
	private static final String STEP_BUTTON_TEXT = "Step";
	private static final String START_BUTTON_TEXT = "Start";
	private static final String PAUSE_BUTTON_TEXT = "Pause";
	private static final String RESET_BUTTON_TEXT = "Reset";
	private static final String SAVE_BUTTON_TEXT = "Save";
	private static final String SPEED_TEXT = "Speed";
	
	private Graphics myGraphics;
	private Group parentNode;
	//private VBox vBox;
	
	private double guiWidth;
	private double guiHeight;
	
	public GUI(Graphics graphics, double guiWidth, double guiHeight, Group parentNode) {
		myGraphics = graphics;
		this.parentNode = parentNode;
		this.guiWidth = guiWidth;
		this.guiHeight = guiHeight;
		
		
		parentNode.getChildren().add(makeGUI());
	}
	
	private Node makeGUI() {
		VBox vBox = new VBox(guiHeight*.05);	
		// Load File
        addButton(LOAD_FILE_BUTTON_TEXT, e -> myGraphics.loadFile(), vBox);
        // Step
		addButton(STEP_BUTTON_TEXT, e -> myGraphics.step(), vBox);
		// Start/Pause
        Button startPauseToggleButton = new Button(START_BUTTON_TEXT);
        startPauseToggleButton.setOnAction(e -> startPauseToggle(startPauseToggleButton));
        vBox.getChildren().add(startPauseToggleButton);
        // Reset
        addButton(RESET_BUTTON_TEXT, e -> myGraphics.reset(), vBox);
        // Save
        addButton(SAVE_BUTTON_TEXT, e -> myGraphics.saveSim(), vBox);
        // Speed
        Label speedCaption = new Label(SPEED_TEXT);
        vBox.getChildren().add(speedCaption);
        makeSlider(1, 10, Graphics.DEFAULT_SPEED, vBox);
        
		return vBox;
	}

	private void addButton(String label, EventHandler<ActionEvent> handler, Pane pane) {
        Button button = new Button(label);
        button.setOnAction(handler);
        pane.getChildren().add(button);
	}
	
	private void startPauseToggle(Button toggleButton) {
		if (toggleButton.getText().equals(START_BUTTON_TEXT)) {
			toggleButton.setText(PAUSE_BUTTON_TEXT);
			myGraphics.start();
		}
		else if (toggleButton.getText().equals(PAUSE_BUTTON_TEXT)) {
			toggleButton.setText(START_BUTTON_TEXT);
			myGraphics.pause();
		}
	}
	
	private void makeSlider(double start, double end, double defaultValue, Pane pane) {
		Slider slider = new Slider(start, end, defaultValue);
		slider.setShowTickLabels(true);
		slider.setOrientation(Orientation.VERTICAL);
		slider.setOnMouseReleased(e -> myGraphics.changeSpeed(slider.getValue()));
		pane.getChildren().add(slider);
	}
	
	
	
}
