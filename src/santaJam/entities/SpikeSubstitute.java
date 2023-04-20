package santaJam.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.maps.Map;

public class SpikeSubstitute extends Entity{
	char direction;
	private BufferedImage img;
	
	public SpikeSubstitute(int x, int y, char direction) {
		BufferedImage[] imgs = Assets.spikeRight;
		if(direction == 'l') {
			x+=Map.TILESIZE-2;
			imgs = Assets.spikeLeft;
		}else if(direction == 'u') {
			y+=Map.TILESIZE-2;
			 imgs = Assets.spikeUp;
		}else if(direction == 'd') {
			imgs = Assets.spikeDown;
		}
		if(direction=='u'||direction=='d') {
			bounds = new Rectangle(x+1,y,6,2);
		}else {
			bounds = new Rectangle(x,y+1,2,6);
		}
		img = imgs[ThreadLocalRandom.current().nextInt(imgs.length)];
		this.direction = direction;
		this.x=x;
		this.y=y;
		grappleable=true;
	}
	@Override
	public void update() {
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				i.kill();
			}
		}
	}
	@Override
	public void render(Graphics2D g, Camera camera) {
		
		if(direction=='l') {
			g.drawImage(img, bounds.x-camera.getxOffset()-2, bounds.y-camera.getyOffset()-1, null);
		}else if(direction=='u') {
			g.drawImage(img, bounds.x-camera.getxOffset()-1, bounds.y-camera.getyOffset()-2, null);
		}else if(direction=='r') {
			g.drawImage(img, bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset()-1, null);
		}else if(direction=='d') {
			g.drawImage(img, bounds.x-camera.getxOffset()-1, bounds.y-camera.getyOffset(), null);
		}
		
		
	}
	
	
	
}




