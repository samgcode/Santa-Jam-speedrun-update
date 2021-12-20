package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.audio.MusicManager;
import santaJam.entities.Entity;
import santaJam.entities.FirePlace;
import santaJam.entities.Radio;
import santaJam.graphics.Camera;
import santaJam.inputs.Inputs;
import santaJam.maps.Room;

public class TitleScreen implements State{
	Room home,lastRoom;
	Camera bgCam  ;
	Entity radio, firePlace = new FirePlace(568,216);
	
	public TitleScreen(Room home, Room lastRoom, Camera bgCam) {
		radio = new Radio(488,264);
		this.home=home;
		this.lastRoom=lastRoom;
		this.bgCam=bgCam;
	}
	public TitleScreen() {
		this(new Room(320, 136, "home.json"), new Room(0, 0, "3-10.json"),new Camera());
		
	}
	
	@Override
	public void start(State prevState) {
		bgCam.setEntityWeight(0);
		bgCam.setLocationWeight(1);
	}

	@Override
	public void update() {
		firePlace.update();
		home.update();
		bgCam.update(home);
		if(Inputs.jump().isPressed()) {
			MusicManager.menuSelect.play();
			StateManager.setCurrentState(new MainMenu(home, lastRoom, bgCam));
		}
	}

	@Override
	public void render(Graphics2D g) {
		lastRoom.render(g, bgCam);
		home.render(g, bgCam);
		firePlace.render(g, bgCam);
		radio.render(g, bgCam);
		g.drawImage(Assets.titleScreen,0, 0, null);
		g.setFont(Assets.bigFont);
		//g.setColor(Color.black);
		//g.drawString("Title Name", Game.WIDTH/2-40, 51);
		g.setColor(Color.white);
		g.drawString("THE QUEST UP PENGUIN PEAK", Game.WIDTH/2-99, 50);
		
		g.setFont(Assets.font);
		g.drawString("--PRESS "+Inputs.jump().getKey().toUpperCase()+" TO START--", Game.WIDTH/2-50, 140);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
