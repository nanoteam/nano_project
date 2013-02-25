package logic.entity;

import java.util.Random;

import logic.CollisionListener;
import logic.Level;

import org.jbox2d.common.Vec2;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class EmmiterEffects {
	private static Level level;
	private static Random random;

	public static void init(Level level) {
		EmmiterEffects.level = level;
		random = new Random();
	}

    public static void drawCollision(Vec2[] points, float[] impulses, int type) {
		Vector2f speed;
		Color color = null;
		int j = 0;
		if (points.length == 0)
			return;
		for (Vec2 v : points) {
			for (int i = 0; i < impulses[j]; i++) {
				// will be another random (simple int)
				if (type == CollisionListener.SHIP_WITH_SHIP) {
					color = (Color) Color.GREEN;
				} else if (type == CollisionListener.SHIP_WITH_WALL) {
					// if (random.nextInt(2) == 1)
					// color = (Color) Color.WHITE;
					// else
					// color = (Color) Color.RED;
					color = (Color) Color.GREY;
				}
				speed = new Vector2f(20f - random.nextFloat() * 40,
						20f - random.nextFloat() * 40);
				level.getNotAddedGameObjects().add(
						new Particle(new Vector2f(v.x * 30, v.y * 30), speed,
								2,
								(int) (random.nextFloat() * impulses[j] * 3),
								color));

			}
			j++;
		}

	}

	public static void drawBoom(Vector2f position) {

		float maxSpeed = 150;
		float angleParticle, speedParticle;
		Color color;

		for (int i = 0; i < 100; i++) {
			if (random.nextBoolean())
				color = (Color) Color.RED;
			else
				color = (Color) Color.PURPLE;

			angleParticle = (float) (random.nextFloat() * 2 * Math.PI);
			speedParticle = (float) (random.nextFloat() * maxSpeed / 2);

			level.getNotAddedGameObjects().add(
					new Particle(new Vector2f(position), new Vector2f(
							(float) (Math.cos(angleParticle) * speedParticle),
							(float) (Math.sin(angleParticle) * speedParticle)),
							2, 30 + random.nextInt(20), color));

		}
		color = (Color) Color.GREY;
		for (int i = 0; i < 10; i++) {
			maxSpeed = 200;
			angleParticle = (float) (random.nextFloat() * 2 * Math.PI);
			speedParticle = (float) (random.nextFloat() * maxSpeed / 2);

			level.getNotAddedGameObjects().add(
					new Particle(new Vector2f(position), new Vector2f(
							(float) (Math.cos(angleParticle) * speedParticle),
							(float) (Math.sin(angleParticle) * speedParticle)),
							4 + random.nextInt(5), 30 + random.nextInt(10),
							color));
		}
	}

	public static void drawParticlesFromEngine(Vector2f position, float angle) {
		int maxSpeed = 20;
		int maxTime = 60;
		int i, j = random.nextInt(15);
		Color color = null;
		if (random.nextBoolean())
			color = (Color) Color.RED;
		else
			color = (Color) Color.BLUE;
		for (i = 0; i < j; i++)
			level.getNotAddedGameObjects().add(
					new Particle(position, new Vector2f((float) (maxSpeed
							* Math.sin(angle) + random.nextFloat() - 1),
							-(float) (maxSpeed * Math.cos(angle)
									+ random.nextFloat() - 1)), random
							.nextInt(4), random.nextInt(maxTime), color));
	}
}
