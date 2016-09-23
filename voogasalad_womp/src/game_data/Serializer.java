package game_data;

import com.thoughtworks.xstream.XStream;
import auth_environment.delegatesAndFactories.FileChooserDelegate;
import exceptions.ExceptionDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

// This is part of my code masterpiece
/* This code's purpose is to provide a tool to allow for saving and loading of objects to and from a file. A large part of the strength
of this code is in the use of generics, which allow the Serializer to be used for any type of object you want, which gives a great
deal of flexibility and lets this code be used in multiple different projects. Another way this code demonstrates good design is through
the implementation of the IDataConverter interface, which means that if you wanted to switch out this Serializer for one that saves to 
a different file format, for example, you could easily do that by using another class that implements the IDataConverter interface,
allowing this feature to be modular. In addition, in Serializer there are only separate methods to choose a file to load and then 
load the object in the file, chooseXMLFile() and loadFromFile(), the IDataConverter interface covers the case where the user might want 
to do both at the same time through the use of default method implementations at the interface level, loadElement() without the need 
to write a separate method in the Serializer class just to do that, avoiding as much code duplication as possible.
*/
public class Serializer<T> implements IDataConverter<T> {
	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);
	private FileChooserDelegate chooser = new FileChooserDelegate();
	
	public File saveElement(T element) {
		File file = this.chooser.save(myNamesBundle.getString("chooseLocationMessage"));
		if (!checkNullFile(file)) {
			XStream xstream = new XStream();
			String xmlString = xstream.toXML(element);
			try {
				FileWriter writer = new FileWriter(file);
				writer.write(xmlString);
				writer.flush();
				writer.close();
				return file;
			} catch (IOException e) {
				new ExceptionDialog(myNamesBundle.getString("saveErrorMessage")).displayMessage();
			}
		}
		return null;
	}
	
	public File chooseXMLFile() {
		return chooser.chooseXML(myNamesBundle.getString("chooseFileMessage"));
	}
	
	@SuppressWarnings("unchecked")
	public T loadFromFile(File file) {
		if(!checkNullFile(file)){
			try {
				XStream xstream = new XStream();
				return (T) xstream.fromXML(file);
			}
			catch(ClassCastException e) {
				new ExceptionDialog(myNamesBundle.getString("wrongFormatMessage")).displayMessage();
			}
		}
		return null;
	}
	
	private boolean checkNullFile(File f) {
		if (f == null) {
			new ExceptionDialog(myNamesBundle.getString("nullFileMessage")).displayMessage();
		}
		return f == null;
	}

}