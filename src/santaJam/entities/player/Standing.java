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
				return jumping;
		}
		
		if(!player.isGrounded()) {
			coyoteTime--;
			if(coyoteTime==0) {
				return falling;
			}
		}else {
			coyoteTime=5;
		}
		return null;
		
	}
	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
