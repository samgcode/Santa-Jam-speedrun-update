package santaJam.inputs;

import java.awt.event.KeyEvent;

public class InputButton {
	boolean held=false, pressed=false, inputUsed=false;
	int holdLength=0, keyCode;
	
	public InputButton(int keyCode) {
		this.keyCode = keyCode;
	}
	
	
	public void update(boolean isHeld){
		if(isHeld) {//checking if the button is being held down on this frame
			holdLength++;
			if(!held) {//checking if the button has been held on the previous frames so pressed is only true for one frame
				held=true;
				pressed=true;
				inputUsed=false;
			}else {
				pressed=false;
			}
		}
		else {//the button is not being held down so pushed is set to false
			held=false;
			pressed=false;
			holdLength=0;
		}
	}
	
	public boolean isPressed() {
		return pressed;
	}
	public boolean isHeld() {
		return held;
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
