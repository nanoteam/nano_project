package logic.entity;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.lwjgl.util.vector.Vector2f;

import logic.Level;

public class Explosion extends GameObject {

	private float distance;
	private float force;

	// private float lifeTime;

	public Explosion(Level level, Vector2f position, final float distance, final float force) {
		this.level = level;
		this.position = position;
		this.distance = distance / 30;
		this.force = force * 5;
		// this.lifeTime = distance * 30;
        final Vec2 posit2 = new Vec2(position.x / 30f, position.y / 30f);
        Vec2 lowerVertex = new Vec2(posit2.x - distance, posit2.y - distance);
        Vec2 upperVertex = new Vec2(posit2.x + distance, posit2.y + distance);

        AABB aabb = new AABB(lowerVertex, upperVertex);
        QueryCallback callback = new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture arg0) {

                Vec2 fixturePosition = arg0.m_body.getWorldCenter();

                float dx = fixturePosition.x - posit2.x;
                float dy = fixturePosition.y - posit2.y;
                float pr = 0.2f;

                // must be refactoring
                if (dx < pr && dx > 0)
                    dx = pr;
                if (dy < pr && dy > 0)
                    dy = pr;

                if (dx > -pr && dx < 0)
                    dx = -pr;
                if (dy > -pr && dy < 0)
                    dy = -pr;

                Vec2 forceVec = new Vec2(distance / dx, distance / dy);
                arg0.m_body.applyForce(forceVec.mul(force), fixturePosition);

                return true;
            }
        };

        level.getWorld().queryAABB(callback, aabb);
	}

	@Override
	public void update() {
		this.live = false;
	}

	@Override
	public void move() {

	}

	@Override
	public void draw() {
		EmmiterEffects.drawBoom(position);
	}

	@Override
	public void playSound() {

	}

	@Override
	public void destroy() {

	}

}
