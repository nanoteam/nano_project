package logic.entity;

import logic.Level;
import org.jbox2d.dynamics.BodyType;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class Wall extends GamePhysicObject {
    float height, width;
    float wallRestitution;
    float angle;

    //by one point and angle
    public Wall(Level level, float x, float y, float width, float height, float angle) {
        this.level = level;
        this.position = new Vector2f(x, y);
        this.height = height;
        this.width = width;
        this.angle = angle;
        wallRestitution = 0;
        physicObject = PhysicObject.createBox(this, position, width, height, angle, Material.Wood,PhysicObject.DINAMIC,level.getWorld());
        physicObject.getBody().setType(BodyType.STATIC);
    }

    //by two point
    public Wall(float x1, float y1, float x2, float y2, float height, Level level) {
        this.level = level;
        this.position = new Vector2f((x1 + x2) / 2f, (y1 + y2) / 2f);
        this.width = (float) (Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
        this.height = height;
        this.angle = (float) (Math.atan2(y2 - y1, x2 - x1));
        wallRestitution = 0;
        physicObject = PhysicObject.createBox(this, position, width, height, angle, Material.Wood,PhysicObject.DINAMIC,level.getWorld());
        physicObject.getBody().setType(BodyType.STATIC);
    }


    @Override
    public void update() {

    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {
        RenderUtil.drawQaud(new Vector2f(position), width, height, angle, (Color) Color.WHITE);
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
    }

}
