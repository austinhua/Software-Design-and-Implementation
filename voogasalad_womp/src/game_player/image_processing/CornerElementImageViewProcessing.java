package game_player.image_processing;

import game_engine.game_elements.Unit;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class CornerElementImageViewProcessing extends ImageViewProcessing {

    public static final String LEFT_CORNER_ELEMENTS = "leftCornerElements";

    public CornerElementImageViewProcessing (ImageView imageView, Pane root) {
        super(imageView, root);
    }

    @Override
    public void processImageView (Unit myUnit) {
        boolean isCornerElement =
                getResourceBundle().testUnitTypePreference(myUnit.toString(), LEFT_CORNER_ELEMENTS);
        double offsetX = isCornerElement ? 0 : -getMainImageView().getImage().getWidth() / 2;
        double offsetY = isCornerElement ? 0 : -getMainImageView().getImage().getHeight() / 2;
        getMainImageView().setX(myUnit.getProperties().getPosition().getX() + offsetX);
        getMainImageView().setY(myUnit.getProperties().getPosition().getY() + offsetY);
    }

    @Override
    public void removeFromRoot () {
    }

}
