package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.inputs.Inputs;

public class PauseState implements State{

	GameState gameState;
	public PauseState(GameState gameState) {
		this.gameState = gameState;
	}
	@Override
	public void start(State prevState) {}

	@Override
	public void update() {
		if(Inputs.pause().isPressed()) {
			StateManager.setCurrentState(gameState);
		}if(Inputs.right().isPressed()) {
			StateManager.setCurrentState(new MapState(gameState));
		}if(Inputs.left().isPressed()) {
			StateManager.setCurrentState(new SettingsState(gameState));
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(57,11,50));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.white);
		g.setFont(Assets.font);
		g.drawString("PAUSED", 150, 10);
		
		if(StateManager.getGameState().getSave().hasDoubleJump()) {
			g.drawString("DOUBLE JUMP AQUIRED", 100, 50);
		}
		if(StateManager.getGameState().getSave().hasGrapple()) {
			g.drawString("GRAPPLE AQUIRED", 100, 70);
		}
	}

	@Override
	public void end() {}

}
