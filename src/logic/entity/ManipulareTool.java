package logic.entity;

import logic.Level;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class ManipulareTool extends GameObject {

	public ManipulareTool(Level level, Vector2f position) {
		this.level = level;
		this.position = position;
		init();
	}

	@Override
	public void init() {

		BodyDef clawDef = new BodyDef();
		clawDef.position.set(new Vec2(position.x / 30, position.y / 30));
		clawDef.type = BodyType.DYNAMIC;
		PolygonShape clawShape = new PolygonShape();
		Vec2[] vertices = new Vec2[4];
		vertices[0] = new Vec2(-1f, -1f);
		vertices[1] = new Vec2(-1f, 1f);
		vertices[2] = new Vec2(1f, 1f);
		vertices[3] = new Vec2(1f, -1f);

		
		 clawShape.set(vertices, 4);

		this.body = level.getWorld().createBody(clawDef);
		this.body.m_userData = this;

		FixtureDef clawFixture = new FixtureDef();
		clawFixture.friction = 0; // trenie
		clawFixture.density = 100f; // plotnost'
		clawFixture.restitution = 1;
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

	}

	@Override
	public void draw() {
		RenderUtil.drawPlot(position, 3, (Color) Color.GREEN);
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
