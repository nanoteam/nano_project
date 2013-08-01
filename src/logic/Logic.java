/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic;

import logic.entity.GameObject;
import main.Engine;
import main.Game;

import java.util.List;

public class Logic implements Engine {
    private Game game;


    public Logic() {
    }

    public Level getLevel() {
        return game.getLevel();
    }


    @Override
    public void tick() {
        // add some objects which created while foreach is going
        if (game.getState() == Game.STATE_LOCAL_GAME) {
            List<GameObject> game_objects = game.getLevel().getGameObjects();
            for (GameObject game_object : game_objects) {
                game_object.update();
                if (!game_object.isLive()) {
                    game.getLevel().getNotDeletedGameObjects().add(game_object);
                }
            }
            game_objects = game.getGlobalWorld().getGameObjects();
            for (GameObject game_object : game_objects) {
                game_object.update();
                if (!game_object.isLive()) {
                    game.getLevel().getNotDeletedGameObjects().add(game_object);
                }
            }
        }

        if (game.getState() == Game.STATE_GLOBAL_GAME) {
            List<GameObject> game_objects = game.getGlobalWorld().getGameObjects();
            for (GameObject game_object : game_objects) {
                game_object.update();
                if (!game_object.isLive()) {
                    game.getLevel().getNotDeletedGameObjects().add(game_object);
                }
            }
        }

        game.getLevel().deleteNotDeletedObjects();
        game.getLevel().addNotAddedObjects();
    }

    @Override
    public void cleanUp() {
    }

    public void setGame(Game game) {
        this.game = game;
    }
}