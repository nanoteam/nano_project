/**
 * @author Arthur
 * @version 1.3
 */
package logic.entity;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;
import util.MathUtil;
import controller.ControlledObject;
import logic.Level;
import main.Game;

final public class Ship extends GameObjectPhysicMoving implements
		ControlledObject {
	private float Ft = 300000f;
	private boolean leftEngineActive = false;
	private boolean rightEngineActive = false;
	private boolean allEngineActive = false;
	private boolean weapon1Shot = false;
	private boolean weapon2Shot = false;
	private float width, height;
	private List<ArsenalGameObject> arsenalList = new ArrayList<ArsenalGameObject>();

	private final static float SPEED_PARTICLE_FROM_ENGINE = 200f;
	private final static float SPEED_PARTICLE_KOOF_RANDOM = 20f;
	private final static float ENGINE_FORCE = 500;

	// private ArrayList<ShipComponent> shipComponents;
	public Ship(Level level, float x, float y) {
		// if(!loadParametres(..))
		// throw...;
		this.level = level;
		position = new Vector2f(x, y);
		init();
	}

	@Override
	public void init() {
		width = 100f;
		height = 40f;

		BodyDef shipDef = new BodyDef();
		shipDef.position.set(new Vec2(position.x / 30, position.y / 30));
		shipDef.type = BodyType.DYNAMIC;
		PolygonShape shipShape = new PolygonShape();
		shipShape.setAsBox(width / 30 / 2, height / 30 / 2);
		this.body = level.getWorld().createBody(shipDef);
		FixtureDef shipFixture = new FixtureDef();
		shipFixture.density = 5f;
		shipFixture.restitution = 0;
		shipFixture.shape = shipShape;
		body.createFixture(shipFixture);

		SimpleWeapon weap1 = new SimpleWeapon(this, 20, 3, 1, 3, 20);
		SimpleWeapon weap2 = new SimpleWeapon(this, 10, 5, 2, 15, 70);
		arsenalList.add(weap1);
		arsenalList.add(weap2);
		level.getGameObjects().add(weap1);
		level.getGameObjects().add(weap2);
	}

	private void createParticleFromEngine(float x, float y, Color color) {
		speed = new Vector2f(body.getLinearVelocity().x,
				body.getLinearVelocity().y);
		level.getNotAddedGameObjects()
				.add(new Particle(
						new Vector2f((float) (position.x + MathUtil.newXTurn(x,
								y, angle)), (float) (position.y + MathUtil
								.newYTurn(x, y, angle))),
						new Vector2f(
								(float) (speed.x + Math.sin(angle / 60)
										* SPEED_PARTICLE_FROM_ENGINE + SPEED_PARTICLE_KOOF_RANDOM
										* (random.nextFloat() - 0.5)),
								(float) (speed.y + -Math.cos(angle / 60)
										* SPEED_PARTICLE_FROM_ENGINE + SPEED_PARTICLE_KOOF_RANDOM
										* (random.nextFloat() - 0.5))), 2,
						100 + random.nextInt(20), color));
	}

	@Override
	public void update() {
		// OMG OMG OMG, in future i refactoring this code
		if (leftEngineActive) {
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.RED);
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.RED);
		}

		if (rightEngineActive) {
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.RED);
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.RED);
		}

		if (allEngineActive) {
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.RED);
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.RED);
			createParticleFromEngine(-width / 2f, -height / 2f,
					(Color) Color.RED);

			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.ORANGE);
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.RED);
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.RED);
			createParticleFromEngine(width / 2f, -height / 2f,
					(Color) Color.RED);
		}

		if (weapon1Shot && !arsenalList.isEmpty()) {
			arsenalList.get(0).setShootOn();
		}
		if (weapon2Shot && !arsenalList.isEmpty()) {
			arsenalList.get(1).setShootOn();
		}

		// create mirage

		clearFlags();
		position = new Vector2f(body.getPosition().x * 30,
				body.getPosition().y * 30);
		angle = body.getAngle();
	}

	// TODO add *dt
	@Override
	public void move() {

		Vec2 force = new Vec2((float) (-ENGINE_FORCE * Math.sin(angle / 60f)),
				(float) (ENGINE_FORCE * Math.cos(angle / 60f)));

		if (allEngineActive) {
			body.applyForce(force.mul(2f), body.getPosition());
		} else if (rightEngineActive) {

			Vec2 pointOfForce = body.getPosition().add(
					new Vec2((float) (width / 30 * Math.cos(angle / 60f)),
							(float) (width / 30 * Math.sin(angle / 60f))));
			body.applyForce(force, pointOfForce);

		} else if (leftEngineActive) {
			Vec2 pointOfForce = body.getPosition().add(
					new Vec2((float) -(width / 30 * Math.cos(angle / 60f)),
							(float) -(width / 30 * Math.sin(angle / 60f))));
			body.applyForce(force, pointOfForce);
		}

	}

	@Override
	public void draw() {
		glPushMatrix();

		// glTranslatef(position.x, position.y, 0.0f);
		GL11.glColor3ub(Color.WHITE.getRedByte(), Color.WHITE.getGreenByte(),
				Color.WHITE.getBlueByte());

		RenderUtil.drawLine(
				new Vector2f(position.x
						+ MathUtil.newXTurn(-width / 2, -height / 2, angle),
						position.y
								+ MathUtil.newYTurn(-width / 2, -height / 2,
										angle)),
				new Vector2f(position.x
						+ MathUtil.newXTurn(width / 2, -height / 2, angle),
						position.y
								+ MathUtil.newYTurn(width / 2, -height / 2,
										angle)), 3f, (Color) Color.GREY);

		RenderUtil.drawLine(
				new Vector2f(position.x
						+ MathUtil.newXTurn(-width / 2, height / 2, angle),
						position.y
								+ MathUtil.newYTurn(-width / 2, height / 2,
										angle)),
				new Vector2f(position.x
						+ MathUtil.newXTurn(width / 2, height / 2, angle),
						position.y
								+ MathUtil.newYTurn(width / 2, height / 2,
										angle)), 3f, (Color) Color.GREY);

		RenderUtil.drawLine(
				new Vector2f(position.x
						+ MathUtil.newXTurn(-width / 2, -height / 2, angle),
						position.y
								+ MathUtil.newYTurn(-width / 2, -height / 2,
										angle)),
				new Vector2f(position.x
						+ MathUtil.newXTurn(-width / 2, height / 2, angle),
						position.y
								+ MathUtil.newYTurn(-width / 2, height / 2,
										angle)), 3f, (Color) Color.GREY);

		RenderUtil.drawLine(
				new Vector2f(position.x
						+ MathUtil.newXTurn(width / 2, -height / 2, angle),
						position.y
								+ MathUtil.newYTurn(width / 2, -height / 2,
										angle)),
				new Vector2f(position.x
						+ MathUtil.newXTurn(width / 2, height / 2, angle),
						position.y
								+ MathUtil.newYTurn(width / 2, height / 2,
										angle)), 3f, (Color) Color.GREY);
		// 0.01f - angle
		// glRotatef(angle, 0.0f, 0.0f, 1.0f);
		/*
		 * glBegin(GL_QUADS); glVertex2i(-width / 2, -height / 2);
		 * glVertex2i(width / 2, -height / 2); glVertex2i(width / 2, height /
		 * 2); glVertex2i(-width / 2, height / 2);
		 * 
		 * glEnd(); glPopMatrix();
		 */
		/*
		 * ArrayList<Vector2f> vector = new ArrayList<Vector2f>(); for (int i =
		 * 0;i<random.nextInt(10)+5;i++){ vector.add(new
		 * Vector2f(random.nextInt(1000),random.nextInt(700))); }
		 * //RenderUtil.drawLine(new Vector2f(100,100), // new
		 * Vector2f(300,300), (float) (5f), (Color) Color.BLUE);
		 * 
		 * RenderUtil.drawPolyLineSmooth(vector, 5, (Color)Color.ORANGE);
		 */

	}

	@Override
	public void playSound() {
	}

	@Override
	public void doAction(int code) {
		System.out.println("Ship.doAction()" + code);
		switch (code) {
		case ControlledObject.LEFT_ENGINE_ACTIVE: {
			leftEngineActive = true;
			break;
		}
		case ControlledObject.RIGHT_ENGINE_ACTIVE: {
			rightEngineActive = true;
			break;
		}
		case ControlledObject.All_ENGINE_ACTIVE: {
			allEngineActive = true;
			break;
		}
		case ControlledObject.FIRE_FIRST_WEAPON: {
			// TODO more situat. check
			weapon1Shot = true;
			break;
		}
		case ControlledObject.FIRE_SECOND_WEAPON: {
			// TODO more situat. check
			weapon2Shot = true;
			break;
		}
		}
	}

	@Override
	public void clearFlags() {
		allEngineActive = false;
		leftEngineActive = false;
		rightEngineActive = false;
		weapon1Shot = false;
		weapon2Shot = false;
	}
}
