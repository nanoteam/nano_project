package logic.entity;

import logic.Level;
import org.jbox2d.dynamics.BodyType;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class Wall extends GameObjectMoving {
    float height, width;
    float wallRestitution;
    float angle;

    public Wall(Level level, float x, float y, float width, float height, float angle) {
        this.level = level;
        this.position = new Vector2f(x, y);
        this.height = height;
        this.width = width;
        this.angle = angle;
        wallRestitution = 0;
        physicObject = PhysicObject.createBox(this, position, width, height, angle, Material.Wood);
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
