package santaJam.states;

import java.awt.Graphics2D;

public class StateManager {
	private static State currentState;
	private static GameState gameState;
	
	
	public static void update() {
		if(currentState!=null) {
			currentState.update();
		}
		
	}
	public static void render(Graphics2D g) {
		if(currentState!=null) {
			currentState.render(g);
		}
	}
	
	public static void setCurrentState(State newState) {
		if(currentState!=null) {
			currentState.end();
		}
		if(newState instanceof GameState) {
			gameState = (GameState) newState;
		}
		newState.start(currentState);
		currentState = newState;
	}
	public static State getCurrentState() {
		return currentState;
	}
	
	public static GameState getGameState() {
		return gameState;
	}

}
