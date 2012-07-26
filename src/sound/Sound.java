/**
* @author Andreyuk Artyom happydroidx@gmail.com
* @version 1.0
*/
package sound;

import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

import logic.Level;
import logic.entity.GameObject;
import main.Engine;

public class Sound implements Engine {
	private Level level;

	@Override
	public void tick() {
		List<GameObject> game_objects = level.getGameObjects();
		for (GameObject game_object : game_objects) {
			game_object.playSound();
		}
	}

	public Sound(Level level) {
		this.level = level;
		// Start up the sound system
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void cleanUp() {
		// Stop the sound
		AL.destroy();

	}
}
