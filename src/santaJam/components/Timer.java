package santaJam.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import santaJam.Game;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.maps.Room;
import santaJam.saves.Save;
import santaJam.states.StateManager;

public class Timer {
  static String basePath = "res/splits/";
  static String bestFile = "best.split";
  static String comparisonFile = "comparison.split";
  static String pbFile = "pb.split";
  static String runFile = "run.split";

  static int frames, split, sumOfBest, lastDiff, lastSplitTime, currentDiff;
  static Split lastComparison;
  public static boolean TASPlayback; 
  public static boolean doSplits; 
  public static boolean recordRoute = false; 
  public static int resets;
  static String savedRoom;
  static Save savedSave;

  static ArrayList<Split> best = new ArrayList<Split>();
  static ArrayList<Split> comparison = new ArrayList<Split>();
  static ArrayList<Split> pb = new ArrayList<Split>();
  static ArrayList<Split> run = new ArrayList<Split>();

  public static void start() {
    recordRoute = false; doSplits = true;
    frames = 0; split = 0; sumOfBest = 0;

    basePath = getSplitPath("res/splits/");
 
    best = loadSplitFile(bestFile);
    comparison = loadSplitFile(comparisonFile);
    pb = loadSplitFile(pbFile);
    run = new ArrayList<Split>();

    sumOfBest = calculateSum();

    StateManager.getGameState().setSplitText(
      "00:00:00.000 (+0.0)\n" +
      "sum of best: " + getTimeStringOf(sumOfBest)
    );
  }

  public static void update(Room room, Save saveData) {
    if(frames == 0) {
      lastComparison = new Split(room, saveData, 0);
    }
    if(frames == 1) {
      if(!room.getName().equals("peak-forest end.json")) { doSplits = false; }
    }
    frames++;
    savedRoom = room.getName();
    savedSave = saveData;
    if(doSplits) { updateSplits(); }
  }

  static void updateSplits() {
    if(!lastComparison.matches(savedRoom, savedSave)) {
      lastComparison = new Split(savedRoom, savedSave, frames);
    }

    if(Inputs.getKey(Keybind.RECORD_SPLITS).isPressed()) {
      recordRoute = true;
      best = new ArrayList<Split>();
      run = new ArrayList<Split>();
    }
    if(recordRoute) {
      if(Inputs.getKey(Keybind.SPLIT).isPressed()) {
        Split split;
        if(run.size() >= 1) {
          split = new Split(lastComparison);
          split.setTime(lastComparison.time - best.get(best.size()-1).time);
        } else {
          split = lastComparison;
        }
        System.out.println(split.toString());
        best.add(split);
        run.add(lastComparison);
      }
    } else {
      if(best.size() >= 1 && split < best.size()) {
        Split currentBest = best.get(split);
        Split currentCompare = comparison.get(split);

        currentDiff = frames - currentCompare.time;
        
        if(currentBest.matches(savedRoom, savedSave)) {
          run.add(lastComparison);
          
          int last = 0;
          if(split >= 1) { last = run.get(split-1).time; }
          int sinceLast = frames - last;

          if(sinceLast < currentBest.time) {
            // update times for sum of best
            best.get(split).setTime(sinceLast);
            saveSplitFile(best, bestFile);
            sumOfBest = calculateSum();
          }

          lastSplitTime = getFrames();
          lastDiff = currentDiff;
          split++;
        }
        StateManager.getGameState().setSplitText(getSplitString());
      } 
    }
  }

  static int calculateSum() {
    int sumOfBest = 0;

    for (Split split : best) {
      sumOfBest += split.time;
    }
    return sumOfBest;
  }

  public static void end() {
    System.out.println("end");
    if(doSplits) {
      if(recordRoute) {
        best.add(new Split("END", savedSave, frames - best.get(best.size()-1).time));
        run.add(new Split("END", savedSave, frames));
        saveSplitFile(best, bestFile);
        saveSplitFile(run, pbFile);
        saveSplitFile(run, comparisonFile);
        saveSplitFile(run, runFile);
      } else {
        savedRoom = "END";
        updateSplits();
  
        saveSplitFile(best, bestFile);
        saveSplitFile(run, runFile);
        if(run.get(run.size()-1).time < pb.get(pb.size()-1).time) {
          saveSplitFile(run, pbFile);
        }
      }
    }
  }

  static String getSplitPath(String prefix) {
    ArrayList<Split> splits = new ArrayList<Split>();
    try {
      File myObj = new File("res/splits/splits.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        myReader.close();
        return prefix + data;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Could not read file");
      e.printStackTrace();
    }
    
    return prefix;
  }
  
  static ArrayList<Split> loadSplitFile(String filepath) {
    ArrayList<Split> splits = new ArrayList<Split>();
    try {
      File myObj = new File(basePath + filepath);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if(data.split(" ").length > 1) {
          splits.add(new Split(data));
        }
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Could not read file");
      e.printStackTrace();
    }
    
    return splits;
  }
  
  static void saveSplitFile(ArrayList<Split> splits, String filepath) {
    try {
      FileWriter file = new FileWriter(basePath + filepath);
      
      for (int i = 0; i < splits.size(); i++) {
        Split split = splits.get(i);
        file.write(split.toString() + "\n");
      }
      
      file.close();
    } catch (IOException e) {
      System.out.println("Could not write to file");
      e.printStackTrace();
    }
  }

  public static String getSplitString() {
    return
      getTimeStringOf(lastSplitTime) + "(" + getDifText(lastDiff) + ")\n" +
      "sum of best: " + getTimeStringOf(sumOfBest)
    ;
  }

  public static int getFrames() {
    return frames;
  }
  public static void setFrames(int newFrames) {
    frames = newFrames;
  }

  public static String getTimeString() {
    double seconds = (double)frames/(double)60;
    int hours = (int)Math.floor(seconds/3600.0);
    seconds = seconds - hours * 3600;
    int minutes = (int)Math.floor(seconds/60.0);
    seconds = seconds - minutes * 60;
    int displaySeconds = (int)Math.floor(seconds);
    seconds -= displaySeconds;

    return 
      String.format("%02d",hours) + ":" + 
      String.format("%02d",minutes) + ":" + 
      String.format("%02d",displaySeconds) + "." + 
      String.format("%03d",(int)Math.floor(seconds*1000))  +
      " (" + getDifText(currentDiff) + ")";
  }

  public static String getDifText(int frames) {
    String prefix = "+";
    if(frames < 0) {
      prefix = "-";
      frames *= -1;
    }
    double seconds = (double)frames/(double)60;
    int minutes = (int)Math.floor(seconds/60.0);
    seconds = seconds - minutes * 60;
    int displaySeconds = (int)Math.floor(seconds);
    seconds -= displaySeconds;

    return prefix +
      String.format("%d",minutes) + ":" + 
      String.format("%02d",displaySeconds) + "." + 
      String.format("%03d",(int)Math.floor(seconds*1000));
  }

  public static String getTimeStringOf(int frames) {
    double seconds = (double)frames/(double)60;
    int hours = (int)Math.floor(seconds/3600.0);
    seconds = seconds - hours * 3600;
    int minutes = (int)Math.floor(seconds/60.0);
    seconds = seconds - minutes * 60;
    int displaySeconds = (int)Math.floor(seconds);
    seconds -= displaySeconds;

    return 
      String.format("%02d",hours) + ":" + 
      String.format("%02d",minutes) + ":" + 
      String.format("%02d",displaySeconds) + "." + 
      String.format("%03d",(int)Math.floor(seconds*1000));
  }
}
