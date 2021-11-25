package santaJam.graphics.particles;

import java.awt.Graphics;

import santaJam.graphics.particles.movers.ParticleMover;
import santaJam.graphics.particles.shapes.ParticleShape;
import santaJam.states.Camera;

public class Particle {
	
	private static ParticleManager particleManager=new ParticleManager();
	private ParticleMover mover;
	private ParticleShape shape;
	private boolean remove=false;
	
	public Particle(ParticleMover mover, ParticleShape shape, boolean isOnTop) {
	
		particleManager.addParticle(this,isOnTop);
		this.mover=mover;
		this.shape=shape;
	}
	
	public void update() {
		mover.update();
		shape.update();
		if(mover.isRemove()||shape.isRemove()) {
			remove=true;
			
		}
	}
	
	public void render(Graphics g, Camera camera) {
		shape.render(g, camera, mover.getX(), mover.getY());
	} 
	
	public static ParticleManager getParticleManager() {
		return particleManager;
	}
	
	public boolean isRemove() {
		return remove;
	}

}
