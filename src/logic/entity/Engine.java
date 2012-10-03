package logic.entity;

import logic.Level;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class Engine extends GameObjectPhysicMoving {

	private float height = 20f;
	private float width = 10f;
	private final static float ENGINE_FORCE = 45;
	private Joint engineJoint;

	public Engine(Level level, Vector2f position) {
		this.level = level;
		this.position = position;
		init();
	}

	@Override
	public void init() {

		BodyDef engineDef = new BodyDef();
		engineDef.position.set(new Vec2(position.x / 30, position.y / 30));
		engineDef.type = BodyType.DYNAMIC;
		PolygonShape engineShape = new PolygonShape();
		engineShape.setAsBox(width / 30 / 2, height / 30 / 2);
		this.body = level.getWorld().createBody(engineDef);
		this.body.m_userData = this;

		FixtureDef engineFixture = new FixtureDef();
		engineFixture.friction = 0.2f; // trenie
		engineFixture.density = 0.1f; // plotnost'
		engineFixture.restitution = 0.15f;
		engineFixture.shape = engineShape;
		body.createFixture(engineFixture);
		body.setAngularDamping(50);
	}

	@Override
	public void update() {

	}

	@Override
	public void move() {
		this.position = new Vector2f(body.getPosition().x * 30,
				body.getPosition().y * 30);
		this.angle = body.getAngle();
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
		angle = body.getAngle();
		Vec2 force = new Vec2((float) (-ENGINE_FORCE * Math.sin(angle)),
				(float) (ENGINE_FORCE * Math.cos(angle)));
//		Vec2 pointOfForce = body.getPosition().add(
//				new Vec2((float) (width / 30 / 2 * Math.sin(angle)),
//						(float) (-height / 30 / 2 * Math.cos(angle))));
		Vec2 pointOfForce = new Vec2(position.x/30f,position.y/30f);
		body.applyForce(force, pointOfForce);

		EmmiterEffects.drawParticlesFromEngine(new Vector2f(
				pointOfForce.x * 30, pointOfForce.y * 30), angle);
	}

	void setEngineJoint(Joint joint) {
		this.engineJoint = joint;
	}

	Joint getEngineJoint() {
		return engineJoint;
	}

}
