package game_player;

/**
 * This class holds user-specific information such as high scores, game-speed, color themes, etc.
 * as well as types of games. Throws exception when data key not found. 
 * @author ownzandy
 *
 */

public interface IGameDataSource {
    
    //sets double values 
    void setDoubleValue(String key, double value);
    
    //get double values 
    double getDoubleValue(String key);
     
}
