package santaJam.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.Game;
import santaJam.SantaJam;
import santaJam.audio.MusicManager;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.ImgShape;
import santaJam.graphics.particles.spawners.RectangleSpawn;

public class Icicle extends Entity{
	private Animation anim = new Animation(Assets.icicle,3);
	private final int FALLSPEED=3;
	private Rectangle fallBounds;
	boolean falling = false;
	int fallDelay=15;
	
	public Icicle(int x, int y) {
		this.x=x+1;
		this.y=y;
		bounds = new Rectangle((int)this.x,(int)this.y,6,16);
		fallBounds = new Rectangle(bounds.x+1,bounds.y,4,Game.HEIGHT);
		grappleable=true;
		
		anim.setLooping(true);
		anim.setPaused(true);
	}
	@Override
	public void update() {
		anim.update();
		if(falling) {
			fallDelay--; 
			if(fallDelay<0) {
				velY=FALLSPEED;
				updateBounds();
				if(velY==0) {
					killed=true;
					MusicManager.playSound(MusicManager.smash);
					new RectangleSpawn(3, bounds.x-3, bounds.y+bounds.height-3, bounds.width+10,5,new Straight(0, 0, -90,60,1),
							new ImgShape(Assets.iceParticle,6,2), true);
				}
			}
			
		}
		for (Entity i:manager.getEntities()) {// looping through all the entities
			
			if (i instanceof Player) {// checking if it is touching this
				if(i.bounds.intersects(fallBounds)&&!falling) {
					falling=true;
					anim.setPaused(false);
					MusicManager.crack.play();
					new RectangleSpawn(2, bounds.x-3, bounds.y, bounds.width+10,5,new Straight(0, 0, 90,60,0.75),
							new ImgShape(Assets.iceParticle,10,5), true);
				}
				if(i.bounds.intersects(bounds)) {
					i.kill();
				}
			}
			
		}
	}
	@Override
	public void render(Graphics2D g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(),bounds.x-1-camera.getxOffset(),bounds.y-camera.getyOffset(),null);
		//g.setColor(Color.white);
		//g.drawRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	
	
	
}




