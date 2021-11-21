package santaJam.entities.player;

import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.inputs.Inputs;
import santaJam.states.StateManager;

public class Grapple extends PlayerState{
	public static final double GRAPPLESTRENGTH=1.5, MAXSPEED=10, SHOTDURATION=15, PULLDURATION=15;
	private static boolean canGrapple=true;
	
	private boolean firstFrame, facingLeft, shooting;
	private int grappleX, duration;
	
	PlayerState prevState;

	
	public Grapple(PlayerState prevState) {
		this.prevState=prevState;
		grappleX=0;
		duration=0;
	}
	@Override
	public void start(PlayerState prevState) {
		firstFrame=true;
		shooting=true;
	}
	

	@Override
	public PlayerState update(Player player) {
		System.out.println("eee");
		if(!canGrapple||!player.hasGrapple()) {
			return prevState;
		}
		if(firstFrame) {
			init(player);
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
			PlayerState returnVal =  grapplePull(player);
			if(returnVal!=null) {
				return returnVal;
			}
		}
		
		firstFrame=false;
		return null;
	}
	private void init(Player player) {
		//stopping the player and checking direction if it is the first frame
		player.setVelX(0);
		player.setVelY(0);
		facingLeft=player.isFaceLeft();
		grappleX=player.getBounds().x+player.getBounds().width/2;
		
	}
	private PlayerState grappleShoot(Player player) {
		//moving the grapple depending on their direction
		if(facingLeft) {
			grappleX-=MAXSPEED*2;
		}else {
			grappleX+=MAXSPEED*2;
		}
		
		//chekcing for walls
		Rectangle checkBox = new Rectangle(grappleX,player.getBounds().y+5,2,2);
		ArrayList<Rectangle> walls = StateManager.getGameState().getMap().getCurrentRoom().getWalls();
		for(Rectangle r:walls) {
			if(r.intersects(checkBox)) {
				shooting=false;
			}
		}
		//going back to the previous state if it has been too long
		if(duration>SHOTDURATION) {
			canGrapple=false;
			return prevState;
		}
		return null;
	}
	
	private PlayerState grapplePull(Player player) {
		refreshAbilities();//refreshing abilities if the land the grapple
		if(facingLeft) {
			//moving left
			if(player.getVelX()>-MAXSPEED) {
				player.addVelX(-GRAPPLESTRENGTH);
			}
		}else {
			//moving right
			if(player.getVelX()<MAXSPEED) {
				player.addVelX(GRAPPLESTRENGTH);
			}
		}
		//letting you cancel the pull into a double jump, or stop the pull if you don't have it unlocked
		if(Inputs.jump().isPressed()) {
			return new DoubleJump(prevState);
		}
		
		if(duration>SHOTDURATION+PULLDURATION) {
			return new Falling();
		}
		return null;
	}

	@Override
	public void end() {}
	
	public int getCheckX() {
		return grappleX;
	}
	public static void refreshGrapple() {
		canGrapple=true;
	}
}
