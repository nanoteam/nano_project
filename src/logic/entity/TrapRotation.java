package logic.entity;

import logic.Level;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.MouseJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class TrapRotation extends GameObjectMoving {

	private static final int TRAP_TIME = 60;

	// private float angle;
	private float speed;
	private float lenght;
	private boolean isActivated = false;
	private int trapTime;
	private Joint join;
	private RevoluteJointDef joinDef;

	public TrapRotation(Level level, Vector2f position) {
		this.position = position;
		this.level = level;
		this.lenght = 200f;
		this.angle = 0f;
		this.speed = 0;
        physicObject = PhysicObject.createBox(this, position, lenght, lenght,
                Material.Stone);
        physicObject.getBody().setFixedRotation(true);
        physicObject.getBody().setAngularVelocity(1f);
	}

	@Override
	public void update() {

		// if (isActivated) {
		// trapTime--;
		// if (trapTime < 0) {
		// isActivated = false;
		// level.getWorld().destroyJoint(join);
		// }
		// }
	}

	@Override
	public void move() {
		position = physicObject.getPosition();
		angle = physicObject.getAngle();
	}

	@Override
	public void draw() {

		RenderUtil.drawLine(new Vector2f((float) (position.x - (lenght - 2)
				* Math.cos(angle) / 2), (float) (position.y - (lenght - 2)
				* Math.sin(angle) / 2)), new Vector2f(
				(float) (position.x + (lenght - 2) * Math.cos(angle) / 2),
				(float) (position.y + (lenght - 2) * Math.sin(angle) / 2)), 3f,
				(Color) Color.GREY);
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void activateTrap(Body bodyB, Vec2 point) {
		joinDef = new RevoluteJointDef();
		joinDef.initialize(physicObject.getBody(), bodyB, point);
		join = level.getWorld().createJoint(joinDef);
		// isActivated = true;
		trapTime = TRAP_TIME;
		System.out.println("trap is activated at the point " + point);
	}

}
