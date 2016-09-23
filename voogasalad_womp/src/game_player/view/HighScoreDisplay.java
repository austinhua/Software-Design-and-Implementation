package game_player.view;

import game_player.GameDataSource;
import game_player.interfaces.IGUIObject;
import game_player.interfaces.IGameView;
import java.util.ResourceBundle;

import game_engine.GameEngineInterface;
import javafx.scene.Node;
import javafx.scene.control.Label;


public class HighScoreDisplay implements IGUIObject {

    private ResourceBundle myResources;
    private Label highScoreLabel;
    private GameDataSource myGameData;
    private IGameView myView;
    private GameEngineInterface myEngine;
    private PlayerGUI myGUI;
    public HighScoreDisplay (ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
        myResources = r;
        myGameData = gameData;
        myView = view;
        myGUI = GUI;
    }

    @Override
    public Node createNode () {
        myView.getGameEngine();
        highScoreLabel = new Label();
        updateText();
        return highScoreLabel;
    }

    @Override
    public void updateNode () {
        updateText();
    }

    private void updateText () {
        highScoreLabel.setText(myResources.getString("HighScore") +
                               myGameData.getDoubleValue(myGUI.getFile().toString()));
    }

}