package game_player.UnitViews;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import exceptions.ExceptionDialog;
import game_engine.game_elements.Unit;
import game_player.image_processing.CornerElementImageViewProcessing;
import game_player.image_processing.HealthImageViewProcessing;
import game_player.image_processing.ImageViewProcessing;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class ImageViewPicker extends UnitImageView {

    public static final String EXTENSION = ".png";
    public static final String RESOURCE_URL = "game_player/resources/animation";
    private ResourceBundle myAnimationBundle;
    private String currState;
    private int numFrames;
    private int currFrame;
    private List<ImageViewProcessing> imageViewProcesses;

    public ImageViewPicker (Unit u, Pane root) {
        super(u, root);
        this.numFrames = u.getNumFrames();
        this.currFrame = 0;
        this.currState = u.getProperties().getState().getString();
        this.myAnimationBundle = ResourceBundle.getBundle(RESOURCE_URL);
        initImageViewProcesses();
    }

    public void initImageViewProcesses () {
        imageViewProcesses = new ArrayList<>();
        imageViewProcesses.add(new HealthImageViewProcessing(getImageView(), getRoot()));
        imageViewProcesses.add(new CornerElementImageViewProcessing(getImageView(), getRoot()));
    }

    public void selectNextImageView (int timer) {
        String state = getUnit().getProperties().getState().getString();
        if (timer % Integer.parseInt(myAnimationBundle.getString(getName() + state)) == 0) {
            setImage(state);
            imageViewProcesses.stream().forEach(i -> i.processImageView(getUnit()));
            setDirection();
        }
    }

    public void setImage (String state) {
        currState = state;
        currFrame = currFrame + 1 == numFrames || !state.equals(currState) ? 1 : currFrame + 1;
        String file = getName() + state + currFrame + EXTENSION;
        try {
            getImageView().setImage(new Image(file));
        }
        catch (IllegalArgumentException e) {
            new ExceptionDialog("IllegalArgumentException, couldn't find file: " + file)
                    .displayMessage();
        }

    }

    public void setDirection () {
        Double nextDirection = transformDirection(getUnit());
        if (!nextDirection.equals(Double.NaN)) {
            getImageView().setRotate(nextDirection);
        }
    }

    public void removeFromRoot () {
        super.removeFromRoot();
        imageViewProcesses.stream().forEach(i -> i.removeFromRoot());
    }

    public static double transformDirection (Unit u) {
        return -u.getProperties().getVelocity().getDirection() + 90;
    }

}
