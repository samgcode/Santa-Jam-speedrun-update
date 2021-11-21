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
	public static final int TILESIZE=12, WALLTILE=1, ENEMYTILE=2, DOUBLEJUMP=3, GRAPPLE=4,TRANSITIONTIME=10;
	private int transitionTimer=0;
	
	
	private ArrayList<Rectangle> roomBounds = new ArrayList<Rectangle>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	Room currentRoom, prevRoom;
	
	
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
			roomBounds.add(new Rectangle(x,y,width,height));
			rooms.add(new Room(x,y,"res/"+fileName));
			System.out.println(fileName+"has data: "+x+", "+y+", - "+width+", "+height);
			
		}
		currentRoom=rooms.get(0);
		
	}
	public void update() {
		Rectangle playerBounds = StateManager.getGameState().getPlayer().getBounds();
		
		Room oldRoom = currentRoom;
		currentRoom=getRoom(playerBounds.x+playerBounds.width/2, playerBounds.y+playerBounds.height/2);
		transitionTimer--;
		if(oldRoom!=currentRoom) {
			transitionTimer=TRANSITIONTIME;
			prevRoom=oldRoom;
			currentRoom.loadRoom();
		}
	}
	
	public void render(Graphics2D g, Camera camera) {
		
		if(transitionTimer>0) {
			
			prevRoom.render(g, camera);
			
		}
		currentRoom.render(g, camera);
		//System.out.println("rendering room:"+currentRoom.name+"because player is at"+playerBounds.x+", "+playerBounds.y);
		
	}
	public Room getRoom(int x, int y) {
		for(int i=0;i<rooms.size();i++) {
			if(roomBounds.get(i).contains(x,y)) {
				return rooms.get(i);
			}
		}
		System.out.println("location: "+x+", "+y+" is not in any room");
		return null;
	}
	public Room getCurrentRoom() {
		return currentRoom;
	}
}
