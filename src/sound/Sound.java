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
	public void tick() {  /*
		List<GameObject> gameObjects = level.getGameObjects();
		for (GameObject gameObject : gameObjects) {
            gameObject.playSound();
		}                   */
	}

	
	public Sound() {
		// Start up the sound system

        /*
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		*/
	}

	public Level getLevel(){
		return level;
	}
	public void setLevel(Level level){
		this.level = level;
	}

	@Override
	public void cleanUp() {

	}
}
