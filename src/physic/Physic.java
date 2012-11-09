/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package physic;

import java.util.List;

import main.Global;
import org.lwjgl.util.vector.Vector2f;

import logic.Level;
import logic.entity.GameObject;
import main.Engine;

//TODO add support jbullet, add loading physic world constanst from Resourses manager
public class Physic implements Engine {
	private Level level;

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
			game_object.move();
			// check for borders
			/*
			if (!level.isInBorders(game_object.getPosition()))
				game_object.setLive(false);
			 */
			
		}
		level.getWorld().step(Global.DT, 7, 3);
	}

	public Physic() {
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}

}
