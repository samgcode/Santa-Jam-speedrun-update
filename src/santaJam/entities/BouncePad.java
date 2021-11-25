package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.entities.player.Player;
import santaJam.maps.Map;
import santaJam.states.Camera;

public class BouncePad extends Entity{
	private final double BOUNCESTRENGTH=6;
	char direction;
	
	public BouncePad(int x, int y, char direction) {
		
		if(direction == 'l') {
			x+=Map.TILESIZE-2;
		}else if(direction == 'u') {
			y+=Map.TILESIZE-2;
		}
		if(direction=='u') {
			bounds = new Rectangle(x+1,y,6,2);
		}else {
			bounds = new Rectangle(x,y+1,2,6);
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
				if(direction=='l') {
					((Player)i).setVelX(-BOUNCESTRENGTH);
				}else if(direction=='r') {
					((Player)i).setVelX(BOUNCESTRENGTH);
				}else if(direction=='u') {
					((Player)i).setVelY(-BOUNCESTRENGTH*1.5);
				}
				
			}
		}
	}
	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(Color.blue);
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	
	
}




