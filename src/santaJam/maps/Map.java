package santaJam.maps;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import santaJam.Assets;
import santaJam.graphics.Camera;
import santaJam.states.StateManager;

public class Map {
	public static final int TILESIZE=8, MILK=0,MARSHMELLOW=1,CHOCOLATE=2,SMASHWALL=3,SAVEPOINT=4,SLIDE=5,GRAPPLE=6, DOUBLEJUMP=7,UPBOOST=8, 
			BINOCULARS=18,GRAPPLEPOINT=9,UPSPIKE=10,DOWNSPIKE=11,RIGHTSPIKE=12,LEFTSPIKE=13,ICICLE=14,RIGHTBOUNCE=15,LEFTBOUNCE=16,UPBOUNCE=17,
			FIREPLACE=19, RADIO=20;
	protected static final int wallStart=0, wallEnd=419, smoothStart=420, smoothEnd=547;
	private ArrayList<Rectangle> loadBounds = new ArrayList<Rectangle>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private Room[] currentRooms = new Room[3];
	private Room playerRoom;
	
	
	public Map(String path) {
		JSONObject file;
		try {
			file=(JSONObject)(new JSONParser().parse(new FileReader(path)));
		} catch (IOException | ParseException e) {
			System.out.print("there was a problem loading JSON file at "+path );
			e.printStackTrace();
			return;
			
		}
		JSONArray map=(JSONArray)file.get("maps");//the array with all the rooms
		for(Object roomData:map) {
			JSONObject roomObject = (JSONObject)roomData;
			int x = (int)(long)(roomObject.get("x"));
			int y = (int)(long)(roomObject.get("y"));
			int width = (int)(long)(roomObject.get("width"));
			int height = (int)(long)(roomObject.get("height"));
			String fileName = (String) roomObject.get("fileName");
			loadBounds.add(new Rectangle(x-TILESIZE*3,y-TILESIZE*3,width+TILESIZE*6,height+TILESIZE*6));
			rooms.add(new Room(x,y,fileName));
			//System.out.println(fileName+"has data: "+x+", "+y+", - "+width+", "+height);
			
		}
	//	currentRooms[1]=rooms.get(0);
		playerRoom=rooms.get(1);
		
		
	}
	public void update() {
		Rectangle playerBounds = StateManager.getGameState().getPlayer().getBounds();
		Rectangle pBounds =StateManager.getGameState().getPlayer().getBounds();
		Room oldRoom = playerRoom;
		currentRooms=getRooms(playerBounds.x+playerBounds.width/2, playerBounds.y+playerBounds.height/2);
		
		
		for(Room r: currentRooms) {			
			if(r!=null&&r.getBounds().contains(pBounds.x+pBounds.width/2, pBounds.y+pBounds.height/2)) {
				playerRoom = r;
			}
		}		
		if(oldRoom!=playerRoom) {
			playerRoom.loadRoom();
			oldRoom.unload();
			for(int i=0;i<rooms.size();i++) {
				if(rooms.get(i)==playerRoom) {
					StateManager.getGameState().addOpenedRoom(i);
				}
			}
			
		}
		playerRoom.update();
		
	}
	
	public void render(Graphics2D g, Camera camera) {
		if(playerRoom.getArea()==3||playerRoom.getArea()==4
				) {
			g.drawImage(Assets.peak,0,0, null);
		}
		if(playerRoom.getArea()==2) {
			g.drawImage(Assets.iceCave,0,0, null);
		}
		if(playerRoom.getArea()==1) {
			g.drawImage(Assets.forest,0,0, null);
		}
		
		for(Room r:rooms) {
			if(r.getBounds().intersects(camera.getBounds())) {
				r.render(g, camera);
			}
		}		
	}
	
	public boolean inBounds(Rectangle rect) {
	
		for(Room r:currentRooms) {
			if(r!=null&&r.getBounds().intersects(rect)) {
				return true;
			}
			
		}
		return false;
	}
	public boolean inMap(Rectangle rect) {
		boolean intersectingRoom=false;
		for(Room r:rooms) {
			
			if(r!=null&&r.getBounds().contains(rect)) {
				return true;
			}else if(r!=null&&r.getBounds().intersects(rect)) {
				if(intersectingRoom) {
					return true;
				}
				intersectingRoom=true;
			}
			
		}
		return false;
	}
	
	public Room[] getRooms(int x, int y) {
	
		Room[] returnRooms= new Room[4];
		int index=0;
		for(int i=0;i<rooms.size();i++) {
			if(loadBounds.get(i).contains(x,y)) {
				returnRooms[index]=rooms.get(i);
				index++;
				if(index==4) {
					System.out.println("room array filled");
					return returnRooms;
				}
			}
		}
		return returnRooms;
	}
	public ArrayList<Room> getAllRooms() {
		return rooms;
	}
	public Room[] getLoadedRooms() {
		return currentRooms;
	}
	public Room getPlayerRoom() {
		return playerRoom;
	}
	
}
