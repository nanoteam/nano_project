package ai;

import controller.InputToAction;
import logic.entity.ship.Ship;
import main.Global;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BotShip {
    private final static int STATE_MOVE = 0;
    private final static int STATE_ATACK = 1;
    private final static int STATE_RUN_OFF = 2;
    private int state;
    private Ship ship;

    private boolean haveCollision;
    //general
    private static ArrayDeque<Scream> screams = new ArrayDeque<Scream>();
    private static int newScreams = 0;

    //^2
    private final static float MAX_DISTANCE_FOR_SCREAM = 600 * 600;
    private final static float MAX_SPEED = 400f;

    public BotShip(Ship ship) {
        this.ship = ship;
    }

    public void toThink() {
        switch (state) {
            case STATE_MOVE: {
                move();

                if (screams.size() != 0) {
                    for (Scream scream : screams) {
                        if (scream.getTypeScream() == Scream.SCREAM_ATACK) {
                            if ((distance(scream.getVector2f(), ship.getPosition()) < MAX_DISTANCE_FOR_SCREAM) && (state == STATE_MOVE)) {
                                scream();
                                state = STATE_ATACK;
                            }
                        }
                    }
                }
                if (seeEnemy()) {
                    scream();
                    state = STATE_ATACK;
                }
                break;
            }
            case STATE_ATACK: {
                if (readyToShoot()) {
                    shoot();
                }
                //if hp<20% run off
                if (ship.getHp() / ship.getMaxHP() < 0.2f) {
                    state = STATE_RUN_OFF;
                }
                break;
            }
            case STATE_RUN_OFF: {
                //if hp>50% go atack
                if (ship.getHp() / ship.getMaxHP() > 0.5f) {
                    state = STATE_ATACK;
                }
                break;
            }
        }
    }

    private void move() {
        if (ship.getSpeed().lengthSquared() < MAX_SPEED) {
            //float angleDirection = ship.getAngleWeapon() + (Global.random.nextFloat() * 0.6f - 0.3f);
            float angleDirection = (float) Math.atan2(ship.getLevel().getPlayer().getControlledObject().getPosition().y - ship.getPosition().y,
                    ship.getLevel().getPlayer().getControlledObject().getPosition().x - ship.getPosition().x);

            System.out.println("**");
            System.out.println(angleDirection);
            if (angleDirection < 0) {
                angleDirection += 2 * 3.14159f;
            }
            if (angleDirection > 2 * 3.14159f) {
                angleDirection -= 2 * 3.14159f;
            }
            System.out.println(angleDirection);
            System.out.println("**");
            //1
            if ((angleDirection < 3.14159f / 3) && (angleDirection > -3.14159f / 3)) {
                System.out.println("right");
                ship.doAction(InputToAction.right);
            }
            //2
            if ((angleDirection < 5 * 3.14159f / 6) && (angleDirection > 3.14159f / 6)) {
                System.out.println("up");
                ship.doAction(InputToAction.up);
            }
            //3
            if ((angleDirection < 4 * 3.14159f / 3) && (angleDirection > 2 * 3.14159f / 3)) {
                System.out.println("left");
                ship.doAction(InputToAction.left);
            }
            //4
            if ((angleDirection < 11 * 3.14159f / 12) && (angleDirection > 7 * 3.14159f / 6)) {
                System.out.println("down");
                ship.doAction(InputToAction.down);
            }
        }
        //rotate
        /*if (Global.random.nextFloat() > 0.005f) {
            if (Global.random.nextFloat() > 0.5f) {
                ship.doAction(InputToAction.rotateWeaponUp);
            } else {
                ship.doAction(InputToAction.rotateWeaponDown);
            }
        }*/


    }

    private boolean seeEnemy() {
        //ship.getPhysicObject().getBody().shouldCollide()
        return false;
    }

    private boolean readyToShoot() {
        return false;
    }

    private void shoot() {

    }

    private void scream() {
        screams.push(new Scream(ship.getPosition(), Scream.SCREAM_ATACK, ship.id));
        newScreams++;
    }

    private void clearingScreams() {
        while (screams.size() != newScreams) {
            screams.pop();
        }
        newScreams = 0;
    }

    //return distance ^ 2
    private float distance(Vector2f vector1, Vector2f vector2) {
        return ((vector1.x - vector2.x) * (vector1.x - vector2.x) + (vector1.y - vector2.y) * (vector1.y - vector2.y));
    }
}
