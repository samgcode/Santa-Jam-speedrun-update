package santaJam.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	private int width, height, scale;
	public Display(int width, int height, int scale ) {
		// setting the proper size so that the window will pack properly
		// the display is scaled up to look 8-bit so the
		// resolution is actually 1/3 of the screen width
		this.scale=scale;
		this.width = width ;
		this.height = height;
		
		this.setPreferredSize(new Dimension(width*scale, height*scale));
		
		// setting the preferred size to the inputed one so that the pack method will work
	
	}

	@Override
	public void repaint() {
		
		super.repaint();	
	}
	@Override
	public void paintComponent(Graphics g) {// where everything is actually drawn
		// all rendering code goes here
		//the image the everything is drawn onto
		g.setColor(StateManager.getBgColour());
		g.fillRect(0,0,Game.getWindow().getWindowWidth(),Game.getWindow().getWindowHeight());
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d=(Graphics2D)image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
		StateManager.render(g2d);
		//putting the image onto the display and scaling it
		g.drawImage(image,Game.getWindow().getxOffset(),Game.getWindow().getyOffset(),width*Game.getWindow().getScale(), 
				height*Game.getWindow().getScale(), null);
		g.setColor(Color.white);
		
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
