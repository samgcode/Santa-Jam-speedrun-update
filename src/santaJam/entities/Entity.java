package santaJam.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.states.Camera;
import santaJam.states.StateManager;

public abstract class Entity {
	public static final double GRAVITY=0.65, MAXGRAVITY=6.5;
	
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
 		Rectangle xCollide=bounds.getBounds();
		Rectangle yCollide=bounds.getBounds();
		Rectangle totalCollide=bounds.getBounds();
		Rectangle groundedBounds = new Rectangle(bounds.x,bounds.y+bounds.height,bounds.width,3);
		xCollide.x+=Math.round(velX);
		yCollide.y+=Math.round(velY);
		totalCollide.x+=Math.round(velX);
		totalCollide.y+=Math.round(velY);
		grounded=false;
		boolean hitWall=false;
		
		for(Rectangle i:walls) {
			
		
			if(groundedBounds.intersects(i)) {
				grounded=true;
			}
			if(i.intersects(yCollide)) {
				velY=0;	
				hitWall=true;
			}
			
			if(i.intersects(xCollide)) {
				velX=0;
				hitWall=true;
			}				
				
						
		}
		if(!hitWall) {
			for(Rectangle i:walls) {
				if(i.getBounds().intersects(totalCollide)) {
					velX=0;
					velY=0;
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
