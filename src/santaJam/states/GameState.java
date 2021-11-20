package santaJam.states;

import java.awt.Graphics2D;

import santaJam.entities.Entity;
import santaJam.entities.Player;

public class GameState implements State {
	int x=0, y=0,xVel=0,yVel=0;
	

	
	@Override
	public void start(State prevState) {
		Entity.getManager().addEntity(new Player());
	}
	
	@Override
	public void update() {
		Entity.getManager().update();
		
	}

	@Override
	public void render(Graphics2D g) {
		Entity.getManager().render(g);
	}

	@Override
	public void end() {}

	

}
