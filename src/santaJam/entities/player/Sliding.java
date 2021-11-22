package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Sliding extends PlayerState{
	int coyoteTime=0;
	
	@Override
	public void start(PlayerState prevState) {
		width=20;
		height=15;
	}

	@Override
	public PlayerState update(Player player) {
		super.update(player);
		slideGravity(player);
		
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {
			
			return new Falling();
		}
		if(Inputs.jump().getHoldLength()<15&&Inputs.jump().getHoldLength()>0) {
			return new SlideJump();
		}
		
		if(!player.isGrounded()) {
			coyoteTime--;
			if(coyoteTime==0) {
				return new SlideFalling();
			}
		}else {
			//resetting coyote time if they are touching the ground
			coyoteTime=5;
		}
		
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
