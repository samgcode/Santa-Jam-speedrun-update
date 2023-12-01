package santaJam.saves;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import santaJam.SantaJam;
import santaJam.components.Timer;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;

public class Settings {
	private Properties propertiesFile=new Properties();
	private String filePath="res/saves/settings.properties";
	
	
	private int[] keyBinds;
	private int music, sounds;
	private boolean speedrunEnabled;
	
	public Settings() {
		loadFile(filePath);
	}
	public Settings(String path) {
		this.filePath=path;
		loadFile(filePath);
	}
	public void loadFile(String filePath) {
		propertiesFile=loadProperties(filePath);
		
		//setting all the variables from the properties file
		String[] keysText = propertiesFile.getProperty("keyBinds").split(",");
		keyBinds = new int[keysText.length];
		for(int i=0;i<keysText.length;i++) {
			keyBinds[i]=Integer.parseInt(keysText[i]);
		}
		
		music=Integer.parseInt(propertiesFile.getProperty("music"));
		sounds=Integer.parseInt(propertiesFile.getProperty("sounds"));
		speedrunEnabled=Boolean.valueOf(propertiesFile.getProperty("speedrun"));
		Timer.TASPlayback=Boolean.valueOf(propertiesFile.getProperty("tas"));
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
	
	public void resetSettings() {
		Properties blankSave = loadProperties("res/saves/defaultSettings.properties");
		writeproperties(blankSave);
		Inputs.setKeyBinds(keyBinds);
		SantaJam.getGame().getMusic().applyVolume();
	}
	
	public int[] getKeyBinds() {
		return keyBinds;
	}
	public void setKeyBinds(int[] keyBinds) {
		String val="";
		for(int i:keyBinds) {
			val+=i+",";
		}
		propertiesFile.setProperty("keyBinds",val);
		writeproperties(propertiesFile);
		
	} 
	public int getMusic() {
		return music;
	}
	public void setMusic(int music) {
		propertiesFile.setProperty("music",music+"");
		writeproperties(propertiesFile);
		SantaJam.getGame().getMusic().applyVolume();
	}
	public int getSounds() {
		return sounds;
	}
	public boolean getSpeedrunEnabled() {
		return speedrunEnabled;
	}
	
	public void toggleSpeedrun() {
		speedrunEnabled = !speedrunEnabled;
		propertiesFile.setProperty("speedrun",speedrunEnabled+"");
		writeproperties(propertiesFile);
	}

	public void setSounds(int sounds) {
		propertiesFile.setProperty("sounds",sounds+"");
		writeproperties(propertiesFile);
	}
}
