package auth_environment.view.tabs;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class AnimationPane {
	private GridPane myPane;
	
	private static final String LABEL_PACKAGE = "auth_environment/properties/creation_tab_labels";
	private static final ResourceBundle labelsBundle = ResourceBundle.getBundle(LABEL_PACKAGE);
	
	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/creation_tab_dimensions";
	private static final ResourceBundle dimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);
	
	private AnimationLoaderTab myAnimationTab;
	
	public AnimationPane(){
		myPane = new GridPane();
    	myAnimationTab = new AnimationLoaderTab();
		init();
	}
	
	private void init(){
		myPane.getColumnConstraints().addAll(new ColumnConstraints(250), new ColumnConstraints(200), new ColumnConstraints(200));
		myPane.getRowConstraints().addAll(new RowConstraints(70));
		addAnimationButton();
	}
	
	public AnimationLoaderTab getAnimationLoaderTab(){
		return myAnimationTab;
	}
	
	private void addAnimationButton() {
		Button animationButton = new Button(labelsBundle.getString("animationText"));
		animationButton.setOnAction( e -> myAnimationTab.show());
		animationButton.setPrefSize(Double.parseDouble(dimensionsBundle.getString("animationWidth")),Double.parseDouble(dimensionsBundle.getString("animationHeight")));	
		myPane.add(animationButton, 1, 0); //col, row
	}
	
	public GridPane getRoot(){
		return myPane;
	}
}
