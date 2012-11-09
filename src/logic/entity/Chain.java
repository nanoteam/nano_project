package logic.entity;

import java.util.ArrayList;
import java.util.List;

import javax.naming.LinkLoopException;

import logic.Level;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;
import util.MathUtil;

public class Chain extends GameObject {
	private float CHAIN_LINK_LENGTH = 15;
	int numOfLinks;
	float length;
	float angle;
	Vector2f begin, end;
	Body beginBody, endBody;
	private List<GameObject> chainLinks = new ArrayList<GameObject>();

	public Chain(Level level, Body body1, Vector2f begin, Body body2,
			Vector2f end) {
		// TODO check is points have a bodyes
		this.begin = begin;
		this.end = end;
		this.beginBody = body1;
		this.endBody = body2;
		this.level = level;

		// lenght from begin to end
		length = Math.abs((begin.x - end.x) * (begin.x - end.x))
				+ Math.abs((begin.y - end.y) * (begin.y - end.y));
		length = (float) Math.sqrt(length);

		// calculate number of links
		numOfLinks = (int) (length / CHAIN_LINK_LENGTH);
		float ox = end.x - begin.x;
		float oy = end.y - begin.y;
		angle = (float) Math.atan(oy / ox);
		if (end.x - begin.x < 0)
			angle -= 3.14;
        float linkLenght = length / numOfLinks;
        float llox = (float) (linkLenght * Math.cos(angle));
        float lloy = (float) (linkLenght * Math.sin(angle));
        Vector2f linkBegin = new Vector2f((float) (begin.x + llox / 2),
                (float) (begin.y + lloy / 2));
        Body b = beginBody;
        Vec2 joinPoint = new Vec2(begin.x, begin.y);
        ChainLink firstLink = new ChainLink(linkBegin, linkLenght, angle);
        chainLinks.add(firstLink);
        RevoluteJointDef join1 = new RevoluteJointDef();

        join1.initialize(b, firstLink.getPhysicObject().getBody(),
                joinPoint.mul(1 / 30f));
        level.getWorld().createJoint(join1);
        Vector2f prevLink = firstLink.getPosition();

        for (int i = 1; i < numOfLinks; i++) {
            joinPoint = new Vec2(joinPoint.x + llox, joinPoint.y + lloy);
            prevLink = new Vector2f(prevLink.x + llox, prevLink.y + lloy);
            ChainLink chainLink = new ChainLink(prevLink, linkLenght, angle);
            chainLinks.add(chainLink);
            RevoluteJointDef join = new RevoluteJointDef();
            join.initialize(firstLink.getPhysicObject().getBody(), chainLink
                    .getPhysicObject().getBody(), joinPoint.mul(1 / 30f));
            level.getWorld().createJoint(join);
            firstLink = chainLink;
            join.collideConnected = false;
        }
        joinPoint = new Vec2(end.x, end.y);

        join1 = new RevoluteJointDef();
        join1.initialize(firstLink.getPhysicObject().getBody(), endBody,
                joinPoint.mul(1 / 30f));
        level.getWorld().createJoint(join1);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {

	}

	@Override
	public void draw() {
		for (GameObject go : chainLinks) {
			go.draw();
		}
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	class ChainLink extends GameObjectMoving {
		private float lenght;
		private float width = 10;
		private float angle;

		public ChainLink(Vector2f position, float lenght, float angle) {
			this.position = position;
			this.lenght = lenght;
			this.angle = angle;
			Chain.this.level.getNotAddedGameObjects().add(this);
            physicObject = PhysicObject.createBox(this, position, lenght,
                    width, Material.Metal);
            physicObject.setAngle(angle);
		}

		@Override
		public void update() {

		}

		@Override
		public void move() {
			position = physicObject.getPosition();
			angle = physicObject.getAngle();
		}

		@Override
		public void draw() {

			RenderUtil.drawLine(new Vector2f((float) (position.x - (lenght - 2)
					* Math.cos(angle) / 2), (float) (position.y - (lenght - 2)
					* Math.sin(angle) / 2)), new Vector2f(
					(float) (position.x + (lenght - 2) * Math.cos(angle) / 2),
					(float) (position.y + (lenght - 2) * Math.sin(angle) / 2)),
					3f, (Color) Color.GREY);

		}

		@Override
		public void playSound() {

		}

		@Override
		public void destroy() {

		}

		public Vector2f getBegin() {
			return new Vector2f(
					(float) (position.x - lenght * Math.cos(angle)),
					(float) (position.y - lenght * Math.sin(angle)));
		}

		public Vector2f getEnd() {
			return new Vector2f(
					(float) (position.x + lenght * Math.cos(angle)),
					(float) (position.y + lenght * Math.sin(angle)));
		}
	}

}
