package logic.entity.ammo;

import logic.Level;
import logic.entity.Explosion;
import logic.entity.GameObjectMoving;
import logic.entity.Particle;
import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class ClusterBomb extends GameObjectMoving {
    //not uses now
    private static final float maxSpeed = 100500;
    private int lifeTime;
    private float size;
    private Color color;
    private float radius;
    private static Image image;

    private final int numberCharges;
//    /private final GameObjectMoving classCharges;

    public static final float STANDART_MASS = 100;

    static {
        name = "RubberBall";
    }

    public static String getName() {
        return RubberBall.name;
    }
    /*
    public RubberBall(Vector2f pos, Level level){
        this.position = new Vector2f(pos.x, pos.y);
        this.level = level;
    } */

    public ClusterBomb(Vector2f pos, Vector2f speed, int numberCharges, Level level) {
        this.position = new Vector2f(pos.x, pos.y);
        this.speed = speed;
        this.size = 5f;
        this.lifeTime = 200 + Global.random.nextInt(50);
        this.color = new Color(Color.DKGREY);
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


        level.getNotAddedGameObjects().add(
                new Particle(
                        new Vector2f(position),
                        new Vector2f((Global.random.nextFloat() - 0.5f) * 100f + speed.x, (Global.random.nextFloat() - 0.5f)* 100f + speed.y),
                        1, 60, (Color) Color.GREY));


        lifeTime--;
        if (lifeTime < 0) {
            live = false;
            physicObject.getBody().setActive(false);
            /*
                * level.getNotAddedGameObjects().add( new PlazmaBall(new
                * Vector2f(position), (float) (random .nextInt(360)), 0, level));
                *
                * level.getNotAddedGameObjects().add( new PlazmaBall(new
                * Vector2f(position), (float) (random .nextInt(360)), 0, level));
                */
            /*
            for (int i = 0; i < 2; i++) {
                level.getNotAddedGameObjects()
                        .add(new RubberBall(new Vector2f(position), new Vector2f(
                                (Global.random.nextFloat() - 0.5f) * 25f + speed.x,
                                (Global.random.nextFloat() - 0.5f) * 25f + speed.y),level));
            } */
        }
    }

    @Override
    public void move() {
        position = physicObject.getPosition();
        angle = physicObject.getAngle();
    }

    @Override
    public void draw() {
        // RenderUtil.drawImage(position.x, position.y,width,
        // height,angle,0.5f,image );
        RenderUtil
                .drawCircle(position.x, position.y, radius, radius , color);
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
        level.getNotAddedGameObjects().add(
                new Explosion(level, position, 30, 3));

    }
}
