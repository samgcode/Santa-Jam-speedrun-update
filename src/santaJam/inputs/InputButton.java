package santaJam.inputs;

import java.awt.event.KeyEvent;

public class InputButton {
	boolean held=false, pressed=false, released=false, inputUsed=false;
	int holdLength=0, keyCode;
	
	public InputButton(int keyCode) {
		this.keyCode = keyCode;
	}
	
	
	public void update(boolean isHeld){
		if(isHeld) {
			if(!held) {
				pressed = true;
				inputUsed=false; //idk whats up with this but the code that uses this looks like the most insane thing of all time so I can't remove it
			} else {
				pressed = false;
			}
			released = false;
			holdLength++;
		} else {
			if(held) {
				released = true;
			} else {
				released = false;
			}
			pressed = false;
			holdLength=0;
		}
		held = isHeld;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	public boolean isHeld() {
		return held;
	}
	public boolean isReleased() {
		return released;
	}
	public int getHoldLength() {
		return holdLength;
	}
	public String getKey() {
		return KeyEvent.getKeyText(keyCode);
	}
	
	public void useInput() {
		inputUsed=true;
	}
	public boolean isInputUsed() {
		return inputUsed;
	}
	
	

}
