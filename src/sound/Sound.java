/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package sound;

import logic.Level;
import main.Engine;
import main.Game;

public class Sound implements Engine {
    private Game game;

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

    public Level getLevel() {
        return game.getLevel();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void cleanUp() {

    }
}
