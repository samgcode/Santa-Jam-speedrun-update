package santaJam.graphics.particles.spawners;

import java.util.concurrent.ThreadLocalRandom;

import santaJam.graphics.particles.Particle;
import santaJam.graphics.particles.movers.ParticleMover;
import santaJam.graphics.particles.shapes.ParticleShape;

public class RectangleSpawn extends Spawner{
	public RectangleSpawn(int amount, int x, int y,int width, int height,ParticleMover mover, ParticleShape shape, boolean isOnTop) {
		super(x, y, mover, shape);
		for(int i=0;i<amount;i++) {
			new Particle(mover.copy(x+ThreadLocalRandom.current().nextInt(0,width), y+ThreadLocalRandom.current().nextInt(0,height)), 
					shape.copy(), isOnTop);
		}
	}
}
