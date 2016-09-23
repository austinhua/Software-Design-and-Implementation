package game_player.interfaces;

import exceptions.ExceptionDialog;
import javafx.scene.control.Tab;


/**
 * Interface for any kind of tab required for the player.
 * 
 * @author David
 *
 */
public interface IPlayerTab {

    /**
     * Creates Tab object that will be placed into TabPane.
     * 
     * @return Tab
     * @throws ExceptionDialog 
     */
    Tab getTab () throws ExceptionDialog;
}
