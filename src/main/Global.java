package main;

import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;

import java.util.Random;

public class Global {
    public static final float SCALE_FOR_PHYSIC_WORLD = 30;
    //Graphic
    public static int DEFAULT_RESOLUTION_X = 640;
    public static int DEFAULT_RESOLUTION_Y = 480;
    public static boolean FULLSCREEN = false;
    //fps render
    public static int FPS = 60;
    //for jbox2d
    public static float DT = 1f / (FPS / 2f);
    //working entity by ai and command
    //if false, player send low level action to Entity
    public static boolean AI_ON = true;
    public static Random random = new Random();
    public static boolean realTime = true;
    public static boolean studyOn = false;
    public static boolean shipHaveAllWeapon = true;

    //this function nead unification for all usage in code, this make code more stable and safe
    //this fucntion convert Vector2d to Vec2, use scale
    public static Vector2f convertToVectro2f(Vec2 vec2) {
        return new Vector2f(vec2.x * SCALE_FOR_PHYSIC_WORLD, vec2.y * SCALE_FOR_PHYSIC_WORLD);
    }

    public static Vec2 convertToVec2(Vector2f vector2f) {
        return new Vec2(vector2f.x / SCALE_FOR_PHYSIC_WORLD, vector2f.y / SCALE_FOR_PHYSIC_WORLD);
    }

}
