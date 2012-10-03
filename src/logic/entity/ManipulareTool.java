package logic.entity;

import java.util.ArrayList;
import java.util.List;

import logic.Level;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;
import util.MathUtil;

public class ManipulareTool extends GameObject {
	Vec2[] vertices;
	float angle = 0;

	public ManipulareTool(Level level, Vector2f position) {
		this.level = level;
		this.position = position;
		init();
	}

	@Override
	public void init() {

		BodyDef leftClafDef = new BodyDef();
		leftClafDef.position.set(new Vec2(position.x / 30, position.y / 30));
		leftClafDef.type = BodyType.DYNAMIC;
		PolygonShape clawShape = new PolygonShape();
		vertices = new Vec2[3];
		vertices[0] = new Vec2(0f, 1f);
		vertices[1] = new Vec2(2f, -2f);
		vertices[2] = new Vec2(-2f, -2f);

		clawShape.set(vertices, 3);

		this.body = level.getWorld().createBody(leftClafDef);
		this.body.m_userData = this;

		FixtureDef clawFixture = new FixtureDef();
		clawFixture.friction = 1; // trenie
		clawFixture.density = 1f; // plotnost'
		clawFixture.restitution = 0;
		clawFixture.shape = clawShape;
		this.body.createFixture(clawFixture);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		position = new Vector2f(body.getPosition().x * 30,
				body.getPosition().y * 30);
		angle = body.getAngle();
	}

	@Override
	public void draw() {
		RenderUtil.drawPlot(position, 3, (Color) Color.ORANGE);
		Vector2f v2;
		for (int i = 0; i < 3; i++) {
			v2 = new Vector2f(position.x

			+ MathUtil.newXTurn(vertices[i].x * 30, vertices[i].y * 30, angle),
					position.y
							+ MathUtil.newYTurn(vertices[i].x * 30,
									vertices[i].y * 30, angle));
			RenderUtil.drawPlot(v2, 3f, (Color) Color.GREEN);
		}
		//
		// list.add(new Vector2f(position.x
		// + MathUtil.newXTurn(vertices[1].x * 30, vertices[1].y * 30,
		// angle), position.y
		// + MathUtil.newYTurn(vertices[1].x * 30, vertices[1].y * 30,
		// angle)));
		// list.add(new Vector2f(position.x
		// + MathUtil.newXTurn(vertices[2].x * 30, vertices[2].y * 30,
		// angle), position.y
		// + MathUtil.newYTurn(vertices[2].x * 30, vertices[2].y * 30,
		// angle)));
		// list.add(new Vector2f(position.x
		// + MathUtil.newXTurn(vertices[3].x * 30, vertices[3].y * 30,
		// angle), position.y
		// + MathUtil.newYTurn(vertices[3].x * 30, vertices[3].y * 30,
		// angle)));
		// list.add(new Vector2f(position.x
		// + MathUtil.newXTurn(vertices[4].x * 30, vertices[4].y * 30,
		// angle), position.y
		// + MathUtil.newYTurn(vertices[4].x * 30, vertices[4].y * 30,
		// angle)));
		// for (Vector2f v : list) {
		// RenderUtil.drawPlot(v, 3, (Color) Color.GREEN);
		// }
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
