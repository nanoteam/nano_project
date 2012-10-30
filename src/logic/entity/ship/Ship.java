/**
 * @author Arthur
 * @version 1.3
 */
package logic.entity.ship;

import controller.ControlledObject;
import controller.InputToAction;
import logic.Level;
import logic.entity.ArsenalGameObject;
import logic.entity.EmmiterEffects;
import logic.entity.Explosion;
import logic.entity.GameObjectMoving;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;

import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

import java.util.ArrayList;
import java.util.List;

final public class Ship extends GameObjectMoving implements ControlledObject {

	// flags
	private boolean leftEngineActive = false;
	private boolean rightEngineActive = false;
	private boolean allEngineActive = false;
	private boolean turnEnginesLeft = false;
	private boolean turnEnginesRight = false;
	private boolean weapon1Shot = false;
	private boolean weapon2Shot = false;
	private boolean collision = false;

	// end flags
	private float width, height;
	private float protection = 0.1f;
	private List<ArsenalGameObject> arsenalList = new ArrayList<ArsenalGameObject>();
	private Image image;
	private Vector2f ENGINE_POSITION = new Vector2f(50f, 0f);
	private Engine leftEngine, rightEngine;
	private float engineRelativeAngle = 0;
	private static final float MAX_ENGINE_ANGLE = 360 / 60f;

	private int stateMove = ControlledObject.MOVE_NONE;
	private int stateFire = ControlledObject.FIRE_NONE;

	private Vector2f moveData;
	private Vector2f fireData;

	private CromsonManager cromsonManager = new CromsonManager();

	static {
		name = "ship";
	}

	public Ship(Level level, float x, float y) {
		this.level = level;
		position = new Vector2f(x, y);
		speed = new Vector2f(0, 0);
		width = 100f;
		height = 40f;
		init();
	}

	@Override
	public void init() {

		liveHealth = 100;
		physicObject = PhysicObject.createBox(this, position, width, height,
				Material.Metal);

		{
			// adding weapons

			Weapon weap1 = new Weapon(this, 20, 3, 1, 0);
			arsenalList.add(weap1);
			level.getGameObjects().add(weap1);

			Weapon weap2 = new Weapon(this, 10, 5, 2, 70);
			arsenalList.add(weap2);
			level.getGameObjects().add(weap2);
		}

		{
			// adding engines

			leftEngine = new Engine(level, new Vector2f(position.x
					- ENGINE_POSITION.x, position.y - ENGINE_POSITION.y));
			addEngine(leftEngine);
			level.getNotAddedGameObjects().add(leftEngine);

			rightEngine = new Engine(level, new Vector2f(position.x
					+ ENGINE_POSITION.x, position.y - ENGINE_POSITION.y));
			addEngine(rightEngine);
			level.getNotAddedGameObjects().add(rightEngine);
		}
		/*
		 * // delete, when complite ersourses manager \/ if (image == null){ try
		 * { image = new Image("ship.png"); } catch (SlickException e) {
		 * e.printStackTrace(); //To change body of catch statement use File |
		 * Settings | File Templates. } } // delete, when complite ersourses
		 * manager /\
		 */

	}

	private void addEngine(Engine engine) {
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(physicObject.getBody(), engine.getPhysicObject()
				.getBody(),
				new Vec2(engine.getPosition().x / 30f,
						engine.getPosition().y / 30f));
		jointDef.collideConnected = false;
		// jointDef.enableLimit = true;
		// jointDef.lowerAngle = -MAX_ENGINE_ANGLE;
		// jointDef.upperAngle = MAX_ENGINE_ANGLE;
		// jointDef.referenceAngle = 0;
		Joint joint = level.getWorld().createJoint(jointDef);
		engine.setEngineJoint(joint);

	}

