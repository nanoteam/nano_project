/**
 *
 * This class in future was extends for support JBox2D
 * and this class was using in game entitys
 *
 * @author Arthur
 * @version 1.0
 */
package physic;

import logic.entity.GameObject;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

public class PhysicObject {
    private Body body;
    private Material material;
    private GameObject gameObject;

    private PhysicObject(GameObject gameObject, Body body, Material material) {
        this.body = body;
        this.material = material;
        this.gameObject = gameObject;
    }

    public Vector2f getSpeed() {
        return new Vector2f(body.m_linearVelocity.x * 30,
                body.getLinearVelocity().y * 30);
    }

    public void setSpeed(Vector2f newSpeed) {
        body.setLinearVelocity(new Vec2(newSpeed.x / 30, newSpeed.y / 30));
    }

    public void setAngularVelocity(float w) {
        body.setAngularVelocity(w);
    }

    public float getAngularVelocity(){
        return body.getAngularVelocity();
    }

    public Vector2f getPosition() {
        return new Vector2f(body.getPosition().x * 30,
                body.getPosition().y * 30);
    }

    public void setPosition(Vector2f newPosition) {
        body.setTransform(new Vec2(newPosition.x / 30, newPosition.y / 30),
                body.getAngle());
    }

    public float getAngle() {
        return body.getAngle();
    }

    public void setAngle(float newAngle) {
        body.setTransform(body.getPosition(), newAngle);
    }

    public float getMass() {
        return body.getMass();
    }

    public Body getBody() {
        return body;
    }

    public void applyForce(float forceX, float forceY, Vector2f point) {
        Vec2 force = new Vec2(forceX, forceY);
        Vec2 pointOfForce = new Vec2(point.x / 30, point.x / 30);
        body.applyForce(force, pointOfForce);
    }

    public void destroy() {
        body.destroyFixture(body.getFixtureList());
        body.getWorld().destroyBody(body);

        // TODO delete object from game, maybe this method must be static and
        // PhysicObject as argument
    }

    public static PhysicObject createBall(GameObject gameObject,
                                          Vector2f position, float radius, Material material) {

        BodyDef objectDef = new BodyDef();
        objectDef.position.set(new Vec2(position.x / 30, position.y / 30));
        objectDef.type = BodyType.DYNAMIC;

        CircleShape objectShape = new CircleShape();
        objectShape.m_radius = radius / 30;

        Body body = gameObject.getLevel().getWorld().createBody(objectDef);
        body.m_userData = gameObject;

        FixtureDef objectFixture = new FixtureDef();
        objectFixture.friction = material.friction;
        objectFixture.density = material.density;
        objectFixture.restitution = material.restitution;
        objectFixture.shape = objectShape;
        body.createFixture(objectFixture);
        return new PhysicObject(gameObject, body, material);
    }

    public static PhysicObject createBox(GameObject gameObject,
                                         Vector2f position, float width, float height, Material material) {

        BodyDef objectDef = new BodyDef();
        objectDef.position.set(new Vec2(position.x / 30f, position.y / 30f));
        objectDef.type = BodyType.DYNAMIC;

        PolygonShape objectShape = new PolygonShape();
        objectShape.setAsBox(width / 30 / 2, height / 30 / 2);

        Body body = gameObject.getLevel().getWorld().createBody(objectDef);

        body.m_userData = gameObject;

        FixtureDef objectFixture = new FixtureDef();
        objectFixture.friction = material.friction;
        objectFixture.density = material.density;
        objectFixture.restitution = material.restitution;
        objectFixture.shape = objectShape;
        body.createFixture(objectFixture);
        return new PhysicObject(gameObject, body, material);
    }

}
