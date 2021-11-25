package santaJam.graphics.particles.spawners;

import java.util.concurrent.ThreadLocalRandom;

import santaJam.graphics.particles.Particle;
import santaJam.graphics.particles.movers.ParticleMover;
import santaJam.graphics.particles.shapes.ParticleShape;

public class EvenRectSpawn extends Spawner{
	private double amount;
	int width, height;
	boolean isOnTop, running=true;
	int timer=0;
	public EvenRectSpawn(double amount, int x, int y,int width, int height,ParticleMover mover, ParticleShape shape, boolean isOnTop) {
		super(x, y, mover, shape);
		this.width=width;
		this.height=height;
		this.isOnTop=isOnTop;
		this.amount=amount*(width*height)/1000;
		System.out.println(amount+" - "+width+","+height+" = "+this.amount);
	}
	
	
	public void update() {
		timer++;
		if(amount>1) {
			for(int i=0;i<amount;i++) {
				newParticle();
			}
		}else {
			
			if(timer%Math.round(1d/amount)==0) {
				newParticle();
			}
		}
		
		
	}
	private void newParticle() {
		new Particle(mover.copy(x+ThreadLocalRandom.current().nextInt(0,width), y+ThreadLocalRandom.current().nextInt(0,height)), 
				shape.copy(), isOnTop);
	}
	
	
	public void start() {
		running=true;
		System.out.println("starting particles");
	}
	public void stop() {
		running=false;
		System.out.println("stopping");
	}
	
}
