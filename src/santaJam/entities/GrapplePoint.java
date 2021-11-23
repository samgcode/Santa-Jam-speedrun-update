package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.states.Camera;

public class GrapplePoint extends Entity{
	
	public GrapplePoint(int x, int y) {
		bounds = new Rectangle(x-7,y-7,14,14);
		this.x=x-7;
		this.y=y-7;
	
		grappleable=true;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(new Color(166,33,144));
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
}
