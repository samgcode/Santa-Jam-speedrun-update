package santaJam.states;

import java.awt.Graphics2D;

public class StateManager {
	private static State currentState;
	
	
	
	public static void update() {
		currentState.update();
	}
	public static void render(Graphics2D g) {
		currentState.render(g);
	}
	
	public static void setCurrentState(State newState) {
		if(currentState!=null) {
			currentState.end();
		}
		newState.start(currentState);
		currentState = newState;
	}
	public static State getCurrentState() {
		return currentState;
	}

}
