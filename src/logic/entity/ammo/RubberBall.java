package logic.entity.ammo;

import logic.Level;

import logic.entity.Explosion;
import logic.entity.GameObjectMoving;
import logic.entity.Particle;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.Image;

import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class RubberBall extends GameObjectMoving {
	private float maxSpeed;
	private int lifeTime;
	private float size;
	private Color color;
	private float width;
	private float height;
	private static Image image;

	static {
		name = "RubberBall";
	}

	public static String getName() {
		return RubberBall.name;
	}

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

		physicObject = PhysicObject.createBox(this, position, width, height,
				Material.Metal);
		physicObject.setSpeed(speed);

		// delete, when complite ersourses manager \/
		/*
		 * if (image == null){ try { image = new Image("rubberbomb.png"); }
		 * catch (SlickException e) { e.printStackTrace(); //To change body of
		 * catch statement use File | Settings | File Templates. } }
		 */
		// delete, when complite ersourses manager /\
		/*
		 * if (image==null){ image =
		 * ResourcesManager.geResourcesManager().getImageByName
		 * (RubberBall.name); }
		 */
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
			physicObject.getBody().setActive(false);
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
		position = physicObject.getPosition();
		angle = physicObject.getAngle();
	}

	@Override
	public void draw() {

		// RenderUtil.drawImage(position.x, position.y,width,
		// height,angle,0.5f,image );

		RenderUtil
				.drawQaud(position.x, position.y, width, height, angle, color);
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		physicObject.destroy();
		level.getNotAddedGameObjects().add(
				new Explosion(level, position, 10, 1));

	}
}
