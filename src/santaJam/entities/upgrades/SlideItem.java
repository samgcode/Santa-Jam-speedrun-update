package santaJam.entities.upgrades;

import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.states.StateManager;

public class SlideItem extends Upgrade{
	private Animation anim = new Animation(Assets.slideIcon);

	
	public SlideItem(int x, int y) {
		super(x, y);
		name = "slide";
		description = "slide on your belly once you get enough speed";
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockSlide(player);
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
