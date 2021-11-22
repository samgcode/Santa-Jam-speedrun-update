package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class SlideFalling extends PlayerState{

	@Override
	public void start(PlayerState prevState) {
		// TODO Auto-generated method stub
		
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
		if(Inputs.jump().isPressed()) {
			System.out.println("slideJump?");
			return new 	SlideDoubleJump(this);
			
		}
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
