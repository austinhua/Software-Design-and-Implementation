package game_player.view;

import java.util.ResourceBundle;
import game_player.interfaces.IGameView;
import javafx.scene.control.Menu;


public abstract class PlayerMenu {

    private ResourceBundle myResources;
    private MenuMaker menuMaker;
    private Menu myMenu;
    public PlayerMenu (ResourceBundle r, IGameView view) {
        myResources = r;
        menuMaker = new MenuMaker(r);
    }

    protected abstract Menu createMenu ();

    protected Menu createNewMenu (String menuName) {
        myMenu = menuMaker.addMenu(myResources.getString(menuName));
        String[] menuItems = myResources.getString(myResources.getString(menuName)).split("/");
        for (String pair : menuItems) {
            String[] combo = pair.split(",");
            try {
                menuMaker.addMenuItem(combo[0], e -> {
                    try {
                        getClass().getDeclaredMethod(combo[1]).invoke(this);
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } , myMenu);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return myMenu;
    }
}
