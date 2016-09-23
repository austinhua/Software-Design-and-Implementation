package game_player.view;
import java.io.File;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class SwitchWindow {

    private static final int NAME_ROW = 0;
    private static final int IMAGE_ROW = 1;
    private static final int DESCRIPTION_ROW = 2;

    private ResourceBundle myResources;
    private PlayerGUI myGUI;
    private GridPane windowRoot;
    private String[] gameNames;
    private Button[] gameImages;
    private String[] gameDescriptions;

    public SwitchWindow (ResourceBundle r, PlayerGUI GUI) {
        myResources = r;
        myGUI = GUI;
    }

    public GridPane createWindow () {
        windowRoot = new GridPane();
        this.addGameElements();
        return windowRoot;
    }

    public void addGameElements () {
        gameNames = myResources.getString("GameNameList").split(";");
        this.addImages();
        gameDescriptions = myResources.getString("GameDescriptionList").split(";");
        for (int i = 0; i < gameNames.length; i++) {
            windowRoot.add(new Label(gameNames[i].trim()), i, NAME_ROW);
            windowRoot.add(gameImages[i], i, IMAGE_ROW);
            windowRoot.add(new Label(gameDescriptions[i].trim()), i, DESCRIPTION_ROW);
        }
    }

    public void addImages () {
        String[] imageNames = myResources.getString("GameImageList").split(";");
        gameImages = new Button[imageNames.length];
        for (int i = 0; i < imageNames.length; i++) {
        	gameImages[i] = new Button();
            ImageView image = new ImageView(new Image(getClass().getClassLoader()
                                                  .getResourceAsStream(imageNames[i].trim())));
            image.setFitWidth(Double.valueOf(myResources.getString("ImageWidth")));
            image.setFitHeight(Double.valueOf(myResources.getString("ImageHeight")));
            String name = gameNames[i];
            gameImages[i].setGraphic(image);
            gameImages[i].setOnAction(e -> myGUI.loadFromXML(new File(myResources.getString(name))));
        }
    }
}
