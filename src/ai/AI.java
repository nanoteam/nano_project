package ai;

import logic.Level;
import logic.entity.GameObject;
import main.Engine;
import main.Global;

import java.util.List;

public class AI implements Engine {
    private Level level;

    @Override
    public void tick() {
        
        if (Global.commandOn){
            List<GameObject> gameObjects = level.getGameObjects();
            for (GameObject gameObject : gameObjects) {
                gameObject.toThink();
            }
        }
    }

    @Override
    public void cleanUp() {
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}