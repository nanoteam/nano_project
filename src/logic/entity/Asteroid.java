package logic.entity;

import logic.Level;
import logic.entity.entityInterface.IsClonable;
import logic.entity.entityInterface.MorfingCreation;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class Asteroid extends GamePhysicObject implements IsClonable, MorfingCreation {

    protected float radius;
    protected Color color;
    protected int typeBody = PhysicObject.UNKNOW;

    //two phase creation
    public Asteroid(Vector2f position, float radius, float angle, int typeObject, Color color) {
        this.position = position;
        this.radius = radius;
        this.angle = angle;
        this.typeBody = typeObject;
        this.color = color;
    }

    //easy creation
    public Asteroid(Vector2f position, float radius, float angle, int typeObject, Color color, Level level) {
        this.position = position;
        this.radius = radius;
        this.angle = angle;
        this.typeBody = typeObject;
        this.color = color;
        initInPhysicWorld(level);
    }


    private Asteroid() {

    }

    public Asteroid clone() {
        Asteroid cloneAsteroid = new Asteroid();
        cloneAsteroid.position = new Vector2f(position);
        cloneAsteroid.radius = radius;
        cloneAsteroid.angle = angle;
        cloneAsteroid.typeBody = typeBody;
        cloneAsteroid.color = new Color(color);
        return cloneAsteroid;
    }


    @Override
    public void initInPhysicWorld(Level level) {
        this.level = level;
        this.physicObject = PhysicObject.createBall(this, position, radius, Material.Wood, typeBody, level.getWorld());
    }


    @Override
    public void update() {

    }

    @Override
    public void move() {
        position = physicObject.getPosition();
        angle = physicObject.getAngle();
        physicObject.applyForce(-level.getGravity().x * physicObject.getMass(), -level.getGravity().y * physicObject.getMass(), position);
    }

    @Override
    public void draw() {
        RenderUtil.drawCircle(position, radius, 4, color);
    }

    @Override
    public void playSound() {

    }

    @Override
    public void toThink() {
    }

    @Override
    public void destroy() {
        physicObject.destroy();
    }

    public int getTypeBody() {
        return typeBody;
    }

    public void setTypeBody(int typeBody) {
        this.typeBody = typeBody;
    }
}
