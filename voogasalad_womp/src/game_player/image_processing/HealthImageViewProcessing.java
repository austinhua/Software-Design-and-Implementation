package game_player.image_processing;

import game_engine.game_elements.Unit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class HealthImageViewProcessing extends ImageViewProcessing {

    public static final String NEEDS_HEALTH = "needsHealth";
    private ImageView health;

    public HealthImageViewProcessing (ImageView imageView, Pane root) {
        super(imageView, root);
        // TODO: Remove this hard-coded image string
        this.health = new ImageView(new Image("health_red.png"));
        root.getChildren().add(health);
        health.toBack();
    }

    @Override
    public void processImageView (Unit myUnit) {
        boolean isHealth =
                getResourceBundle().testUnitTypePreference(myUnit.toString(), NEEDS_HEALTH);
        health.setFitWidth(myUnit.getProperties().getHealth().getValue() /
                           myUnit.getProperties().getHealth().getInitialValue() *
                           health.getImage().getWidth());
        double xpos = isHealth ? getMainImageView().getX() : Integer.MAX_VALUE;
        double ypos = isHealth ? getMainImageView().getY() - 5 : Integer.MAX_VALUE;
        health.setX(xpos);
        health.setY(ypos);
        health.toFront();  
    }

    @Override
    public void removeFromRoot () {
        getRoot().getChildren().remove(health);    
    }

}
