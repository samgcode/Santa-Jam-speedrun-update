package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.states.Camera;
import santaJam.states.StateManager;

public class DoubleJump extends Upgrade{
	
	private int timer=0;
	
	public DoubleJump(int x, int y) {
		bounds = new Rectangle(x,y,10,10);
		this.x=x;
		this.y=y;
	}
	

	@Override
	protected void onCollect() {
		StateManager.getGameState().getPlayer().unlockDoubleJump();
	}
	@Override
	public void update() {
		super.update();
		timer++;
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(timer%60>30) {
			g.setColor(Color.orange);
		}else{
			g.setColor(Color.white);
		}
		g.fillOval(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	

}
