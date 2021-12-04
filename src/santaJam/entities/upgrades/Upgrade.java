package santaJam.entities.upgrades;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.Game;
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
	
	protected abstract void onCollect(Player player);
	
	
	@Override
	public void update() {
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				onCollect((Player) i);
				StateManager.getGameState().saveData(i.getBounds().x,i.getBounds().y);
				((Player) i).setAnim(Player.dance);
				TextElement text = new TextElement(true, Game.WIDTH/2-65,Game.HEIGHT/2-50,6,7,120,
						"--"+name.toUpperCase()+"-- \n \n "+description+" \n \n "+Inputs.jump().getKey()+" TO CONTINUE", Assets.font) {
					@Override
					protected void onSelect() {
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
				
				
				text.centre();
				UIElement.getUIManager().addElement(rect);
				UIElement.getUIManager().addElement(text);
				killed=true;
			}
		}
	}
	public String getDescription() {
		return description;
	}
}
