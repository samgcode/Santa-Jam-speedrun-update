package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.states.StateManager;

public class SlideItem extends Upgrade{
	
	private int timer=0;
	
	public SlideItem(int x, int y) {
		super(x, y);
		name = "slide";
		description = "slide on your belly once you get enough speed";
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockSlide(player);
		StateManager.getGameState().saveData(player.getBounds().x,player.getBounds().y-10);
	}
	@Override
	public void update() {
		super.update();
		timer++;
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(timer%40>20) {
			g.setColor(Color.blue);
		}else{
			g.setColor(Color.white);
		}
		g.fillOval(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	

}
