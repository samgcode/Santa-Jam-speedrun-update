package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import santaJam.Game;
import santaJam.SantaJam;
import santaJam.audio.MusicManager;
import santaJam.components.InputDisplay;
import santaJam.components.Timer;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.graphics.UI.RectElement;
import santaJam.graphics.UI.TextElement;
import santaJam.graphics.UI.UIElement;
import santaJam.graphics.particles.Particle;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.maps.Map;
import santaJam.saves.Save;

public class GameState implements State {
	int x=0, y=0,xVel=0,yVel=0, stateTime = 0;
	
	private double deathTransition=0;
	private boolean resetting=false;
	
	private Map map;
	private Camera camera;
	private Player player, savedPlayer;
	private Save save;
	private ArrayList<Integer> openedRooms = new ArrayList<>();
	private boolean stalled=false;

	private TasPlayback tas;
	
	TextElement timerText = new TextElement(2, 0, 2, 7, "1 \n 2");
	RectElement timerBG = new RectElement(timerText.getX()-2,timerText.getY()-2,160,timerText.getHeight()+3, new Color(6,50,52, 150));
	TextElement infoText = new TextElement(3, 51, 1, 7, "");
	TextElement infoText2 = new TextElement(2, 50, 1, 7, "");
	InputDisplay inputDisplay = new InputDisplay();

	public GameState(Save saveFile) {
		this.save=saveFile;
		saveFile.startTimer();
		Entity.getManager().reset();
		player = new Player(save.getStartX(),save.getStartY());
		Entity.getManager().addEntity(player);
		map = new Map("res/maps/main.world");
		camera = new Camera();
		for(int i:save.getOpenedRooms()) {
			openedRooms.add(i);
		}
		tas = new TasPlayback();
		if(Timer.TASPlayback) {
			tas.initPlayback();
		}
		timerText.setOpacity(150);
		infoText2.setColor(new Color(0, 0, 0));
	}
	
	@Override
	public void start(State prevState) {
		if(Timer.TASPlayback) { tas.update(); } 
		else { tas.initRecording(); }
		
		UIElement.getUIManager().clear();
		showTimer();
	}
	
	public void showTimer() {
		if(SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			UIElement.getUIManager().addElement(timerBG);
			UIElement.getUIManager().addElement(timerText);
			UIElement.getUIManager().addElement(infoText2);
			UIElement.getUIManager().addElement(infoText);
			infoText2.update("");
			infoText.update("");
			inputDisplay.show();
		}
	}

	public void hardReset() {
		new Save().resetSave();
		StateManager.setCurrentState(new TitleScreen());
	}

	public void updateInputDisplay() {
		inputDisplay.update();
	}
	
	@Override
	public void update() {
		Timer.update();

		if(SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			updateInputDisplay();
			if(Inputs.getKey(Keybind.DEBUG).isPressed()) {
				Game.setFps(0);
			}
			if(Inputs.getKey(Keybind.FULL_RESET).isPressed()) {
				hardReset();
			}
			if(Inputs.getKey(Keybind.SAVE_STATE).isPressed()) {
				stateTime = Timer.getFrames();
				System.out.println("saved state");
				savedPlayer = new Player(player, player.getBounds().x, player.getBounds().y);
			}
			if(Inputs.getKey(Keybind.LOAD_STATE).isPressed()) {
				if(savedPlayer instanceof Player) {
					Timer.setFrames(stateTime);
					System.out.println("loaded state");
					player = new Player(savedPlayer, savedPlayer.getBounds().x, savedPlayer.getBounds().y);
					Entity.getManager().reset();
					Entity.getManager().addEntity(player);
					map.getPlayerRoom().loadRoom();
				}
			}
			
			if(Timer.TASPlayback) { tas.update(); }
			else { tas.updateRecord(); }
		}
		
		if(Inputs.getKey(Keybind.RESET).isPressed()) {
			StateManager.setCurrentState(new GameState(new Save()));
		}
		
		if(resetting) {
			deathTransition+=0.1;
			
		}
		if(deathTransition>0) {
			if(deathTransition>=1) {
				resetting=false;
				player = new Player(save.getStartX(),save.getStartY());
				Entity.getManager().reset();
				Entity.getManager().addEntity(player);
				map.getPlayerRoom().loadRoom();
				UIElement.getUIManager().clear();
				showTimer();
			}
			if(!resetting) {
				deathTransition-=0.1;
			}else {
				return;
			}		
		}
		
		stalled=false;
		for (UIElement i:UIElement.getUIManager().getElements()) {
			if(i.visible&&i.pauseGame) {
				stalled=true;
			}
		}
		if(!stalled) {
			Entity.getManager().update();
		}

		map.update();
		camera.moveToEntity(player);
		camera.update(map.getPlayerRoom());
		
		timerText.update(Timer.getTimeString() + 
			String.format("  frame: %d\nfps: %d, resets: %d", 
			Timer.getFrames(), Game.getFps(), Timer.resets
		));

		if(Game.DEBUG_ENABLED) {
			String info = String.format("pos|x:%d,y:%d\nvel|x:%.2f,y:%.2f\n", 
				player.getBounds().x, player.getBounds().y, player.getVelX(), player.getVelY()
				) + "State|" + player.getStateName() + "\n"
				+ "Can jump|" + player.canJump() + "\n"
				+ "Frames since input|" + tas.waitTime + "\n"
			;
			infoText2.update(info);
			infoText.update(info);
		} else {
			infoText2.update("");
			infoText.update("");
		}

		UIElement.getUIManager().update();
		Particle.getParticleManager().update();
		
		if(Inputs.getKey(Keybind.PAUSE).isPressed()) {
			System.out.println(Timer.getTimeString());
			MusicManager.menuSelect.play();
			StateManager.setCurrentState(new PauseState(this));
			UIElement.getUIManager().clear();
		}

	}

	@Override
	public void render(Graphics2D g) {
		Particle.getParticleManager().renderBack(g, camera);
		map.render(g, camera);
		Entity.getManager().render(g, camera);
		Particle.getParticleManager().renderFront(g, camera);
		UIElement.getUIManager().render(g);
		
		g.setColor(new Color(5,28,40));
		if(resetting) {
			g.fillRect(0, 0, Game.WIDTH,(int)(Game.HEIGHT*deathTransition*1.2));
		}else if(deathTransition>0) {
			g.fillRect(0, Game.HEIGHT-(int)(Game.HEIGHT*deathTransition*1.2) ,Game.WIDTH,(int)(Game.HEIGHT*deathTransition*1.5));
		}
	}
	public void gameOver() {
		player.setAnim(Player.falling);
		player.getCurrentState().end();
		MusicManager.death.play();
		
		resetting=true;
		
	}
	public void saveData(int x, int y) {
		save.saveGameTime();
		save.saveOpenedRooms(openedRooms);
		save.savePlayerData(x,y);
	}
	
	public void saveTime() {
		save.saveGameTime();
	}

	public void saveTas() {
		tas.saveInputs();
	}

	@Override
	public void end() {}
	
	
	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}
	public Save getSave() {
		return save;
	}
	public ArrayList<Integer> getOpenedRooms() {
		return openedRooms;
	}
	
	public void addOpenedRoom(int room) {
		for(int i:openedRooms) {
			if (i==room) {
				return;
			}
		}
		openedRooms.add(room);
	}
	public Camera getCamera() {
		return camera;
	}

}
