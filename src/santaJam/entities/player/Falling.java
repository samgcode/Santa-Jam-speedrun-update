package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Falling extends PlayerState{
	boolean hasDoubleJump=true;

	@Override
	public void start(PlayerState prevState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerState update(Player player) {
		normalMoveLeftRight(player);
		normalGravity(player);
		
		if(player.isGrounded()) {
			return new Standing();
		}if(Inputs.jump().isPressed()&&player.hasDoubleJump()&&hasDoubleJump) {
			hasDoubleJump=false;
			return new 	DoubleJump(this);
			
		}if(Inputs.attack().getHoldLength()<5&&Inputs.attack().getHoldLength()>0) {
			return new Attacking(this);
		}
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
