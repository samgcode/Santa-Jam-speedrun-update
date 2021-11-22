package santaJam.entities.player;

import santaJam.states.StateManager;

public class SlideDoubleJump extends SlideJump{
	public static final double JUMPSTRENGTH=9, STOPSTRENGTH=0.75;
	private static boolean canDoubleJump=true;
	private PlayerState prevState;

	
	public SlideDoubleJump(PlayerState prevState) {
		this.prevState=prevState;
	}
	@Override
	public void start(PlayerState prevState) {
		super.start(prevState);
	}

	@Override
	public PlayerState update(Player player) {
		
		if(canDoubleJump&&StateManager.getGameState().getSave().hasDoubleJump()) {
			PlayerState returnSate=super.update(player);
			if(returnSate!=null) {
				canDoubleJump=false;
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
}
