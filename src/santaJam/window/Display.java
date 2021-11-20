package santaJam.window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import santaJam.Game;
import santaJam.states.StateManager;

public class Display extends JPanel {
	
	private static final long serialVersionUID = 7385492982541933056L;
	/*
	 * this is the display where everything is actually drawn onto the display is then put onto
	 * the window so we can see it
	 * extending JPanel so that you have access to the PaintComponent to actually draw things
	 * everything is drawn onto a display which is then added onto the window,
	 * because you aren't supposed to draw directly onto it
	 */
	private int width, height, scale=Game.SCALE;
	public Display(int width, int height ) {
		// setting the proper size so that the window will pack properly
		// the display is scaled up to look 8-bit so the
		// resolution is actually 1/3 of the screen width
		//scale=3;
		this.width = width / scale;
		this.height = height / scale;
		
		this.setPreferredSize(new Dimension(width, height));
		
		// setting the preferred size to the inputed one so that the pack method will work
	
	}

	@Override
	public void paintComponent(Graphics g) {// where everything is actually drawn
		// all rendering code goes here
		//the image the everything is drawn onto
		g.clearRect(0, 0, width*scale, height*scale);
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d=(Graphics2D)image.getGraphics();
	
		StateManager.render(g2d);
		//putting the image onto the display and scaling it
		g.drawImage(image,0,0,width*scale, height*scale, null);
		
	}
	
	public int getRelativeWidth() {
		return width;
	}

	public int getRelativeHeight() {
		return height;
	}
	//getters
	public int getScale() {
		return scale;
	}
}
