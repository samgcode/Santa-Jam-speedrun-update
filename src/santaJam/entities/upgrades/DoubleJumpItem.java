package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.entities.player.Player;
import santaJam.states.Camera;
import santaJam.states.StateManager;

public class DoubleJumpItem extends Upgrade{
	
	private int timer=0;
	
	public DoubleJumpItem(int x, int y) {
		super(x, y);
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockDoubleJump(player);
	}
	@Override
	public void update() {
		super.update();
		timer++;
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(timer%40>20) {
			g.setColor(Color.orange);
		}else{
			g.setColor(Color.white);
		}
		g.fillOval(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	

}
