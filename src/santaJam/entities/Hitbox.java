package santaJam.entities;

import java.awt.Graphics2D;

import santaJam.states.Camera;

public class Hitbox extends Entity{

	int timeLeft;
	boolean hit=false;
	public Hitbox(int x, int y, int width, int height, int time, int team, int damage, boolean faceLeft) {
		super(x, y, width, height);
		this.timeLeft=time;
		this.team=team;
		this.damage=damage;
		this.faceLeft=faceLeft;
	}
	public Hitbox(int x, int y, int width, int height, int time, boolean faceLeft) {
		this(x, y, width, height, time,0,1,faceLeft);
	}
	
	
	@Override
	public void update() {
		timeLeft--;
		if(timeLeft==0) {
			killed=true;
		}
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
		
	}
	@Override
	public void damage(double d) {
		if(d>0) {
			hit=true;
		}
		
	}
	
	public void move(int x, int y) {
		this.x=x;
		this.y=y;
		bounds.x=x;
		bounds.y=y;
		
	}
	public boolean isHit() {
		return hit;
	}

}
