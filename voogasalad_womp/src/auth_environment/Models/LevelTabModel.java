package auth_environment.Models;

public class LevelTabModel {
	
	private int myLevelIndex;
	
	public LevelTabModel() {
		this.myLevelIndex = 0; 
	}
	
	public LevelTabModel(int index) {
		this.myLevelIndex = index;
	}
	
	public int getLevelIndex() {
		return this.myLevelIndex;
	}
	
	public void setLevelIndex(int index) {
		this.myLevelIndex = index; 
	}
	
}
