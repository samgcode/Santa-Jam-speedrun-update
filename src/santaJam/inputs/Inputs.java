package santaJam.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inputs implements KeyListener{
	private static int leftKey=KeyEvent.VK_LEFT, rightKey=KeyEvent.VK_RIGHT,upKey=KeyEvent.VK_UP,downKey=KeyEvent.VK_DOWN,
			jumpKey=KeyEvent.VK_C, attackKey=KeyEvent.VK_X,grappleKey = KeyEvent.VK_Z;
	private static boolean leftPushed=false, rightPushed=false,upPushed=false,downPushed=false, jumpPushed=false,
			attackPushed=false, grapplePushed=false;
	private static InputButton left = new InputButton(), right = new InputButton(),up = new InputButton(),
			down = new InputButton(), jump = new InputButton(),attack  = new InputButton(), grapple = new InputButton();

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("push");
		if(e.getKeyCode()==leftKey) {
			leftPushed=true;
		}else if(e.getKeyCode()==rightKey) {
			rightPushed=true;
		}else if(e.getKeyCode()==upKey) {
			upPushed=true;
		}else if(e.getKeyCode()==downKey) {
			downPushed=true;
		}else if(e.getKeyCode()==jumpKey) {
			jumpPushed=true;
		}else if(e.getKeyCode()==attackKey) {
			attackPushed=true;
		}else if(e.getKeyCode()==grappleKey) {
			grapplePushed=true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("release");
		if(e.getKeyCode()==leftKey) {
			leftPushed=false;
		}else if(e.getKeyCode()==rightKey) {
			rightPushed=false;
		}else if(e.getKeyCode()==upKey) {
			upPushed=false;
		}else if(e.getKeyCode()==downKey) {
			downPushed=false;
		}else if(e.getKeyCode()==jumpKey) {
			jumpPushed=false;
		}else if(e.getKeyCode()==attackKey) {
			attackPushed=false;
		}else if(e.getKeyCode()==grappleKey) {
			grapplePushed=false;
		}
		
	}
	public static void update() {
		//System.out.println("update");
		left.update(leftPushed);
		right.update(rightPushed);
		up.update(upPushed);
		down.update(downPushed);
		jump.update(jumpPushed);
		attack.update(attackPushed);
		grapple.update(grapplePushed);
	}
	
	public static InputButton left() {
		return left;
	}
	public static InputButton right() {
		return right;
	}
	public static InputButton Up() {
		return up;
	}
	public static InputButton Down() {
		return down;
	}
	public static InputButton jump() {
		return jump;
	}
	public static InputButton attack() {
		return attack;
	}
	public static InputButton grapple() {
		return grapple;
	}
}
