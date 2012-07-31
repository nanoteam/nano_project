
/**
* @author Andreyuk Artyom happydroidx@gmail.com
* @version 1.0
*/
package logic;

//class have list of constans for game, have game engines,
import logic.entity.Player;
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
	private Player player;
	
	//physic constans 
	//TODO add support resourses manager
	public static double gravity = 10;

	
	// list of all object
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	
	private static Random random = new Random();

	public Level(Player player) {
		this.player = player;
		this.widthLevel = Level.defaultWidth;
		this.heightLevel = Level.defaultHeight;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	//this is method for runninig game in test mode
	public void testInitLevel() {
		Ship ship = new Ship(500f, 500f);
		player.setControlledObject(ship);
		gameObjects.add(ship);
	}
}
