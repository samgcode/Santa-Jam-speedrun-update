package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.graphics.UI.RectElement;
import santaJam.graphics.UI.TextElement;
import santaJam.graphics.UI.UIElement;
import santaJam.inputs.Inputs;
import santaJam.states.MapState;
import santaJam.states.StateManager;

public class Binoculars extends Upgrade{
	
	private int timer=0;
	private Animation anim = new Animation(Assets.binoculars);
	
	public Binoculars(int x, int y) {
		super(x,y);
		name = "Binoculars";
		description = "collectables shown on map";
	}
	

	@Override
	protected void onCollect(Player player) {
		StateManager.getGameState().getSave().unlockBinoculars(player);
		StateManager.getGameState().saveData(player.getBounds().x,player.getBounds().y);
		player.setAnim(Player.dance);
		
		
		TextElement text = new TextElement(true, Game.WIDTH/2-65,Game.HEIGHT/2-50,6,7,120,
				"--"+name.toUpperCase()+"-- \n \n "+description+" \n \n "+Inputs.jump().getKey()+" TO CONTINUE", Assets.font) {
			@Override
			protected void onSelect() {
				StateManager.setCurrentState(new MapState(StateManager.getGameState()));
				visible=false;
				remove=true;
			}
		};
		
		RectElement rect = new RectElement(text.getX()-3,text.getY()-3,text.getWidth()+6,text.getHeight()+6,10, Color.DARK_GRAY) {
			@Override
			protected void onSelect() {
				visible=false;
				remove=true;
			}
		};
		
		
		text.centre(120);
		UIElement.getUIManager().addElement(rect);
		UIElement.getUIManager().addElement(text);
	}
	@Override
	public void update() {
		anim.update();
		super.update();
		timer++;
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(),bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),null);
		g.fillOval(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}
	

}
