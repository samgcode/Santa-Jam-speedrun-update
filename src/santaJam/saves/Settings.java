package santaJam.saves;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import santaJam.inputs.Inputs;

public class Settings {
	private Properties propertiesFile=new Properties();
	private String filePath="res/saves/settings.properties";
	
	
	private int[] keyBinds;
	
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
	
}
