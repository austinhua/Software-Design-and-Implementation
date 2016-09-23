package game_player.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


public class MenuMaker {

    private MenuBar menu;
    private ResourceBundle myResources;

    public MenuMaker (ResourceBundle r) {
        setMenu(new MenuBar());
        myResources = r;
    }

    public Menu addMenu (String title) {
        Menu mb = new Menu(title);
        getMenu().getMenus().add(mb);
        return mb;

    }

    public MenuItem addMenuItem (String title, EventHandler<ActionEvent> event, Menu root) {
        MenuItem back = new MenuItem(myResources.getString(title));
        back.setOnAction(event);
        root.getItems().add(back);
        return back;
    }

    public MenuBar getMenu () {
        return menu;
    }

    public void setMenu (MenuBar menu) {
        this.menu = menu;
    }
}
