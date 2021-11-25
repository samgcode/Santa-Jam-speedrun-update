package santaJam.graphics.particles.spawners;

import santaJam.graphics.particles.Particle;
import santaJam.graphics.particles.movers.ParticleMover;
import santaJam.graphics.particles.shapes.ParticleShape;

public class PointSpawn extends Spawner{
	
	public PointSpawn(int amount, int x, int y,ParticleMover mover, ParticleShape shape, boolean isOnTop) {
		super(x, y, mover, shape);
		for(int i=0;i<amount;i++) {
			new Particle(mover.copy(x, y), shape.copy(), isOnTop);
		}
	}

}
