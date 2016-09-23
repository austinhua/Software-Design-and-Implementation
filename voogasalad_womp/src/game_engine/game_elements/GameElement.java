package game_engine.game_elements;

/**
 * This interface is the external API of the game element module for providing element information to the front-end.
 *
 */

public abstract class GameElement {
	
	private String myName;
	
	public GameElement(String name){
		this.myName = name;
	}
	
	/**
	 * Returns a String representation of the element
	 */
	public String toString(){
		return myName;
	}
	
	public String getName(){
		return myName;
	}
	
	
}