package logic.entity.ship;

import ai.ControlledEntity;
import ai.nnga.Manager;
import controller.InputToAction;
import logic.Level;
import logic.entity.ArsenalGameObject;
import logic.entity.GameObjectMoving;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;
import util.MathUtil;

public class Ship extends GameObjectMoving implements ControlledEntity {
    private float width, height;
    private float radiusBody = 25;
    private float protection = 0.1f;

    private Engine mainEngine;
    private ArsenalGameObject mainWeapon;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private boolean firePrimary = false;
    private boolean fireAlternative = false;

    private boolean speciality = false;

    private boolean stateAutopilot = true;


    /*private static Image image;*/
    static {
        className = "Ship";
    }

    public Ship(Level level, float x, float y) {
        this.level = level;
        position = new Vector2f(x, y);
        speed = new Vector2f(0, 0);
        /*width = 100f;
        height = 40f;            */
        liveHealth = 10000000;
        physicObject = PhysicObject.createBall(this, position, radiusBody,
                Material.Metal);
        physicObject.setAngularDamping(0.1f);
        physicObject.setLinearDamping(0.01f);
        mainEngine = new Engine(this, new Vector2f(position.x, position.y));
        mainWeapon = Weapon.getWeapon(this, "any");
        level.getGameObjects().add(mainWeapon);
    }


    @Override
    public void update() {
        if (firePrimary) {
            mainWeapon.firePrimary();
        }
        if (fireAlternative) {
            mainWeapon.fireAlternative();
        }
        if (speciality) {

        }
        clearFlags();
        if (liveHealth < 0) {
            startDestroy();
        }
    }

    @Override
    public void move() {
        if (stateAutopilot) {

            //autopilot
            if (up) {
                physicObject.applyForce(0, mainEngine.getForce(), position);
            } else {
                if (!down) {
                    if (speed.y < 0) {
                        physicObject.applyForce(0, mainEngine.getForce() / 1.5f, position);
                    }
                }
            }
        } else {
            //free falling
            if (up) {
                physicObject.applyForce(0, mainEngine.getForce(), position);
            }
        }

        if (left) {
            physicObject.applyForce(-mainEngine.getForce() / 4f, 0, position);
        }

        if (right) {
            physicObject.applyForce(mainEngine.getForce() / 4f, 0, position);
        }


        position = null;
        position = physicObject.getPosition();
        speed = null;
        speed = physicObject.getSpeed();
        angle = physicObject.getAngle();
    }

    @Override
    public void draw() {
        /*RenderUtil.drawQaud(position.x, position.y, width, height, angle,
    (Color) Color.GREY);   */
        RenderUtil.drawCircle(position, radiusBody, 6, (Color) Color.RED);
        //RenderUtil.drawImage(position.x, position.y, width, height,angle,1,image);
    }

    @Override
    public void playSound() {
    }

    @Override
    public void toThink() {
        Manager.get().getReaction(this);
    }

    @Override
    public void doAction(int code) {
        // System.out.println("ship.doAction()" + code);
        switch (code) {
            case InputToAction.left: {
                left = true;
                break;
            }
            case InputToAction.right: {
                right = true;
                break;
            }
            case InputToAction.down: {
                down = true;
                break;
            }
            case InputToAction.up: {
                up = true;
                break;
            }

            case InputToAction.firePrimary: {
                firePrimary = true;
                break;
            }

            case InputToAction.fireAlternative: {
                fireAlternative = true;
                break;
            }

            case InputToAction.specialAction: {
                speciality = true;
                break;
            }


            /*
            case InputToAction.left: {
                leftEngineOn = true;
                break;
            }
            case InputToAction.right: {
                rightEngineOn = true;
                break;
            }
            case InputToAction.down: {
                allEngineActive = true;
                break;
            }

            case InputToAction.leftEngineLeft: {
                turnLeftEnginesLeft = true;
                break;
            }

            case InputToAction.leftEngineRight: {
                turnLeftEnginesRight = true;
                break;
            }
            case InputToAction.rightEngineLeft: {
                turnRightEnginesLeft = true;
                break;
            }

            case InputToAction.rightEngineRight: {
                turnRightEnginesRight = true;
                break;
            }

            case InputToAction.leftEngineOn: {
                leftEngineOn = true;
                break;
            }

            case InputToAction.rightEngineOn: {
                rightEngineOn = true;
                break;
            }
            */
        }
    }

    public void clearFlags() {

        left = false;
        right = false;
        up = false;
        down = false;

        firePrimary = false;
        fireAlternative = false;

        speciality = false;

    }

    @Override
    public void destroy() {
        //set live false all component

        mainEngine.setLive(false);
        mainWeapon.setLive(false);

        level.getPlayer().setControlledObject(null);
        physicObject.destroy();
        //EmmiterEffects.drawBoom(position);
        /*level.getNotAddedGameObjects().add(
                new Explosion(level, position, 60, 1)); */
    }

    void startDestroy() {
        live = false;
    }

    public void damage(float... impulses) {
        if (Manager.get().getState() == Manager.TRAINING) {
            liveHealth = -1;
        } else {
            float result = 0;
            for (float f : impulses)
                result += f;
            liveHealth -= result * (1 - protection);
        }

    }
}