/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package physic;

import logic.Level;
import logic.entity.GameObject;
import main.Engine;
import main.Game;
import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;
import resourses.configuration.ConfigsLibrary;
import resourses.configuration.SheetParse;

import java.util.List;


public class Physic implements Engine {
    private Game game;
    private float deltaTimePhysEngine = 1 / 30f;
    private static final float SCALE_FOR_PHYSIC_WORLD = 30;

    public Level getLevel() {
        return game.getLevel();
    }


    @Override
    public void tick() {

        if (game.getState() == Game.STATE_LOCAL_GAME) {
            List<GameObject> game_objects = game.getLevel().getGameObjects();
            for (GameObject game_object : game_objects) {
                game_object.move();
                if (!game_object.isLive()) {
                    game.getLevel().getNotDeletedGameObjects().add(game_object);
                }
            }
            game.getLevel().getWorld().step(deltaTimePhysEngine, 7, 3);
        }

        if (game.getState() == Game.STATE_GLOBAL_GAME) {
            List<GameObject> game_objects = game.getGlobalWorld().getGameObjects();
            for (GameObject game_object : game_objects) {
                game_object.move();
                if (!game_object.isLive()) {
                    game.getLevel().getNotDeletedGameObjects().add(game_object);
                }
            }
        }


    }

    public Physic() {
        initByConfig();
    }

    private void initByConfig() {
        SheetParse setting = ConfigsLibrary.get().getConfig(ConfigsLibrary.pathToSetting);
        try {
            deltaTimePhysEngine = Float.parseFloat(setting.findSheetParseByName("DeltaTimePhysEngine").getValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        /*
        try {

            deltaTimePhysEngine = Float.parseFloat(setting.findSheetParseByName("ScaleForPhysicWorld").getValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        */

    }

    public float getDeltaTimePhysEngine() {
        return deltaTimePhysEngine;
    }

    @Override
    public void cleanUp() {

    }

    //this function nead unification for all usage in code, this make code more stable and safe
    //this fucntion convert Vector2d to Vec2, use scale
    public static Vector2f convertToVectro2f(Vec2 vec2) {
        return new Vector2f(vec2.x * SCALE_FOR_PHYSIC_WORLD, vec2.y * SCALE_FOR_PHYSIC_WORLD);
    }

    public static Vec2 convertToVec2(Vector2f vector2f) {
        return new Vec2(vector2f.x / SCALE_FOR_PHYSIC_WORLD, vector2f.y / SCALE_FOR_PHYSIC_WORLD);
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
