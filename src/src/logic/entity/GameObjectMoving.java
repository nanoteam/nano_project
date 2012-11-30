/**
 *    
 * This abstract class need extend to writing your entity class.
 * Special for entity: phys body, ship, bullet.
 * Entity have physic part.  
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 * @data 23.07.2012
 */
package logic.entity;

import org.lwjgl.util.vector.Vector2f;

import physic.PhysicObject;

//TODO add modificator visiable to protected code to field and method
abstract public class GameObjectMoving extends GameObject {
	protected float mass;
	protected float angle;
	protected Vector2f speed;

	protected PhysicObject physicObject;

	@Override
	abstract public void update();

	@Override
	abstract public void move();

	@Override
	abstract public void draw();

	@Override
	abstract public void playSound();

    @Override
    abstract public void toThink();

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getAngle() {
		return angle;
	}

	public PhysicObject getPhysicObject() {
		return physicObject;
	}

    public Vector2f getSpeed(){
        return speed;
    }
    public float getAngularVelocity(){
        return physicObject.getAngularVelocity();
    }




}
