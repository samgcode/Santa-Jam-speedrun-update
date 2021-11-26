package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.entities.player.Player;
import santaJam.states.Camera;
import santaJam.states.StateManager;


public class Collectible extends Upgrade{
	public static final int MILK=0, MARSHMELLOW=1,CHOCOLATE=3;
	private int type = 0;

	public Collectible(int x, int y, int type) {
		super(x, y);
		this.type=type;
		if(type==MILK) {
			name = "MILK";
			description = "the base of all good hot chocolate";
		}else if(type==MARSHMELLOW) {
			name = "MARSHMELLOW";
			description = "nice and fluffy";
		}else if(type==CHOCOLATE) {
			name = "CHOCOLATE";
			description = "puts the chocolate in hot chocolate";
		}
		
	}
	

	@Override
	protected void onCollect(Player player) {
		String room = StateManager.getGameState().getMap().getPlayerRoom().getName();
		StateManager.getGameState().getSave().addCollectible(room);
	}
	@Override
	public void update() {
		super.update();
		
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(type==MILK) {
			g.setColor(Color.white);
		}else if(type==MARSHMELLOW) {
			g.setColor(Color.lightGray);
		}else if(type==CHOCOLATE) {
			g.setColor(new Color(50,25,12));
		}
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	

}
