package santaJam.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import santaJam.maps.Map;
import santaJam.states.StateManager;

public abstract class Entity {
	public static final double GRAVITY=0.9;
	
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
	
	public abstract void render(Graphics2D g);
	
	public static EntityManager getManager() {
		return manager;
	}
	
	protected void updateBounds(){
		
		ArrayList<Rectangle> walls = StateManager.getGameState().getMap().getWalls();
 		Rectangle xCollide=bounds.getBounds();
		Rectangle yCollide=bounds.getBounds();
		Rectangle totalCollide=bounds.getBounds();
		Rectangle groundedBounds = new Rectangle(bounds.x,bounds.y+bounds.height,bounds.width,2);
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
		x+=velX;
		y+=velY;
		bounds.x=(int)Math.round(x);
		bounds.y=(int)Math.round(y);
		
	}
		
		/*ArrayList<Rectangle> walls = StateManager.getGameState().getMap().getWalls();
		
		Rectangle xCollide=bounds;
		Rectangle yCollide=bounds;
		Rectangle totalCollide=bounds;
		Rectangle groundedBounds = new Rectangle(bounds.x,bounds.y+bounds.height,bounds.width,2);
		xCollide.x+=velX;
		yCollide.y+=velY;
		totalCollide.x+=velX;
		totalCollide.y+=velY;
		boolean hitwall=false;
		
		grounded=false;
		
		for(Rectangle i:walls) {
			
			if(xCollide.intersects(i)) {
				velX=0;
				hitwall=true;
			}
			if(yCollide.intersects(i)) {
				
				velY=0;
				hitwall=true;
				
			}
			if(groundedBounds.intersects(i)) {
				grounded=true;
			}
		}
		/*if(!hitwall) {
			for(Rectangle i:walls) {
				if(totalCollide.intersects(i)) {
					velX=0;
					velY=0;
				}
			}
		}*/
		
		
		
		
		
		
		
	
	
	
	

}
