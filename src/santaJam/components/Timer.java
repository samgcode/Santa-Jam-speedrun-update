package santaJam.components;

import santaJam.Game;

public class Timer {
  static int frames = 0;
  public static boolean TASPlayback; 
  public static int resets;

  public static void update() {
    frames++;
  }

  public static int getFrames() {
    return frames;
  }
  public static void setFrames(int newFrames) {
    frames = newFrames;
  }

  public static String getTimeString() {
    Game.getFps();
    double seconds = (double)frames/(double)Game.getFps();
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
