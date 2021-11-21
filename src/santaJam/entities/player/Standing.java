package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Standing extends PlayerState{
	
	private int coyoteTime=5;
	
	@Override
	public void start(PlayerState prevState) {
		coyoteTime=0;	
	}
	
	@Override
	public PlayerState update(Player player) {
		normalMoveLeftRight(player);
		normalGravity(player);
		
		if(Inputs.jump().getHoldLength()<15&&Inputs.jump().getHoldLength()>0) {
				return new Jumping();
		}
		if(Inputs.attack().getHoldLength()<5&&Inputs.attack().getHoldLength()>0) {
			return new Attacking(this);
		}
		
		if(!player.isGrounded()) {
			coyoteTime--;
			if(coyoteTime==0) {
				return new Falling();
			}
		}else {
			coyoteTime=5;
		}
		return null;
		
	}
	@Override
	public void end() {}
}
