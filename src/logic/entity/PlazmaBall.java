package logic.entity;

import logic.Level;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class PlazmaBall extends GameObjectPhysicMoving {
	// constant
	private float maxSpeed;
	private int lifeTime;
	private float size;
	private Color color;

	// cool if float randTrajectory
	// warning! i use link on variable.
	public PlazmaBall(Vector2f pos, float angle, float randTrajectory,
			Level level) {
		this.maxSpeed = 200;
		this.position = new Vector2f(pos);
		this.speed = new Vector2f((float) (maxSpeed * Math.cos(angle / 60f)
				+ randTrajectory * Math.random() - randTrajectory / 2f),
				(float) (maxSpeed * Math.sin(angle / 60f) + randTrajectory
						* Math.random() - randTrajectory / 2f));
		this.size = 8;
		this.lifeTime = 120;
		this.angle = angle;
		this.color = new Color(Color.YELLOW);
		this.level = level;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (random.nextInt(32) > 24) {
			level.getNotAddedGameObjects().add(
					new Particle(new Vector2f(position), new Vector2f((random
							.nextFloat() - 0.5f) * 100f + speed.x, (random
							.nextFloat() - 0.5f) * 100f + speed.y), 1, 60,
							(Color) Color.YELLOW));
		}
		lifeTime--;
		if (lifeTime < 0) {
			live = false;

			/*
			 * level.getNotAddedGameObjects().add( new PlazmaBall(new
			 * Vector2f(position), (float) (random .nextInt(360)), 0, level));
			 * 
			 * level.getNotAddedGameObjects().add( new PlazmaBall(new
			 * Vector2f(position), (float) (random .nextInt(360)), 0, level));
			 */
			for (int i = 0; i < 100; i++) {
				level.getNotAddedGameObjects().add(
						new Particle(new Vector2f(position), new Vector2f(
								(random.nextFloat() - 0.5f) * 100f + speed.x, (random
										.nextFloat() - 0.5f) * 100f + speed.y), 2,
								30 + random.nextInt(20) , (Color) Color.RED));
			}
		}
	}

	@Override
	public void move() {
		position.x += speed.x * 0.016666;
		position.y += speed.y * 0.016666;
	}

	@Override
	public void draw() {
		RenderUtil.drawPlot(position, size, color);
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

}
