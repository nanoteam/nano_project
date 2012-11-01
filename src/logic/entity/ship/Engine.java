package logic.entity.ship;

import logic.Level;

import logic.entity.EmmiterEffects;
import logic.entity.GameObjectMoving;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import physic.Material;
import physic.PhysicObject;

import render.RenderUtil;

public class Engine extends GameObjectMoving {

	private final static float SPEED_PARTICLE_FROM_ENGINE = 200f;
	private final static float SPEED_PARTICLE_KOOF_RANDOM = 20f;
	private final static float ENGINE_TURN_VELOCITY = 10f;
	private final static float ENGINE_FORCE = 15;
	private float height = 20f;
	private float width = 10f;

	private Joint engineJoint;

	public Engine(Level level, Vector2f position) {
		this.level = level;
		this.position = position;
		init();
	}

	@Override
	public void init() {
		physicObject = PhysicObject.createBox(this, position, width, height,
				Material.Metal);
		physicObject.getBody().setAngularDamping(500);
	}

	@Override
	public void update() {

	}

	@Override
	public void move() {
		position = physicObject.getPosition();
		angle = physicObject.getAngle();
	}

	@Override
	public void draw() {
		RenderUtil.drawQaud(position.x, position.y, width, height, angle,
				(Color) Color.BLUE);
	}

	@Override
	public void playSound() {

	}

	@Override
	public void destroy() {

	}

	void enableForce() {
		float forceX = (float) (-ENGINE_FORCE * Math.sin(angle));
		float forceY = (float) (ENGINE_FORCE * Math.cos(angle));
		// Vec2 force = new Vec2((float) (-ENGINE_FORCE * Math.sin(angle)),
		// (float) (ENGINE_FORCE * Math.cos(angle)));
		// // Vec2 pointOfForce = body.getPosition().add(
		// // new Vec2((float) (width / 30 / 2 * Math.sin(angle)),
		// // (float) (-height / 30 / 2 * Math.cos(angle))));
		// Vec2 pointOfForce = new Vec2(position.x / 30f, position.y / 30f);
		physicObject.applyForce(forceX, forceY, position);

		EmmiterEffects.drawParticlesFromEngine(position, angle);
	}

	void setEngineJoint(Joint joint) {
		this.engineJoint = joint;
	}

	Joint getEngineJoint() {
		return engineJoint;
	}

	public float getAngle() {
		return angle;
	}
    public void turnOnAngle(float dAngle){
        physicObject.setAngle(angle+=dAngle);
        //angle+=dAngle;
    }

}
