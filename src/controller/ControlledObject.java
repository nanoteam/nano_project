package controller;

public interface ControlledObject {
	public void doAction(int code);
	
	public static final int LEFT_ENGINE_ACTIVE = 1;
	public static final int RIGHT_ENGINE_ACTIVE = 2;
	public static final int All_ENGINE_ACTIVE = 3;
	
}
