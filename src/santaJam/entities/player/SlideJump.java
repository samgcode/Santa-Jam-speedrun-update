package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class SlideJump extends PlayerState{
	public static final double JUMPSTRENGTH=9, STOPSTRENGTH=0.75;
	boolean firstFrame;

	public SlideJump() {
		firstFrame=true;
	}
	@Override
	public void start(PlayerState prevState) {}

	@Override
	public PlayerState update(Player player) {
		super.update(player);
		slideGravity(player);
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {
			return new Falling();
			
		}
		if(firstFrame) {
			player.setVelY(-JUMPSTRENGTH);
		}
		firstFrame=false;
		
		
		if(Inputs.up().isPressed()) {
			return new UpBoost(player);
		}
		
		if(player.getVelY()<=0) {
			if(!Inputs.jump().isHeld()) {
				player.addVelY(STOPSTRENGTH);
			}	
		}else {
			return new SlideFalling();
		}
		
		return null;
		
		
	}

	@Override
	public void end() {}
}
