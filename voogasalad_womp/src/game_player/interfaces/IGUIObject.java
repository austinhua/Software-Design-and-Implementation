package game_player.interfaces;

import javafx.scene.Node;


/**
 * Interface for creating and updating GUI elements. Throws exception when
 * elements cannot be created correctly.
 * 
 * @author ownzandy
 *
 */
public interface IGUIObject {

    // creates GUI object
    Node createNode ();

    // updates GUI object
    void updateNode ();
}
