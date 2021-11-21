package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Jumping extends PlayerState{
	public static final double JUMPSTRENGTH=9, STOPSTRENGTH=0.75;
	boolean firstFrame;

	public Jumping() {
		firstFrame=true;
	}
	@Override
	public void start(PlayerState prevState) {}

	@Override
	public PlayerState update(Player player) {
		normalMoveLeftRight(player);
		normalGravity(player);
		if(firstFrame) {
			player.setVelY(-JUMPSTRENGTH);
		}
		firstFrame=false;
		
		if(Inputs.attack().getHoldLength()<5&&Inputs.attack().getHoldLength()>0) {
			return new Attacking();
		}if(Inputs.grapple().getHoldLength()<5&&Inputs.grapple().getHoldLength()>0) {
			player.setVelY(0);
			return new Grapple(this);
			
		}
		if(player.getVelY()<=0) {
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
