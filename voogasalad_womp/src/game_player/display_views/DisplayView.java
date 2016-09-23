package game_player.display_views;

import java.util.ArrayList;
import java.util.List;
import game_engine.game_elements.Unit;
import game_player.UnitViews.UnitImageView;
import game_player.interfaces.IGameView;
import javafx.scene.layout.Pane;


public abstract class DisplayView {

    private List<UnitImageView> imageViews;
    private IGameView gameView;
    private Pane root;

    public DisplayView (IGameView gameView, Pane root) {
        this.imageViews = new ArrayList<UnitImageView>();
        this.gameView = gameView;
        this.root = root;
    }

    public abstract void display (List<Unit> unitList, int timer);

    public boolean hasImageView (Unit u, List<UnitImageView> imageViews) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (imageViews.get(i).getUnit() == u) {
                return true;
            }
        }
        return false;
    }

    public List<UnitImageView> getImageViews () {
        return imageViews;
    }

    public IGameView getGameView () {
        return gameView;
    }

    public Pane getRoot () {
        return root;
    }

}
