package exceptions;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class ExceptionDialog extends Exception{

	private static final long serialVersionUID = 1L;
	private String myMessage;
	
	public ExceptionDialog(String message) {
		super(message);
		this.myMessage = message;
	}
	
	public void displayMessage(){
		Dialog<?> alert = new Dialog<Object>();
		alert.setTitle("Error");
		alert.setHeaderText(myMessage);
		ButtonType buttonTypeOk = new ButtonType("Okay");
		alert.getDialogPane().getButtonTypes().add(buttonTypeOk);
		alert.showAndWait();
	}

}