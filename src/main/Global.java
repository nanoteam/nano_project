package main;

import java.util.Random;

public class Global {
    //Graphic
    public static int RESOLUTION_X = 800;
    public static int RESOLUTION_Y = 600;
    public static boolean FULLSCREEN = false;
    //fps render
    public static int FPS = 60;
    //for jbox2d
    public static float DT = 1f / (FPS/2f);
    //working entity by ai and command
    //if false, player send low level action to Entity
    public static boolean commandOn = true;
    public static Random random = new Random();
    public static boolean realTime = true;
    public static boolean studyOn = false;
}
