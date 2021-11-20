package santaJam.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inputs implements KeyListener{
	private static int leftKey=KeyEvent.VK_LEFT, rightKey=KeyEvent.VK_RIGHT, jumpKey=KeyEvent.VK_UP;
	private static boolean leftPushed=false, rightPushed=false,jumpPushed=false;
	private static InputButton left = new InputButton(), right = new InputButton(), jump = new InputButton();

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==leftKey) {
			leftPushed=true;
		}else if(e.getKeyCode()==rightKey) {
			rightPushed=true;
		}else if(e.getKeyCode()==jumpKey) {
			jumpPushed=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==leftKey) {
			leftPushed=false;
		}else if(e.getKeyCode()==rightKey) {
			rightPushed=false;
		}else if(e.getKeyCode()==jumpKey) {
			jumpPushed=false;
		}
		
	}
	public static void update() {
		left.update(leftPushed);
		right.update(rightPushed);
		jump.update(jumpPushed);
	}
	
	public static InputButton left() {
		return left;
	}
	public static InputButton right() {
		return right;
	}
	public static InputButton jump() {
		return jump;
	}
	
	
}
