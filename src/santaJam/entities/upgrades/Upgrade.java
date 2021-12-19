package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.Game;
import santaJam.audio.MusicManager;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.graphics.UI.RectElement;
import santaJam.graphics.UI.TextElement;
import santaJam.graphics.UI.UIElement;
import santaJam.inputs.Inputs;
import santaJam.states.StateManager;

public abstract class Upgrade extends Entity{
	protected String description="", name="";
	
	public Upgrade(int x, int y) {
		bounds = new Rectangle(x,y,10,10);
		this.x=x;
		this.y=y;
		grappleable=true;
	}
	
	protected void onCollect(Player player) {
		MusicManager.itemGet.play();
		
		StateManager.getGameState().saveData(bounds.x+bounds.width/2,bounds.y+bounds.height/2);
		(player).setAnim(Player.dance);
		TextElement text = new TextElement(true, Game.WIDTH/2-60,Game.HEIGHT/2-50,TextElement.BIGMONOWIDTH,7,120,
				"--"+name.toUpperCase()+"-- \n \n "+description+" \n \n "+Inputs.jump().getKey()+" TO CONTINUE", Assets.font) {
			@Override
			protected void onSelect() {
				visible=false;
				remove=true;
			}
		};
		
		RectElement rect = new RectElement(text.getX()-3,text.getY()-3,126,text.getHeight()+6, new Color(6,50,52)) {
			@Override
			protected void onSelect() {
				visible=false;
				remove=true;
			}
			@Override
			public void render(Graphics g) {
				super.render(g);
				g.drawImage(Assets.upgradeTop,x-8,y-3, null);
				g.drawImage(Assets.upgradeBottom,x-8,y+height-3, null);
			}
		};
		
		
		text.centre(120);
		UIElement.getUIManager().addElement(rect);
		UIElement.getUIManager().addElement(text);
	}
	
	
	@Override
	public void update() {
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				onCollect((Player) i);
				killed=true;
			}
		}
	}
	public String getDescription() {
		return description;
	}
}
