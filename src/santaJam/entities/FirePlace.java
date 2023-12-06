package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import santaJam.Assets;
import santaJam.Game;
import santaJam.components.Timer;
import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.graphics.UI.PicElement;
import santaJam.graphics.UI.RectElement;
import santaJam.graphics.UI.TextElement;
import santaJam.graphics.UI.UIElement;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.ShrinkOvalParticle;
import santaJam.graphics.particles.shapes.colourers.RandTimed;
import santaJam.graphics.particles.spawners.EvenRectSpawn;
import santaJam.maps.Map;
import santaJam.maps.Room;
import santaJam.saves.Save;
import santaJam.states.Credits;
import santaJam.states.StateManager;

public class FirePlace extends Entity{
	private EvenRectSpawn particles;
	private boolean ended = false;
	
	
	public FirePlace(int x, int y) {
		bounds.width=Assets.firePlace.getWidth();
		bounds.height=Assets.firePlace.getHeight();
		this.x=x;
		this.y=y-bounds.height+8;
		bounds.x=(int) this.x;
		bounds.y=(int) this.y;
		grappleable=true;
		
		Color[] colors = new Color[] {
				new Color(233,87,33),new Color(229,157,37),new Color(158,64,19),new Color(23,23,23),
		};
		particles = new EvenRectSpawn(10,bounds.x+8,bounds.y+10, 10,6,new Straight(0,0,-90,10, 0.3),
				new ShrinkOvalParticle(new RandTimed(colors, 60,0),4,5,0.1,0.12), true);
	}
	@Override
	public void update() {
		particles.update();
		
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				if(!ended) {
					ended=true;
					playerCollide((Player) i);
				}
			}
		}
		
	}
	private void playerCollide(Player player) {
		StateManager.getGameState().saveData(bounds.x-20,bounds.y+bounds.height/2);
		(player).setAnim(Player.dance);
		Save save = StateManager.getGameState().getSave();
		int completion = completionStats(save);
		winText(save, completion);
		
	}
	private int completionStats(Save save) {
		int collected = save.getCollectibles().length;
		if(save.hasSlide()) {
			collected++;
		}
		if(save.hasGrapple()) {
			collected++;
		}
		if(save.hasDoubleJump()) {
			collected++;
		}
		if(save.hasUpBoost()) {
			collected++;
		}
		int completion =(int) ((double)collected/Save.maxCompletion*100);
		String completionText = "--CONGRATULATIONS-- \n \n COMPLETION: " + completion + "% \n \n ";
		
		String gameTime = "TIME: " + Timer.getTimeString() + 
			String.format(" \n (frame: %d, fps: %d) resets: %d", 
			Timer.getFrames(), Game.getFps(), Timer.resets
			) + " \n \n ";

		long millis =save.getPlayRealTime();
		long seconds =save.getPlayRealTime()/(1000);
		long minutes =seconds/60;
		long hours =minutes/60;
		String realTimeText = "REAL TIME: "+hours+":"
			+String.format("%02d:%02d.%03d",minutes%60, seconds%60, millis%1000);;
		
		TextElement text = new TextElement(true, Game.WIDTH/2-80,Game.HEIGHT/2-80,TextElement.BIGMONOWIDTH,8,166,
				completionText + gameTime + realTimeText, Assets.font) {
			@Override
			protected void onSelect() {
				StateManager.setCurrentState(new Credits(StateManager.getGameState()));
				visible=false;
				remove=true;
			}
		};
		
		RectElement rect = new RectElement(text.getX()-3,text.getY()-3,166,text.getHeight()+6, new Color(6,50,52)) {
			@Override
			protected void onSelect() {
				visible=false;
				remove=true;
			}
			@Override
			public void render(Graphics g) {
				super.render(g);
				g.drawImage(Assets.winTop,x-8,y-3, null);
				g.drawImage(Assets.winBottom,x-8,y+height-3, null);
			}
		};
		text.centre(166);
		UIElement.getUIManager().addElement(rect);
		UIElement.getUIManager().addElement(text);
		
		return completion;
	}
	
	private void winText(Save save, int completion) {
		String text = "you won!";
		BufferedImage image = null;
		long minutes =save.getPlayRealTime()/(1000*60);
		
		int milkNum = 0, chocolateNum=0, marshmellowNum=0;
		for(Room r:StateManager.getGameState().getMap().getAllRooms()) {
			if(r.getCollectedItem()==Map.MILK) {
				milkNum++;
			}else if(r.getCollectedItem()==Map.CHOCOLATE) {
				chocolateNum++;
			}else if(r.getCollectedItem()==Map.MARSHMELLOW) {
				marshmellowNum++;
			}
		}
		
		if(milkNum==0||chocolateNum==0||marshmellowNum==0) {
			text="too bad this hot chocolate has no ";
					if(chocolateNum==0) {
						text+="chocolate. ";
					}else if(milkNum==0) {
						text+="milk. ";
					}else if(marshmellowNum==0) {
						text+="marshmallows. ";
					}
					text+="at least i have a nice fire to keep warm.";
		}else {
			text = "time to relax with a nice cup of hot chocolate";
		}
		if(completion==100) {
			text="good job you collected everything! now you can make the ultimate cup of hot chocolate";
			image = Assets.completeHotChocolate;
		}
		if(completion>100) {
			text="how is this possible, did you cheat?";
		}
		
		if(minutes<10) {
			text="wow, that was so fast you didnt even get cold!";
		}
		if(!save.hasDoubleJump()) {
			text="wait you didnt even get all the upgrades you must be in quite the rush";
		}
		if(!save.hasDoubleJump()&&milkNum==3&&chocolateNum==3&&marshmellowNum==3){
			text="you got everything but the double jump? thats pretty impressive!";
		}
		
		if(image!=null) {
			
			PicElement hotChocolate = new PicElement(Game.WIDTH/2-image.getWidth()/2,Game.HEIGHT/2-image.getHeight()/2, image) {
				protected void onSelect() {
					visible=false;
					remove=true;
				}
				
			};
			UIElement.getUIManager().addElement(hotChocolate);
			
		}
		
				
		TextElement textBox = new TextElement(true, Game.WIDTH/2-125,Game.HEIGHT/2+60,TextElement.BIGMONOWIDTH,7,250,
				text.toUpperCase(), Assets.font) {
			@Override
			protected void onSelect() {
				visible=false;
				remove=true;
			}
		};
		
		RectElement rect = new RectElement(textBox.getX()-3,textBox.getY()-3,256,textBox.getHeight()+6, new Color(6,50,52)) {
			@Override
			protected void onSelect() {
				visible=false;
				remove=true;
			}
			@Override
			public void render(Graphics g) {
				super.render(g);
				particles.update();
				g.setColor(Color.white);
				g.drawRect(x,y, width-1, height-1);
				g.drawImage(Assets.menuMarker,x-8,y+height/2-2, null);
				g.drawImage(Assets.menuMarker,x+width+8,y+height/2-2,-Assets.menuMarker.getWidth(),Assets.menuMarker.getHeight(), null);
			}
		};
		textBox.centre(256);
		UIElement.getUIManager().addElement(rect);
		UIElement.getUIManager().addElement(textBox);
	}
	
	@Override
	public void render(Graphics2D g, Camera camera) {
		
		g.setColor(Color.blue);
		g.drawImage(Assets.firePlace,bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),null);
	}
	
	
	
}




