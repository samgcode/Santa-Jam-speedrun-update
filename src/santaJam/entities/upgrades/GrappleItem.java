package santaJam.entities.upgrades;

import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.states.StateManager;

public class GrappleItem extends Upgrade{
	
	
	private Animation anim = new Animation(Assets.grappleIcon);

	
	public GrappleItem(int x, int y) {
		super(x,y);
		name = "grappling hook";
		description = "grapple onto things by pressing "+Inputs.getKey(Keybind.GRAPPLE).getKey();
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockGrapple(player);
		super.onCollect(player);		
	}
	@Override
	public void update() {
		super.update();
		anim.update();
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(),bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),null);
	}
	

}
