package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.entities.player.Player;
import santaJam.inputs.Inputs;
import santaJam.states.Camera;
import santaJam.states.StateManager;

public class GrappleItem extends Upgrade{
	
	private int timer=0;
	
	public GrappleItem(int x, int y) {
		super(x,y);
		name = "grappling hook";
		description = "grapple onto things by pressing "+Inputs.grapple().getKey();
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockGrapple(player);
		
	}
	@Override
	public void update() {
		super.update();
		timer++;
		
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(timer%40>20) {
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.white);
		}
		g.fillOval(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	

}
