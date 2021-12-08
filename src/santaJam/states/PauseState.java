package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.entities.upgrades.DoubleJumpItem;
import santaJam.entities.upgrades.GrappleItem;
import santaJam.entities.upgrades.SlideItem;
import santaJam.entities.upgrades.UpBoostItem;
import santaJam.inputs.Inputs;

public class PauseState implements State{

	GameState gameState;
	public PauseState(GameState gameState) {
		this.gameState = gameState;
	}
	@Override
	public void start(State prevState) {}

	@Override
	public void update() {
		if(Inputs.pause().isPressed()) {
			StateManager.setCurrentState(gameState);
		}if(Inputs.right().isPressed()) {
			StateManager.setCurrentState(new MapState(gameState));
		}if(Inputs.left().isPressed()) {
			StateManager.setCurrentState(new SettingsState(gameState));
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(57,11,50));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.white);
		g.setFont(Assets.font);
		g.drawString("PAUSED", 150, 10);

		
		if(StateManager.getGameState().getSave().hasSlide()) {
			g.drawString("SLIDE:", 50, 50);
			g.drawString("| "+new SlideItem(0, 0).getDescription().toUpperCase(), 150, 50);
		}
		if(StateManager.getGameState().getSave().hasDoubleJump()) {
			g.drawString("DOUBLE JUMP:", 50, 60);
			g.drawString("| "+new DoubleJumpItem(0, 0).getDescription().toUpperCase(), 150, 60);
		}
		if(StateManager.getGameState().getSave().hasGrapple()) {
			g.drawString("GRAPPLE:", 50, 70);
			g.drawString("| "+new GrappleItem(0, 0).getDescription().toUpperCase(), 150, 70);
		}if(StateManager.getGameState().getSave().hasUpBoost()) {
			g.drawString("UPBOOST:", 50, 80);
			g.drawString("| "+new UpBoostItem(0, 0).getDescription().toUpperCase(), 150, 80);
		}
		g.drawImage(Assets.settings, 5, Game.HEIGHT/2-Assets.settings.getHeight()/2, null);
		g.drawImage(Assets.mapIcon, Game.WIDTH-5-Assets.mapIcon.getWidth(), Game.HEIGHT/2-Assets.mapIcon.getHeight()/2, null);
	}

	@Override
	public void end() {}

}
