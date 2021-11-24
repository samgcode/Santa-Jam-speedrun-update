package santaJam.entities.player;

import santaJam.states.StateManager;

public class SlideGrapple extends Grapple{

	public SlideGrapple(PlayerState prevState, Player player) {
		super(prevState, player);
		SHOTSPEED=50;
		SHOTDURATION=7;
		CHECKSPERFRAME=10;
		width=20;
		height=15;
		
	}
	public void start(PlayerState prevState) {
		super.start(prevState);
	}
	public PlayerState update(Player player) {
		if(!StateManager.getGameState().getSave().hasSlide()) {
			normalGravity(player);
			normalMoveLeftRight(player);
			return prevState;
		}
		player.changeBounds(width, height);
		if(!shooting) {
			slideGravity(player);
		}
		
		if(!canGrapple||!StateManager.getGameState().getSave().hasGrapple()) {
			return prevState;
		}		
		
		duration++;
		
		//doing things for the grapple shoot
		if(shooting) {			
			PlayerState returnVal =  grappleShoot(player);
			if(returnVal!=null) {
				return returnVal;
			}
			
		//bringing the player towards the wall if they hit something
		}else {
			player.setVelY(0);
			PlayerState returnVal =  grapplePull(player);
			if(returnVal!=null) {
				return returnVal;
			}
		}
		
		firstFrame=false;
		return null;
		
	}
	
	

}
