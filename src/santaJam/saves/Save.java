package santaJam.saves;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import santaJam.components.Timer;
import santaJam.entities.player.Player;

public class Save {
	public static final double maxCompletion = 13;
	private long timeOfSave=0, playRealTime = 0;
	private Properties propertiesFile=new Properties();
	private String filePath="res/saves/save.properties";
	
	private boolean started;
	private boolean doubleJump, grapple, upBoost, slide,binoculars;
	private int startX, startY;
	private int[] openedRooms;
	private String[] collectibles;
	
	public Save() {
		loadFile(filePath);
	}
	public Save(String path) {
		this.filePath=path;
		loadFile(filePath);
	}
	public void loadFile(String filePath) {
		// System.out.println("loading");
		propertiesFile=loadProperties(filePath);
		
		//setting all the variables from the properties file
		started=Boolean.valueOf(propertiesFile.getProperty("started"));
		slide=Boolean.valueOf(propertiesFile.getProperty("slide"));
		doubleJump=Boolean.valueOf(propertiesFile.getProperty("doubleJump"));
		grapple=Boolean.valueOf(propertiesFile.getProperty("grapple"));
		upBoost=Boolean.valueOf(propertiesFile.getProperty("upBoost"));
		binoculars=Boolean.valueOf(propertiesFile.getProperty("binoculars"));
		startX=Integer.parseInt(propertiesFile.getProperty("startX"));
		startY=Integer.parseInt(propertiesFile.getProperty("startY"));		
		playRealTime=Integer.parseInt(propertiesFile.getProperty("realtime"));	
		if(Timer.getFrames() == 0) {
			Timer.setFrames(Integer.parseInt(propertiesFile.getProperty("gametime")));
		}	
		Timer.resets = (Integer.parseInt(propertiesFile.getProperty("resets")));

		String[] openedRoomsText = propertiesFile.getProperty("openedRooms").split(",");
		openedRooms = new int[openedRoomsText.length];
		for(int i=0;i<openedRoomsText.length;i++) {
			openedRooms[i]=Integer.parseInt(openedRoomsText[i]);
		}
		collectibles = propertiesFile.getProperty("collectibles").split(",");
		
		
	}
	
	private Properties loadProperties(String path) {
		Properties file=new Properties();
		try {
			file.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			System.out.println("save file at '"+path+"' not found");
		} catch (IOException e) {
			System.out.println("uh oh something bad happened");
			e.printStackTrace();
		}
		return file;
	}
	public void writeproperties(Properties newProperties) {
		//this method rewrites the settings file to a new one that is given to it
		try {
			newProperties.store(new FileWriter(filePath), 
					"this file holds all the save data");
		} catch (IOException e) {
			System.out.println("couldnt update proprties file for save data");
			e.printStackTrace();
		}
		//updates everything to all the new settings
		loadFile(filePath);
	}
	public void saveOpenedRooms(ArrayList<Integer> openedRooms) {
		String val="";
		for(int i:openedRooms) {
			val+=i+",";
		}
		propertiesFile.setProperty("openedRooms",val);
		writeproperties(propertiesFile);
	} 
	public void saveGameTime() {
		String val="";
		propertiesFile.setProperty("gametime",""+Timer.getFrames());
		writeproperties(propertiesFile);
	} 

	public void addCollectible(String roomName) {
		String val="";
		for(String i:collectibles) {
			val+=i+",";
		}
		val+=roomName;
		propertiesFile.setProperty("collectibles",val);
		writeproperties(propertiesFile);
	}
	public void savePlayerData(int x, int y) {
		System.out.println("saving");
		long timefromSave = System.currentTimeMillis()-timeOfSave;
		timeOfSave=System.currentTimeMillis();
		propertiesFile.setProperty("realtime",""+(playRealTime+timefromSave));
		System.out.println(Timer.getTimeString());
		propertiesFile.setProperty("gametime",""+Timer.getFrames());
		propertiesFile.setProperty("started",""+true);
		propertiesFile.setProperty("startX",""+x);
		propertiesFile.setProperty("startY",""+y);
		writeproperties(propertiesFile);
	}
	
	public void unlockDoubleJump(Player player) {
		propertiesFile.setProperty("doubleJump",""+true);
		savePlayerData(player.getBounds().x, player.getBounds().y);
	}
	public void unlockSlide(Player player) {
		propertiesFile.setProperty("slide",""+true);
		savePlayerData(player.getBounds().x, player.getBounds().y);
	}
	
	public void unlockGrapple(Player player) {
		propertiesFile.setProperty("grapple",""+true);
		savePlayerData(player.getBounds().x, player.getBounds().y);
	}
	public void unlockUpBoost(Player player) {
		propertiesFile.setProperty("upBoost",""+true);
		savePlayerData(player.getBounds().x, player.getBounds().y);
	}
	public void unlockBinoculars(Player player) {
		propertiesFile.setProperty("binoculars",""+true);
		savePlayerData(player.getBounds().x, player.getBounds().y);
	}
	public void resetSave() {
		if(!Timer.TASPlayback) { Timer.resets++; }
		Timer.setFrames(0);
		Properties blankSave = loadProperties("res/saves/blankSave.properties");
		blankSave.setProperty("resets",""+Timer.resets);
		writeproperties(blankSave);
	}
	
	public boolean isStarted() {
		return started;
	}
	public boolean hasSlide() {
		return slide;
	}
	public boolean hasDoubleJump() {
		return doubleJump;
	}
	public boolean hasGrapple() {
		return grapple;
	}
	public boolean hasUpBoost() {
		return upBoost;
	}
	public boolean hasBinoculars() {
		return binoculars;
	}
	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public int[] getOpenedRooms() {
		return openedRooms;
	}
	public String[] getCollectibles() {
		return collectibles;
	}
	public long getPlayRealTime() {
		return playRealTime;
	}
	public void startTimer() {
		timeOfSave = System.currentTimeMillis();
	}
	

		
	
}
