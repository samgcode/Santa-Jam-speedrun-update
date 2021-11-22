package santaJam.entities.player;

import santaJam.states.StateManager;

public class DoubleJump extends Jumping{
	public static final double JUMPSTRENGTH=9, STOPSTRENGTH=0.75;
	private static boolean canDoubleJump=true;
	private PlayerState prevState;

	
	public DoubleJump(PlayerState prevState) {
		this.prevState=prevState;
	}
	@Override
	public void start(PlayerState prevState) {
		super.start(prevState);
	}

	@Override
	public PlayerState update(Player player) {
		if(canDoubleJump()&&StateManager.getGameState().getSave().hasDoubleJump()) {
			PlayerState returnSate=super.update(player);
			if(returnSate!=null) {
				usedDoubleJump();
			}
			return returnSate;
		}
		
		return prevState;
		
	}

	@Override
	public void end() {}
	
	public static void refreshDoubleJump() {
		canDoubleJump=true;
	}
	public static boolean canDoubleJump() {
		return canDoubleJump;
	}
	public static void usedDoubleJump() {
		canDoubleJump=false;
	}
}
