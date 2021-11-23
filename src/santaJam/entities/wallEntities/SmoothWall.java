package santaJam.entities.wallEntities;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.states.Camera;

public class SmoothWall extends WallEntity{

	public SmoothWall(int x, int y) {
		super(x,y);
	}	
	@Override
	public void update() {

	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(new Color(30,30,30));
		g.fillRect(bounds.x-camera.getxOffset(),bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	

}
