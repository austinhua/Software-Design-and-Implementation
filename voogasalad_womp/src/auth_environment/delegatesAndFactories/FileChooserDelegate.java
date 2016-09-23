package auth_environment.delegatesAndFactories;

import java.io.File;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.scene.control.ContextMenu;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileChooserDelegate {
	
	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);
		
    public File chooseFile(String title) {
		return this.fileFromWindow(this.initChooser(title));
    }
    
    public File chooseImage(String title) {
    	return this.fileFromWindow(this.addImageFilter(this.initChooser(title)));
    }
    
    public File chooseXML(String title) {
    	return this.fileFromWindow(this.addXMLFilter(this.initChooser(title)));
    }
    
    public File chooseDirectory(String title) {
    	return this.directoryFromWindow(this.initDirectoryChooser(title));
    }
    
    public File save(String title) {
    	ContextMenu prefWindow = new ContextMenu();
        File file = this.initChooser(title).showSaveDialog(prefWindow.getOwnerWindow());
        return file;
    }
    
    private File directoryFromWindow(DirectoryChooser d) {
    	ContextMenu prefWindow = new ContextMenu();
    	File dir = d.showDialog(prefWindow.getOwnerWindow()); 
    	return dir; 
    }
    
    private File fileFromWindow(FileChooser f) {
        ContextMenu prefWindow = new ContextMenu();
        File file = f.showOpenDialog(prefWindow.getOwnerWindow());
        return file;
    }
    
    private FileChooser initChooser(String title) {
    	FileChooser f = new FileChooser();
    	f.setTitle(title);
    	return f; 
    }
    
    private DirectoryChooser initDirectoryChooser(String title) {
    	DirectoryChooser d = new DirectoryChooser(); 
    	d.setTitle(title);
    	return d; 
    }
    
    private FileChooser addImageFilter(FileChooser f) {
    	f.getExtensionFilters().add(new ExtensionFilter(myNamesBundle.getString("imageFilterName"), 
    			Arrays.asList(myNamesBundle.getString("imageFilterExtensions").split(" "))));
    	return f;
    }
    
    private FileChooser addXMLFilter(FileChooser f) {
    	f.getExtensionFilters().add(new ExtensionFilter(myNamesBundle.getString("xmlFilterName"),
    			Arrays.asList(myNamesBundle.getString("xmlFilterExtensions").split(" "))));
    	return f; 
    }

}
