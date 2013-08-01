package ai;

import logic.Level;
import logic.entity.GameObject;
import main.Engine;
import main.Game;
import main.Global;

import java.util.List;

public class AI implements Engine {
    private Game game;

    @Override
    public void tick() {
        if (Global.AI_ON) {
            if (game.getState() == Game.STATE_LOCAL_GAME) {
                List<GameObject> game_objects = game.getLevel().getGameObjects();
                for (GameObject game_object : game_objects) {
                    game_object.toThink();

                }
                game_objects = game.getGlobalWorld().getGameObjects();
                for (GameObject game_object : game_objects) {
                    game_object.toThink();

                }
            }

            if (game.getState() == Game.STATE_GLOBAL_GAME) {
                List<GameObject> game_objects = game.getGlobalWorld().getGameObjects();
                for (GameObject game_object : game_objects) {
                    game_object.toThink();
                }
            }
        }
    }

    @Override
    public void cleanUp() {
    }

    public Level getLevel() {
        return game.getLevel();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}