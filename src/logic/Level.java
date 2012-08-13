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

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import controller.Cursor;

import logic.entity.GameObject;

public class Level {
	private static int defaultWidth = 1600;
	private static int defaultHeight = 900;
	private int widthLevel;
	private int heightLevel;
	private Player player;
	private Cursor cursor;
	// physic constans
	// TODO add support resourses manager
	public static double gravity = 10;

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
		Ship ship = new Ship(this, 500f, 500f);
		player.setControlledObject(ship);
		gameObjects.add(ship);
		Ship botShip = new Ship(this, 800f, 500f);
		Bot botForShip = new Bot(botShip);
		gameObjects.add(botShip);
		gameObjects.add(botForShip);
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
}
