package main;

import java.util.Random;

public class Global {
    //Graphic
    public static int DEFAULT_RESOLUTION_X = 640;
    public static int DEFAULT_RESOLUTION_Y = 480;
    public static boolean FULLSCREEN = true;
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
    
    public static boolean shipHaveAllWeapon = true;
}
