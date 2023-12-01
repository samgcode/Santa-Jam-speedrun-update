package santaJam.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import santaJam.Game;
import santaJam.components.Timer;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;

public class TasPlayback {
  HashMap<String, Keybind> keys = new HashMap<String, Keybind>() {{
    put("up", Keybind.UP);
    put("down", Keybind.DOWN);
    put("left", Keybind.LEFT);
    put("right", Keybind.RIGHT);
    put("jump", Keybind.JUMP);
    put("grapple", Keybind.GRAPPLE);
    put("fps", Keybind.TASTEST);
  }};

  ArrayList<Action> actions = new ArrayList<Action>();

  public TasPlayback() {
    try {
      File myObj = new File("res/saves/tas.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        
        String[] line = data.split(" ");

        if(!line[0].equals("//")) {
          actions.add(new Action(
            Integer.parseInt(line[0]),
            keys.get(line[1]),
            Boolean.parseBoolean(line[2])
          ));
        }
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }


  public void update() {
    int frames = Timer.getFrames();
    for (Action action : actions) {
      if(action.frame == frames) {
        if(action.bind == Keybind.TASTEST) {
          Game.setFps(60);
        } else {
          if(action.pressed) { Inputs.simulateKeyPress(action.bind); }
          else { Inputs.simulateKeyRelease(action.bind); }
        }
      }
    }
  }

  private class Action {
    public int frame;
    public Keybind bind;
    public boolean pressed;
  
    public Action(int frame, Keybind bind, boolean pressed) {
      this.frame = frame;
      this.bind = bind;
      this.pressed = pressed;
    }
  }
}


