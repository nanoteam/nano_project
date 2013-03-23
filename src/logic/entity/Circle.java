package logic.entity;

import logic.Level;
import logic.entity.entityInterface.IsClonable;
import logic.entity.entityInterface.MorfingCreation;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class Circle extends GamePhysicObject implements IsClonable, MorfingCreation {

    protected float radius;
    protected Color color;
    protected int typeBody = PhysicObject.UNKNOW;

    //two phase creation
    public Circle(Vector2f position, float radius, float angle, int typeObject, Color color) {
        this.position = position;
        this.radius = radius;
        this.angle = angle;
        this.typeBody = typeObject;
        this.color = color;
    }

    //easy creation
    public Circle(Vector2f position, float radius, float angle, int typeObject, Color color, Level level) {
        this(position, radius, angle, typeObject, color);
        initInPhysicWorld(level);
    }

    private Circle() {

    }

    public Circle clone() {
        Circle cloneCircle = new Circle();
        cloneCircle.position = new Vector2f(position);
        cloneCircle.radius = radius;
        cloneCircle.angle = angle;
        cloneCircle.typeBody = typeBody;
        cloneCircle.color = new Color(color);
        return cloneCircle;
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
        speed = physicObject.getSpeed();
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
