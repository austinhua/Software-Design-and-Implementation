package game_engine.interfaces;

public interface ILevelDisplayer {
	/*
	 * Returns a string that can be displayed on screen to indicate information about the player's
	 * current status in the game. Information related to lives, progress, or any other in-game 
	 * standings can be displayed here.
	 * 
	 * @return	String of the formatted information from the game status.
	 */
	String getGameStatus();
	
	/*
	 * Returns the number of lives that the player has left for the current level.
	 * 
	 * @return	int number of lives left in the level before the player dies.
	 */
	int getLives();
	
	/*
	 * Returns the score of the player that was accumulated throughout the level.
	 * 
	 * @return	double value represents points for the player
	 */
	double getScore();
	
	/*
	 * Returns true if the game is paused, and false otherwise.
	 * 
	 * @return	boolean value represents whether or not the game is playing currently.
	 */
	boolean isPaused();
}
