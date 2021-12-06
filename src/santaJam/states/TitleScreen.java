package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.inputs.Inputs;

public class TitleScreen implements State{
	@Override
	public void start(State prevState) {
	}

	@Override
	public void update() {
		if(Inputs.jump().isPressed()) {
			StateManager.setCurrentState(new MainMenu());
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setFont(Assets.font);
		g.setColor(Color.black);
		g.drawString("COOL TITLE NAME", Game.WIDTH/2-40, 51);
		g.setColor(Color.white);
		g.drawString("COOL TITLE NAME", Game.WIDTH/2-41, 50);
		
		g.drawString("--PRESS "+Inputs.jump().getKey().toUpperCase()+" TO START--", Game.WIDTH/2-50, 100);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
