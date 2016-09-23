package game_player.view;

import java.util.ResourceBundle;

public class LosingScreen extends Screen{
	
	public LosingScreen(ResourceBundle r, PlayerGUI GUI) {
		super(r, GUI, r.getString("LoseText"), r.getString("LoseImage"));
	}
}