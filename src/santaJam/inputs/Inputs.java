package santaJam.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inputs implements KeyListener{
	private static int left=KeyEvent.VK_LEFT, right=KeyEvent.VK_RIGHT, jump=KeyEvent.VK_UP;
	private static boolean leftPushed=false, rightPushed=false,jumpPushed=false;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==left) {
			leftPushed=true;
		}else if(e.getKeyCode()==right) {
			rightPushed=true;
		}else if(e.getKeyCode()==jump) {
			jumpPushed=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==left) {
			leftPushed=false;
		}else if(e.getKeyCode()==right) {
			rightPushed=false;
		}else if(e.getKeyCode()==jump) {
			jumpPushed=false;
		}
		
	}
	
	public static boolean left() {
		return leftPushed;
	}
	public static boolean right() {
		return rightPushed;
	}
	public static boolean jump() {
		return jumpPushed;
	}
	
	
}
