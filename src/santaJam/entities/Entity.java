package santaJam.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.states.Camera;
import santaJam.states.StateManager;

public abstract class Entity {
	public static final double GRAVITY=0.5, MAXGRAVITY=6.3;
	
	protected static EntityManager manager = new EntityManager();
	
	protected Rectangle bounds;
	protected double x=0, y=0, velX=0, velY=0;
	protected int health=10;
	protected boolean grounded=false;
	
	
	public Entity() {
		this(0,0,0,0);
	}
	
	public Entity(int x, int y, int width, int height) {
		bounds = new Rectangle(x,y,width,height);
		this.x=x;
		this.y=y;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics2D g, Camera camera);
	
	public static EntityManager getManager() {
		return manager;
	}
	
	protected void updateBounds(){
		
		ArrayList<Rectangle> walls = StateManager.getGameState().getMap().getWalls();
		//if we on;y check wall near the entity it will be faster, but thats kinda complicted for now
		//ArrayList<Rectangle> relaventWalls = StateManager.getGameState().getMap().getWalls();
 		Rectangle newBounds=bounds.getBounds();
		Rectangle groundedBounds = new Rectangle(bounds.x,bounds.y+bounds.height,bounds.width,3);
		newBounds.x+=Math.round(velX);
		grounded=false;
		
		//horizontal collisions
		for(Rectangle i:walls) {		
			if(groundedBounds.intersects(i)) {
				grounded=true;
			}
			
			if(i.intersects(newBounds)) {
				if(velX>0) {
					while(i.intersects(newBounds)&&newBounds.x>bounds.x) {
						velX--;
						newBounds.x--;
					}
				}else if(velX<0) {
					while(i.intersects(newBounds)&&newBounds.x<bounds.x) {
						velX++;
						newBounds.x++;
					}
				}
			}								
		}
		newBounds.y+=Math.round(velY);
		for(Rectangle i:walls) {		
			if(groundedBounds.intersects(i)) {
				grounded=true;
			}
			
			if(i.intersects(newBounds)) {
				if(velY>0) {
					while(i.intersects(newBounds)&&newBounds.y>bounds.y) {
						velY--;
						newBounds.y--;
					}
				}else if(velY<0) {
					while(i.intersects(newBounds)&&newBounds.y<bounds.y) {
						velY++;
						newBounds.y++;
					}
				}
			}								
		}
		
		
		
		
		
		x+=Math.round(velX);
		y+=Math.round(velY);
		bounds.x=(int)Math.round(x);
		bounds.y=(int)Math.round(y);
		
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}
	public int getHealth() {
		return health;
	}public boolean isGrounded() {
		return grounded;
	}
}
