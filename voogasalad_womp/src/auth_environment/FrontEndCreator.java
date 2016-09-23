package auth_environment;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class FrontEndCreator {
	
	public void createTextLabels(GridPane pane, String s, int row, int col, double size){
		addRow(pane, size);
		pane.add(new Text(s), col, row);
	}
	
	public TextField createTextField(GridPane pane, int row, int col){
		TextField textField = new TextField();
		pane.add(textField, col, row);
		return textField;
	}

	public ComboBox<String> createStringComboBox(GridPane pane, List<String> list, int row, int col){
		ComboBox<String> cb = new ComboBox<>();
		cb.getItems().addAll(list);
		pane.add(cb, col, row);
		return cb;
	}
	
	public Button createButton(GridPane pane, String s, int row, int col){
		Button button = new Button(s);
		pane.add(button, col, row);
		return button;
	}
	
	public void addRow(GridPane pane, double size){
		pane.getRowConstraints().add(new RowConstraints(size));
	}
}
