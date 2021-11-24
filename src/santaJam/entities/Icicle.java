package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Game;
import santaJam.entities.player.Player;
import santaJam.maps.Map;
import santaJam.states.Camera;

public class Icicle extends Entity{
	private final int FALLSPEED=3;
	Rectangle fallBounds;
	boolean falling = false, onScreen = false;
	int fallDelay=10;
	
	public Icicle(int x, int y) {
		this.x=x+Map.TILESIZE/2-2;
		this.y=y;
		bounds = new Rectangle((int)this.x,(int)this.y,6,20);
		fallBounds = new Rectangle(bounds.x,bounds.y,6,Game.HEIGHT);
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
				if(onScreen&&i.bounds.intersects(fallBounds)) {
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
		onScreen=false;
		if(camera.getBounds().contains(bounds)) {
			onScreen=true;
		}
		
		g.setColor(Color.red);
		if(falling) {
			g.setColor(Color.yellow);
		}
		
		
		
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	
	
}




