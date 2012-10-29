package controller;

import org.lwjgl.util.vector.Vector2f;

public interface ControlledObject {

    //special action
    //firing
	public static final int FIRE_TARGET = 0;
	public static final int FIRE_SHOOT = 1;
	public static final int FIRE_NONE = 2;
	//moving
    public static final int MOVE_TARGET = 10;
    public static final int MOVE_VECTOR = 11;
    public static final int MOVE_NONE = 12;


    public void doAction(int code);
	public void clearFlags();
    public Vector2f getPosition();
}
