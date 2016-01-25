import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
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
    public static final String SPLASH_SCREEN = "SplashScreen.jpg";
   	    
    public static final double SECONDS_PER_FRAME = 2;
    private static final double MILLISECOND_DELAY = 1000 * SECONDS_PER_FRAME;
    private static final double SECOND_DELAY = SECONDS_PER_FRAME;

    private CellCraft myGame;    

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
        Scene mainGame = myGame.init(DEFAULT_MAP);
        
        splashScreen.setOnKeyPressed(e -> startMainGame(s, mainGame));
    }
    
    private void startMainGame(Stage s, Scene mainGame) {
    	s.setScene(mainGame);
    	s.show();
    	startLoop();
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
