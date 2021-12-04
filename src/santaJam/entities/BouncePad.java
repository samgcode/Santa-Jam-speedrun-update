package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.entities.player.Falling;
import santaJam.entities.player.Jumping;
import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.maps.Map;

public class BouncePad extends Entity{
	private final double BOUNCESTRENGTH=5.55;
	char direction;
	
	public BouncePad(int x, int y, char direction) {
		
		if(direction == 'l') {
			x+=Map.TILESIZE-3;
		}else if(direction == 'u') {
			y+=Map.TILESIZE-3;
		}
		if(direction=='u') {
			bounds = new Rectangle(x+1,y,6,3);
		}else {
			bounds = new Rectangle(x,y+1,3,6);
		}
		this.direction = direction;
		this.x=x;
		this.y=y;
		grappleable=true;
	}
	@Override
	public void update() {
		
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				System.out.println("boing");
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
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	
	
}




