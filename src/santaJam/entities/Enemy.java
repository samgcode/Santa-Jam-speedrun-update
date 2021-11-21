package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.states.Camera;

public class Enemy extends Entity{

	public Enemy(int x, int y) {
		super(x-8,y-8,16,16);
		health=4;
		damage=1;
		team=1;
		
		
	}
	@Override
	public void update() {
		doNormalGravity();
		doFriction();
		updateBounds();
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		
		g.setColor(Color.RED);
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
		for(int i=0;i<health;i++) {
			g.fillRect(bounds.x-camera.getxOffset()-5+i*5, bounds.y-camera.getyOffset()-5, 3, 3);
		}
		
	}

}
