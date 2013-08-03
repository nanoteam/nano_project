/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package render;

import logic.entity.GameObject;
import main.Engine;
import main.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import resourses.configuration.ConfigsLibrary;
import resourses.configuration.SheetParse;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Render implements Engine {
    private Game game;
    public static final int VIEWPORT_GLOBAL_WORLD = 0;
    public static final int VIEWPORT_ON_PLAYER = 1;
    private int stateViewPort = 0;
    private static float zoom = 1;
    private float left, right, top, bottom;

    private int resolutionX = 100, resolutionY = 100;
    private boolean fullScreen = false;
    private int renderFps = 60;

    //TODO add avto detecting optimal working set display, 1600x900x32 or 640x480x16 or ...
    public Render() {
        initByConfig();
        stateViewPort = Render.VIEWPORT_ON_PLAYER;
        try {
            Display.setFullscreen(fullScreen);
            if (fullScreen) {
                Display.create();
                resolutionX = Display.getWidth();
                resolutionY = Display.getHeight();
            } else {
                Display.setDisplayMode(new DisplayMode(resolutionX, resolutionY));
                Display.create();
            }
            // Enable vsync if we can
            //Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        //without this part of code render not working correctly!
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, resolutionX, 0.0, resolutionY, -1.0, 1.0);
        //glMatrixMode(GL_MODELVIEW);
        //glLoadIdentity();
        //glViewport(0, 0, Display.getDisplayMode().getResolutionX(), Display
        //		.getDisplayMode().getResolutionY());
        //glViewport(0, 0,3200,1800);
        setZoom(1);
    }

    private void initByConfig() {
        SheetParse setting = ConfigsLibrary.get().getConfig(ConfigsLibrary.pathToSetting);
        try {
            resolutionX = Integer.parseInt(setting.findSheetParseByName("ResolutionX").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            resolutionY = Integer.parseInt(setting.findSheetParseByName("ResolutionY").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fullScreen = Boolean.parseBoolean(setting.findSheetParseByName("FullScreen").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            renderFps = Integer.parseInt(setting.findSheetParseByName("RenderFps").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo create mechanizm update OpenGL setting only after changing setting
    @Override
    public void tick() {
        if (stateViewPort == VIEWPORT_ON_PLAYER) {
            Vector2f p = game.getPlayer().getControlledObject().getPosition();
            float left = -resolutionX / 2;
            float right = resolutionX / 2;
            float bottom = -resolutionY / 2;
            float top = resolutionY / 2;
            //glOrtho(left, right, bottom, top, -1, 1);

            glViewport(0, 0, resolutionX, resolutionY);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, resolutionX, 0, resolutionY, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glScalef(zoom, zoom, zoom);
            glTranslatef((int) (-p.x + resolutionX / 2 / zoom), (int) (-p.y + resolutionY / 2 / zoom), 0);
        } else {
            zoom = 1;
            //glViewport(0, 1600, 0, 900);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            glOrtho(0, resolutionX, 0, resolutionY, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glScalef(1, 1, 1);
            glTranslatef(0, 0, 0);
            //glOrtho(0, 1600, 0, 900, -1, 1);
        }

        //clear buffer and add image to buffer, display been do good
        glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        glClear(GL_COLOR_BUFFER_BIT);

        //glSelectBuffer();
        //
        glPushMatrix();

//        if (game.getState() == Game.STATE_LOCAL_GAME) {
//            List<GameObject> game_objects = game.getLevel().getGameObjects();
//            for (GameObject game_object : game_objects) {
//                game_object.draw();
//            }
//        }

//        if (game.getState() == Game.STATE_GLOBAL_GAME) {
            List<GameObject> game_objects = game.getGlobalWorld().getGameObjects();
            for (GameObject game_object : game_objects) {
                game_object.draw();
            }
  //      }

        if (game.getState() == Game.STATE_GAME_MENU) {

        }
        glPopMatrix();


    }


    @Override
    public void cleanUp() {
        // Close the window
        Display.destroy();
    }

    public void update() {
        Display.update();
    }

    public static float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }


    public int getStateViewPort() {
        return stateViewPort;
    }

    public void setStateViewPort(int stateViewPort) {
        if ((stateViewPort == VIEWPORT_GLOBAL_WORLD) || (stateViewPort == VIEWPORT_ON_PLAYER)) {
            this.stateViewPort = stateViewPort;
        } else {
            System.out.println("invalid state view port");
            System.out.println(-1);
        }
    }

    public void syncFps() {
        Display.sync(renderFps);
    }


    public int getResolutionX() {
        return resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public int getRenderFps() {
        return renderFps;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}