package santaJam.entities;

import java.awt.Graphics2D;

public abstract class Entity {
	protected static EntityManager manager = new EntityManager();
	public static final double GRAVITY=0.9;
	double x=0, y=0, velX=0, velY=0; 
	int health=10;
	
	public abstract void update();
	
	public abstract void render(Graphics2D g);
	
	public static EntityManager getManager() {
		return manager;
	}
	
	
	

}
