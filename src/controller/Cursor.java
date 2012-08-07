package controller;

import org.lwjgl.util.vector.Vector2f;

public class Cursor {

	private Vector2f position;
	private Vector2f mouseSpeed;

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getSpeed() {
		return new Vector2f();
	}

	public void setPosition(Vector2f pos) {
		this.position = pos;

	}

	public void setSpeed(Vector2f mSpeed) {
		this.mouseSpeed = mSpeed;
	}
}
