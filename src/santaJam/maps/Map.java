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

import santaJam.states.Camera;
import santaJam.states.StateManager;

public class Map {
	public static final int TILESIZE=12, WALLTILE=1, DOUBLEJUMP=3, GRAPPLE=4,SAVEPOINT=5,RIGHTBOUNCE=6,LEFTBOUNCE=7,UPBOUNCE=8,
			SMASHWALL=9,SMOOTHWALL=10,GRAPPLEPOINT=11, TRANSITIONTIME=10;
	
	private ArrayList<Rectangle> loadBounds = new ArrayList<Rectangle>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private Room[] currentRooms = new Room[2];
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
			rooms.add(new Room(x,y,"res/maps/"+fileName));
			//System.out.println(fileName+"has data: "+x+", "+y+", - "+width+", "+height);
			
		}
	//	currentRooms[1]=rooms.get(0);
		playerRoom=rooms.get(0);
		
	}
	public void update() {
		Rectangle playerBounds = StateManager.getGameState().getPlayer().getBounds();
		Rectangle pBounds =StateManager.getGameState().getPlayer().getBounds();
		Room oldRoom = playerRoom;
		boolean roomsFound=false;
		currentRooms=getRooms(playerBounds.x+playerBounds.width/2, playerBounds.y+playerBounds.height/2);
		
		
		for(Room r: currentRooms) {
			if(r!=null&&r.getBounds().contains(pBounds.x+pBounds.width/2, pBounds.y+pBounds.height/2)) {
				playerRoom = r;
				roomsFound=true;
			}
		}
		if(!roomsFound) {
			System.out.println("player is not in a loaded room");
		}
		
		if(oldRoom!=playerRoom) {
			playerRoom.loadRoom();
		}
		
	}
	
	public void render(Graphics2D g, Camera camera) {
		
		for(Room r:rooms) {
			if(r.getBounds().intersects(camera.getBounds())) {
				r.render(g, camera);
				//System.out.println("rendering room:"+currentRoom.name+"because player is at"+playerBounds.x+", "+playerBounds.y);
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
	public Room[] getRooms(int x, int y) {
		boolean multipleRooms=false;
		Room[] returnRooms= new Room[2];
		for(int i=0;i<rooms.size();i++) {
			if(loadBounds.get(i).contains(x,y)) {
				if(multipleRooms) {
					returnRooms[1]=rooms.get(i);
					return returnRooms;
				}else {
					returnRooms[0]=rooms.get(i);
					multipleRooms=true;
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
