/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic;

import java.util.List;
import org.lwjgl.input.Keyboard;
import logic.entity.GameObject;
import logic.entity.Ship;
import logic.entity.Ship2;
import main.Client;
import main.Engine;

public class Logic implements Engine {
	private Level level;
	private Client client;
	public Logic(Client client) {
		this.client = client;
	}
	public Level getLevel(){
		return level;
	}
	public void setLevel(Level level){
		this.level = level; 
	}
	@Override
	public void tick() {
		List<GameObject> game_objects = level.getGameObjects();

		for (GameObject game_object : game_objects) {
			game_object.update();
		}
	}

	@Override
	public void cleanUp() {
	}
}