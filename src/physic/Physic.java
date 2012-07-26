
/**
* @author Andreyuk Artyom happydroidx@gmail.com
* @version 1.0
*/
package physic;

import java.util.List;
import logic.Level;
import logic.entity.GameObject;
import main.Engine;

public class Physic implements Engine {
	private Level level;

	@Override
	public void tick() {

		List<GameObject> game_objects = level.getGameObjects();
		for (GameObject game_object : game_objects) {
			game_object.move();
		}
	}

	public Physic(Level level) {
		this.level = level;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub

	}
}
