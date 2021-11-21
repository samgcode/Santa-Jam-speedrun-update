package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Jumping extends PlayerState{
	public static final double JUMPSTRENGTH=9, STOPSTRENGTH=0.75;
	boolean firstFrame;

	@Override
	public void start(PlayerState prevState) {
		
		firstFrame=true;
		
	}

	@Override
	public PlayerState update(Player player) {
		normalMoveLeftRight(player);
		normalGravity(player);
		if(firstFrame) {
			player.setVelY(-JUMPSTRENGTH);
		}
		firstFrame=false;
		
		if(Inputs.attack().getHoldLength()<5&&Inputs.attack().getHoldLength()>0) {
			return new Attacking(new Falling());
		}
		if(player.getVelY()<0) {
			if(!Inputs.jump().isHeld()) {
				player.addVelY(STOPSTRENGTH);
			}	
		}else {
			return new Falling();
		}
		
		
		
		
		
		
		return null;
		
		
	}

	@Override
	public void end() {}
}
