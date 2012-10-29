/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic;

import java.util.List;

import logic.entity.GameObject;
import logic.entity.ship.CromsonManager;
import main.Client;
import main.Engine;

public class Logic implements Engine {
	private Level level;
	private Client client;

	public Logic(Client client) {
		this.client = client;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public void tick() {

		List<GameObject> game_objects = level.getGameObjects();
		for (GameObject game_object : game_objects) {
			game_object.update();
			if (!game_object.isLive()){
				level.getNotDeletedGameObjects().add(game_object);
				game_object.destroy();
			}
		}

		// add some objects which created while foreach is going
		level.deleteNotDeletedObjects();
		level.addNotAddedObjects();

	}

	@Override
	public void cleanUp() {
	}
}