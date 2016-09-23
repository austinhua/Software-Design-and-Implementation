package game_player.view;

import java.util.ResourceBundle;

public class NextLevelScreen extends Screen{
        
        public NextLevelScreen(ResourceBundle r, PlayerGUI GUI) {
                super(r, GUI, r.getString("WinText"), r.getString("WinImage"));
        }
}