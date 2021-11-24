package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Jumping extends PlayerState{
	public static final double JUMPSTRENGTH=7, STOPSTRENGTH=0.75;
	boolean firstFrame=true;

	public Jumping() {
	}
	@Override
	public void start(PlayerState prevState) {}

	@Override
	public PlayerState update(Player player) {
		super.update(player);
		if(Math.abs(player.getVelX())>TOPWALKSPEED) {
			return new SlideJump();
			
		}
		normalMoveLeftRight(player);
		normalGravity(player);
		
		
		if(firstFrame) {
			player.setVelY(-JUMPSTRENGTH);
			firstFrame=false;
		}
		
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0) {
			player.setVelY(0);
			return new Grapple(this,player);
			
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
