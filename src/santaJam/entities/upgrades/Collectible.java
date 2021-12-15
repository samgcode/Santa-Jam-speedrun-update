package santaJam.entities.upgrades;

import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.states.StateManager;


public class Collectible extends Upgrade{
	public static final int MILK=0, MARSHMALLOW=1,CHOCOLATE=3, MAXANIMTIME=120;
	private int type = 0, animTimer=0;
	private Animation anim;

	public Collectible(int x, int y, int type) {
		super(x, y);
		this.type=type;
		grappleable=false;
		if(type==MILK) {
			name = "MILK";
			description = "the base of all good hot chocolate";
			anim = new Animation(Assets.milk);
		}else if(type==MARSHMALLOW) {
			name = "MARSHMELLOW";
			description = "nice and fluffy";
			anim=new Animation(Assets.marshmallow);
		}else if(type==CHOCOLATE) {
			name = "CHOCOLATE";
			description = "puts the chocolate in hot chocolate";
			anim=new Animation(Assets.chocolate);
		}
		anim.setLooping(false);
		
	}
	

	@Override
	protected void onCollect(Player player) {
		String room = StateManager.getGameState().getMap().getPlayerRoom().getName();
		StateManager.getGameState().getSave().addCollectible(room);
		super.onCollect(player);
		

	}
	@Override
	public void update() {
		super.update();
		anim.update();
		animTimer++;
		if(animTimer>MAXANIMTIME) {
			anim.setPaused(false);
			animTimer=0;
		}
		
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(),bounds.x+bounds.width/2-anim.getCurrentFrame().getWidth()/2-camera.getxOffset(),
				bounds.y+bounds.height/2-anim.getCurrentFrame().getHeight()/2-camera.getyOffset(), null);
		//g.drawRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width-1, bounds.height-1);
		
	}
	

}
