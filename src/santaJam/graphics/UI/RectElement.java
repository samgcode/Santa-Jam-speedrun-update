package santaJam.graphics.UI;

import java.awt.Color;
import java.awt.Graphics;

public class RectElement extends UIElement{
	/*
	 * this class is for parts of ui that are a picture it is its own class so it can be added to the 
	 * uiManager and be rendered ontop if other things
	 */
	int width, height, arcSize;
	Color colour;
	//this constructor takes the location and picture
	public RectElement(int x, int y, int width,int height, int arcSize, Color colour) {
		super(x,y);
		this.width=width;
		this.height=height;
		this.arcSize=arcSize;
		this.colour = colour;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(colour);
		g.fillRoundRect(x,y,width,height,arcSize,arcSize);//drawing the picture at its x and y
		
		
	}
}

