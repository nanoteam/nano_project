package logic.entity;

import java.util.Random;

import main.Global;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import physic.Material;
import physic.PhysicObject;

import render.RenderUtil;

import logic.Level;

public class Asteroid extends GameObjectMoving {

	private float radius;
	private int MAX_SPEED = 50;

	public Asteroid(Level level, Vector2f position) {
		this.position = position;
		this.level = level;
		this.radius = 30;
		this.angle = 0f;
        this.speed = new Vector2f((float) MAX_SPEED
                - Global.random.nextInt(MAX_SPEED * 2), (float) MAX_SPEED
                - Global.random.nextInt(MAX_SPEED * 2));

        this.physicObject = PhysicObject.createBall(this, position, radius,
                Material.Stone,PhysicObject.DINAMIC);

        physicObject.setSpeed(speed);
	}

	@Override
	public void update() {

	}

	@Override
	public void move() {
		position = physicObject.getPosition();
		angle = physicObject.getAngle();
		physicObject.applyForce(0,
				-level.getGravity() * physicObject.getMass(), position);
	}

	@Override
	public void draw() {

		RenderUtil.drawCircle(position, radius, 4,
				(Color) Color.RED);
	}

	@Override
	public void playSound() {

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
