package utility;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import com.twilio.sdk.TwilioRestException;

import auth_environment.delegatesAndFactories.FileChooserDelegate;
import auth_environment.delegatesAndFactories.NodeFactory;
import exceptions.ExceptionDialog;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/*
 * Example code demonstrating how to integrate cloud storage with front-end
 */
public class CloudStorageFrontend {
	
	private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
	private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);
	
	private static final String NAMES_PACKAGE = "auth_environment/properties/names";
	private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);
	
	private static final String URLS_PACKAGE = "auth_environment/properties/urls";
	private ResourceBundle myURLSBundle = ResourceBundle.getBundle(URLS_PACKAGE);
	
	private Scene myScene;
	private VBox myVBox;
	private TextField myKeyInput; 
	private ListView<String> myBoxContents; 
	private String location; 
	private String myDevKey = "";
	private CloudStorage myCloudStorage; 
	private FileChooserDelegate myChooser; 
	private NodeFactory myNodeFactory; 

	public CloudStorageFrontend () {
		init(); 
	}

	private void init() {
		Stage myStage = new Stage(); 
		myChooser = new FileChooserDelegate(); 
		myNodeFactory = new NodeFactory(); 
		myVBox = new VBox(); 
		myScene = new Scene(myVBox);
		myVBox.getChildren().addAll( 
				buildBoxImage(),
				buildKeyInput(),
				buildFileButton(),
				buildViewButton(),
				buildViewContents()
				);
		myVBox.setStyle(myDimensionsBundle.getString("greyBackgroundStyle"));
		myVBox.setPrefSize(Double.parseDouble(myDimensionsBundle.getString("boxWidth")), 
				Double.parseDouble(myDimensionsBundle.getString("boxHeight"))); 
		myStage.setScene(myScene);
		myStage.show();
	}
	
	private Node buildFileButton() {
		Button chooseFile = new Button(myNamesBundle.getString("fileChooserTitle"));
		chooseFile.setOnAction(e -> uploadFile());
		
		Button chooseFolder = new Button(myNamesBundle.getString("chooseFolderMessage")); 
		chooseFolder.setOnAction(e -> uploadFolder());
		
		HBox hb = myNodeFactory.buildHBox(Double.parseDouble(myDimensionsBundle.getString("defaultHBoxSpacing")),
				Double.parseDouble(myDimensionsBundle.getString("defaultHBoxPadding")));
		hb.getChildren().addAll(chooseFile, chooseFolder); 
		return myNodeFactory.centerNode(hb); 
	}
	
	private void uploadFolder() {
		File f = this.myChooser.chooseDirectory(myNamesBundle.getString("chooseFolderMessage")); 
		if (f!=null) {
			String path = f.getPath(); 
			try {
				myCloudStorage.uploadFolder(path);
			} catch (FileNotFoundException e) {
				new ExceptionDialog(myNamesBundle.getString("nullFileMessage")).displayMessage();
			} catch (TwilioRestException e) {
				new ExceptionDialog(myNamesBundle.getString("twilioErrorMessage")).displayMessage();
			}
			printResults(myCloudStorage);
		}
	}
	
	private void uploadFile() {
		File f = myChooser.chooseFile(myNamesBundle.getString("fileChooserTitle")); 
		if (f != null) {
	        try {
				myCloudStorage.uploadFile(f.getAbsolutePath(), f.getName());
			} catch (FileNotFoundException e) {
				new ExceptionDialog(myNamesBundle.getString("nullFileMessage")).displayMessage();
			}
	        printResults(myCloudStorage);
        }
	}
	
	private void checkDevKey() {
		myDevKey = myKeyInput.getText();
		try {
			myCloudStorage = new CloudStorage(myDevKey);
			myCloudStorage.setUpNotifications(
					myNamesBundle.getString("utilPath"), 
					myNamesBundle.getString("accountSID"), 
					myNamesBundle.getString("authToken"), 
					myNamesBundle.getString("twilioNumber"));
		} catch (Exception e) {
			new ExceptionDialog(myNamesBundle.getString("invalidKeyMessage")).displayMessage();
		}
	}
	
	private Node buildKeyInput() {
		myKeyInput = myNodeFactory.buildTextFieldWithPrompt(myNamesBundle.getString("accountKeyMessage"));
		myKeyInput.setOnAction(e -> checkDevKey());
		
		Button submitButton = new Button(myNamesBundle.getString("submitButtonLabel"));
		submitButton.setOnAction(e -> checkDevKey());
		
		HBox hb = myNodeFactory.buildHBox(Double.parseDouble(myDimensionsBundle.getString("defaultHBoxSpacing")),
				Double.parseDouble(myDimensionsBundle.getString("defaultHBoxPadding")));
		hb.getChildren().addAll(myKeyInput, submitButton);
		return myNodeFactory.centerNode(hb); 
	}
	
	private Node buildBoxImage() {
		HBox hb = new HBox();
		hb.getChildren().add(myNodeFactory.buildImageView(myURLSBundle.getString("boxLogo")));
		return myNodeFactory.centerNode(hb); 
	}
	
	private void printResults (CloudStorage c) {
        c.listFolders();
        try {
			c.notify(myNamesBundle.getString("newFilesMessage"));
		} catch (TwilioRestException e) {
			new ExceptionDialog(myNamesBundle.getString("twilioErrorMessage")).displayMessage();
		}
	}
	
	private Node buildViewButton() {
		HBox hb = new HBox(); 
		Button view = new Button(myNamesBundle.getString("viewContentsMessage")); 
		view.setOnAction(e -> populateViewContents());
		Button choose = new Button(myNamesBundle.getString("chooseSaveMessage")); 
		choose.setOnAction(e -> {
			File f = myChooser.chooseDirectory(myNamesBundle.getString("chooseSaveMessage")); 
			if (f!=null) {
				this.location = f.getAbsolutePath();
			}
		});
		TextField field = myNodeFactory.buildTextFieldWithPrompt(myNamesBundle.getString("saveLocationMessage"));
		field.setOnAction(e -> this.location = field.getText());
		hb.getChildren().addAll(view, choose, field);
		return hb; 
	}

	private Node buildViewContents() {
		myBoxContents = new ListView<String>();
		myBoxContents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.toString()!=null) {
				try {
					this.myCloudStorage.downloadFromCurrent(processName(newValue.toString()), this.location);
				} catch (Exception e) {
					new ExceptionDialog(myNamesBundle.getString("boxErrorMessage")).displayMessage();
				}
			}
		});
		return myBoxContents; 
	}
	
	private String processName(String in) {
		if (in.toLowerCase().contains(myNamesBundle.getString("folderDelimiter"))) {
			return in.split(myNamesBundle.getString("folderDelimiter"))[0].trim(); 
		}
		else {
			return in;
		}
	}
	
	private void populateViewContents() {
		myBoxContents.getItems().clear();
		myBoxContents.getItems().addAll(this.myCloudStorage.getCurrentFiles());
	}
	
}
