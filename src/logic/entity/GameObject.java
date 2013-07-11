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
import org.lwjgl.util.vector.Vector2f;

//TODO add modificator visiable to protected code to field and method
abstract public class GameObject {
    protected static long idGlobal = 0;
    // unique name for class, uses by Resourses manager
    // by this name creater pair Class RubberBall - config file RubberBall.ini,
    // RubberBall.jpg
    public final long id;
    protected Vector2f position;
    protected boolean live = true;
    protected Level level;
    protected float maxHP;
    protected float hp;

    public GameObject() {
        id = idGlobal++;
    }

    abstract public void update();

    abstract public void move();

    abstract public void draw();

    abstract public void playSound();

    abstract public void destroy();

    abstract public void toThink();

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Level getLevel() {
        return level;
    }

    abstract public String getAdditionalName();

    abstract public String getMyClassName();

    public float getHp() {
        return hp;
    }

    public float getMaxHP() {
        return maxHP;
    }
}