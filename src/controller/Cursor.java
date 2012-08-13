package controller;

import org.lwjgl.util.vector.Vector2f;


//TODO in future may be add deque with coordinats mouse position
public class Cursor {

	private Vector2f position;
	//corsor havent got speed(i think), but has list(deque) of prevois position
	//but speed = (last position - new position) / time_one_step;
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
