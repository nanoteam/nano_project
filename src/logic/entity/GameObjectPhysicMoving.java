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

import physic.PhysicObject;

//TODO add modificator visiable to protected code to field and method
abstract public class GameObjectPhysicMoving extends GameObjectSimpleMoving {
	float mass;
	// w - omega, speed rotate, in radian
	float w;
	// angle of position, in radian. pi, pi/2, -3*pi/2
	float angle;
	// inertia, this is importent, but need manual setting
	float I;
	
	

	PhysicObject physicObject;

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

	public float getAlfa() {
		return angle;
	}

	public void setAlfa(float alfa) {
		this.angle = alfa;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getI() {
		return I;
	}

	public void setI(float i) {
		I = i;
	}

	public float getAngle() {
		return angle;
	}
	

}
