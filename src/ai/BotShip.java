package ai;

import controller.InputToAction;
import logic.entity.ship.Ship;
import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.RayCastClosestCallback;

import java.util.*;

public class BotShip {
    private final static int STATE_MOVE = 0;
    private final static int STATE_ATACK = 1;
    private final static int STATE_RUN_OFF = 2;
    //^2
    private final static float MAX_DISTANCE_FOR_SCREAM = 600;
    private final static float MAX_SPEED = 50f;
    //general
    private static ArrayDeque<Scream> screams = new ArrayDeque<Scream>();
    private static int newScreams = 0;
    private int state = STATE_MOVE;
    private Ship ship;
    private boolean haveCollision;

    public BotShip(Ship ship) {
        this.ship = ship;
    }

    public void toThink() {
        switch (state) {
            case STATE_MOVE: {
                move();
                //screams from prev step handler by there
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

                /*if (new Random().nextFloat() > 0.99f) {

                } */


                if (seeEnemy()) {
                    scream();
                    //state = STATE_ATACK;
                }
                break;
            }
            case STATE_ATACK: {
                if (readyToShoot()) {
                    shoot();
                }
                if (new Random().nextFloat() > 0.995) {
                    scream();
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
        //if (ship.getSpeed().lengthSquared() < MAX_SPEED) {
        //float angleDirection = ship.getAngleWeapon() + (Global.random.nextFloat() * 0.6f - 0.3f);
        float directionX = ship.getPosition().x - ship.getLevel().getPlayer().getControlledObject().getPosition().x;
        float directionY = ship.getPosition().y - ship.getLevel().getPlayer().getControlledObject().getPosition().y;

        float distance = directionX * directionX + directionY * directionY;
        //if distanse between players ship and bot ship > 20 000 (sqrt(100*100+100*100))
        if (distance > 20000) {
            //if need go left
            if ((directionX > 0)) {
                //and speed x no max, add speed
                if (ship.getSpeed().x > MAX_SPEED * -1) {
                    ship.doAction(InputToAction.left);
                }
            } else
            //nead go right
            {
                if (ship.getSpeed().x < MAX_SPEED) {
                    ship.doAction(InputToAction.right);
                }
            }
            if ((directionY > 0)) {
                if (ship.getSpeed().y > MAX_SPEED * -1) {
                    ship.doAction(InputToAction.down);
                }
            } else {
                if (ship.getSpeed().y < MAX_SPEED) {
                    ship.doAction(InputToAction.up);
                }
            }
        } else {

        }


        //}


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
        RayCastClosestCallback rayCastClosestCallback = new RayCastClosestCallback();

        ship.getLevel().getWorld().raycast(rayCastClosestCallback,
                ship.getPhysicObject().getBody().getPosition(), Global.convertToVec2(ship.getLevel().getPlayer().getControlledObject().getPosition()));

        if (rayCastClosestCallback.isVisible()) {
            scream();
        }

        //ship.getPhysicObject().getBody().shouldCollide()
        return false;
    }

    private boolean readyToShoot() {
        return false;
    }

    private void shoot() {

    }

    private void scream() {
        ship.getLevel().getEmitterEffects().drawAura(ship.getPosition(), (int) MAX_DISTANCE_FOR_SCREAM, 30, (Color) Color.RED);
        /*screams.push(new Scream(ship.getPosition(), Scream.SCREAM_ATACK, ship.id));
        newScreams++;                                                                */
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
