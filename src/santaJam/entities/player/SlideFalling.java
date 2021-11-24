package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class SlideFalling extends PlayerState{
	@Override
	public void start(PlayerState prevState) {
		width=20;
		height=15;
	}

	@Override
	public PlayerState update(Player player) {
		super.update(player);
		//letting the player be gravitied;
		slideGravity(player);
	
		
		//doing a normal fall if they are to slow
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {
			
			return new Falling();
		}
		
		//going back to sliding if the hit the ground
		if(player.isGrounded()) {
			return new Sliding();
		}
		
		//trying to double jump if they push jump
		if(Inputs.jump().getHoldLength()<BUFFERLENGTH&&Inputs.jump().getHoldLength()>0) {
			return new 	SlideDoubleJump(this);
		}
		if(Inputs.up().getHoldLength()<BUFFERLENGTH&&Inputs.up().getHoldLength()>0) {
			return new UpBoost(this, player);
		}
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0) {
			return new SlideGrapple(this,player);
		}
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
