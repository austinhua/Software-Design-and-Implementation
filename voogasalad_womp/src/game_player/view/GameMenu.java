package game_player.view;

import java.util.ResourceBundle;
import game_player.GameDataSource;
import game_player.interfaces.IGUIObject;
import game_player.interfaces.IGameView;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class GameMenu implements IGUIObject {

    private ResourceBundle myResources;
    private Menu myMenu;
    private MenuBar myMenuBar;
    private IGameView myView;
    private PlayerGUI myGUI;

    public GameMenu (ResourceBundle r, GameDataSource gameData, IGameView view, PlayerGUI GUI) {
        myResources = r;
        myView = view;
        myGUI = GUI;
    }

    @Override
    public Node createNode () {
        myMenuBar = new MenuBar();
        myMenu = new OptionsMenu(myResources, myView, myGUI).createMenu();

        myMenuBar.getMenus().add(myMenu);
        return myMenuBar;
    }

    @Override
    public void updateNode () {

    }

}
