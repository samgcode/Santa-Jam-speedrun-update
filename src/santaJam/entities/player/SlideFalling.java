package santaJam.entities.player;

import santaJam.audio.MusicManager;
import santaJam.inputs.Inputs;
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
		if(Inputs.jump().getHoldLength()<BUFFERLENGTH&&Inputs.jump().getHoldLength()>0&&!Inputs.jump().isInputUsed()) {
			Inputs.jump().useInput();
			return new 	SlideDoubleJump(this);
		}
		if(Inputs.up().getHoldLength()<BUFFERLENGTH&&Inputs.up().getHoldLength()>0&&!Inputs.up().isInputUsed()) {
			Inputs.up().useInput();
			return new UpBoost(this, player);
		}
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0&&!Inputs.grapple().isInputUsed()) {
			Inputs.grapple().useInput();
			return new SlideGrapple(this,player);
		}
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
