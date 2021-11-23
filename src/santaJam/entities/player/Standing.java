package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Standing extends PlayerState{
	
	private int coyoteTime=5;
	
	@Override
	public void start(PlayerState prevState) {
		coyoteTime=0;	
		refreshAbilities();
		
	}
	
	@Override
	public PlayerState update(Player player) {
		super.update(player);
		if(Math.abs(player.getVelX())>TOPWALKSPEED) {
			return new Sliding();
			
		}
		normalMoveLeftRight(player);//moving the player
		normalGravity(player);//doing gravity
		
		
		//if they pressed/buffered a jump, then they should jump
		if(Inputs.jump().getHoldLength()<15&&Inputs.jump().getHoldLength()>0) {
			return new Jumping();
		}
		//if they pressed/buffered an grapple, they should grapple
		if(Inputs.grapple().getHoldLength()<5&&Inputs.grapple().getHoldLength()>0) {
			return new Grapple(this,player);
			
			
		}
		
		//transitioning to the falling state if the are not on the ground, and it is after coyote time
		if(!player.isGrounded()) {
			coyoteTime--;
			if(coyoteTime==0) {
				return new Falling();
			}
		}else {
			//resetting coyote time if they are touching the ground
			coyoteTime=5;
		}
		return null;
		
	}
	@Override
	public void end() {}
}
