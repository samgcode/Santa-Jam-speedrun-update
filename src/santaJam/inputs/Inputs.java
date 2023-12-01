package santaJam.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import santaJam.SantaJam;

public class Inputs implements KeyListener{
	
	private static InputButton left = new InputButton(0), right = new InputButton(0),up = new InputButton(0),down = new InputButton(0), 
	jump = new InputButton(0), grapple = new InputButton(0), pause = new InputButton(0), savestate = new InputButton(0), reset = new InputButton(0);
	
	private static int[] keyCodes = new int[Keybind.values().length];
	private static boolean[] keyStates = new boolean[Keybind.values().length];
	private static InputButton[] inputButtons = new InputButton[Keybind.values().length];

	private static boolean keyPressed=false;
	private static int lastKeyCode=-1;
	private static InputButton anyKey = new InputButton(0);

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {	
		keyPressed=true;
		lastKeyCode=e.getKeyCode();
		for (Keybind keybind : Keybind.values()) {
			if(e.getKeyCode() == keyCodes[keybind.index]) {
				keyStates[keybind.index] = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed=false;
		for (Keybind keybind : Keybind.values()) {
			if(e.getKeyCode() == keyCodes[keybind.index]) {
				keyStates[keybind.index] = false;
			}
		}
	}

	public static void update() {
		anyKey.update(keyPressed);
		for (Keybind keybind : Keybind.values()) {
			inputButtons[keybind.index].update(keyStates[keybind.index]);
		}
	}
	public static void setKeyBinds(int[] newKeyCodes) {
		keyCodes=newKeyCodes;
		for (Keybind keybind : Keybind.values()) {
			inputButtons[keybind.index] = new InputButton(newKeyCodes[keybind.index]);
		}
		
		if(SantaJam.getGame()!=null) {
			SantaJam.getGame().getSettings().setKeyBinds(newKeyCodes);
		}
	}
	
	public static InputButton AnyKey() {
		return anyKey;
	}
	public static int getLastKeyCode() {
		return lastKeyCode;
	}
	public static InputButton getKey(Keybind keybind) {
		return inputButtons[keybind.index];
	}

	public static void simulateKeyPress(Keybind keybind) {
		keyPressed=true;
		keyStates[keybind.index] = true;
	}
	public static void simulateKeyRelease(Keybind keybind) {
		keyPressed=false;
		keyStates[keybind.index] = false;
	}
}
