package game_data;

import java.io.File;

// This is part of my code masterpiece
// This is the interface that the Serializer implements that is described at the top of the serializer class.

public interface IDataConverter<T> {
	
	/**
	 * @param Data object to be saved
	 * @return File that the data was saved to
	 */
	public File saveElement(T data);
	
	/**
	 * @return XML file the user selected
	 */
	public File chooseXMLFile();
	
	/**
	 * @return The object loaded from file
	 */
	public T loadFromFile(File file);
	
	public default T loadElement() {
		return loadFromFile(chooseXMLFile());
	}
	
}
