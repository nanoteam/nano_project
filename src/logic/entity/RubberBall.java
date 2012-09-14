package logic.entity;

import logic.Level;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class RubberBall extends GameObjectPhysicMoving {
	private float maxSpeed;
	private int lifeTime;
	private float size;
	private Color color;
	private float width;
	private float height;

	public RubberBall(Vector2f pos, float angle, float randTrajectory,
			Level level) {

		this.maxSpeed = 7;
		this.position = new Vector2f(pos.x, pos.y + 30);

		this.speed = new Vector2f((float) (maxSpeed * Math.cos(angle)
				+ randTrajectory * Math.random() - randTrajectory / 2f),
				(float) (maxSpeed * Math.sin(angle) + randTrajectory
						* Math.random() - randTrajectory / 2f));

		this.size = 5f;
		this.lifeTime = 500 + random.nextInt(100);
		this.angle = angle;
		this.color = new Color(Color.GREEN);
		this.level = level;

		width = 10f;
		height = 10f;

		BodyDef shipDef = new BodyDef();
		shipDef.position.set(new Vec2(position.x / 30, position.y / 30));
		shipDef.type = BodyType.DYNAMIC;
		PolygonShape shipShape = new PolygonShape();

		shipShape.setAsBox(width / 30 / 2, height / 30 / 2);
		// shipShape.m_radius = size/30;
		this.body = level.getWorld().createBody(shipDef);
		this.body.m_userData = this;
		FixtureDef shipFixture = new FixtureDef();
		shipFixture.friction = 10f;
		shipFixture.density = 0.2f;
		shipFixture.restitution = 0.15f;
		shipFixture.shape = shipShape;
		body.createFixture(shipFixture);
		// body.setAngularDamping(1);

		body.setLinearVelocity(new Vec2(speed.x, speed.y));
		body.setAngularVelocity((float) (random.nextFloat() * 0.5 - 1) * 10);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		/*
		 * if (random.nextInt(32) > 24) { level.getNotAddedGameObjects().add(
		 * new Particle(new Vector2f(position), new Vector2f((random
		 * .nextFloat() - 0.5f) * 100f + speed.x, (random .nextFloat() - 0.5f) *
		 * 100f + speed.y), 1, 60, (Color) Color.YELLOW)); }
		 */
		lifeTime--;
		if (lifeTime < 0) {
			live = false;
			body.setActive(false);
			/*
			 * level.getNotAddedGameObjects().add( new PlazmaBall(new
			 * Vector2f(position), (float) (random .nextInt(360)), 0, level));
			 * 
			 * level.getNotAddedGameObjects().add( new PlazmaBall(new
			 * Vector2f(position), (float) (random .nextInt(360)), 0, level));
			 */
			for (int i = 0; i < 10; i++) {
				level.getNotAddedGameObjects()
						.add(new Particle(new Vector2f(position), new Vector2f(
								(random.nextFloat() - 0.5f) * 25f + speed.x,
								(random.nextFloat() - 0.5f) * 25f + speed.y),
								3, 30 + random.nextInt(20), (Color) Color.GREEN));
			}
		}
	}

	@Override
	public void move() {
	}

	@Override
	public void draw() {
		position = new Vector2f(body.getPosition().x * 30,
				body.getPosition().y * 30);
		angle = body.getAngle();
		// RenderUtil.drawPlot(position, size, color);
		// RenderUtil.drawLine();
		RenderUtil
				.drawQaud(position.x, position.y, width, height, angle, color);
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
