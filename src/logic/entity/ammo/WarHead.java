package logic.entity.ammo;

import logic.Level;
import logic.entity.GameObjectMoving;
import logic.entity.Particle;
import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

//can be part of complex ammo.
public class WarHead extends GameObjectMoving {
    //not uses now
    private static final float maxSpeed = 100500;
    private int lifeTime;
    private float size;
    private Color color;
    private float radius;
    private static Image image;

    private final int numberCharges;
    //private final GameObjectMoving classCharges;

    public static final float STANDART_MASS = 20;

    static {
        className = "WarHead";
    }

    public static String getName() {
        return className;
    }

    public WarHead(Vector2f pos, Vector2f speed, int numberCharges, Level level) {
        this.position = new Vector2f(pos.x, pos.y);
        this.speed = speed;
        this.size = 5f;
        this.lifeTime = 70 + Global.random.nextInt(10);
        this.color = new Color(Color.ORANGE);
        this.level = level;
        radius = 10;
        this.numberCharges = numberCharges;
        //this.classCharges = classCharges;

        physicObject = PhysicObject.createBall(this, position, radius,
                Material.Metal);
        physicObject.setSpeed(speed);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        /*
           * if (random.nextInt(32) > 24) { level.getNotAddedGameObjects().add(
           * new Particle(new Vector2f(position), new Vector2f((random
           * .nextFloat() - 0.5f) * 100f + speed.x, (random .nextFloat() - 0.5f) *
           * 100f + speed.y), 1, 60, (Color) Color.YELLOW)); }
           */

        lifeTime--;
        if (lifeTime < 0) {
            live = false;
            physicObject.getBody().setActive(false);
            for (int i = 0; i < 10; i++) {
                level.getNotAddedGameObjects()
                        .add(new Particle(new Vector2f(position), new Vector2f(
                                (Global.random.nextFloat() - 0.5f) * 150f,
                                (Global.random.nextFloat() - 0.5f) * 150f), 4, Global.random.nextInt(30) + 20, (Color) Color.ORANGE));
            }
        }
    }

    @Override
    public void move() {
        position = physicObject.getPosition();
        angle = physicObject.getAngle();
    }

    @Override
    public void draw() {
        RenderUtil
                .drawCircle(position.x, position.y, radius, radius, color);
    }

    @Override
    public void playSound() {
        // TODO Auto-generated method stub
    }

    @Override
    public void toThink() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        physicObject.destroy();
        //level.getNotAddedGameObjects().add(new Explosion(level, position, 30, 3));
    }

}
