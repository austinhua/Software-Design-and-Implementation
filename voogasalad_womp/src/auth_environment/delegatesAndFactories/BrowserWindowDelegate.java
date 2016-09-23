package auth_environment.delegatesAndFactories;

import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class BrowserWindowDelegate {

	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);
	
	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);
	
	private Pane loadingPane;
	private ProgressBar loadProgress;
	private Label progressText;
	
	private Stage browserStage; 
	
	private WebView browser = new WebView();

	public BrowserWindowDelegate() {
	}
	
	// Source: https://gist.github.com/jewelsea/1588531
	public void init(double width) {
		ImageView loadingImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(myNamesBundle.getString("browserLoadingImage"))));
		loadProgress = new ProgressBar();
		loadProgress.setPrefWidth(width - Double.parseDouble(myDimensionsBundle.getString("loadingBarSpacer")));
		progressText = new Label(myNamesBundle.getString("loadingText"));
		loadingPane = new VBox();
		loadingPane.getChildren().addAll(loadingImage, loadProgress, progressText);
		progressText.setAlignment(Pos.CENTER);
		loadingPane.setStyle(myDimensionsBundle.getString("loadingPaneStyle"));
		loadingPane.setEffect(new DropShadow());
	}

	@SuppressWarnings("rawtypes") 
	private void addLoadingListener(Stage initStage) {
		browser.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                            if (initStage.isShowing()) {
                  	          setupTransition(initStage);
                  	        }
                        }
                    }
			});
	}
	
	private void setupTransition(Stage initStage) {
		loadProgress.progressProperty().unbind();
		  loadProgress.setProgress(1);
		  progressText.setText(myNamesBundle.getString("finishedLoadingText"));
		  browserStage.setIconified(false);
		  initStage.toFront();
		  FadeTransition fadeSplash = new FadeTransition(
			Duration.seconds(Double.parseDouble(myDimensionsBundle.getString("browserTransitionTime"))), loadingPane);
		  fadeSplash.setFromValue(1.0);
		  fadeSplash.setToValue(0.0);
		  fadeSplash.setOnFinished(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent actionEvent) {
		      initStage.hide();
		    }
		  });
		  fadeSplash.play();
	}

	
	private void setupSplashAndListener(double width, double height) {
		this.init(width);
		Stage initStage = new Stage(); 
		this.showSplash(initStage, width, height);
		this.addLoadingListener(initStage);
		loadProgress.progressProperty().bind(browser.getEngine().getLoadWorker().workDoneProperty().divide(100));
	}

	public void openWindow(String title, String url, double width, double height) {
		browserStage = new Stage();
		this.setupSplashAndListener(width, height);
		Pane root = new Pane();
		browserStage.setTitle(title);
		browserStage.setIconified(true);
		browser.getEngine().load(url);
		root.getChildren().add(browser);
		Scene scene = new Scene(root, width, height);
		browser.prefWidthProperty().bind(scene.widthProperty());
		browser.prefHeightProperty().bind(scene.heightProperty());
		browserStage.setScene(scene);
		browserStage.show();
	}

	private void showSplash(Stage initStage, double width, double height) {
		Scene splashScene = new Scene(loadingPane, width, height);
		initStage.initStyle(StageStyle.DECORATED);
		initStage.setScene(splashScene);
		initStage.show();
	}
}
