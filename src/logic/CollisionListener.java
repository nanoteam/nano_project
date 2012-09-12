package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logic.entity.Particle;
import logic.entity.RubberBall;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.Manifold.ManifoldType;
import org.jbox2d.collision.ManifoldPoint;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class CollisionListener implements ContactListener {

	private Level level = null;

	public CollisionListener(logic.Level level) {
		this.level = level;
		Manifold manifold = new Manifold();
	}

	@Override
	public void beginContact(Contact contact) {
		WorldManifold worldManifold = new WorldManifold();
		contact.getWorldManifold(worldManifold);
		Vec2[] points = worldManifold.points;

		// TODO check bodyes

		addParticles(points, 0);

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	private void addParticles(Vec2[] points, int type) {
		Vector2f speed;
		if (points.length == 0)
			return;
		for (Vec2 v : points) {
			speed = new Vector2f(20f - (float) Math.random() * 40f,
					20f - (float) Math.random() * 40f);

			level.getNotAddedGameObjects().add(
					new Particle(new Vector2f(v.x * 30, v.y * 30), speed, 2,
							120, (Color) Color.WHITE));
			speed = new Vector2f(20f - (float) Math.random() * 40f,
					20f - (float) Math.random() * 40f);
			level.getNotAddedGameObjects().add(
					new Particle(new Vector2f(v.x * 30, v.y * 30), speed, 2,
							120, (Color) Color.RED));

		}
	}

}
