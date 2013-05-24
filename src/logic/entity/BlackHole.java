package logic.entity;

import logic.Level;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.PhysicObject;
import render.RenderUtil;

/**
 * Created with IntelliJ IDEA.
 * User: arthur_
 * Date: 24.03.13
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class BlackHole extends GameObject {

    protected Color color;
    private float distance;
    private float force;
    private int impulsePeriod; // interval of time for gravity impulses action
    private int timeBeforeAction;


    // private float lifeTime;

    public BlackHole(Level level, Vector2f position, final float distance, final float force) {
        this.level = level;
        this.position = position;
        this.distance = distance / 30f;
        this.force = force;
        this.color = (Color) Color.GREY;
        impulsePeriod = 2;
        timeBeforeAction = impulsePeriod;
        level.getNotAddedGameObjects().add(new Circle(new Vector2f(position),50,0, PhysicObject.STATIC,(Color)Color.DKGREY,level));
        // this.lifeTime = distance * 30;

    }

    @Override
    public void update() {
        if (--timeBeforeAction < 0) {
            ringRound();
            timeBeforeAction = impulsePeriod;
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {
        RenderUtil.drawCircle(position, distance * 30, 4, color);

        /*RenderUtil.drawCircle(position, distance * 20, 4, color);
        RenderUtil.drawCircle(position, distance * 10, 4, color);*/
    }

    @Override
    public void playSound() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void toThink() {
    }

    public void ringRound() {
        final Vec2 posit2 = new Vec2(position.x / 30f, position.y / 30f);
        Vec2 lowerVertex = new Vec2(posit2.x - distance, posit2.y - distance);
        Vec2 upperVertex = new Vec2(posit2.x + distance, posit2.y + distance);

        AABB aabb = new AABB(lowerVertex, upperVertex);
        QueryCallback callback = new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture arg0) {

                Vec2 fixturePosition = arg0.m_body.getPosition();

                float dx = fixturePosition.x - posit2.x;
                float dy = fixturePosition.y - posit2.y;

                //System.out.println(arg0.m_body.getMass());
                float Fo = - distance*arg0.m_body.getMass()*100f / (dx*dx+dy*dy);

                float angle = (float) Math.atan2(dy,dx);

                Vec2 forceVec = new Vec2((float) Math.cos(angle)*Fo, (float) Math.sin(angle)*Fo);

                arg0.m_body.applyForce(forceVec.mul(force), fixturePosition);

                return true;
            }
        };

        level.getWorld().queryAABB(callback, aabb);
    }
}