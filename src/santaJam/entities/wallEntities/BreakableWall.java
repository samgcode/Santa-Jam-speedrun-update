package santaJam.entities.wallEntities;

import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;

import santaJam.Assets;
import santaJam.audio.MusicManager;
import santaJam.graphics.Camera;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.ImgShape;
import santaJam.graphics.particles.spawners.RectangleSpawn;

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
		MusicManager.playSound(MusicManager.smash);
		killed=true;
		new RectangleSpawn(3, bounds.x, bounds.y+bounds.height, bounds.width,bounds.height,
			new Straight(0, 0, 0,360,0.5), new ImgShape(Assets.iceParticle,10, 3), true);
						
	}
	

}
