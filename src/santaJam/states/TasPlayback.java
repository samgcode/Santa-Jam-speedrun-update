package santaJam.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Comparator;

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
  }};

  ArrayList<Action> actions = new ArrayList<Action>();

  public TasPlayback() {
    loadTasFile("tas.txt", 0);

    actions.sort(new CompareAction());

    System.out.println("Loaded TAS input: ");
    for (int i = 0; i < actions.size(); i++) {
      Action action = actions.get(i);
      if(action.bind.equals("fps")) {
        System.out.println(action.frame + ": set fps, " + action.payload);
      } else {
        System.out.println(action.frame + ": " + action.bind + " " + action.pressed);
      }
    }

    System.out.println("-- TAS START --");
  }

  public int loadTasFile(String filepath, int currentFrame) {
    int globalFrame = currentFrame;
    int lineNumber = 0;
    try {
      File myObj = new File("res/saves/" + filepath);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        
        String[] line = data.split(" ");
        lineNumber++;
        
        if(!line[0].equals("//")) {
          String action = line[0];
          if(action.equals("file")) {
            globalFrame = loadTasFile(line[1], globalFrame);
          }  else {
            int payload = Integer.parseInt(line[1]);
            
            if(action.equals("wait")) {
              globalFrame += payload;
            } else {
              actions.add(new Action(
                globalFrame, action, (payload==1), payload, filepath, lineNumber
              ));
              globalFrame++;
            }
          }
        }
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Could not read file");
      e.printStackTrace();
    }
    return globalFrame;
  }
  
  public void update() {
    int frames = Timer.getFrames();
    for (int i = 0; i < actions.size(); i++) {
      Action action = actions.get(i);
      
      if(action.frame == frames) {
        if(action.bind.equals("fps")) {
          // System.out.println(action.frame + ": set fps, " + action.payload);
          System.out.println(action.frame + "| " + action.file + "::" + action.lineNumber + ": " + ": set fps, " + action.payload);
          Game.setFps(action.payload);
        } else {
          // System.out.println(action.frame + ": " + action.bind + ", " + action.pressed);
          System.out.println(action.frame + "| " + action.file + "::" + action.lineNumber + ": " + action.bind + ", " + action.pressed);
          if(action.pressed) { Inputs.simulateKeyPress(keys.get(action.bind)); }
          else { Inputs.simulateKeyRelease(keys.get(action.bind)); }
        }
        actions.remove(i);
      }
    }
  }

  private class Action {
    public int frame;
    public String bind;
    public int payload;
    public boolean pressed;
    public String file;
    public int lineNumber;
  
    public Action(int frame, String bind, boolean pressed, int payload, String file, int lineNumber) {
      this.frame = frame;
      this.bind = bind;
      this.pressed = pressed;
      this.payload = payload;
      this.file = file;
      this.lineNumber = lineNumber;
    }
  }

  private class CompareAction implements Comparator<Action> {
    @Override
    public int compare(Action o1, Action o2) {
      if(o1.frame > o2.frame) return 1;
      if(o1.frame < o2.frame) return -1;
      return 0;
    }
  }
}
