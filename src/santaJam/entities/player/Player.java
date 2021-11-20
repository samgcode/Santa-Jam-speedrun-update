package santaJam.entities.player;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.entities.Entity;
import santaJam.inputs.Inputs;
import santaJam.states.Camera;

public class Player extends Entity {
	
	private double topSpeed=2.5, friction=0.5;
	private int coyoteTime=0, jumpTime=0;
	
	private PlayerState currentState = PlayerState.standing;
	
	
	public Player() {
		super(100,100,11,17);
	}

	@Override
	public void update() {
		
		PlayerState nextState = currentState.update(this);
		if(nextState!=null) {
			currentState.end();
			nextState.start(currentState);
			currentState=nextState;
		}
		
		System.out.println(currentState.toString());
		
		updateBounds();
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(currentState instanceof Standing) {
			g.setColor(Color.black);
		}else if(currentState instanceof Jumping) {
			g.setColor(Color.red);
		}else if(currentState instanceof Falling) {
			g.setColor(Color.orange);
		}
		
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
		
		//g.fillRect(bounds.x-camera.getxOffset(),bounds.y+bounds.height-camera.getyOffset(),bounds.width,1);
	}
	
	protected void addVelX(double amount) {
		velX+=amount;
	}
	protected void addVelY(double amount) {
		velY+=amount;
	}
	protected void setVelX(double amount) {
		velX=amount;
	}
	protected void setVelY(double amount) {
		velY = amount;
	}
	
	
	

}
