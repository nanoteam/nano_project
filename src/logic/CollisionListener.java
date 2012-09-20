package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.util.List;

import java.util.Vector;

import logic.entity.EmmiterEffects;
import logic.entity.Particle;
import logic.entity.RubberBall;
import logic.entity.Ship;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.Manifold.ManifoldType;
import org.jbox2d.collision.ManifoldPoint;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.pooling.arrays.FloatArray;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class CollisionListener implements ContactListener {

	public static final int SHIP_WITH_SHIP = 1;
	public static final int SHIP_WITH_WALL = 2;
	private boolean isCollision = false;

	private Level level = null;

	public CollisionListener(logic.Level level) {
		this.level = level;

		// Manifold manifold = new Manifold();

	}

	@Override
	public void beginContact(Contact contact) {

		isCollision = true;

		// WorldManifold worldManifold = new WorldManifold();
		// contact.getWorldManifold(worldManifold);
		// Vec2[] points = worldManifold.points;

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

		if (!isCollision)
			return;
		WorldManifold worldManifold = new WorldManifold();
		contact.getWorldManifold(worldManifold);
		Vec2[] points = worldManifold.points;
		float[] impulses = impulse.normalImpulses;

		Object bodyA = contact.m_fixtureA.m_body.m_userData;
		Object bodyB = contact.m_fixtureB.m_body.m_userData;
		String nameA = bodyA.getClass().getSimpleName();
		String nameB = bodyB.getClass().getSimpleName();

		System.out.println("Collision " + nameA + " with " + nameB
				+ " with impulses " + Arrays.toString(impulses));

		if (nameA.equals("Ship") || nameB.equals("Ship")) {
			// will be added 'else's in future for more optimization
			if (nameA.equals("Ship") && nameB.equals("Ship")) {
				EmmiterEffects.drawCollision(points, impulses,
						CollisionListener.SHIP_WITH_SHIP);

				// TODO damage method in super class
				((Ship) bodyA).damage(impulses);
				((Ship) bodyB).damage(impulses);

			}
			if (nameA.equals("Wall") || nameB.equals("Wall")) {

				// TODO nono, not good
				float maxF = 0;
				for (float f : impulses)
					if (f > maxF)
						maxF = f;
				if (maxF < 1f)
					return;

				EmmiterEffects.drawCollision(points, impulses, SHIP_WITH_WALL);
				if (nameA.equals("Ship")) {
					((Ship) bodyA).damage(impulses);
				} else {
					((Ship) bodyB).damage(impulses);

				}
			}
			if (nameA.equals("RubberBall") || nameB.equals("RubberBall")) {
				if (nameA.equals("Ship")) {
					((Ship) bodyA).damage(50);
				} else {
					((Ship) bodyB).damage(50);

				}
			}
		}
	}

}
