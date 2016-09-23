package game_player.view;

import java.util.List;
import java.util.ResourceBundle;
import game_engine.game_elements.Unit;
import game_engine.GameEngineInterface;
import game_player.GameDataSource;
import game_player.interfaces.IGUIObject;
import game_player.interfaces.IGameView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class GameTowerPicker implements IGUIObject {
	
    private static final int PANEL_SPACING = 10;
	private static final int TOP_PADDING = 10;
	private static final int RIGHT_PADDING = 0;
	private static final int BOTTOM_PADDING = 0;
	private static final int LEFT_PADDING = 0;
    
    private ResourceBundle myResources;
    private IGameView myView;
    private GameEngineInterface myEngine;
    private ListView<Unit> myListView;
    private ObservableList<Unit> myTowers;
    private Label myMoney;

    public GameTowerPicker (ResourceBundle r, GameDataSource data, IGameView view, PlayerGUI GUI) {
        myResources = r;
        myView = view;
        myEngine = myView.getGameEngine();
        myTowers = FXCollections.observableArrayList();
		myMoney = new Label();
    }

    @Override
    public Node createNode () {
        VBox box = new VBox(PANEL_SPACING);
        myListView = new ListView<>();
        myListView.setItems(myTowers);
        myListView.setCellFactory(cellFactory -> new TowerCell());
        myListView.setOnMouseClicked(e -> {
            myView.setUnitToPlace(myListView.getSelectionModel().getSelectedItem().toString());
        });
        myListView.setPrefWidth(Double.valueOf(myResources.getString("ListViewWidth")));
        updateNode();
        box.getChildren().addAll(myMoney, myListView);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING, LEFT_PADDING));
        VBox.setVgrow(myListView, Priority.ALWAYS);
        return box;
    }

    @Override
    public void updateNode () {
        updateTowerList();
		myMoney.setText(String.valueOf(myResources.getString("Money") + 
				(myEngine.getUnitController().getStore().getMoney() + myEngine.getLevelController().getScore())));
		myListView.setPrefHeight(myView.getCanvas().getScrollPaneHeight() - 
				Double.valueOf(myResources.getString("ListViewOffset")));
    }

    private void updateTowerList () {
        List<Unit> allTowerTypes = myEngine.getUnitController().getTowerTypes();
        for (int i = 0; i < allTowerTypes.size(); i++) {
            if (!hasUnitImageView(allTowerTypes.get(i), myTowers)) {
                myTowers.add(allTowerTypes.get(i));
            }
        }
        for (int i = 0; i < myTowers.size(); i++) {
            if (!allTowerTypes.contains(myTowers.get(i))) {
                myTowers.remove(i);
            }
        }
    }

    public double transformDirection (Unit u) {
        return -u.getProperties().getVelocity().getDirection() + 
        		Double.valueOf(myResources.getString("RotationAngle"));
    }

    public boolean hasUnitImageView (Unit u, List<Unit> imageViews) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (imageViews.get(i) == u) {
                return true;
            }
        }
        return false;
    }
}
