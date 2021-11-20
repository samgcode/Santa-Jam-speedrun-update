package santaJam.window;

import javax.swing.JFrame;

import santaJam.inputs.Inputs;

/*
 * this class represents the window that the game is played on and uses java
 * swing not javaFX
 */
/* 
 * by: Matthew Milum
 */
public class Window {
	/*
	 * this class represents the window that the game is played on and uses java
	 * swing not javaFX
	 */
	private JFrame frame;
	private Display display;

	public Window(int width, int height) {
		// initializing variables
		
		
		frame = new JFrame("Merry Christmas!!");
		
		frame.setResizable(false);// not letting you resize the window so it doesn't mess things up when rendering
		resize(width, height);
		
		frame.setLocationRelativeTo(null);// centers the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// makes the program stop when you close the window
		frame.addKeyListener(new Inputs());
		
		frame.setVisible(true);// making the window visible

	}

	public void render() {
		display.repaint();
	}

	public void resize(int width, int height) {
		
		display = new Display(width, height);//making the display
		
		frame.add(display);// adding the display to the window so it can actually show it
		// pack needs to come after setResizable because it changes the window size, so
		// the window will be the wrong size
		frame.pack();// making the window fit the panel perfectly
	}

	// getters/setters
	public  Display getDisplay() {
		return display;
	}

	public JFrame getFrame() {
		return frame;
	}
}

