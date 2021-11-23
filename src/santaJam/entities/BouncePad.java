package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.entities.player.Player;
import santaJam.maps.Map;
import santaJam.states.Camera;

public class BouncePad extends Entity{
	private final double BOUNCESTRENGTH=9;
	boolean facingLeft=false;
	
	public BouncePad(int x, int y, boolean facingLeft) {
		if(facingLeft) {
			x+=Map.TILESIZE-2;
		}else {
	
		}
		bounds = new Rectangle(x,y+2,2,9);
		this.faceLeft=facingLeft;
		this.x=x;
		this.y=y;
		grappleable=true;
	}
	@Override
	public void update() {
		
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				if(faceLeft) {
					((Player)i).setVelX(-BOUNCESTRENGTH);
				}else {
					((Player)i).setVelX(BOUNCESTRENGTH);
				}
				
			}
		}
	}
	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(Color.red);
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	
	
}




