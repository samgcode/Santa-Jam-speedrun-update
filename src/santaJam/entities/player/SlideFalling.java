package santaJam.entities.player;

import santaJam.audio.MusicManager;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.states.StateManager;

public class SlideFalling extends PlayerState{
	PlayerState prevState;
	@Override
	public void start(PlayerState prevState) {
		width=slideWidth;
		height=slideHeight;
		this.prevState = prevState;
	}

	@Override
	public PlayerState update(Player player) {
		if(!StateManager.getGameState().getSave().hasSlide()) {
			normalGravity(player);
			normalMoveLeftRight(player);
			return prevState;
		}
		super.update(player);
		//letting the player be gravitied;
		slideGravity(player);
		player.setAnim(Player.slideFall);
	
		
		//doing a normal fall if they are to slow
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {
			
			return new Falling();
		}
		
		//going back to sliding if the hit the ground
		if(player.isGrounded()) {
			MusicManager.playSound(MusicManager.landing);
			return new Sliding();
		}
		
		//trying to double jump if they push jump
		if(Inputs.getKey(Keybind.JUMP).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.JUMP).getHoldLength()>0&&!Inputs.getKey(Keybind.JUMP).isInputUsed()
			&&DoubleJump.canDoubleJump()&&StateManager.getGameState().getSave().hasDoubleJump()) {
			Inputs.getKey(Keybind.JUMP).useInput();
			return new 	SlideDoubleJump(this);
		}
		if(Inputs.getKey(Keybind.UP).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.UP).getHoldLength()>0&&!Inputs.getKey(Keybind.UP).isInputUsed()) {
			Inputs.getKey(Keybind.UP).useInput();
			return new UpBoost(this, player);
		}
		if(Inputs.getKey(Keybind.GRAPPLE).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.GRAPPLE).getHoldLength()>0&&!Inputs.getKey(Keybind.GRAPPLE).isInputUsed()) {
			Inputs.getKey(Keybind.GRAPPLE).useInput();
			return new SlideGrapple(this,player);
		}
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
