package santaJam.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Comparator;

import santaJam.Game;
import santaJam.components.Timer;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;

public class TasPlayback {
  String playbackFile = "run.tas";
  String recordingFile = "recording.tas";

  HashMap<String, Keybind> keys = new HashMap<String, Keybind>() {{
    put("up", Keybind.UP);
    put("down", Keybind.DOWN);
    put("left", Keybind.LEFT);
    put("right", Keybind.RIGHT);
    put("jump", Keybind.JUMP);
    put("grapple", Keybind.GRAPPLE);
  }};

  ArrayList<Action> actions = new ArrayList<Action>();

  int waitTime = 0;
  int inputsProcessed = 0;
  
  public void initPlayback() {
    loadTasFile(playbackFile, 0, "res/saves");
  
    actions.sort(new CompareAction());
  
    System.out.println("Loaded TAS input: ");
    for (int i = 0; i < actions.size(); i++) {
      Action action = actions.get(i);
      action.print();
    }
  
    System.out.println("\n\n\n\n-- TAS START --");
  }

  public int loadTasFile(String filepath, int currentFrame, String stackTrace) {
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
            globalFrame = loadTasFile(line[1], globalFrame,
            stackTrace + "/" + (extractFileName(filepath)) + "::" + lineNumber
            );
          }  else {
            int payload = Integer.parseInt(line[1]);
            
            if(action.equals("wait")) {
              globalFrame += payload;
            } else {
              actions.add(new Action(
                globalFrame, action, (payload==1), payload, 
                stackTrace + "/" + (extractFileName(filepath)) + "::" + lineNumber
              ));
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

  String extractFileName(String path) {
    String[] line = path.split("/");
    return line[line.length-1];
  }
  
  public void update() {
    int frames = Timer.getFrames();

    for (int i = 0; i < actions.size(); i++) {
      Action action = actions.get(i);

      if(action.frame == frames) {
        action.print();
        if(action.bind.equals("fps")) {
          Game.setFps(action.payload);
        } else {
          if(action.pressed) { Inputs.simulateKeyPress(keys.get(action.bind)); }
          else { Inputs.simulateKeyRelease(keys.get(action.bind)); }
        }
      }
    }
  }

  public void initRecording() {
    for (String inputName : keys.keySet()) {
      if(Inputs.getKey(keys.get(inputName)).isHeld()) {
        addAction(inputName, 1);
      }
    }
  }

  public void updateRecord() {    
    for (String inputName : keys.keySet()) {
      if(Inputs.getKey(keys.get(inputName)).isPressed()) {
        if(waitTime != 0) {
          addAction("wait", waitTime);
        }
        addAction(inputName, 1);
        waitTime = 0;
      } else if(Inputs.getKey(keys.get(inputName)).isReleased()) {
        if(waitTime != 0) {
          addAction("wait", waitTime);
        }
        addAction(inputName, 0);
        waitTime = 0;
      }
    }
    waitTime++;
  }

  public void addAction(String bind, int payload) {
    actions.add(new Action(
      Timer.getFrames(), bind, (payload==1), payload, 
      "run.tas::" + actions.size()
    ));
    inputsProcessed++;
  }

  public void saveInputs() {
    try {
      FileWriter file = new FileWriter("res/saves/" + recordingFile);

      for (int i = 0; i < actions.size(); i++) {
        Action action = actions.get(i);
        file.write(action.bind + " " + action.payload + "\n");
      }
      
      file.close();
    } catch (IOException e) {
      System.out.println("Could not write to file");
      e.printStackTrace();
    }
  }

  private class Action {
    public int frame;
    public String bind;
    public int payload;
    public boolean pressed;
    public String file;
  
    public Action(int frame, String bind, boolean pressed, int payload, String file) {
      this.frame = frame;
      this.bind = bind;
      this.pressed = pressed;
      this.payload = payload;
      this.file = file;
    }

    public void print() {
      if(bind.equals("fps")) {
        System.out.println(frame + "| " + file + ":  " + "set fps, " + payload);
      } else {
        System.out.println(frame + "| " + file + ":  " + bind + ", " + pressed);
      }
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
