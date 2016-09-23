package auth_environment.view.tabs;
import java.util.ResourceBundle;

import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.view.tabs.AffectorTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ElementCreationTab extends Tab{
	public ElementCreationTab(String name, IAuthModel authModel, ResourceBundle myNamesBundle){
		super(name);
		init(authModel, myNamesBundle);
	}
	
	private void init(IAuthModel authModel, ResourceBundle myNamesBundle){
		this.setOnSelectionChanged(e -> refresh(myNamesBundle, authModel));
	}
	
	private void refresh(ResourceBundle myNamesBundle, IAuthModel authModel){
		TabPane myTabs = new TabPane();
		ElementTab unitTab = new UnitTab(myNamesBundle.getString("unitTabLabel"), authModel);
		myTabs.getTabs().addAll(unitTab, new AffectorTab(myNamesBundle.getString("affectorTabLabel"), authModel));
		//myTabs.getTabs().addAll(new AffectorTab(myNamesBundle.getString("affectorTabLabel"), authModel));

		this.setContent(myTabs);
	}
	
	
	
}