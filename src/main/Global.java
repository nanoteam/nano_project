package main;

import java.util.Random;

public class Global {

    public static int RESOLUTION_X = 800;
    public static int RESOLUTION_Y = 450;
    public static boolean FULLSCREEN = false;
    public static boolean CONTROLED_AI = true;
    public static int FPS = 60;
    public static float DT = 1f / (FPS/2f);
    public static Random random = new Random();
}
