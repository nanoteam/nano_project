/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic;

//class have list of constans for game, have game engines,
import logic.entity.Player;
import logic.entity.Ship;
import logic.entity.Ship2;
import logic.entity.Ship3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import logic.entity.GameObject;

public class Level {
	private static int defaultWidth = 1600;
	private static int defaultHeight = 1200;
	private int widthLevel;
	private int heightLevel;
	private Player player;

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

	public Level(Player player) {
		this.player = player;
		this.widthLevel = Level.defaultWidth;
		this.heightLevel = Level.defaultHeight;
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
		Ship3 ship = new Ship3(this, 500f, 500f);
		player.setControlledObject(ship);
		gameObjects.add(ship);
	}

	/**
	 * added by Arthur for testing objects 31.07.12
	 */
	public Player getPlayer() {
		return this.player;
	}

	// method for adding not added objects after last 'foreach'
	public void addNotAddedObjects() {
		if (gameObjectsToAdd.size() != 0)
			gameObjects.addAll(gameObjectsToAdd);
		gameObjectsToAdd.clear();
	}

	// -----//--------- (delete)
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

}
