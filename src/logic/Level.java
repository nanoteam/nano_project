package logic;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;
import logic.entity.Bot;
import logic.entity.Particle;
import logic.entity.Player;
import logic.entity.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import controller.Cursor;

import logic.entity.GameObject;

public class Level {
	private static int defaultWidth = 1280;
	private static int defaultHeight = 800;
	private int widthLevel;
	private int heightLevel;
	private Player player;
	private Cursor cursor;
	private World world = null;
	// physic constans
	// TODO add support resourses manager
	public static float gravity = -9.8f;

	// list of all object
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	// list of objects which will be added to main list
	private List<GameObject> gameObjectsToAdd = new ArrayList<GameObject>();
	// list of objects which will be deleted from main list
	private List<GameObject> gameObjectsToDelete = new ArrayList<GameObject>();

	private static Random random = new Random();

	public Level(Player player, Cursor cursor) {
		this.player = player;
		this.widthLevel = Level.defaultWidth;
		this.heightLevel = Level.defaultHeight;
		this.cursor = cursor;
		this.world = new World(new Vec2(0, gravity), false);
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public List<GameObject> getNotAddedGameObjects() {
		return gameObjectsToAdd;
	}

	public List<GameObject> getNotDeletedGameObjects() {
		return gameObjectsToDelete;
	}

	// this is method for runninig game in test mode
	public void testInitLevel() {
		Ship ship = new Ship(this, 200f, 500f);
		player.setControlledObject(ship);
		gameObjects.add(ship);

		Ship ship2 = new Ship(this, 500f, 500f);
		Bot bot = new Bot(ship2);
		gameObjects.add(ship2);
		gameObjects.add(bot);

		// walls and them restitution
		float wallRestitution = 0f;
		float wallHeight = defaultHeight / 30f;
		float wallWidth = defaultWidth / 30f;

		BodyDef downWallDef = new BodyDef();
		downWallDef.position.set(new Vec2(0, 0));
		downWallDef.type = BodyType.STATIC;
		PolygonShape downWallShape = new PolygonShape();
		downWallShape.setAsBox(wallWidth, 0);
		Body downWall = world.createBody(downWallDef);
		FixtureDef downWallFixture = new FixtureDef();
		downWallFixture.restitution = wallRestitution;
		downWallFixture.shape = downWallShape;
		downWall.createFixture(downWallFixture);

		BodyDef upWallDef = new BodyDef();
		upWallDef.position.set(new Vec2(0, wallHeight));
		upWallDef.type = BodyType.STATIC;
		PolygonShape upWallShape = new PolygonShape();
		upWallShape.setAsBox(wallWidth, 0);
		Body upWall = world.createBody(upWallDef);
		FixtureDef upWallFixture = new FixtureDef();
		upWallFixture.restitution = wallRestitution;
		upWallFixture.shape = upWallShape;
		upWall.createFixture(upWallFixture);

		BodyDef leftWallDef = new BodyDef();
		leftWallDef.position.set(new Vec2(0, 0));
		leftWallDef.type = BodyType.STATIC;
		PolygonShape leftWallShape = new PolygonShape();
		leftWallShape.setAsBox(0, wallHeight);
		Body leftWall = world.createBody(leftWallDef);
		FixtureDef leftWallFixture = new FixtureDef();
		leftWallFixture.restitution = wallRestitution;
		leftWallFixture.shape = leftWallShape;
		leftWall.createFixture(leftWallFixture);

		BodyDef rightWallDef = new BodyDef();
		rightWallDef.position.set(new Vec2(wallWidth, 0));
		rightWallDef.type = BodyType.STATIC;
		PolygonShape rightWallShape = new PolygonShape();
		rightWallShape.setAsBox(0, wallHeight);
		Body rightWall = world.createBody(rightWallDef);
		FixtureDef rightWallFixture = new FixtureDef();
		rightWallFixture.restitution = wallRestitution;
		rightWallFixture.shape = rightWallShape;
		rightWall.createFixture(rightWallFixture);
	}

	public Player getPlayer() {
		return this.player;
	}

	/**
	 * added by Arthur for testing objects 31.07.12
	 */
	// method for adding not added objects after last 'foreach'
	public void addNotAddedObjects() {
		if (gameObjectsToAdd.size() != 0)
			gameObjects.addAll(gameObjectsToAdd);
		gameObjectsToAdd.clear();
	}

	public void deleteNotDeletedObjects() {
		if (gameObjectsToDelete.size() != 0)
			gameObjects.removeAll(gameObjectsToDelete);
		gameObjectsToDelete.clear();
	}

	public boolean isInBorders(Vector2f position) {
		if (position.x < 0 || position.x > widthLevel || position.y < 0
				|| position.y > heightLevel)
			return false;
		else
			return true;
	}

	public Vector2f getPositionMouse() {
		return cursor.getPosition();
	}

	public World getWorld() {
		return world;
	}
}