	@Override
	public void update() {

		if (weapon1Shot && !arsenalList.isEmpty()) {
			arsenalList.get(0).setShootOn();
		}
		if (weapon2Shot && !arsenalList.isEmpty()) {
			arsenalList.get(1).setShootOn();
		}

		clearFlags();
		if (liveHealth < 0)
			live = false;

		/*
		 * if (stateMove==ControlledObject.MOVE_VECTOR){
		 * 
		 * } if (stateMove == ControlledObject.MOVE_NONE){
		 * 
		 * } // create mirage
		 */
		/*
		 * //cromsone!!! float[] box = new float[7]; while(angle>2*3.14159f){
		 * angle = angle-2*3.14159f; } box[0] = angle/(2*3.14159f);
		 * 
		 * box[1] = speed.x/200f; box[2] = speed.y/200f; box[3] =
		 * Math.abs(position.x-100)/1600f; box[4] =
		 * Math.abs(position.x-500)/900f; float angleEngine =
		 * leftEngine.getAlfa(); while(angleEngine>2*3.14159f){ angleEngine =
		 * angleEngine-2*3.14159f; } box[5] = angleEngine/(2*3.14159f); box[6] =
		 * Math.abs(w)/5f; float[] activity =
		 * cromsonManager.getActivityCurrentCromsone(box);
		 * 
		 * if (random.nextFloat()<activity[0]){ allEngineActive = true; }
		 * leftEngine.setAlfa(activity[1]);
		 * 
		 * 
		 * //end cromsone!!!
		 */

	}

	// TODO add *dt
	@Override
	public void move() {
		position = physicObject.getPosition();
		speed = physicObject.getSpeed();
		angle = physicObject.getAngle();

		if (allEngineActive) {
			leftEngine.enableForce();
			rightEngine.enableForce();

		}

		if (rightEngineActive) {
			rightEngine.enableForce();
		}

		if (leftEngineActive) {
			leftEngine.enableForce();
		}

		if (turnEnginesLeft) {
			engineRelativeAngle += 0.1;
		}
		if (turnEnginesRight) {
			engineRelativeAngle -= 0.1;

		}

		if (engineRelativeAngle > MAX_ENGINE_ANGLE)
			engineRelativeAngle = MAX_ENGINE_ANGLE;
		else if (engineRelativeAngle < -MAX_ENGINE_ANGLE)
			engineRelativeAngle = -MAX_ENGINE_ANGLE;

		leftEngine.getPhysicObject().setAngle(angle + engineRelativeAngle);
		rightEngine.getPhysicObject().setAngle(angle + engineRelativeAngle);
	}

	@Override
	public void draw() {
		RenderUtil.drawQaud(position.x, position.y, width, height, angle,
				(Color) Color.GREY);
	}

	@Override
	public void playSound() {
	}

	@Override
	public void doAction(int code) {
		// System.out.println("ship.doAction()" + code);
		switch (code) {
		case InputToAction.left: {
			leftEngineActive = true;
			break;
		}
		case InputToAction.right: {
			rightEngineActive = true;
			break;
		}
		case InputToAction.down: {
			allEngineActive = true;
			break;
		}
		/*
		 * case InputToAction.: { allEngineActive = true; break; }
		 */

		/*
		 * case ControlledObject.FIRE_FIRST_WEAPON: { weapon1Shot = true; break;
		 * } case ControlledObject.FIRE_SECOND_WEAPON: { weapon2Shot = true;
		 * break; }
		 */

		case InputToAction.engineLeft: {
			turnEnginesLeft = true;
			break;
		}
		case InputToAction.engineRight: {
			turnEnginesRight = true;
			break;
		}
		case InputToAction.fire1: {
			weapon1Shot = true;
			break;
		}
		}
	}

	public void doAction(int code, Vector2f actionData) {
		switch (code) {
		// move
		case ControlledObject.MOVE_TARGET: {
			stateMove = ControlledObject.MOVE_TARGET;
			moveData = actionData;
			break;
		}
		case ControlledObject.MOVE_VECTOR: {
			stateMove = ControlledObject.MOVE_VECTOR;
			moveData = actionData;
			break;
		}
		case ControlledObject.MOVE_NONE: {
			stateMove = ControlledObject.MOVE_TARGET;
			moveData = null;
			break;
		}
		// data
		case ControlledObject.FIRE_TARGET: {
			stateFire = ControlledObject.FIRE_TARGET;
			fireData = actionData;
			break;
		}
		case ControlledObject.FIRE_SHOOT: {
			stateFire = ControlledObject.FIRE_SHOOT;
			fireData = actionData;
			break;
		}
		case ControlledObject.FIRE_NONE: {
			stateFire = ControlledObject.FIRE_NONE;
			fireData = null;
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
		turnEnginesLeft = false;
		turnEnginesRight = false;
		collision = false;
	}

	@Override
	public void destroy() {
		physicObject.destroy();
		EmmiterEffects.drawBoom(position);
		level.getNotAddedGameObjects().add(
				new Explosion(level, position, 60, 1));
	}

	public void damage(float... impulses) {
		float result = 0;
		for (float f : impulses)
			result += f;
		liveHealth -= result * (1 - protection);
	}

	public static String getName() {
		return name;
	}
}