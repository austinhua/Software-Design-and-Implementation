package exceptions;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class CorrectableException extends Exception{

	private static final long serialVersionUID = 1L;
	private String myResult;

	public CorrectableException(String message, Object inputType){
		myResult = correctMessage(message, inputType);
	}

	public CorrectableException(List<String> options){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("WOMP EXCEPTION");
		dialog.setHeaderText("Choose an option.");
		String opts = "Options: ";
		for(String o : options){
			opts += o+" ";
		}
		dialog.setContentText(opts);
	}

	private String correctMessage(String message, Object type){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("WOMP EXCEPTION");
		dialog.setHeaderText(message);
		dialog.setContentText("Please enter new argument.");
		Optional<String> unresolved = dialog.showAndWait();
		if(unresolved.isPresent()){
			String result = unresolved.get();
			if(type instanceof Double){
				try{
					Double.parseDouble(result);
					return result;
				}
				catch(Exception e){
					correctMessage(message, type);
				}
			}
			if(type instanceof Integer){
				try{
					Integer.parseInt(result);
					return result;
				}
				catch(Exception e){
					correctMessage(message, type);
				}
			}
		}
		return null;
	}

	public String getResult(){
		return myResult;
	}

}