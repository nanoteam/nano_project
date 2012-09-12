package controller;

import org.lwjgl.util.vector.Vector2f;

public interface ControlledObject {
	public void doAction(int code);
	public void clearFlags();
	
	public Vector2f getPosition();
	
	public static final int LEFT_ENGINE_ACTIVE = 1;
	public static final int RIGHT_ENGINE_ACTIVE = 2;
	public static final int All_ENGINE_ACTIVE = 3;
	public static final int FIRE_FIRST_WEAPON = 4;
	public static final int FIRE_SECOND_WEAPON = 5;
	
	
}
