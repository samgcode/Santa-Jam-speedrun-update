package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.Assets;
import santaJam.Game;
import santaJam.audio.MusicManager;
import santaJam.entities.upgrades.Binoculars;
import santaJam.entities.upgrades.Collectible;
import santaJam.entities.upgrades.DoubleJumpItem;
import santaJam.entities.upgrades.GrappleItem;
import santaJam.entities.upgrades.SlideItem;
import santaJam.entities.upgrades.UpBoostItem;
import santaJam.graphics.UI.TextElement;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.maps.Map;
import santaJam.maps.Room;
import santaJam.states.menus.Menu;
import santaJam.states.menus.MenuText;

public class PauseState implements State{

	GameState gameState;
	Menu menu;
	MenuText slide, grapple, doubleJump, boost, binoculars, milk, chocolate, marshmellow;
	TextElement itemText;
	int itemTextWidth=108;
	
	public PauseState(GameState gameState) {
		this.gameState = gameState;
		
		int milkNum = 0, chocolateNum=0, marshmellowNum=0;
		for(Room r:gameState.getMap().getAllRooms()) {
			if(r.getCollectedItem()==Map.MILK) {
				milkNum++;
			}else if(r.getCollectedItem()==Map.CHOCOLATE) {
				chocolateNum++;
			}else if(r.getCollectedItem()==Map.MARSHMELLOW) {
				marshmellowNum++;
			}
		}
		Color textColour = new Color(200,254,255),hoverColour =  new Color(14,117,50);
		
		slide =new MenuText(new Rectangle(60,50,50,10), "SLIDE",textColour,hoverColour);
		grapple =new MenuText(new Rectangle(60,60,50,10), "GRAPPLE",textColour,hoverColour);
		doubleJump =new MenuText(new Rectangle(60,70,50,10), "DOUBLE JUMP",textColour,hoverColour);
		boost =new MenuText(new Rectangle(60,80,50,10), "UP BOOST",textColour,hoverColour);
		binoculars =new MenuText(new Rectangle(60,90,50,10), "BINOCULARS",textColour,hoverColour);
		milk =new MenuText(new Rectangle(60,150,50,10), "MILK:"+milkNum,textColour,hoverColour);
		chocolate =new MenuText(new Rectangle(110,150,50,10), "CHOCOLATE:"+chocolateNum,textColour,hoverColour);
		marshmellow =new MenuText(new Rectangle(190,150,50,10), "MARSHMALLOW:"+marshmellowNum,textColour,hoverColour);
		
		ArrayList<MenuText> abilities = new ArrayList<>();
		if(StateManager.getGameState().getSave().hasSlide()) {
			abilities.add(slide);
		}
		if(StateManager.getGameState().getSave().hasGrapple()) {
			abilities.add(grapple);
		}
		if(StateManager.getGameState().getSave().hasDoubleJump()) {
			abilities.add(doubleJump);
		}
		if(StateManager.getGameState().getSave().hasUpBoost()) {
			abilities.add(boost);
		}
		if(StateManager.getGameState().getSave().hasBinoculars()) {
			abilities.add(binoculars);
		}
		abilities.add(milk);
		abilities.add(chocolate);
		abilities.add(marshmellow);
		menu = new Menu(new Rectangle(),abilities.toArray(new MenuText[abilities.size()]));
		
		menu.select();
		
		itemText = new TextElement(false, Game.WIDTH/2+10,70,TextElement.BIGMONOWIDTH,TextElement.SMALLMONOHEIGHT,itemTextWidth,"", Assets.font);
		itemText.centre(itemTextWidth);
		
	}
	@Override
	public void start(State prevState) {}

	@Override
	public void update() {
		menu.update();
		if(Inputs.getKey(Keybind.PAUSE).isPressed()) {
			MusicManager.playSound(MusicManager.menuBack);
			StateManager.setCurrentState(gameState);
		}if(Inputs.getKey(Keybind.RIGHT).isPressed()) {
			MusicManager.playSound(MusicManager.menuBack);
			StateManager.setCurrentState(new MapState(gameState));
		}if(Inputs.getKey(Keybind.LEFT).isPressed()) {
			MusicManager.playSound(MusicManager.menuBack);
			StateManager.setCurrentState(new SettingsState(gameState));
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Assets.pauseScreen,0,0, null);
		g.setColor(new Color(200,254,255));
		g.setFont(Assets.bigFont);
		g.drawString("Inventory", 126, 24);
		
		
		menu.render(g);
		itemText.render(g);
		
		
		if(menu.getHovered()==slide) {
			itemText.update(new SlideItem(0, 0).getDescription(),itemTextWidth);			
		}
		if(menu.getHovered()==grapple) {
			itemText.update(new GrappleItem(0, 0).getDescription(),itemTextWidth);
		}
		if(menu.getHovered()==doubleJump) {
			itemText.update(new DoubleJumpItem(0, 0).getDescription(),itemTextWidth);
		}
		if(menu.getHovered()==boost) {
			itemText.update(new UpBoostItem(0, 0).getDescription(),itemTextWidth);
		}
		if(menu.getHovered()==binoculars) {
			itemText.update(new Binoculars(0, 0).getDescription(),itemTextWidth);
		}
		if(menu.getHovered()==milk) {
			itemText.update(new Collectible(0, 0,Collectible.MILK).getDescription(),itemTextWidth);
		}
		if(menu.getHovered()==chocolate) {
			itemText.update(new Collectible(0, 0,Collectible.CHOCOLATE).getDescription(),itemTextWidth);
		}
		if(menu.getHovered()==marshmellow) {
			itemText.update(new Collectible(0, 0,Collectible.MARSHMALLOW).getDescription(),itemTextWidth);
		}
		itemText.centre(itemTextWidth);

		//g.drawRect(itemText.getX(),itemText.getY(),itemTextWidth,itemText.getHeight());
	}

	
	@Override
	public void end() {}

}
