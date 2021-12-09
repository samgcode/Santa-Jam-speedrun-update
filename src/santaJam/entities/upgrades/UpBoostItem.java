package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.inputs.Inputs;
import santaJam.states.StateManager;

public class UpBoostItem extends Upgrade{
	
	private int timer=0;
	
	public UpBoostItem(int x, int y) {
		super(x,y);
		name = "up boost thing";
		description = "press "+Inputs.up().getKey()+" to convert forward moment into vertival momentum";
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockUpBoost(player);
		super.onCollect(player);

	}
	@Override
	public void update() {
		super.update();
		timer++;
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(timer%40>20) {
			g.setColor(Color.CYAN);
		}else{
			g.setColor(Color.white);
		}
		g.fillOval(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	

}
