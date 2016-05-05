
/**
 * @author becul
 * states of the player
 *
 */
public enum State {
	DEALING,PLAYING,FINISHED;
	private State s;
	public void setState(State s){
		this.s=s;
	}
	public State getState(){
		return s;
	}
}
