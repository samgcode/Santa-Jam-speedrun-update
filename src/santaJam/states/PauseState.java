package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.Assets;
import santaJam.Game;
import santaJam.entities.upgrades.Binoculars;
import santaJam.entities.upgrades.Collectible;
import santaJam.entities.upgrades.DoubleJumpItem;
import santaJam.entities.upgrades.GrappleItem;
import santaJam.entities.upgrades.SlideItem;
import santaJam.entities.upgrades.UpBoostItem;
import santaJam.graphics.UI.TextElement;
import santaJam.inputs.Inputs;
import santaJam.maps.Map;
import santaJam.maps.Room;
import santaJam.states.menus.Menu;
import santaJam.states.menus.MenuSelection;

public class PauseState implements State{

	GameState gameState;
	Menu menu;
	MenuSelection slide, grapple, doubleJump, boost, binoculars, milk, chocolate, marshmellow;
	TextElement itemText;
	int itemTextWidth=Game.WIDTH/2-50;
	
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
		
		slide =new MenuSelection(new Rectangle(50,40,50,10), "SLIDE");
		grapple =new MenuSelection(new Rectangle(50,50,50,10), "GRAPPLE");
		doubleJump =new MenuSelection(new Rectangle(50,60,50,10), "DOUBLE JUMP");
		boost =new MenuSelection(new Rectangle(50,70,50,10), "BOOST THING");
		binoculars =new MenuSelection(new Rectangle(50,80,50,10), "BINOCULARS");
		milk =new MenuSelection(new Rectangle(50,150,50,10), "MILK:"+milkNum);
		chocolate =new MenuSelection(new Rectangle(110,150,50,10), "CHOCOLATE:"+chocolateNum);
		marshmellow =new MenuSelection(new Rectangle(190,150,50,10), "MARSHMALLOW:"+marshmellowNum);
		
		ArrayList<MenuSelection> abilities = new ArrayList<>();
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
		menu = new Menu(new Rectangle(),abilities.toArray(new MenuSelection[abilities.size()]));
		
		menu.select();
		
		itemText = new TextElement(false, Game.WIDTH/2,50,6,7,itemTextWidth,"NO ABILITIES OBTAINED", Assets.font);
		itemText.centre();
		
	}
	@Override
	public void start(State prevState) {}

	@Override
	public void update() {
		menu.update();
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

		menu.render(g);
		itemText.render(g);
		
		if(menu.getHovered()==slide) {
			itemText.update(new SlideItem(0, 0).getDescription(),itemTextWidth);
			itemText.centre();
		}
		if(menu.getHovered()==grapple) {
			itemText.update(new GrappleItem(0, 0).getDescription(),itemTextWidth);
			itemText.centre();

		}
		if(menu.getHovered()==doubleJump) {
			itemText.update(new DoubleJumpItem(0, 0).getDescription(),itemTextWidth);
			itemText.centre();

		}
		if(menu.getHovered()==boost) {
			itemText.update(new UpBoostItem(0, 0).getDescription(),itemTextWidth);
			itemText.centre();

		}
		if(menu.getHovered()==binoculars) {
			itemText.update(new Binoculars(0, 0).getDescription(),itemTextWidth);
			itemText.centre();

		}
		if(menu.getHovered()==milk) {
			itemText.update(new Collectible(0, 0,Collectible.MILK).getDescription(),itemTextWidth);
			itemText.centre();

		}
		if(menu.getHovered()==chocolate) {
			itemText.update(new Collectible(0, 0,Collectible.CHOCOLATE).getDescription(),itemTextWidth);
			itemText.centre();

		}
		if(menu.getHovered()==marshmellow) {
			itemText.update(new Collectible(0, 0,Collectible.MARSHMALLOW).getDescription(),itemTextWidth);
			itemText.centre();

		}
		
		g.drawImage(Assets.settings, 5, Game.HEIGHT/2-Assets.settings.getHeight()/2, null);
		g.drawImage(Assets.mapIcon, Game.WIDTH-5-Assets.mapIcon.getWidth(), Game.HEIGHT/2-Assets.mapIcon.getHeight()/2, null);
	}

	@Override
	public void end() {}

}
