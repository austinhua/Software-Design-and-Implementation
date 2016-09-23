package game_engine.store_elements;

public class Pair<T1, T2> {
	private T1 left;
	private T2 right;
	public Pair(T1 first, T2 second){
		left = first;
		right = second;
	}
	public T1 getLeft(){
		return left;
	}
	public T2 getRight(){
		return right;
	}
	public void setLeft(T1 update){
		this.left = update;
	}
	public void setRight(T2 update){
		this.right = update;
	}
}
