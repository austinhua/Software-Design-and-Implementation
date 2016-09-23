package auth_environment.view.tabs;

import javafx.scene.Node;

/**
 * Created by BrianLin on 4/15/16.
 * Team member responsible: Brian
 *
 * This class represents a single tab within our View.
 */

public interface IWorkspace {

	// Any info this Tab modifies goes into the constructor so dependencies are clear

	// Return the Root so that this can be added to the Tabs 
	public Node getRoot();
	
}