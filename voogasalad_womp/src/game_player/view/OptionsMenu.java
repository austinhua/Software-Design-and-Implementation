package game_player.view;

import java.util.ResourceBundle;
import game_player.interfaces.IGameView;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class OptionsMenu extends PlayerMenu {

    private Stage menuStage;
    private ResourceBundle myResources;
    private MenuMaker menuMaker;
    private Menu myMenu;
    private GridPane myRoot;
    private SwitchWindow mySwitchWindow;
    private IGameView myView;
    private PlayerGUI myGUI;

    public OptionsMenu (ResourceBundle r, IGameView view, PlayerGUI GUI) {
        super(r, view);
        myResources = r;
        menuMaker = new MenuMaker(r);
        mySwitchWindow = new SwitchWindow(r, GUI);
        myView = view;
        myGUI = GUI;
    }

    public Menu createMenu () {
        myMenu = super.createNewMenu("OptionsMenu");
        return myMenu;
    }

    protected void openSwitchWindow () {
        menuStage = new Stage();
        myRoot = mySwitchWindow.createWindow();
        Scene switchWindowScene = new Scene(myRoot, 
        		Double.valueOf(myResources.getString("SwitchWindowSize")),
        		Double.valueOf(myResources.getString("SwitchWindowSize")));
        menuStage.setScene(switchWindowScene);
        menuStage.show();
    }

    protected void restartGame () {
        myGUI.restartGame();
    }

    protected void saveGame () {
    	myView.getGameEngine().saveGame();
    }

    protected void loadGame () {
        myGUI.loadNewTab();
    }
    
    protected void returnToAuth() {
    	myView.getMainView().displayAuth();
    }
}
