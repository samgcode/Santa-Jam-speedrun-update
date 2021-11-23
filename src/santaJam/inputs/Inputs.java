package santaJam.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import santaJam.SantaJam;

public class Inputs implements KeyListener{
	private static int[] keyCodes = new int[] {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,
			KeyEvent.VK_C,KeyEvent.VK_Z, KeyEvent.VK_ESCAPE};
	
	private static int upIndex=0,downIndex=1,leftIndex=2, rightIndex=3,jumpIndex=4, grappleIndex = 6, pauseIndex=7;
	private static boolean leftPushed=false, rightPushed=false,upPushed=false,downPushed=false, jumpPushed=false,
		 grapplePushed=false, pausePushed=false;
	private static InputButton left = new InputButton(), right = new InputButton(),up = new InputButton(),down = new InputButton(), 
			jump = new InputButton(), grapple = new InputButton(), pause = new InputButton();
	
	private static boolean keyPressed=false;
	private static int lastKeyCode=-1;
	private static InputButton anyKey = new InputButton();

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {	
		keyPressed=true;
		lastKeyCode=e.getKeyCode();
		if(e.getKeyCode()==keyCodes[leftIndex]) {
			leftPushed=true;
		}else if(e.getKeyCode()==keyCodes[rightIndex]) {
			rightPushed=true;
		}else if(e.getKeyCode()==keyCodes[upIndex]) {
			upPushed=true;		
		}else if(e.getKeyCode()==keyCodes[downIndex]) {
			downPushed=true;
		}else if(e.getKeyCode()==keyCodes[jumpIndex]) {
			jumpPushed=true;
		}else if(e.getKeyCode()==keyCodes[grappleIndex]) {
			grapplePushed=true;
		}else if(e.getKeyCode()==keyCodes[pauseIndex]) {
			pausePushed=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed=false;
		if(e.getKeyCode()==keyCodes[leftIndex]) {
			leftPushed=false;
		}else if(e.getKeyCode()==keyCodes[rightIndex]) {
			rightPushed=false;
		}else if(e.getKeyCode()==keyCodes[upIndex]) {
			upPushed=false;
		}else if(e.getKeyCode()==keyCodes[downIndex]) {
			downPushed=false;
		}else if(e.getKeyCode()==keyCodes[jumpIndex]) {
			jumpPushed=false;
		}else if(e.getKeyCode()==keyCodes[grappleIndex]) {
			grapplePushed=false;
		}else if(e.getKeyCode()==keyCodes[pauseIndex]) {
			pausePushed=false;
		}
		
	}
	public static void update() {
		anyKey.update(keyPressed);
		left.update(leftPushed);
		right.update(rightPushed);
		up.update(upPushed);
		down.update(downPushed);
		jump.update(jumpPushed);
		grapple.update(grapplePushed);
		pause.update(pausePushed);
	}
	public static void setKeyBinds(int[] newKeyCodes) {
		keyCodes=newKeyCodes;
		if(SantaJam.getGame()!=null) {
			SantaJam.getGame().getSettings().setKeyBinds(newKeyCodes);
		}
	}
	public static InputButton AnyKey() {
		return anyKey;
	}
	public static int getLastKeyCode() {
		return lastKeyCode;
	}
	public static InputButton left() {
		return left;
	}
	public static InputButton right() {
		return right;
	}
	public static InputButton up() {
		return up;
	}
	public static InputButton down() {
		return down;
	}
	public static InputButton jump() {
		return jump;
	}
	public static InputButton grapple() {
		return grapple;
	}
	public static InputButton pause() {
		return pause;
	}
}
