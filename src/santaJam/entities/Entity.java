package santaJam.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.graphics.Camera;
import santaJam.maps.Room;
import santaJam.states.StateManager;

public abstract class Entity {
	public static final double GRAVITY=0.3, MAXGRAVITY=5;
	
	protected static EntityManager manager = new EntityManager();
	
	protected Rectangle bounds;
	protected double friction = 0.5;
	protected double x=0, y=0, velX=0, velY=0;
	protected int health=10, maxInvincibility=10, invincibility=0,damage=0, team=0;//what "team" the entity is on (player, enemy, something else) 
	protected boolean grounded=false, killed=false;
	protected boolean faceLeft=false;
	protected boolean grappleable=false;
	
	
	public Entity() {
		this(0,0,0,0);
	}
	
	public Entity(int x, int y, int width, int height) {
		bounds = new Rectangle(x,y,width,height);
		this.x=x;
		this.y=y;
	}
	
	public Entity(Entity clone, int x, int y, int width, int height) {
		bounds = new Rectangle(x,y,width,height);
		this.bounds = clone.bounds;
		this.x = x;
		this.y = y;
		this.friction = clone.friction;
		this.velX = clone.velX;
		this.velY = clone.velY;
		this.health = clone.health;
		this.maxInvincibility = clone.maxInvincibility;
		this.invincibility = clone.invincibility;
		this.damage = clone.damage;
		this.team = clone.team;
		this.grounded = clone.grounded;
		this.killed = clone.killed;
		this.faceLeft = clone.faceLeft;
		this.grappleable = clone.grappleable;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g, Camera camera);
	
	protected ArrayList<Entity> entityCollide() {
		// this is used to see what is colliding with what
		ArrayList<Entity> entities = new ArrayList<Entity>();// this holds all the entities that are touching
		
		for (int i=manager.getEntities().size()-1;i>=0;i--) {// looping through all the entities
			if (manager.getEntities().get(i) != this &&manager.getEntities().get(i).getBounds().intersects(this.bounds)) {// checking if it is touching this
				entities.add(manager.getEntities().get(i));// if something is touching then it adds it to the arrayList
			}
			
		}
		return entities;// returning everything that is touching this entity
	}
	
	
	protected void updateBounds(){
		ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
		for(Room i:StateManager.getGameState().getMap().getRooms((int)x+bounds.width/2,(int)y+bounds.height/2)) {
			if(i!=null) {
				for(Rectangle r:i.getWalls()) {
					walls.add(r);
				}
			}
		}
				
				
		//if we on;y check wall near the entity it will be faster, but thats kinda complicted for now
		//ArrayList<Rectangle> relaventWalls = StateManager.getGameState().getMap().getWalls();
 		Rectangle newBounds=bounds.getBounds();
		Rectangle groundedBounds = new Rectangle(bounds.x,bounds.y+bounds.height,bounds.width,2);
		newBounds.x+=Math.round(velX);
		boolean xCollide=false, yCollide=false;
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
						xCollide=true;
					}
				}else if(velX<0) {
					while(i.intersects(newBounds)&&newBounds.x<bounds.x) {
						velX++;
						newBounds.x++;
						xCollide=true;
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
						yCollide=true;
					}
				}else if(velY<0) {
					while(i.intersects(newBounds)&&newBounds.y<bounds.y) {
						velY++;
						newBounds.y++;
						yCollide=true;
					}
				}
			}								
		}
		x+=Math.round(velX);
		y+=Math.round(velY);
		bounds.x=(int)Math.round(x);
		bounds.y=(int)Math.round(y);	
		if(xCollide){
			velX=0;
		}
		if(yCollide) {
			velY=0;
		}
	}
	public void knockBack(double x, double y) {
		velY+=y;
		velX+=x;
		
	}
	protected void doNormalGravity() {
		velY+=GRAVITY;
		if(velY>MAXGRAVITY) {
			velY=MAXGRAVITY;
		}
	}
	protected void doFriction() {
		if(velX>friction) {
			velX-=friction;
		}else if(velX<-friction){
			velX+=friction;
		}else {
			velX=0;
		}
		
	}
	
	
	public static EntityManager getManager() {
		return manager;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public int getTeam() {
		return team;
	}
	public int getDamage() {
		return damage;
	}
	public int getHealth() {
		return health;
	}
	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}
	public boolean isGrounded() {
		return grounded;
	}
	public void kill() {
		killed = true;
	}
	public boolean isKilled() {
		return killed;
	}
	public boolean isFaceLeft() {
		return faceLeft;
	}
	public boolean isGrappleable() {
		return grappleable;
	}
}
