package game_player.view;

import game_engine.game_elements.Unit;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class TowerCell extends ListCell<Unit> {

    public static final String EXTENSION = ".png";
    
    public TowerCell () {
        super();
    }

    @Override
    protected void updateItem (Unit item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
        	ImageView image = createImageView(item);
            setText(String.valueOf(item.getProperties().getPrice().getValue()));
            setGraphic(image);
        }
    }

    private ImageView createImageView (Unit unit) {
        String name = unit.toString();
        Image image = new Image(name + EXTENSION);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}
