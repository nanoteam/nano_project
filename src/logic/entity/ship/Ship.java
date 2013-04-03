package logic.entity.ship;

import ai.ControlledEntity;
import ai.nnga.Manager;
import controller.InputToAction;
import logic.Level;
import logic.entity.GamePhysicObject;
import logic.entity.entityInterface.IsClonable;
import logic.entity.entityInterface.MorfingCreation;
import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

import java.util.ArrayDeque;
import java.util.LinkedList;

public class Ship extends GamePhysicObject implements ControlledEntity, MorfingCreation, IsClonable {
    private float width, height;
    private float radiusBody = 25;
    private float protection = 0.1f;

    private Engine mainEngine;
    private LinkedList<Weapon> listWeapon;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private boolean firePrimary = false;
    private boolean fireAlternative = false;

    private boolean speciality = false;

    private boolean stateAutopilot = true;

    private int rotateListWeapon = 0;


    /*private static Image image;*/
    static {
        className = "Ship";
    }

    public Ship(Vector2f position) {
        this.position = new Vector2f(position);
    }

    public Ship(Level level, Vector2f position) {
        this(position);
        initInPhysicWorld(level);
    }

    private Ship() {

    }

    public void initInPhysicWorld(Level level) {
        this.level = level;
        this.speed = new Vector2f(0, 0);
        liveHealth = 10000000;
        physicObject = PhysicObject.createBall(this, position, radiusBody,
                Material.Metal, PhysicObject.DINAMIC, level.getWorld());
        physicObject.setAngularDamping(0.5f);
        physicObject.setLinearDamping(0.01f);
        mainEngine = new Engine(this, new Vector2f(position.x, position.y));
        if (Global.shipHaveAllWeapon) {
            listWeapon = new LinkedList<Weapon>(Weapon.getAllWeapon(this));
            for (Weapon weapon : listWeapon) {
                level.getNotAddedGameObjects().add(weapon);
            }
        } else {
            listWeapon = new LinkedList<Weapon>();
            listWeapon.add(Weapon.getWeapon(this, "any"));
            level.getNotAddedGameObjects().add(listWeapon.element());
        }
        level.getPlayer().setControlledObject(this);
    }

    public Ship clone() {
        Ship ship = new Ship();
        ship.position = new Vector2f(this.position.x, this.position.y);
        return ship;
    }

    @Override
    public void update() {
        if (rotateListWeapon>0){
            listWeapon.addLast(listWeapon.pollFirst());
        }

        if (rotateListWeapon<0){
            listWeapon.addFirst(listWeapon.pollLast());
        }

        if (firePrimary) {
            listWeapon.getFirst().firePrimary();
        }
        if (fireAlternative) {
            listWeapon.getFirst().fireAlternative();
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

        /*
        RenderUtil.drawLine(
                new Vector2f(position.x + MathUtil.newXTurn(30, 0, angle), position.y + MathUtil.newYTurn(30, 0, angle)),
                new Vector2f(position.x, position.y), 5, (Color) Color.WHITE);*/
        //RenderUtil.drawImage(position.x, position.y, width, height,angle,1,image);
    }

    @Override
    public void playSound() {
    }

    @Override
    public void toThink() {
        //Manager.get().getReaction(this);
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
            case InputToAction.previousWeapon: {
                rotateListWeapon = -1;
                break;
            }
            case InputToAction.nextWeapon: {
                rotateListWeapon = 1;
                break;
            }
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

        rotateListWeapon = 0;
    }

    @Override
    public void destroy() {
        //set live false all component

        mainEngine.setLive(false);
        for (Weapon weapon : listWeapon) {
            weapon.setLive(false);
        }
        level.getPlayer().setControlledObject(null);
        physicObject.destroy();
        //EmitterEffects.drawBoom(position);
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