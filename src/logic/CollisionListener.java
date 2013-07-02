package logic;

import logic.entity.GamePhysicObject;
import logic.entity.TrapRotation;
import logic.entity.ship.Engine;
import logic.entity.ship.Ship;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import physic.Material;
import physic.PhysicObject;

import render.PopUpText;

import java.util.Arrays;

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

		if (impulses[0] < 1f) {
			// simple contact
		} else {

			String popUpText = "Collision:'" + nameA + "'x'" + nameB + "' "
					+ impulses[0];

			level.getNotAddedGameObjects().add(
					new PopUpText(points[0].x * 30, points[0].y * 30 + 30,
							popUpText, org.newdawn.slick.Color.green, 1f));

			Material materialA = ((GamePhysicObject) bodyA).getPhysicObject()
					.getMaterial();
			Material materialB = ((GamePhysicObject) bodyB).getPhysicObject()
					.getMaterial();


			if (nameA.equals("Ship") || nameB.equals("Ship")) {
				// will be added 'else's in future for more optimization
				if (nameA.equals("Ship") && nameB.equals("Ship")) {
					level.getEmitterEffects().drawCollision(points, impulses,
							CollisionListener.SHIP_WITH_SHIP);

					// TODO damage method in super class
					((Ship) bodyA).damage(materialA, impulses);
					((Ship) bodyB).damage(materialB, impulses);

				}
				if (nameA.equals("Polygon") || nameB.equals("Polygon")) {

					level.getEmitterEffects().drawCollision(points, impulses,
							SHIP_WITH_WALL);

					if (nameA.equals("Ship")) {
						((Ship) bodyA).damage(materialB, impulses);
					} else {
						((Ship) bodyB).damage(materialA, impulses);

					}
				}
				if (nameA.equals("RubberBall") || nameB.equals("RubberBall")) {
					if (nameA.equals("Ship")) {
						((Ship) bodyA).damage(materialB, 50);
					} else {
						((Ship) bodyB).damage(materialA, 50);

					}
				}
				if (nameA.equals("TrapRotation")
						|| nameB.equals("TrapRotation")) {
					if (nameA.equals("TrapRotation")) {
						TrapRotation tr = (TrapRotation) bodyA;
						Ship s = (Ship) bodyB;
						tr.activateTrap(s.getPhysicObject().getBody(),
								points[0]);
					} else if (nameB.equals("TrapRotation")) {
						TrapRotation tr = (TrapRotation) bodyB;
						Ship s = (Ship) bodyA;
						tr.activateTrap(s.getPhysicObject().getBody(),
								points[0]);
						System.out.println("points is "
								+ Arrays.toString(points));
					}
				}
			}
		}
	}
}