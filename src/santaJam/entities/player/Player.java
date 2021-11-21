package santaJam.entities.player;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.entities.Entity;
import santaJam.states.Camera;

public class Player extends Entity {
	private boolean doubleJump=false, grapple=false;

	private PlayerState currentState = new Standing();
	
	
	public Player() {
		super(-50,-90,15,20);
		damage=0;
		health=5;
		maxInvincibility=30;
		team=0;
	}

	@Override
	public void update() {
		//System.out.println(x+", "+y);
		//System.out.println(currentState.toString());
		
		PlayerState nextState = currentState.update(this);
		if(nextState!=null) {
			currentState.end();
			nextState.start(currentState);
			currentState=nextState;
		}
		updateBounds();
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		if(currentState instanceof Standing) {
			g.setColor(Color.black);
		}else if(currentState instanceof Jumping||currentState instanceof Falling) {
			g.setColor(Color.orange);
		}else if(currentState instanceof Grapple&&hasGrapple()) {
			g.setColor(Color.red);
			g.fillRect(((Grapple) currentState).getCheckX()-camera.getxOffset(),bounds.y+5-camera.getyOffset(),2,2);
		}else if(currentState instanceof Attacking) {
			g.setColor(Color.cyan);
		}
		g.setColor(Color.black);
		if(!(invincibility>0&&invincibility%10>5)) {
			g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
			if(faceLeft) {
				g.setColor(Color.white);
				g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset()+5, 5, 5);
			}else {
				g.setColor(Color.white);
				g.fillRect(bounds.x+bounds.width-5-camera.getxOffset(), bounds.y-camera.getyOffset()+5, 5, 5);
			}
		}
		
		
		g.setColor(Color.RED);
		for(int i=0;i<health;i++) {
			g.fillRect(bounds.x-camera.getxOffset()-5+i*5, bounds.y-camera.getyOffset()-5, 3, 3);
		}
		
	}
	
	@Override
	public void knockBack(boolean faceLeft, double x, double y) {
		// TODO Auto-generated method stub
		super.knockBack(!this.faceLeft, x, y);
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
	protected void setDirection(boolean facingLeft) {
		faceLeft=facingLeft;
	}
	public boolean hasDoubleJump() {
		return doubleJump;
	}
	public void unlockDoubleJump() {
		doubleJump=true;
	}
	public boolean hasGrapple() {
		return grapple;
	}
	public void unlockGrapple() {
		grapple=true;
	}
}
