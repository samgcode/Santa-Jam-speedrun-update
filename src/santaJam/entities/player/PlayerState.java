package santaJam.entities.player;

import santaJam.inputs.Inputs;

public abstract class PlayerState {
	private static PlayerState currentState = new Standing();
	
	
	protected final static double FRICTION=0.5, TOPWALKSPEED=2.75, WALKACCELERATION=0.75, BUFFERLENGTH=10;
	
	protected int width=5, height=13, slideWidth=12,slideHeight=6;
	
	public abstract void start(PlayerState prevState);
	public PlayerState update(Player player) {
		player.changeBounds(width, height);
		return null;
	}
	public abstract void end();
	
	protected void normalMoveLeftRight(Player player) {
		doFriction(player);
		if(Inputs.left().isHeld()&&player.getVelX()>-TOPWALKSPEED) {
			player.addVelX(-WALKACCELERATION);
			player.setDirection(true);
			if(player.getVelX()<-TOPWALKSPEED) {
				player.setVelX(-TOPWALKSPEED);
			}
			
		}if(Inputs.right().isHeld()&&player.getVelX()<TOPWALKSPEED) {
			player.addVelX(WALKACCELERATION);
			player.setDirection(false);
			if(player.getVelX()>TOPWALKSPEED) {
				player.setVelX(TOPWALKSPEED);
			}
		}
		
		
		
	}
	
	protected void normalGravity(Player player) {
		player.addVelY(Player.GRAVITY);
		if(player.getVelY()>Player.MAXGRAVITY) {
			player.setVelY(Player.MAXGRAVITY);
		}
	}
	protected void slideGravity(Player player) {
		player.addVelY(Player.GRAVITY/1.5);
		if(player.getVelY()>Player.MAXGRAVITY/1.5) {
			player.setVelY(Player.MAXGRAVITY/1.5);
		}
		if(player.getVelX()>0) {
			player.setDirection(false);
		}else if(player.getVelX()<0) {
			player.setDirection(true);
		}
	}
	protected void doFriction(Player player) {
		if(player.getVelX()>FRICTION) {
			player.addVelX(-FRICTION);
		}else if(player.getVelX()<-FRICTION){
			player.addVelX(FRICTION);
		}else {
			player.setVelX(0);
		}
	}
	
	protected void refreshAbilities() {
		Grapple.refreshGrapple();
		DoubleJump.refreshDoubleJump();
	}
	
	
	public static void setState(PlayerState newState) {
		currentState.end();
		newState.start(currentState);
		currentState=newState;
		
	}
	
	

}
