package santaJam.entities.upgrades;

import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.inputs.Inputs;
import santaJam.states.StateManager;

public class UpBoostItem extends Upgrade{
	

	private Animation anim = new Animation(Assets.boostIcon);

	
	public UpBoostItem(int x, int y) {
		super(x,y);
		name = "up boost";
		description = "press "+Inputs.up().getKey()+" while sliding to boost up with current momentum";
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockUpBoost(player);
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
