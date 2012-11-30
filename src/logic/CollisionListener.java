package logic;

import logic.entity.EmmiterEffects;
import logic.entity.TrapRotation;
import logic.entity.ship.Engine;
import logic.entity.ship.Ship;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

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

//		System.out.println("Collision " + nameA + " with " + nameB
//				+ " with impulses " + Arrays.toString(impulses));

        //delete !
        if (nameA.equals("Engine")) {
            ((Engine) bodyA).damage();
        }
        if (nameB.equals("Engine")) {
            ((Engine) bodyB).damage();
        }


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

                EmmiterEffects.drawCollision(points, impulses,
                        SHIP_WITH_WALL);

                if (nameA.equals("Ship")) {
                    ((Ship) bodyA).damage(impulses);
                } else {
                    ((Ship) bodyB).damage(impulses);

                }
            }
            if (nameA.equals("RubberBall") || nameB.equals("RubberBall")) {
                System.out.println("!");
                if (nameA.equals("Ship")) {
                    ((Ship) bodyA).damage(50);
                } else {
                    ((Ship) bodyB).damage(50);

                }
            }
            if (nameA.equals("TrapRotation") || nameB.equals("TrapRotation")) {
                if (nameA.equals("TrapRotation")) {
                    TrapRotation tr = (TrapRotation) bodyA;
                    Ship s = (Ship) bodyB;
                    tr.activateTrap(s.getPhysicObject().getBody(), points[0]);
                } else if (nameB.equals("TrapRotation")) {
                    TrapRotation tr = (TrapRotation) bodyB;
                    Ship s = (Ship) bodyA;
                    tr.activateTrap(s.getPhysicObject().getBody(), points[0]);
                    System.out.println("points is " + Arrays.toString(points));
                }
            }
        }
    }
}