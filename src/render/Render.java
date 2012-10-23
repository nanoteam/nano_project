/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package render;

import logic.Level;
import logic.entity.GameObject;
import main.Engine;
import main.Global;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Render implements Engine {
    private Level level;
    public static final int VIEWPORT_GLOBAL_WORLD = 0;
    public static final int VIEWPORT_ON_PLAYER = 1;
    private int stateViewPort = 0;
    private float zoom = 1;

    private float left, right, top, bottom;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void tick() {
        if (stateViewPort == VIEWPORT_ON_PLAYER) {

            Vector2f p = level.getPlayer().getControlledObject().getPosition();
            float left = -Global.RESOLUTION_X / 2;
            float right = Global.RESOLUTION_X / 2;
            float bottom = -Global.RESOLUTION_Y / 2;
            float top = Global.RESOLUTION_Y / 2;

            //glOrtho(left, right, bottom, top, -1, 1);

            glViewport(0, 0, Global.RESOLUTION_X, Global.RESOLUTION_Y);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, 1600, 0, 900, -1, 1);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glScalef(zoom, zoom, zoom);

            glTranslatef((int) (-p.x + Global.RESOLUTION_X / 2 / zoom), (int) (-p.y + Global.RESOLUTION_Y / 2 / zoom), 0);
        } else {
            zoom = 1;

            //glViewport(0, 1600, 0, 900);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            glOrtho(0, Global.RESOLUTION_X, 0, Global.RESOLUTION_Y, -1, 1);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glScalef(1, 1, 1);

            glTranslatef(0, 0, 0);


            //glOrtho(0, 1600, 0, 900, -1, 1);
        }

        //clear buffer and add image to buffer, display been do good
        glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        glClear(GL_COLOR_BUFFER_BIT);

        glPushMatrix();
        List<GameObject> game_objects = level.getGameObjects();
        for (GameObject game_object : game_objects) {
            game_object.draw();
        }
        glPopMatrix();
    }

    //TODO add avto detecting optimal working set display, 1600x900x32 or 640x480x16 or ...
    public Render() {
        stateViewPort = Render.VIEWPORT_ON_PLAYER;
        try {
            Display.setFullscreen(Global.FULLSCREEN);


        if (Global.FULLSCREEN){
            Display.create();
        }
        else {
            Display.setDisplayMode(new DisplayMode(Global.RESOLUTION_X,Global.RESOLUTION_Y));
            Display.create();
        }

        

        // Enable vsync if we can
        Display.setVSyncEnabled(true);





        } catch (LWJGLException e) {
            e.printStackTrace();
        }



        //without this part of code render not working correctly!

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, Display.getDisplayMode().getWidth(), 0.0, Display
                .getDisplayMode().getHeight(), -1.0, 1.0);

        //glMatrixMode(GL_MODELVIEW);
        //glLoadIdentity();
        //glViewport(0, 0, Display.getDisplayMode().getWidth(), Display
        //		.getDisplayMode().getHeight());
        //glViewport(0, 0,3200,1800);
        setZoom(1);
    }

    @Override
    public void cleanUp() {
        // Close the window
        Display.destroy();
    }

    public void update() {
        Display.update();
    }

    public float getZoom() {
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

    // TODO move this method in other classs
    public void syncFps(int frameRate) {
        Display.sync(frameRate);
    }
}