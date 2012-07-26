
/**
* @author Andreyuk Artyom happydroidx@gmail.com
* @version 1.0
*/
package logic;

//class have list of constans for game, have game engines,

import logic.entity.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.entity.GameObject;
public class Level {
	private static int defaultWidth = 800;
	private static int defaultHeight = 600;
	private int widthLevel;
	private int heightLevel;
	// list of all object
	private List<GameObject> gameObjects = new ArrayList<GameObject>();

	public static final double GRAVITY = 0.10;
	private static Random random = new Random();

	public Level() {
		this.widthLevel = Level.defaultWidth;
		this.heightLevel = Level.defaultHeight;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void testInitLevel() {
		Ship game_object = new Ship(500f, 500f);
		gameObjects.add(game_object);
	}
}
