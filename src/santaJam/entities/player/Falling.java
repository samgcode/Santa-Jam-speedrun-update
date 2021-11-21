package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Falling extends PlayerState{

	@Override
	public void start(PlayerState prevState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerState update(Player player) {
		//letting the player move, and be gravitied;
		normalMoveLeftRight(player);
		normalGravity(player);
		
		//going back to standing if the hit the ground
		if(player.isGrounded()) {
			return new Standing();
		}
		//attacking if the buffer an attack
		if(Inputs.attack().getHoldLength()<5&&Inputs.attack().getHoldLength()>0) {
			return new Attacking();
		}
		//grappling if the buffer an grapple
		if(Inputs.grapple().getHoldLength()<5&&Inputs.grapple().getHoldLength()>0) {
			return new Grapple(this);
		}
		//trying to double jump if they push jump
		if(Inputs.jump().isPressed()) {
			return new 	DoubleJump(this);
			
		}
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
