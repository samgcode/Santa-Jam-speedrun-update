package santaJam.inputs;

public class InputButton {
	boolean held, pressed;
	int holdLength=0;
	
	
	
	public void update(boolean isHeld){
		if(isHeld) {//checking if the button is being held down on this frame
			holdLength++;
			if(!held) {//checking if the button has been held on the previous frames so pressed is only true for one frame
				held=true;
				pressed=true;
			}else {
				pressed=false;
			}
		}
		else {//the button is not being held down so pushed is set to false
			held=false;
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
	
	

}
