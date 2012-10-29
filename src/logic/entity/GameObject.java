/**
 *
 * Basic abstract class for game entity, need extend to writing your entity class.
 * Special for entity: sound effect, render effect,  
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic.entity;

import logic.Level;
import org.jbox2d.dynamics.Body;
import org.lwjgl.util.vector.Vector2f;
import render.RenderObject;

import java.util.Random;

//TODO add modificator visiable to protected code to field and method
abstract public class GameObject {

    //unique name for class, uses by Resourses manager
    //by this name creater pair Class RubberBall - config file RubberBall.ini, RubberBall.jpg
    protected static String name = "unknown";

    protected static Random random = new Random();
    protected Vector2f position;
    protected boolean live = true;
    protected RenderObject renderObject;
    protected Level level;
    protected float liveHealth;
    protected Body body = null;

    abstract public void init();

    abstract public void update();

    abstract public void move();

    abstract public void draw();

    abstract public void playSound();

    // this method for graphical destroying
    abstract public void destroy();

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Body getBody() {
        return body;
    }

    public Level getLevel(){
        return level;
    }
}
