/**
 *    
 * This abstract class need extend to writing your entity class.
 * Special for entity: particle, sound effect, render effect.
 * Entity don't have physic mass, but can have hard moving traectory.  
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic.entity;

import org.lwjgl.util.vector.Vector2f;

//TODO add modificator visiable to protected code to field and method
abstract public class GameObjectSimpleMoving extends GameObject {
	Vector2f speed;
	boolean isCollision = false;

	@Override
	abstract public void init();

	@Override
	abstract public void update();

	@Override
	abstract public void move();

	@Override
	abstract public void draw();

	@Override
	abstract public void playSound();

	public Vector2f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2f speed) {
		this.speed = speed;
	}

	public boolean isCollision() {
		return isCollision;
	}

	public void setCollision(boolean isCollision) {
		this.isCollision = isCollision;
	}
}
