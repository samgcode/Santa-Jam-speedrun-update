package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Game;
import santaJam.entities.player.Player;
import santaJam.graphics.Camera;

public class Icicle extends Entity{
	private final int FALLSPEED=3;
	Rectangle fallBounds, cameraBounds;
	boolean falling = false;
	int fallDelay=10;
	
	public Icicle(int x, int y) {
		this.x=x;
		this.y=y;
		bounds = new Rectangle((int)this.x,(int)this.y,8,16);
		fallBounds = new Rectangle(bounds.x+1,bounds.y,6,Game.HEIGHT);
		cameraBounds = new Rectangle(bounds.x,bounds.y+bounds.height/2,bounds.width,bounds.height/2);
		grappleable=true;
	}
	@Override
	public void update() {
		if(falling) {
			fallDelay--; 
			if(fallDelay<0) {
				velY=FALLSPEED;
				updateBounds();
				if(velY==0) {
					killed=true;
				}
			}
			
		}
		for (Entity i:manager.getEntities()) {// looping through all the entities
			
			if (i instanceof Player) {// checking if it is touching this
				if(i.bounds.intersects(fallBounds)) {
					falling=true;
				}
				if(i.bounds.intersects(bounds)) {
					i.kill();
				}
			}
			
		}
	}
	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(Color.red);
		if(falling) {
			g.setColor(Color.yellow);
		}
		
		
		
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	
	
}




