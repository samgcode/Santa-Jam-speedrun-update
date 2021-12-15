package santaJam.graphics;

import java.awt.Rectangle;

import santaJam.Game;
import santaJam.entities.Entity;
import santaJam.maps.Room;

//@author Matthew
public class Camera {
	/*
	 * this is the camera which works by giving everything an offset, which then 
	 * causes everything to be drawn in a different place making it look like the camera is moving
	 */
	private double xOffset, yOffset;
	private int entityX, entityY,locX,locY, screenWidth, screenHeight;
	private float entityWeight=1, locWeight=1;
	private float speed=0.4f;
	
	private Rectangle bounds;
	//constructors
	public Camera() {
		//screen height and width are required to center things on the screen
		xOffset = 0;
		yOffset = 0;
		this.screenWidth = Game.WIDTH;
		this.screenHeight = Game.HEIGHT;	
		bounds = new Rectangle(screenWidth, screenHeight);
	}
	//lets you start the camera with in a specific location
	/*public Camera(int screenWidth, int screenHeight, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}*/
	
	public void update(Room currentRoom) {
		double targetX= Math.round((entityX*entityWeight+locX*locWeight)/(entityWeight*locWeight));
		double targetY= Math.round((entityY*entityWeight+locY*locWeight)/(entityWeight*locWeight));
		if(currentRoom!=null) {
			if(targetX+screenWidth>currentRoom.getX()+currentRoom.getWidth()) {
				targetX=currentRoom.getX()+currentRoom.getWidth()-screenWidth;
			}
			if(targetX<currentRoom.getX()) {
				targetX=currentRoom.getX();
			}
			
			
			if(targetY+screenHeight>currentRoom.getY()+currentRoom.getHeight()) {
				targetY=currentRoom.getY()+currentRoom.getHeight()-screenHeight;
			}
			if(targetY<currentRoom.getY()) {
				targetY=currentRoom.getY();		
			}
			
		}
		xOffset+=speed*(targetX-xOffset);
		yOffset+=speed*(targetY-yOffset);
		bounds.x=(int) Math.round(xOffset);
		bounds.y=(int) Math.round(yOffset);
		
		
		
	}

	public void snapToLoc(int x, int y) {
		//lets you manually move the camera however much you want
		xOffset=x- screenWidth / 2; 
		yOffset=y- screenHeight / 2;
		
	}
	public void moveToEntity(Entity e) {
		//positioning the camera so that the inputed entity is in the middle
		entityX = e.getBounds().x - screenWidth / 2 + e.getBounds().width / 2;
		entityY = e.getBounds().y - screenHeight / 2 + e.getBounds().height / 2;
		
		
	}
	public void moveToPoint(int x, int y) {
		locX=x- screenWidth / 2;
		locY=y- screenHeight / 2;
	}
	
	
	//getters so things can know how offset they should be
	public int getxOffset() {
		return (int) Math.round(xOffset);
	}

	public int getyOffset() {
		return (int) Math.round(yOffset);
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setEntityWeight(int entityWeight) {
		this.entityWeight = entityWeight;
	}
	public void setLocationWeight(int locationWeight) {
		this.locWeight = locationWeight;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
