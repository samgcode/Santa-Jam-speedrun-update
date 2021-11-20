package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.inputs.Inputs;
import santaJam.states.Camera;

public class Player extends Entity {
	private double topSpeed=2.5, friction=0.5;
	private int cayoteTime=0;
	
	public Player() {
		super(100,100,11,17);
	}

	@Override
	public void update() {
		
		velY+=GRAVITY;
		if(velX>friction) {
			velX-=friction;
		}else if(velX<-friction){
			velX+=friction;
		}else {
			velX=0;
		}
		velX=Math.min(velX, topSpeed);
		velX=Math.max(velX, -topSpeed);
				
		if(velY>MAXGRAVITY) {
			velY=MAXGRAVITY;
		}
		
		if(grounded) {
			cayoteTime=5;
		}else {
			cayoteTime--;
		}
		if(Inputs.left()) {
			velX-=1;
		}if(Inputs.right()) {
			velX+=1;
		}if(Inputs.jump()&&cayoteTime>0) {
			velY=-8;
			cayoteTime=0;
		}
		updateBounds();
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(Color.black);
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
		g.setColor(Color.yellow);
		//g.fillRect(bounds.x-camera.getxOffset(),bounds.y+bounds.height-camera.getyOffset(),bounds.width,1);
	}
	
	

}
