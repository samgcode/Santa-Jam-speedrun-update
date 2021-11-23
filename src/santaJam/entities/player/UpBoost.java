package santaJam.entities.player;

public class UpBoost extends PlayerState{

	private boolean firstFrame=true;
	private int chargeTime=30;
	private double boostVel;
	
	public UpBoost(Player player) {
		boostVel=Math.abs(player.getVelX());
	}
	@Override
	public void start(PlayerState prevState) {
		
	}
	@Override
	public PlayerState update(Player player) {
		
		chargeTime--;
		if(firstFrame) {
			//stopping the player and checking direction if it is the first frame
			player.setVelX(0);
			player.setVelY(0);
			firstFrame=false;
		}
		if(chargeTime==0) {
			player.setVelY(-boostVel*1.5);
			return new Falling();
		}

		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
