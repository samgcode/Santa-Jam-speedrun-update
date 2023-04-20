package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.audio.MusicManager;
import santaJam.entities.player.Falling;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.maps.Map;

public class BouncePad extends Entity{
	private final double BOUNCESTRENGTH=5.55;
	char direction;
	private Animation anim;
	
	public BouncePad(int x, int y, char direction) {
		anim = new Animation(Assets.bounceRight,0,1);
		if(direction == 'l') {
			x+=Map.TILESIZE-2;
			anim = new Animation(Assets.bounceLeft,5,1);
		}else if(direction == 'u') {
			y+=Map.TILESIZE-2;
			x+=1;
			anim = new Animation(Assets.bounceUp,1,5);
			
		}
		if(direction=='u') {
			bounds = new Rectangle(x,y,6,2);
		}else {
			bounds = new Rectangle(x,y+1,2,6);
		}
		anim.setLooping(false);
		anim.setPaused(true);
		this.direction = direction;
		this.x=x;
		this.y=y;
		grappleable=true;
	}
	@Override
	public void update() {
		anim.update();
		
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				MusicManager.playSound(MusicManager.spring);
				
				anim.setPaused(false);
				if(direction=='l') {
					((Player)i).setVelX(-BOUNCESTRENGTH);
				}else if(direction=='r') {
					((Player)i).setVelX(BOUNCESTRENGTH);
				}else if(direction=='u') {
					((Player)i).setVelY(-BOUNCESTRENGTH*1.4); 
				}
				((Player)i).setState(new Falling());
				
			}
		}
	}
	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(Color.blue);
		g.drawImage(anim.getCurrentFrame(),bounds.x-camera.getxOffset()-anim.getxOffset(), bounds.y-camera.getyOffset()-anim.getyOffset(),null);
		//g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	
	
}




