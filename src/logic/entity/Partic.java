//package logic.entity;
//
//import logic.Level;
//
//import org.jbox2d.collision.shapes.CircleShape;
//import org.jbox2d.collision.shapes.PolygonShape;
//import org.jbox2d.collision.shapes.ShapeType;
//import org.jbox2d.common.Vec2;
//import org.jbox2d.dynamics.BodyDef;
//import org.jbox2d.dynamics.BodyType;
//import org.jbox2d.dynamics.FixtureDef;
//import org.lwjgl.util.Color;
//import org.lwjgl.util.vector.Vector2f;
//
//import render.RenderUtil;
//
//public class Partic extends GameObjectPhysicMoving {
//
//	public Partic(Level level, Vector2f pos) {
//		this.level = level;
//		this.position = pos;
//
//		init();
//	}
//
//	@Override
//	public void init() {
//
//		BodyDef particDef = new BodyDef();
//		particDef.position.set(new Vec2(position.x / 30, position.y / 30));
//		particDef.type = BodyType.DYNAMIC;
//		PolygonShape particDefShape = new PolygonShape();
//		particDefShape.setAsBox(6/30f, 6/30f);
//		this.body = level.getWorld().createBody(particDef);
//		this.body.m_userData = this;
//		FixtureDef particDefFixture = new FixtureDef();
//		particDefFixture.friction = 0.5f; // trenie
//		particDefFixture.density = 1f; // plotnost'
//		particDefFixture.restitution = 0.15f;
//		particDefFixture.shape = particDefShape;
//		body.createFixture(particDefFixture);
//
//	}
//
//	@Override
//	public void update() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void move() {
//		this.position = new Vector2f(body.getPosition().x * 30,
//				body.getPosition().y * 30);
//	}
//
//	@Override
//	public void draw() {
//		RenderUtil.drawPlot(position, 12f, (Color) Color.BLUE);
//	}
//
//	@Override
//	public void playSound() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
