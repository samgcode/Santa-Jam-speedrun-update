package santaJam.entities.player;

import santaJam.audio.MusicManager;
import santaJam.states.StateManager;

public class SlideGrapple extends Grapple{

	public SlideGrapple(PlayerState prevState, Player player) {
		super(prevState, player);
		SHOTSPEED=50;
		SHOTDURATION=7;
		CHECKSPERFRAME=10;
		width=slideWidth;
		height=slideHeight;
		
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
		if(!canGrapple||!StateManager.getGameState().getSave().hasGrapple()) {
			return prevState;
		}
		drawGrapple=true;
		if(firstFrame) {
			MusicManager.playSound(MusicManager.grappleThrow);
		}
		player.changeBounds(width, height);
		grappleY=player.getBounds().y+3;
		if(shooting) {
			slideGravity(player);
		}
		
			
		
		duration++;
		
		//doing things for the grapple shoot
		if(shooting) {			
			PlayerState returnVal =  grappleShoot(player);
			player.setAnim(Player.sliding);
			if(returnVal!=null) {
				if(returnVal instanceof Falling) {
					return new SlideFalling();
				}
				return returnVal;
			}
			
		//bringing the player towards the wall if they hit something
		}else {
			player.setVelY(0);
			PlayerState returnVal =  grapplePull(player);
			player.setAnim(Player.sliding);
			if(returnVal!=null) {
				if(returnVal instanceof Falling) {
					return new SlideFalling();
				}
				return returnVal;
			}
		}
		
		firstFrame=false;
		return null;
		
	}
	
	

}
