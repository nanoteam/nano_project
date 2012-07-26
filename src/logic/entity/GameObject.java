/**
 *    
 * Basic abstract class for game entity, need extend to writing your entity class.
 * Special for entity: sound effect, render effect,  
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic.entity;

import java.util.Random;
import org.lwjgl.util.vector.Vector2f;

import render.RenderObject;

//TODO add modificator visiable to protected code to field and method
abstract public class GameObject {
	static Random random = new Random();
	Vector2f position;
	boolean live = true;
	RenderObject renderObject;

	abstract public void init();

	abstract public void update();

	abstract public void move();

	abstract public void draw();

	abstract public void playSound();

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
}
