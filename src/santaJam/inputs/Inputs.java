package santaJam.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import santaJam.SantaJam;

public class Inputs implements KeyListener{
	private static int[] keyCodes = new int[] {0,0,0,0,0,0,0,0,0,0};

	private static int upIndex=0,downIndex=1,leftIndex=2, rightIndex=3,jumpIndex=4, grappleIndex = 5, savestateIndex=6, resetIndex=7, pauseIndex=8;
	private static boolean leftPushed=false, rightPushed=false,upPushed=false,downPushed=false, jumpPushed=false,
		 grapplePushed=false, pausePushed=false,savestatePushed=false,resetPushed=false;
	private static InputButton left = new InputButton(0), right = new InputButton(0),up = new InputButton(0),down = new InputButton(0), 
			jump = new InputButton(0), grapple = new InputButton(0), pause = new InputButton(0), savestate = new InputButton(0), reset = new InputButton(0);
	
	private static boolean keyPressed=false;
	private static int lastKeyCode=-1;
	private static InputButton anyKey = new InputButton(0);

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
		}else if(SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			if(e.getKeyCode()==keyCodes[savestateIndex]) {
				savestatePushed=true;
			}else if(e.getKeyCode()==keyCodes[pauseIndex]) {
				pausePushed=true;
			}
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
		}else if(SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			if(e.getKeyCode()==keyCodes[savestateIndex]) {
				savestatePushed=false;
			}else if(e.getKeyCode()==keyCodes[pauseIndex]) {
				pausePushed=false;
			}
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
		savestate.update(savestatePushed);
		reset.update(grapplePushed);
	}
	public static void setKeyBinds(int[] newKeyCodes) {
		keyCodes=newKeyCodes;
		left = new InputButton(keyCodes[leftIndex]);
		right = new InputButton(keyCodes[rightIndex]);
		up = new InputButton(keyCodes[upIndex]);
		down = new InputButton(keyCodes[downIndex]);
		jump = new InputButton(keyCodes[jumpIndex]);
		grapple = new InputButton(keyCodes[grappleIndex]);
		savestate = new InputButton(keyCodes[savestateIndex]);
		reset = new InputButton(keyCodes[resetIndex]);
		
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
