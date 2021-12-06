package santaJam.entities.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import santaJam.Assets;
import santaJam.entities.Entity;
import santaJam.entities.wallEntities.BreakableWall;
import santaJam.entities.wallEntities.WallEntity;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.maps.Room;
import santaJam.states.StateManager;

public class Player extends Entity {
	private int maxHealth=5;
	private PlayerState currentState = new Standing();
	public static final Animation idle = new Animation(new BufferedImage[] { Assets.walking[0]},3,0);
	public static final Animation walking = new Animation(Assets.walking,3,0);
	public static final Animation jumping = new Animation(Assets.jumping,3,0);
	public static final Animation sliding = new Animation(Assets.sliding,1,0);
	public static final Animation slideFall = new Animation(Assets.slideFall,1,0);
	public static final Animation grapplePull = new Animation(Assets.grapplePull,0,0);
	public static final Animation dance = new Animation(Assets.dance,3,1);

	
	private Animation currentAnim = walking;
	
	
	
	
	public Player(int x, int y, int health) {
		super(x,y,8,13);
		System.out.println("reloading player");
		this.health=health;
		damage=0;
		maxInvincibility=30;
		team=0;
		jumping.setLooping(false);
	}

	@Override
	public void update() {
	
		//System.out.println(velX);
	//	System.out.println(currentState);
		
		PlayerState nextState = currentState.update(this);
		setState(nextState);
		
	
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
		
		if(velX>0) {
			
			while(!StateManager.getGameState().getMap().inMap(newBounds)&&newBounds.x>bounds.x) {
				velX--;
				newBounds.x--;
			}
		}else if(velX<0) {
			while(!StateManager.getGameState().getMap().inMap(newBounds)&&newBounds.x<bounds.x) {
				velX++;
				newBounds.x++;
			}
		}
		
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

		if(velY>0) {
			
			while(!StateManager.getGameState().getMap().inMap(newBounds)&&newBounds.y>bounds.y) {
				velY--;
				newBounds.y--;
			}
		}else if(velY<0) {
			while(!StateManager.getGameState().getMap().inMap(newBounds)&&newBounds.y<bounds.y) {
				velY++;
				newBounds.y++;
			}
		}
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
		currentAnim.update();
		if(currentState instanceof Grapple&&StateManager.getGameState().getSave().hasGrapple()) {
			g.setColor(new Color(66,39,37));
			g.drawLine(((Grapple) currentState).getCheckX()-camera.getxOffset(),bounds.y+7-camera.getyOffset(),
					bounds.x-camera.getxOffset(),bounds.y+7-camera.getyOffset());
		}
		
		//g.setColor(Color.red);
		//g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
		
		BufferedImage currentFrame = currentAnim.getCurrentFrame();
		if(faceLeft) {
			g.drawImage(currentFrame,bounds.x-camera.getxOffset()-currentAnim.getxOffset(), bounds.y-camera.getyOffset()-currentAnim.getyOffset(), null);
		}else {
			g.drawImage(currentFrame,bounds.x-camera.getxOffset()+bounds.width+currentAnim.getxOffset(), bounds.y-camera.getyOffset()+currentAnim.getyOffset(),
					-currentFrame.getWidth(),currentFrame.getHeight(), null);
		}
	}
	

	public boolean changeBounds(int width, int height) {
		int xChange=bounds.width-width, yChange=bounds.height-height;
		Rectangle newBounds = new Rectangle(bounds.x+xChange/2,bounds.y+yChange,width,height);
		for(Room r:StateManager.getGameState().getMap().getLoadedRooms()) {
			if(r!=null) {
				for(Rectangle i:r.getWalls()) {
					if(newBounds.intersects(i)) {
						return false;
						
					}
				}
			}
		}
		for(Entity i:manager.getEntities()) {
			if(i instanceof WallEntity&& i.getBounds().intersects(newBounds)) {
				return false;
			}
		}
		
		bounds = newBounds;
		x=bounds.x;
		y=bounds.y;
		return true;
		
	}
	public void setAnim(Animation animation) {
		if(animation!=currentAnim) {
			animation.setCurrentFrame(0);
		}
		
		this.currentAnim = animation;
	}
	public boolean isSliding() {
		if(currentState instanceof Sliding||currentState instanceof SlideFalling||currentState instanceof SlideJump||
				currentState instanceof SlideDoubleJump||currentState instanceof UpBoost) {
			return true;
		}
		return false;
	}
	
	@Override
	public void kill() {
		StateManager.getGameState().gameOver();
	}
	public PlayerState getCurrentState() {
		return currentState;
	}
	public void setState(PlayerState nextState){
		if(nextState!=null) {
			currentState.end();
			nextState.start(currentState);
			currentState=nextState;
		}
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
