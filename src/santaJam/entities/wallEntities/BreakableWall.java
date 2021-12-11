package santaJam.entities.wallEntities;

import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;

import santaJam.Assets;
import santaJam.graphics.Camera;

public class BreakableWall extends WallEntity{
	private int image=0;

	public BreakableWall(int x, int y) {
		super(x,y);
		image=ThreadLocalRandom.current().nextInt(Assets.iceWall.length);
	}	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.drawImage(Assets.iceWall[image],bounds.x-camera.getxOffset(),bounds.y-camera.getyOffset(),null);
	}
	
	public void smash() {
		killed=true;
	}
	

}
