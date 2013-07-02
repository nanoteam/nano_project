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
import org.jbox2d.dynamics.*;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class PhysicObject {
	private Body body;
	private Material material;
	private GameObject gameObject;

	public static final int UNKNOW = -1;
	public static final int DINAMIC = 0;
	public static final int KINEMATIC = 1;
	public static final int STATIC = 2;

	private PhysicObject(GameObject gameObject, Body body, Material material) {
		this.body = body;
		this.material = material;
		this.gameObject = gameObject;
	}

	public Vector2f getSpeed() {
		return new Vector2f(body.m_linearVelocity.x * 30f,
				body.getLinearVelocity().y * 30f);
	}

	public void setSpeed(Vector2f newSpeed) {
		body.setLinearVelocity(new Vec2(newSpeed.x / 30f, newSpeed.y / 30f));
	}

	public void setAngularVelocity(float w) {
		body.setAngularVelocity(w);
	}

	public float getAngularVelocity() {
		return body.getAngularVelocity();
	}

	public Vector2f getPosition() {
		return new Vector2f(body.getPosition().x * 30f,
				body.getPosition().y * 30f);
	}

	public void setPosition(Vector2f newPosition) {
		body.setTransform(new Vec2(newPosition.x / 30f, newPosition.y / 30f),
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
		Vec2 pointOfForce = new Vec2(point.x / 30f, point.y / 30f);
		body.applyForce(force, pointOfForce);
	}

	public void destroy() {
		body.destroyFixture(body.getFixtureList());
		body.getWorld().destroyBody(body);

		// TODO delete object from game, maybe this method must be static and
		// PhysicObject as argument
	}

	public void setAngularDamping(float angularDamping) {
		body.setAngularDamping(angularDamping);
	}

	public void setLinearDamping(float linearDamping) {
		body.setLinearDamping(linearDamping);
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public static PhysicObject createBall(GameObject gameObject,
			Vector2f position, float radius, Material material, int typeBody,
			World world) {

		BodyDef objectDef = new BodyDef();
		objectDef.position.set(new Vec2(position.x / 30f, position.y / 30f));

		if (typeBody == DINAMIC) {
			objectDef.type = BodyType.DYNAMIC;
		} else {
			if (typeBody == KINEMATIC) {
				objectDef.type = BodyType.KINEMATIC;
			} else {
				if (typeBody == STATIC) {
					objectDef.type = BodyType.STATIC;
				}
			}
		}

		CircleShape objectShape = new CircleShape();
		objectShape.m_radius = radius / 30f;

		Body body = world.createBody(objectDef);
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
			Vector2f position, float width, float height, float angle,
			Material material, int typeBody, World world) {

		BodyDef objectDef = new BodyDef();
		objectDef.position.set(new Vec2(position.x / 30f, position.y / 30f));
		if (typeBody == DINAMIC) {
			objectDef.type = BodyType.DYNAMIC;
		} else {
			if (typeBody == KINEMATIC) {
				objectDef.type = BodyType.KINEMATIC;
			} else {
				if (typeBody == STATIC) {
					objectDef.type = BodyType.STATIC;
				}
			}
		}
		objectDef.angle = angle;
		PolygonShape objectShape = new PolygonShape();
		objectShape.setAsBox(width / 30f / 2f, height / 30f / 2f);
		// objectShape.setAsBox(width / 30f / 2f, height / 30f / 2f, new
		// Vec2(position.x / 30f, position.y / 30f), angle);
		Body body = world.createBody(objectDef);
		body.m_userData = gameObject;
		FixtureDef objectFixture = new FixtureDef();
		objectFixture.friction = material.friction;
		objectFixture.density = material.density;
		objectFixture.restitution = material.restitution;
		objectFixture.shape = objectShape;
		body.createFixture(objectFixture);
		return new PhysicObject(gameObject, body, material);
	}

	public static PhysicObject createPolygon(GameObject gameObject,
			Vector2f position, List<Vector2f> vertex, float angle,
			Material material, int typeBody, World world) {

		BodyDef objectDef = new BodyDef();
		objectDef.position.set(new Vec2(position.x / 30f, position.y / 30f));
		if (typeBody == DINAMIC) {
			objectDef.type = BodyType.DYNAMIC;
		} else {
			if (typeBody == KINEMATIC) {
				objectDef.type = BodyType.KINEMATIC;
			} else {
				if (typeBody == STATIC) {
					objectDef.type = BodyType.STATIC;
				}
			}
		}
		objectDef.angle = angle;
		PolygonShape objectShape = new PolygonShape();
		Vec2[] vec2List = new Vec2[vertex.size()];
		for (int i = 0; i < vertex.size(); i++) {
			vec2List[i] = new Vec2(vertex.get(i).x / 30f, vertex.get(i).y / 30f);
		}
		objectShape.set(vec2List, vertex.size());
		// objectShape.setAsBox(width / 30f / 2f, height / 30f / 2f, new
		// Vec2(position.x / 30f, position.y / 30f), angle);
		Body body = world.createBody(objectDef);
		body.setTransform(objectDef.position, angle);
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
