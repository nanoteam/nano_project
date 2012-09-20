/**
 * @author Arthur
 * @version 1.3
 */
package logic.entity;

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
import org.newdawn.slick.Image;

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
	private float protection = 0.1f;
	private List<ArsenalGameObject> arsenalList = new ArrayList<ArsenalGameObject>();
	private Image image;
	private final static float SPEED_PARTICLE_FROM_ENGINE = 200f;
	private final static float SPEED_PARTICLE_KOOF_RANDOM = 20f;
	private final static float ENGINE_FORCE = 90;

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
		liveHealth = 100;
		BodyDef shipDef = new BodyDef();
		shipDef.position.set(new Vec2(position.x / 30, position.y / 30));
		shipDef.type = BodyType.DYNAMIC;
		PolygonShape shipShape = new PolygonShape();
		shipShape.setAsBox(width / 30 / 2, height / 30 / 2);
		this.body = level.getWorld().createBody(shipDef);
		this.body.m_userData = this;

		FixtureDef shipFixture = new FixtureDef();
		shipFixture.friction = 0.5f; // trenie
		shipFixture.density = 0.5f; // plotnost'
		shipFixture.restitution = 0.15f;
		shipFixture.shape = shipShape;
		// ships do not interact ships with each other
		// shipFixture.filter.groupIndex = -1;
		body.createFixture(shipFixture);
		body.setAngularDamping(3);

		SimpleWeapon weap1 = new SimpleWeapon(this, 20, 3, 1, 3, 4);
		SimpleWeapon weap2 = new SimpleWeapon(this, 10, 5, 2, 15, 70);
		arsenalList.add(weap1);
		arsenalList.add(weap2);
		level.getGameObjects().add(weap1);
		level.getGameObjects().add(weap2);

	}

	@Override
	public void update() {
		speed = new Vector2f(body.getLinearVelocity().x,
				body.getLinearVelocity().y);
		if (leftEngineActive) {

			EmmiterEffects.drawParticlesFromEngine(
					new Vector2f((float) (position.x + MathUtil.newXTurn(
							-width / 2f, -height / 2f, angle)),
							(float) (position.y + MathUtil.newYTurn(
									-width / 2f, -height / 2f, angle))), angle);
		}

		if (rightEngineActive) {

			EmmiterEffects.drawParticlesFromEngine(
					new Vector2f((float) (position.x + MathUtil.newXTurn(
							width / 2f, -height / 2f, angle)),
							(float) (position.y + MathUtil.newYTurn(width / 2f,
									-height / 2f, angle))), angle);
		}

		if (allEngineActive) {

			EmmiterEffects.drawParticlesFromEngine(
					new Vector2f((float) (position.x + MathUtil.newXTurn(
							-width / 2f, -height / 2f, angle)),
							(float) (position.y + MathUtil.newYTurn(
									-width / 2f, -height / 2f, angle))), angle);
			EmmiterEffects.drawParticlesFromEngine(
					new Vector2f((float) (position.x + MathUtil.newXTurn(
							width / 2f, -height / 2f, angle)),
							(float) (position.y + MathUtil.newYTurn(width / 2f,
									-height / 2f, angle))), angle);
		}

		if (weapon1Shot && !arsenalList.isEmpty()) {
			arsenalList.get(0).setShootOn();
		}
		if (weapon2Shot && !arsenalList.isEmpty()) {
			arsenalList.get(1).setShootOn();
		}

		// create mirage

		clearFlags();
		if (liveHealth < 0)
			live = false;

	}

	// TODO add *dt
	@Override
	public void move() {
		position = new Vector2f(body.getPosition().x * 30,
				body.getPosition().y * 30);
		angle = body.getAngle();
		Vec2 force = new Vec2((float) (-ENGINE_FORCE * Math.sin(angle)),
				(float) (ENGINE_FORCE * Math.cos(angle)));

		if (allEngineActive) {
			body.applyForce(force, body.getPosition());
		}

		if (rightEngineActive) {

			Vec2 pointOfForce = body.getPosition().add(
					new Vec2((float) (width / 30 / 7 * Math.cos(angle)),
							(float) (width / 30 / 7 * Math.sin(angle))));
			body.applyForce(force, pointOfForce);
		}

		if (leftEngineActive) {
			Vec2 pointOfForce = body.getPosition().add(
					new Vec2((float) -(width / 30 / 7 * Math.cos(angle)),
							(float) -(width / 30 / 7 * Math.sin(angle))));
			body.applyForce(force, pointOfForce);
		}

	}

	@Override
	public void draw() {
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
	}

	@Override
	public void playSound() {
	}

	@Override
	public void doAction(int code) {
		// System.out.println("Ship.doAction()" + code);
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

	@Override
	public void destroy() {
		EmmiterEffects.drawBoom(position);
		level.getWorld().destroyBody(body);
	}

	public void damage(float... impulses) {
		float result = 0;
		for (float f : impulses)
			result += f;
		liveHealth -= result * (1 - protection);
	}
}
