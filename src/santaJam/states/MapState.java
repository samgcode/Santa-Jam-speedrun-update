package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import santaJam.Assets;
import santaJam.Game;
import santaJam.audio.MusicManager;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.inputs.Inputs;
import santaJam.maps.Map;
import santaJam.maps.Room;

public class MapState implements State{
	private final int LEFT=0, RIGHT=1, UP=2, DOWN=3;
	private final double MINSCALE=0.25;
	
	GameState gameState;
	BufferedImage mapImg;
	double scale=MINSCALE;
	int mapX=0, mapY=0,xOffset,yOffset;
	int visitMinX, visitMinY, visitMaxX, visitMaxY,mapMinX, mapMinY, mapMaxX, mapMaxY;
	ArrayList<RoomExit> exits;
	ArrayList<Integer> openedRooms = new ArrayList<Integer>();

	public MapState(GameState gameState) {
		this.gameState = gameState;
		openedRooms = gameState.getOpenedRooms();
		setMapVals(gameState.getMap());
		mapImg = buildMap(gameState.getMap());
		center();
		
		
	}
	
	@Override 
	public void start(State prevState) {
		// TODO Auto-generated method stub
		
	}
	private void setMapVals(Map map){
		for(int i=0;i<map.getAllRooms().size();i++) {
			Room r = map.getAllRooms().get(i);
			if(r.getX()<mapMinX) {
				mapMinX=r.getX();
			}
			if(r.getY()<mapMinY) {
				mapMinY=r.getY();
			}
			if(r.getX()+r.getWidth()>mapMaxX) {
				mapMaxX=r.getX()+r.getWidth();
			}
			if(r.getY()+r.getHeight()>mapMaxY) {
				mapMaxY=r.getY()+r.getHeight();
			}
			if(openedRooms.contains(i)){
				if(r.getX()<visitMinX) {
					visitMinX=r.getX();
				}
				if(r.getY()<visitMinY) {
					visitMinY=r.getY();
				}
				if(r.getX()+r.getWidth()>visitMaxX) {
					visitMaxX=r.getX()+r.getWidth();
				}
				if(r.getY()+r.getHeight()>visitMaxY) {
					visitMaxY=r.getY()+r.getHeight();
				}
			}
		}
		makeRoomExits(map);
	}
	private void makeRoomExits(Map map) {
		//idk whats goin on here but it was lagin the map so now it only runs once at startup
		exits = new ArrayList<>();
		for(int i:openedRooms) {
			Room checkRoom = map.getAllRooms().get(i);
			for(int x=0;x<checkRoom.getWidth()/Map.TILESIZE;x++) {
				if(!checkRoom.isWall(x, 0)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()+x*Map.TILESIZE,checkRoom.getY()-1)) {
							int xOffset = checkRoom.getX()-r.getX();
							if(!r.isWall(xOffset/Map.TILESIZE+x,r.getHeight()/Map.TILESIZE-1)) {
								exits.add(new RoomExit(checkRoom.getX()+x*Map.TILESIZE, checkRoom.getY(),UP));
							}
						}
					}
				}
				if(!checkRoom.isWall(x, checkRoom.getHeight()/Map.TILESIZE)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()+x*Map.TILESIZE,checkRoom.getY()+checkRoom.getHeight()+1)) {
							int xOffset = checkRoom.getX()-r.getX();
							if(!r.isWall(xOffset/Map.TILESIZE+x,0)) {
								exits.add(new RoomExit(checkRoom.getX()+x*Map.TILESIZE, checkRoom.getY()+checkRoom.getHeight(),DOWN));
							}
						}
					}
				}
			}
			for(int y=0;y<checkRoom.getHeight()/Map.TILESIZE;y++) {
				if(!checkRoom.isWall(0, y)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()-1,checkRoom.getY()+y*Map.TILESIZE)) {
							int yOffset = checkRoom.getY()-r.getY();
							if(!r.isWall(r.getWidth()/Map.TILESIZE,yOffset/Map.TILESIZE+y)) {
								exits.add(new RoomExit(checkRoom.getX(), checkRoom.getY()+y*Map.TILESIZE,LEFT));
							}
						}
					}
				}
				if(!checkRoom.isWall(checkRoom.getWidth()/Map.TILESIZE, y)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()+checkRoom.getWidth()+1,checkRoom.getY()+y*Map.TILESIZE)) {
							int yOffset = checkRoom.getY()-r.getY();
							if(!r.isWall(0,yOffset/Map.TILESIZE+y)) {
								exits.add(new RoomExit(checkRoom.getX()+checkRoom.getWidth()-1, checkRoom.getY()+y*Map.TILESIZE,RIGHT));
							}
						}
					}
				}
			}
		}
	}

	private BufferedImage buildMap(Map map) {
		BufferedImage mapImg;
	
		mapImg = new BufferedImage((int)((mapMaxX-mapMinX)/Map.TILESIZE*scale)+2,
				(int)((mapMaxY-mapMinY)/Map.TILESIZE*scale)+2, BufferedImage.TYPE_4BYTE_ABGR);	
		
		
		Graphics2D g = mapImg.createGraphics();
		drawRooms(mapImg,g, map);
		
		for(RoomExit e:exits) {
			
			int x=(int) Math.round((e.x-mapMinX)/Map.TILESIZE*scale);
			int y=(int)Math.round((e.y-mapMinY)/Map.TILESIZE*scale);
			mapImg.setRGB(x,y,Color.white.getRGB());		
			
		}
		g.drawImage(Assets.houseIcon, (int)(325*scale), (int)(36*scale), null);
		if(gameState.getSave().hasBinoculars()) {
			drawCollectibles(mapImg, g, map);
		}
		drawPlayer(mapImg, g, map);
		

		return mapImg;
	}
	private void drawRooms(BufferedImage img,Graphics2D g, Map map) {
		for(int i:openedRooms) {
			Room room = map.getAllRooms().get(i);
			int roomX= (int) Math.round((room.getX()-mapMinX)/Map.TILESIZE*scale);
			int roomY= (int) Math.round((room.getY()-mapMinY)/Map.TILESIZE*scale);
			if(room.getArea()==1){
				g.setColor(new Color(14,117,50));
			}if(room.getArea()==2){
				g.setColor(new Color(0,89,241));
			}if(room.getArea()==3){
				g.setColor(new Color(184,184,184));
			}if(room.getArea()==4){
				g.setColor(new Color(170,141,96));
			}
			
			g.fillRect(roomX,roomY,(int) Math.round(room.getWidth()/Map.TILESIZE*scale),
					(int) Math.round(room.getHeight()/Map.TILESIZE*scale));
			g.setColor(new Color(36,29,41));
			g.drawRect(roomX,roomY,(int) Math.round(room.getWidth()/Map.TILESIZE*scale),
					(int) Math.round(room.getHeight()/Map.TILESIZE*scale));
		}
		
	}
	
	
	private void drawCollectibles(BufferedImage img, Graphics2D g, Map map) {
		for(Room r:gameState.getMap().getAllRooms()) {
			int collectible = r.getCollectibles();
			int roomX= (int) Math.round((r.getX()-mapMinX+r.getWidth()/2)/Map.TILESIZE*scale);
			int roomY= (int) Math.round((r.getY()-mapMinY+r.getHeight()/2)/Map.TILESIZE*scale);

			if(collectible==Map.MILK) {
				g.drawImage(Assets.milkIcon, roomX-Assets.milkIcon.getWidth()/2, roomY-Assets.milkIcon.getHeight()/2, null);
			}
			if(collectible==Map.CHOCOLATE) {
				g.drawImage(Assets.chocolateIcon, roomX-Assets.chocolateIcon.getWidth()/2, roomY-Assets.chocolateIcon.getHeight()/2, null);
				
			}
			if(collectible==Map.MARSHMELLOW) {
				g.drawImage(Assets.marchmallowIcon, roomX-Assets.marchmallowIcon.getWidth()/2, 
						roomY-Assets.marchmallowIcon.getHeight()/2, null);
				
			}
				
		}
	}
		
	private void drawPlayer(BufferedImage img, Graphics2D g, Map map) {
		for(Entity i :Entity.getManager().getEntities()) {
			if(i instanceof Player) {
				
				int x = (int) Math.round((i.getBounds().x+i.getBounds().width/2-mapMinX)/Map.TILESIZE*scale);	
				int y = (int) Math.round((i.getBounds().y+i.getBounds().height/2-mapMinY)/Map.TILESIZE*scale);	
				g.drawImage(Assets.playerIcon, x-Assets.playerIcon.getWidth()/2, y-Assets.playerIcon.getHeight()/2, null);				
			}
		}
		
	}
	@Override
	public void update() { 
		mapImg = buildMap(gameState.getMap());
		if(Inputs.jump().isHeld()&&scale<1-scale/20) {
			scale+=scale/20;
		
		}
		if(Inputs.grapple().isHeld()) {
			if(scale>MINSCALE) {
				scale-=scale/20;
			}else {
				center();
			}
		}		
		
		if(Inputs.left().isHeld()&&scale>MINSCALE) {
			mapX+=4;
		}
		if(Inputs.right().isHeld()&&scale>MINSCALE) {
			mapX-=4;
		}
		
		if(Inputs.up().isHeld()&&scale>MINSCALE) {
			mapY+=4;
		}
		if(Inputs.down().isHeld()&&scale>MINSCALE) {
			mapY-=4;
		}
	
		if(Inputs.left().isPressed()) {
			if(scale<=MINSCALE) {
				MusicManager.menuBack.play();
				StateManager.setCurrentState(new PauseState(gameState));
			}else {
				
			}
		}if(Inputs.pause().isPressed()) {
			MusicManager.menuBack.play();
			StateManager.setCurrentState(gameState);
		}
		
		
		
	}
	private void center(){
		mapX =0;
		mapY =4;

	}

	@Override
	public void render(Graphics2D g) {
		int mapDrawX=Game.WIDTH/2+mapX-mapImg.getWidth()/2;
		int mapDrawY=Game.HEIGHT/2+mapY-mapImg.getHeight()/2;
		g.setColor(new Color(10,84,62));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.drawImage(mapImg,mapDrawX,mapDrawY, null);
	
		
		if(scale<=MINSCALE) {
			g.drawImage(Assets.mapScreen,0,0,null);
			
			g.setColor(new Color(200,254,255));
			g.setFont(Assets.bigFont);
			g.drawString("Map", Game.WIDTH/2-11, 24);
			
			g.setFont(Assets.font);
			g.drawString(Inputs.jump().getKey()+"/"+Inputs.grapple().getKey()+" TO ZOOM", Game.WIDTH/2-34, 166);
		
		
		
		}
		//g.drawRect(Game.WIDTH/2-2, Game.HEIGHT/2-2, visitWidth/, visitHeight);
		
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
	
}

class RoomExit {
	int x, y;
	int direction;
	public RoomExit(int x, int y, int direction) {
		this.x=x;
		this.y=y;
		this.direction=direction;
	}
	
}
