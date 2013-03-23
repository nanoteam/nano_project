package logic.entity;

import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import physic.Material;
import physic.PhysicObject;

import render.RenderUtil;

import logic.Level;

public class Asteroid extends GamePhysicObject {

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
                Material.Stone,PhysicObject.DINAMIC,level.getWorld());

        physicObject.setSpeed(speed);
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
