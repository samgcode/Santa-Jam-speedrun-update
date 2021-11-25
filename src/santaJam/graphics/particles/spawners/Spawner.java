package santaJam.graphics.particles.spawners;

import santaJam.graphics.particles.movers.ParticleMover;
import santaJam.graphics.particles.shapes.ParticleShape;

public abstract class Spawner {
	protected ParticleMover mover;
	protected ParticleShape shape;
	protected int x, y;
	public Spawner(int x, int y,ParticleMover mover, ParticleShape shape) {
		this.x=x;
		this.y=y;
		this.mover = mover;
		this.shape = shape;
	}
	
	
	
	
}
