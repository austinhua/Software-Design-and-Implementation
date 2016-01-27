import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Main program, containing mostly boilerplate code.
 * 
 * @author Austin Hua
 */
public class Main extends Application {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final String DEFAULT_MAP = "defaultmap.cellcraft";
    public static final String OBSTACLE_MAP = "obstaclemap.cellcraft";
    public static final String SPLASH_SCREEN = "SplashScreen.jpg";
	public static final String DEFAULT_FRIENDLY_IMAGE = "HumanTCell.png";
	public static final String DEFAULT_ENEMY_IMAGE = "PurpleHumanTCell.png";
	public static final String NANOROBOT_IMAGE = "Nanorobot.png";
   	    
    public static final double SECONDS_PER_FRAME = 2;
    private static final double MILLISECOND_DELAY = 1000 * SECONDS_PER_FRAME;
    private static final double SECOND_DELAY = SECONDS_PER_FRAME;

    private CellCraft myGame;
    private String myMapFile = DEFAULT_MAP;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        // create your own game here
        myGame = new CellCraft();
        s.setTitle(myGame.getTitle());
        Scene splashScreen = myGame.setUpSplashScreen(SPLASH_SCREEN);
        s.setScene(splashScreen);
        s.show();
        
        splashScreen.setOnKeyPressed(e -> startMainGame(s, e.getCode()));
    }
    
    private void startMainGame(Stage s, KeyCode code) {
    	switch(code) {
    		case DIGIT1:
    			myMapFile= DEFAULT_MAP;
    			break;
    		case DIGIT2:
    			myMapFile = OBSTACLE_MAP;
    			break;
    		case ENTER:
    	        Scene mainGame = myGame.init(myMapFile);
	    		s.setScene(mainGame);
	    		s.show();
	    		startLoop();
    			break;
	    	default:
    	}
    }


	private void startLoop() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> myGame.step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
	}
	

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
