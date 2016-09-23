package game_player.UnitViews;

import game_engine.game_elements.Unit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class UnitImageView {
    
    private ImageView imageView;
    private String name;
    private Unit myUnit;
    private Pane root;
    
    public UnitImageView (Unit u, Pane root) {
        this.imageView = new ImageView(new Image(u.toString() + ".png"));
        this.myUnit = u;
        this.name = u.toString();
        this.root = root;
        root.getChildren().add(imageView);
    }
    
    public void removeFromRoot() {
        root.getChildren().remove(imageView);
    }
    
    public Unit getUnit() {
        return myUnit;
    }

    public String getName() {
        return name;
    }
    
    public Pane getRoot() {
        return root;
    }
    
    public ImageView getImageView() {
        return imageView;
    }

}
