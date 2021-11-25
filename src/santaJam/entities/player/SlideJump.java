package santaJam.entities.player;

import santaJam.inputs.Inputs;
import santaJam.states.StateManager;

public class SlideJump extends PlayerState{
	public static final double JUMPSTRENGTH=7, STOPSTRENGTH=0.75;
	boolean firstFrame=true;

	
	@Override
	public void start(PlayerState prevState) {
		width=slideWidth;
		height=slideHeight;
	}

	@Override
	public PlayerState update(Player player) {		
		if(!StateManager.getGameState().getSave().hasSlide()) {
			normalGravity(player);
			normalMoveLeftRight(player);
			return new Falling();
		}
		
		
		super.update(player);
		slideGravity(player);
		player.setAnim(player.jumping);
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {
			return new Falling();
			
		}
		if(firstFrame) {
			player.setVelY(-JUMPSTRENGTH);
			firstFrame=false;
		}
		
		
		if(Inputs.up().getHoldLength()<BUFFERLENGTH&&Inputs.up().getHoldLength()>0) {
			return new UpBoost(this,player);
		}
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0) {
			return new SlideGrapple(this,player);
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
