package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.inputs.Inputs;

public class MapState implements State{
	GameState gameState;
	public MapState(GameState gameState) {
		this.gameState = gameState;
	}
	@Override
	public void start(State prevState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		if(Inputs.left().isPressed()) {
			StateManager.setCurrentState(new PauseState(gameState));
		}if(Inputs.pause().isPressed()) {
			StateManager.setCurrentState(gameState);
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.white);
		g.setFont(Assets.font);
		g.drawString("MAP", 150, 10);
		
		g.drawString("IMAGINE THIS IS A REALLY COOL MAP", 100, 70);
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
