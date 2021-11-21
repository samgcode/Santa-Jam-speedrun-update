package santaJam.states.menus;

import java.awt.Graphics;
import java.awt.Rectangle;


public abstract class MenuObject {
	protected Rectangle bounds;
	protected boolean selected=false;
	public MenuObject(int x, int y, int width, int height) {
		this.bounds=new Rectangle(x, y, width, height);
	}
	public MenuObject(Rectangle bounds) {
		this.bounds=bounds;
	}


	
	public abstract void render(Graphics g);
	
	
	public void hover() {}
	
	public void select() {
		selected=true;
		
	}
	
	
	public void deselect() {
		selected=false;
		
	}
	public void leftPress() {}
	public void rightPress() {}
	
	public Rectangle getBounds() {
		return bounds;
	}

}
