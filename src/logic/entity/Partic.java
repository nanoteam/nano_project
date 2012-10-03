package logic.entity;

import logic.Level;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class Partic extends GameObjectPhysicMoving {

	public Partic(Level level, Vector2f pos, Vector2f speed) {
		this.level = level;
		this.position = pos;
		this.speed = speed;
		this.liveHealth = 1000;
		init();
	}

	@Override
	public void init() {

		BodyDef particDef = new BodyDef();
		particDef.position.set(new Vec2(position.x / 30, position.y / 30));
		particDef.type = BodyType.DYNAMIC;
		particDef.linearVelocity = new Vec2(speed.x / 30, speed.y / 30);
		PolygonShape particDefShape = new PolygonShape();
		particDefShape.setAsBox(1 / 30f, 1 / 30f);
		this.body = level.getWorld().createBody(particDef);
		this.body.m_userData = this;
		FixtureDef particDefFixture = new FixtureDef();
		particDefFixture.friction = 0f; // trenie
		particDefFixture.density = 0.001f; // plotnost'
		particDefFixture.restitution = 0f;
		particDefFixture.shape = particDefShape;
		body.createFixture(particDefFixture);
		// for more realistic
		// body.setBullet(true);

	}

	@Override
	public void update() {
		liveHealth--;
		if (liveHealth < 0)
			live = false;
	}

	@Override
	public void move() {
		this.position = new Vector2f(body.getPosition().x * 30,
				body.getPosition().y * 30);
	}

	@Override
	public void draw() {
		RenderUtil.drawPlot(position, 2f, (Color) Color.YELLOW);
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		level.getWorld().destroyBody(body);

	}

}
