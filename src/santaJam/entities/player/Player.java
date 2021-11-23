package santaJam.entities.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.entities.Entity;
import santaJam.entities.wallEntities.BreakableWall;
import santaJam.entities.wallEntities.WallEntity;
import santaJam.states.Camera;
import santaJam.states.StateManager;

public class Player extends Entity {
	private int maxHealth=5;
	private PlayerState currentState = new Standing();
	
	
	public Player(int x, int y, int health) {
		super(x,y,15,20);
		System.out.println("reloading player");
		this.health=health;
		damage=0;
		maxInvincibility=30;
		team=0;
	}

	@Override
	public void update() {
		
		PlayerState nextState = currentState.update(this);
		if(nextState!=null) {
			currentState.end();
			nextState.start(currentState);
			currentState=nextState;
		}
		hitWallEntities();
		updateBounds();
	}
	
	private void hitWallEntities() {		
		ArrayList<WallEntity> walls = new ArrayList<WallEntity>();
		
		for(Entity i:manager.getEntities()) {
			if(i instanceof WallEntity) {
				walls.add((WallEntity) i);
			}
		}
 		Rectangle newBounds=bounds.getBounds();
		Rectangle groundedBounds = new Rectangle(bounds.x,bounds.y+bounds.height,bounds.width,3);
		newBounds.x+=Math.round(velX);
		grounded=false;
		
		//horizontal collisions
		for(WallEntity i:walls) {		
			if(groundedBounds.intersects(i.getBounds())) {
				grounded=true;
			}
			
			if(i.getBounds().intersects(newBounds)) {
				if(isSliding()&& i instanceof BreakableWall) {
					((BreakableWall) i).smash();
				}else {
					if(velX>0) {
						while(i.getBounds().intersects(newBounds)&&newBounds.x>bounds.x) {
							velX--;
							newBounds.x--;
						}
					}else if(velX<0) {
						while(i.getBounds().intersects(newBounds)&&newBounds.x<bounds.x) {
							velX++;
							newBounds.x++;
						}
					}
				}
				
			}								
		}
		newBounds.y+=Math.round(velY);
		for(WallEntity i:walls) {		
			if(groundedBounds.intersects(i.getBounds())) {
				grounded=true;
			}
			
			if(i.getBounds().intersects(newBounds)) {
				if(isSliding()&& i instanceof BreakableWall) {
					((BreakableWall) i).smash();
				}else {
					if(velY>0) {
						while(i.getBounds().intersects(newBounds)&&newBounds.y>bounds.y) {
							velY--;
							newBounds.y--;
						}
					}else if(velY<0) {
						while(i.getBounds().intersects(newBounds)&&newBounds.y<bounds.y) {
							velY++;
							newBounds.y++;
						}
					}
				}
			}								
		}

	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(Color.black);
		if(currentState instanceof Grapple&&StateManager.getGameState().getSave().hasGrapple()) {
			g.setColor(Color.red);
			g.drawLine(((Grapple) currentState).getCheckX()-camera.getxOffset(),bounds.y+5-camera.getyOffset(),
					bounds.x-camera.getxOffset(),bounds.y+5-camera.getyOffset());
		}else if(currentState instanceof UpBoost) {
			g.setColor(Color.ORANGE);
		}else if(currentState instanceof Standing) {
			g.setColor(Color.BLUE);
		}else if(currentState instanceof Sliding||currentState instanceof SlideJump||currentState instanceof SlideFalling||
				currentState instanceof SlideDoubleJump) {
			g.setColor(Color.cyan);
		}
		//g.setColor(Color.black);
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
	

	public void changeBounds(int width, int height) {
		//bounds.width=width;
		//bounds.height=height;
	}
	public boolean isSliding() {
		if(currentState instanceof Sliding||currentState instanceof SlideFalling||currentState instanceof SlideJump||
				currentState instanceof SlideDoubleJump) {
			return true;
		}
		return false;
	}
	public PlayerState getCurrentState() {
		return currentState;
	}
	
	protected void addVelX(double amount) {
		velX+=amount;
	}
	protected void addVelY(double amount) {
		velY+=amount;
	}
	public void setVelX(double amount) {
		velX=amount;
	}
	public void setVelY(double amount) {
		velY = amount;
	}
	protected void setDirection(boolean facingLeft) {
		faceLeft=facingLeft;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
}
