package game_player.view;

import java.util.ResourceBundle;
import game_player.GameDataSource;
import game_player.interfaces.IGUIObject;
import game_player.interfaces.IGameView;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;


public class GameSpeedSlider implements IGUIObject {
    private static final float TICK_UNITS = 1;
    private static final int SLIDER_DEFAULT = 1;
    private static final int SLIDER_MIN = 0;
    private static final double SLIDER_MAX = 4;
    private static final int VBOX_SPACING = 5;
    private ResourceBundle myResources;
    private IGameView myView;

    public GameSpeedSlider (ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
        myResources = r;
        myView = view;
    }

    @Override
    public Node createNode () {
        VBox sliderBox = new VBox(VBOX_SPACING);
        Label sliderLabel = new Label(myResources.getString("AnimationSlider"));
        Slider animationSpeed = new Slider();
        animationSpeed.setOrientation(Orientation.VERTICAL);
        animationSpeed.setMin(SLIDER_MIN);
        animationSpeed.setMax(SLIDER_MAX);
        animationSpeed.setValue(SLIDER_DEFAULT);
        animationSpeed.setMajorTickUnit(TICK_UNITS);
        animationSpeed.setShowTickMarks(true);
        animationSpeed.setShowTickLabels(true);
        animationSpeed.valueProperty().addListener(event -> setSpeed(animationSpeed.getValue()));
        sliderBox.setAlignment(Pos.TOP_CENTER);
        sliderBox.getChildren().addAll(sliderLabel, animationSpeed);
        return sliderBox;
    }

    private void setSpeed (double value) {
        myView.changeGameSpeed(value);
    }

    @Override
    public void updateNode () {

    }

}
