package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Standing extends PlayerState{
	
	private int coyoteTime=5;
	private boolean holdingJump=false;
	

	@Override
	public void start(PlayerState prevState) {
		coyoteTime=0;
		if(Inputs.jumpHeld()) {
			holdingJump=true;
		}
		
	}
	@Override
	public PlayerState update(Player player) {
		normalMoveLeftRight(player);
		normalGravity(player);
		
		
		if(Inputs.jumpPushed()) {
			if(!holdingJump) {
				return jumping;
			}
		}else {
			holdingJump=false;
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
