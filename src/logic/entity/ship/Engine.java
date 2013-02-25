package logic.entity.ship;

import ai.nnga.Manager;
import logic.entity.EmmiterEffects;
import logic.entity.GameObjectMoving;
import main.Global;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

public class Engine extends GameObjectMoving {

    private final static float SPEED_PARTICLE_FROM_ENGINE = 200f;
    private final static float SPEED_PARTICLE_KOOF_RANDOM = 20f;
    private final static float ENGINE_TURN_VELOCITY = 10f;
    private final static float ENGINE_FORCE = 15;
    private float height = 20f;
    private float width = 10f;

    private Ship fatherObj;
    private Joint engineJoint;

    public Engine(Ship fatherObj, Vector2f position) {
        this.fatherObj = fatherObj;
        this.position = position;
        level = fatherObj.getLevel();
        physicObject = PhysicObject.createBox(this, position, width, height,
                Material.Metal);
        physicObject.getBody().setAngularDamping(50);
        liveHealth = 100;
        live = true;
        angle = 0f;

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(fatherObj.getPhysicObject().getBody(), physicObject.getBody(),
                new Vec2(position.x / 30f, position.y / 30f));
        jointDef.collideConnected = false;
        //jointDef.enableLimit = true;
        // jointDef.lowerAngle = -MAX_ENGINE_ANGLE;
        // jointDef.upperAngle = MAX_ENGINE_ANGLE;
        // jointDef.referenceAngle = 0;

        Joint joint = level.getWorld().createJoint(jointDef);
        setEngineJoint(joint);

        level.getNotAddedGameObjects().add(this);

    }

    @Override
    public void update() {
        if (liveHealth < 0) {
            live = false;
            fatherObj.startDestroy();
        }
    }

    @Override
    public void move() {
        position = physicObject.getPosition();
        physicObject.setAngle(angle+fatherObj.getAngle());

    }

    @Override
    public void draw() {
       /* RenderUtil.drawQaud(position.x, position.y, width, height, angle+fatherObj.getAngle(),
                (Color) Color.BLUE);   */
    }

    @Override
    public void playSound() {

    }

    @Override
    public void toThink() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        engineJoint.destructor();
        physicObject.destroy();
    }

    void enableForce() {
        float forceX = (float) (-ENGINE_FORCE * Math.sin(angle+fatherObj.getAngle()));
        float forceY = (float) (ENGINE_FORCE * Math.cos(angle+fatherObj.getAngle()));
        // Vec2 force = new Vec2((float) (-ENGINE_FORCE * Math.sin(angle)),
        // (float) (ENGINE_FORCE * Math.cos(angle)));
        // // Vec2 pointOfForce = body.getPosition().add(
        // // new Vec2((float) (width / 30 / 2 * Math.sin(angle)),
        // // (float) (-height / 30 / 2 * Math.cos(angle))));
        // Vec2 pointOfForce = new Vec2(position.x / 30f, position.y / 30f);
        physicObject.applyForce(forceX, forceY, position);
        if (Global.realTime){
            EmmiterEffects.drawParticlesFromEngine(position, angle+fatherObj.getAngle());
        }
    }

    void setEngineJoint(Joint joint) {
        this.engineJoint = joint;
    }

    Joint getEngineJoint() {
        return engineJoint;
    }

    public float getAngle() {
        return angle;
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    public void turnByAngle(float angle) {
        this.angle+= angle;
    }

    public void damage(float... impulses) {
        if (Manager.get().getState() == Manager.TRAINING) {
            liveHealth = -1;
        }
        //level.getPlayer().getControlledObject().doAction(InputToAction.death);
        //Manager.get().touchWall();
    }

    float getForce(){
        return 10;
    }

}
