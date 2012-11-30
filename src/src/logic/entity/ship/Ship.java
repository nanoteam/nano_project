package logic.entity.ship;

import ai.ControlledEntity;
import ai.commands.Command;
import ai.nnga.Manager;
import controller.InputToAction;
import logic.Level;
import logic.entity.ArsenalGameObject;
import logic.entity.GameObjectMoving;
import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Ship extends GameObjectMoving implements ControlledEntity {
    // flags
    private boolean leftEngineOn = false;
    private boolean rightEngineOn = false;
    private boolean allEngineActive = false;

    private boolean turnLeftEnginesLeft = false;
    private boolean turnLeftEnginesRight = false;

    private boolean turnRightEnginesLeft = false;
    private boolean turnRightEnginesRight = false;

    private boolean weapon1Shot = false;
    private boolean weapon2Shot = false;

    Deque<Command> aiCommand = new ArrayDeque<Command>();

    private float width, height;
    private float protection = 0.1f;

    private static Image image;

    private static Vector2f ENGINE_POSITION = new Vector2f(50f, 0f);
    //component
    private Engine leftEngine, rightEngine;
    private List<ArsenalGameObject> arsenalList = new ArrayList<ArsenalGameObject>();

    static {
        name = "Ship";
    }

    public Ship(Level level, float x, float y) {
        this.level = level;
        position = new Vector2f(x, y);
        speed = new Vector2f(0, 0);
        width = 100f;
        height = 40f;
        liveHealth = 1000;
        physicObject = PhysicObject.createBox(this, position, width, height,
                Material.Metal);
        /*
        {
            // adding weapons

            Weapon weap1 = new Weapon(this, 20, 3, 1, 0);
            arsenalList.add(weap1);
            level.getGameObjects().add(weap1);

            Weapon weap2 = new Weapon(this, 10, 5, 2, 70);
            arsenalList.add(weap2);
            level.getGameObjects().add(weap2);
        } */

        {
            // adding engines

            leftEngine = new Engine(this, new Vector2f(position.x
                    - ENGINE_POSITION.x, position.y - ENGINE_POSITION.y));

            rightEngine = new Engine(this, new Vector2f(position.x
                    + ENGINE_POSITION.x, position.y - ENGINE_POSITION.y));
        }

        // delete, when complite ersourses manager \/
        /*if (image == null) {
            try {
                image = new Image("D:/ship.png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }      */
    }


        @Override
        public void update () {

            if (weapon1Shot && !arsenalList.isEmpty()) {
                arsenalList.get(0).setShootOn();
            }
            if (weapon2Shot && !arsenalList.isEmpty()) {
                arsenalList.get(1).setShootOn();
            }
            clearFlags();
            if (liveHealth < 0) {
                startDestroy();
            }
        }

        @Override
        public void move () {
            position = null;
            position = physicObject.getPosition();
            speed = null;
            speed = physicObject.getSpeed();
            angle = physicObject.getAngle();

            if (allEngineActive) {
                leftEngine.enableForce();
                rightEngine.enableForce();
            }
            if (rightEngineOn) {
                rightEngine.enableForce();
            }
            if (leftEngineOn) {
                leftEngine.enableForce();
            }
            if (turnLeftEnginesLeft) {
                leftEngine.turnByAngle(0.1f);
            }
            if (turnLeftEnginesRight) {
                leftEngine.turnByAngle(-0.1f);
            }
            if (turnRightEnginesLeft) {
                rightEngine.turnByAngle(0.1f);
            }
            if (turnRightEnginesRight) {
                rightEngine.turnByAngle(-0.1f);
            }


        }

        @Override
        public void draw () {
            RenderUtil.drawQaud(position.x, position.y, width, height, angle,
                    (Color) Color.GREY);
            //RenderUtil.drawImage(position.x, position.y, width, height,angle,1,image);
        }

        @Override
        public void playSound () {
        }

        @Override
        public void toThink () {
            Manager.get().getReaction(this);
        }

        @Override
        public void doAction ( int code){
            // System.out.println("ship.doAction()" + code);
            switch (code) {
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
            }
        }

    public void clearFlags() {
        allEngineActive = false;
        leftEngineOn = false;
        rightEngineOn = false;
        weapon1Shot = false;
        weapon2Shot = false;
        turnLeftEnginesLeft = false;
        turnLeftEnginesRight = false;
        turnRightEnginesLeft = false;
        turnRightEnginesRight = false;
    }

    @Override
    public void destroy() {
        leftEngine.setLive(false);
        rightEngine.setLive(false);
        for (ArsenalGameObject arsenalGameObject : arsenalList) {
            arsenalGameObject.setLive(false);
        }
        level.getPlayer().setControlledObject(null);
        physicObject.destroy();
        //EmmiterEffects.drawBoom(position);
        /*level.getNotAddedGameObjects().add(
                new Explosion(level, position, 60, 1)); */
    }

    void startDestroy() {
        live = false;
        leftEngine.setLive(false);
        rightEngine.setLive(false);
        if (arsenalList != null && arsenalList.size() != 0) {
            for (GameObjectMoving arsenalObj : arsenalList) {
                arsenalObj.setLive(false);
            }
        }
        Manager.get().nextTest();
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

    public float getAngleLeftEngine() {
        return leftEngine.getAngle();
    }

    public float getAngleRightEngine() {
        return rightEngine.getAngle();
    }

    public void pushCommand(Command command) {

    }

    public Command popCommand() {
        return null;
    }

    public void clearCommand() {

    }

    public void addChaosInMovement() {
        //System.out.println("chaos!!!");
        physicObject.applyForce((Global.random.nextFloat() - 0.5f) * 20f, (Global.random.nextFloat() - 0.5f) * 20f, new Vector2f((Global.random.nextFloat() * width), (Global.random.nextFloat() * height)));
        //physicObject.setAngle(((Global.random.nextFloat() - 0.5f) * 3.14159f) / 3f);
        //physicObject.setSpeed(new Vector2f(Global.random.nextInt(100) - 50f, Global.random.nextInt(100) - 50f));
        //physicObject.setAngularVelocity((Global.random.nextFloat() - 0.5f) * 2f);
    }
}