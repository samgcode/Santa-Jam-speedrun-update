package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Sliding extends PlayerState{
	int coyoteTime=0;
	
	@Override
	public void start(PlayerState prevState) {
		width=20;
		height=10;
		refreshAbilities();
	}

	@Override
	public PlayerState update(Player player) {
		super.update(player);
		slideGravity(player);
		
		
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {	
			return new Falling();
		}
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0) {
			return new SlideGrapple(this,player);
		}
		if(Inputs.jump().getHoldLength()<BUFFERLENGTH&&Inputs.jump().getHoldLength()>0) {
			return new SlideJump();
		}
		if(Inputs.up().getHoldLength()<BUFFERLENGTH&&Inputs.up().getHoldLength()>0) {
			return new UpBoost(this,player);	
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
