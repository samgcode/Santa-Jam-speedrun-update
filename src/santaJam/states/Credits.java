package santaJam.states;

import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.audio.MusicManager;
import santaJam.graphics.UI.TextElement;
import santaJam.inputs.Inputs;

public class Credits implements State{

	GameState gameState;
	TextElement itemText;
	
	String credits = "programming/level design: agoogaloo \n "
			+ "art: bab chunko + agoogaloo \n "
			+ "music: popbirdprogression \n "
			+ "incredible penguin: you \n "
			+ "\n title font: Setback TT BRK by aenigma Fonts \n "
			+ "sounds: alexo400, voxlab, cylon8472, dland, mallement, soundscalpel.com, and j_p_higgins from freesound.org \n "
			
			+ "\n ---- "+Inputs.jump().getKey()+" to continue ----";
	public Credits(GameState gameState) {
		this.gameState = gameState;
		
		itemText = new TextElement(false, 20,40,TextElement.BIGMONOWIDTH,TextElement.SMALLMONOHEIGHT+3,Game.WIDTH-40,
				credits, Assets.font);
		itemText.centre(Game.WIDTH-40);
		
	}
	@Override
	public void start(State prevState) {}

	@Override
	public void update() {
		
		if(Inputs.pause().isPressed()||Inputs.jump().isPressed()) {
			MusicManager.playSound(MusicManager.menuBack);
			StateManager.setCurrentState(gameState);
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Assets.creditScreen,0,0, null);
		g.setFont(Assets.bigFont);
		g.drawString("Credits", 133, 24);
		
		itemText.render(g);
	}

	
	@Override
	public void end() {}

}
