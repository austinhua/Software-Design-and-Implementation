package game_player.view;

import game_engine.affectors.Affector;
import javafx.scene.control.ListCell;


public class UpgradeCell extends ListCell<Affector> {

    public UpgradeCell () {
        super();
    }

    @Override
    protected void updateItem (Affector item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
        } else {
            setText(item.getName());
        }
    }
}
