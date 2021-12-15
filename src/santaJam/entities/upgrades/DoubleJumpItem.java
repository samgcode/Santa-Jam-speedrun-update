package santaJam.entities.upgrades;

import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.states.StateManager;

public class DoubleJumpItem extends Upgrade{
	

	private Animation anim = new Animation(Assets.doubleJumpIcon);

	
	public DoubleJumpItem(int x, int y) {
		super(x, y);
		name = "double jump";
		description = "jump but again";
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockDoubleJump(player);
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
