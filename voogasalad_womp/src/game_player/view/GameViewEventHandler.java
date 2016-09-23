package game_player.view;

import java.util.ResourceBundle;
import game_engine.GameEngineInterface;
import game_engine.game_elements.Unit;
import game_player.interfaces.IGameView;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class GameViewEventHandler {
	
    private final static int DEFAULT_MOVE_SPEED = 1;

    private GameEngineInterface playerEngineInterface;
    private IGameView gameView;
    private Pane root;
    private int moveSpeed;
    private Unit specificUnitIsSelected;
    private String unitToPlace;
    private boolean canPlaceUnit;

    public GameViewEventHandler (GameEngineInterface gameEngine, IGameView gameView, Pane root) {
        this.playerEngineInterface = gameEngine;
        this.gameView = gameView;
        this.moveSpeed = DEFAULT_MOVE_SPEED;
        this.root = root;
    }

    public void setEventHandlers (Scene myScene) {
        myScene.setOnKeyPressed(e -> setUpKeyPressed(e.getCode()));
        this.root.setOnMouseClicked(e -> setUpMouseClicked(e));
        this.root.setOnMouseMoved(e -> setUpMouseMoved(e));
    }

    public void setUpMouseClicked (MouseEvent e){
        if (unitToPlace != null) {
           playerEngineInterface.getUnitController().addTower(unitToPlace, e.getX(), e.getY());
        }
        else if (canPlaceUnit) {
            gameView.hideHUD();
        }
        canPlaceUnit = true;
    }

    public void setUpMouseMoved (MouseEvent e) {
        playerEngineInterface.setCursorPosition(e.getX(), e.getY());
    }

    public void setUpKeyPressed (KeyCode code) {
    	ResourceBundle keyResource = ResourceBundle.getBundle("game_player/resources/Hotkeys");
    	if (code.equals(KeyCode.valueOf(keyResource.getString("PlayPause")))) gameView.toggleGame();
    	else if (code.equals(KeyCode.valueOf(keyResource.getString("Escape")))) unitToPlace = null;
    	else if (code.equals(KeyCode.valueOf(keyResource.getString("Forward")))) moveSelectedUnit(0, -moveSpeed);
    	else if (code.equals(KeyCode.valueOf(keyResource.getString("Left")))) moveSelectedUnit(-moveSpeed, 0);
    	else if (code.equals(KeyCode.valueOf(keyResource.getString("Right")))) moveSelectedUnit(moveSpeed, 0);
    	else if (code.equals(KeyCode.valueOf(keyResource.getString("Backward")))) moveSelectedUnit(0, moveSpeed);
    }

    public void moveSelectedUnit (double offsetX, double offsetY) {
        if (specificUnitIsSelected != null) {
            playerEngineInterface.getUnitController()
                    .moveUnit(specificUnitIsSelected,
                              specificUnitIsSelected.getProperties().getPosition().getX() +
                                                      offsetX,
                              specificUnitIsSelected.getProperties().getPosition().getY() +
                                                                     offsetY);
        }
    }

    public void setSpecificUnitIsSelected (Unit unit) {
        this.specificUnitIsSelected = unit;
    }

    public void setCanPlaceUnit (boolean canPlaceUnit) {
        this.canPlaceUnit = canPlaceUnit;
    }

    public void setUnitToPlace (String name) {
        this.unitToPlace = name;
    }
}
