package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class DoubleJump extends PlayerState{
	public static final double JUMPSTRENGTH=9, STOPSTRENGTH=0.75;
	private boolean firstFrame;
	private PlayerState prevState;

	
	public DoubleJump(PlayerState prevState) {
		this.prevState=prevState;
	}
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
			return new Attacking(prevState);
		}
		if(player.getVelY()<0) {
			if(!Inputs.jump().isHeld()) {
				player.addVelY(STOPSTRENGTH);
			}	
		}else {
			return prevState;
		}
		
		
		
		
		
		
		return null;
		
		
	}

	@Override
	public void end() {}
}
