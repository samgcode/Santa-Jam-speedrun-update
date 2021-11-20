package santaJam.states;

import santaJam.Game;
import santaJam.entities.Entity;

//@author Matthew
public class Camera {
	/*
	 * this is the camera which works by giving everything an offset, which then 
	 * causes everything to be drawn in a different place making it look like the camera is moving
	 */
	private int xOffset, yOffset, entityX, entityY,mouseX,mouseY,locX,locY, screenWidth, screenHeight;
	private float entityWeight=1, locWeight=1;
	private float speed=0.25f;
	
	//constructors
	public Camera() {
		//screen height and width are required to center things on the screen
		xOffset = 0;
		yOffset = 0;
		this.screenWidth = Game.WIDTH/Game.SCALE;
		this.screenHeight = Game.HEIGHT/Game.SCALE;
	}
	//lets you start the camera with in a specific location
	public Camera(int screenWidth, int screenHeight, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void update() {
		int targetX= Math.round((entityX*entityWeight+locX*locWeight)/(entityWeight*locWeight));
		int targetY= Math.round((entityY*entityWeight+locY*locWeight)/(entityWeight*locWeight));
		xOffset+= speed*(targetX-xOffset);
		yOffset+= speed*(targetY-yOffset);
		
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
	public void moveToMouse(int x,int y) {
		mouseX=x- screenWidth / 2;
		mouseY=y- screenHeight / 2;
	}
	public void moveToPoint(int x, int y) {
		locX=x- screenWidth / 2;
		locY=y- screenHeight / 2;
	}
	
	
	//getters so things can know how offset they should be
	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
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
