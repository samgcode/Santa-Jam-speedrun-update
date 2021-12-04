package santaJam.graphics.particles;

import java.awt.Graphics;
import java.util.ArrayList;

import santaJam.graphics.Camera;

public class ParticleManager {
	private ArrayList<Particle> frontParticles=new ArrayList<Particle>();
	private ArrayList<Particle> backParticles=new ArrayList<Particle>();
	
	public void addParticle(Particle p, boolean topLayer) {
		//adding the particle to the right list depending on whether it should go above or below the entities
		if(topLayer) {
			frontParticles.add(p);
		}else {
			backParticles.add(p);
		}
	}
	
	public void update() {
		//updating the particles that are above the entities
		for(int i=frontParticles.size()-1;i>=0;i--) {
			frontParticles.get(i).update();
			if(frontParticles.get(i).isRemove()) {
				frontParticles.remove(i);
			}
		}
		//updating particles that are below the entity layer
		for(int i=backParticles.size()-1;i>=0;i--) {
			backParticles.get(i).update();
			if(backParticles.get(i).isRemove()) {
				backParticles.remove(i);
			}
		}
	}
	
	//drawing the particles that should be above the entities
	public void renderFront(Graphics g, Camera camera) {
		for(int i=frontParticles.size()-1;i>=0;i--) {
			frontParticles.get(i).render(g, camera);
		}
	}
	//rendering the particles that should go below the entities
	public void renderBack(Graphics g, Camera camera) {
		for(int i=backParticles.size()-1;i>=0;i--) {
			backParticles.get(i).render(g, camera);
		}
	}

}
