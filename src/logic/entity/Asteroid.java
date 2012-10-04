package logic.entity;

import java.util.Random;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

import logic.Level;

public class Asteroid extends GameObjectPhysicMoving {

	private float radius;
	private int MAX_SPEED = 30;

	public Asteroid(Level level, Vector2f position) {
		this.position = position;
		this.level = level;
		this.radius = 30;
		this.angle = 0f;
		init();

	}

	@Override
	public void init() {
		this.random = new Random();
		this.speed = new Vector2f((float) MAX_SPEED
				- random.nextInt(MAX_SPEED * 2), (float) MAX_SPEED
				- random.nextInt(MAX_SPEED * 2));

		BodyDef asteroidDef = new BodyDef();
		asteroidDef.position.set(new Vec2(position.x / 30f, position.y / 30f));
		asteroidDef.type = BodyType.DYNAMIC;
		PolygonShape asteroidShape = new PolygonShape();
		asteroidShape.setAsBox(radius / 30f, radius / 30f);
		this.body = level.getWorld().createBody(asteroidDef);
		this.body.m_userData = this;

		FixtureDef asteroidFixture = new FixtureDef();
		asteroidFixture.friction = 0.7f; // trenie

		asteroidFixture.density = 50; // plotnost'
		asteroidFixture.restitution = 1f;
		asteroidFixture.shape = asteroidShape;
		// asteroids do not interact asteroids with each other
		// asteroidFixture.filter.groupIndex = -1;
		body.createFixture(asteroidFixture);
		//body.setAngularVelocity(50);

		body.setLinearVelocity(new Vec2(speed.x / 30, speed.y / 30));
	}

	@Override
	public void update() {

	}

	@Override
	public void move() {
		this.position = new Vector2f(body.getPosition().x * 30f,
				body.getPosition().y * 30f);
		this.angle = body.getAngle();
		body.applyForce(new Vec2(0,-level.getGravity() * body.getMass()),
				body.getWorldCenter());

	}

	@Override
	public void draw() {
		
		RenderUtil.drawQaud(position.x, position.y, radius * 2, radius * 2,
				angle, (Color) Color.PURPLE);
	}

	@Override
	public void playSound() {

	}

	@Override
	public void destroy() {
		level.getWorld().destroyBody(body);
	}

}
