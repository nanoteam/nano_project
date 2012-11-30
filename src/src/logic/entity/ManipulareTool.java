//package logic.entity;
//
//import org.jbox2d.collision.shapes.PolygonShape;
//import org.jbox2d.common.Vec2;
//import org.jbox2d.dynamics.BodyDef;
//import org.jbox2d.dynamics.BodyType;
//import org.jbox2d.dynamics.FixtureDef;
//import org.jbox2d.dynamics.joints.RevoluteJointDef;
//import org.lwjgl.util.Color;
//import org.lwjgl.util.vector.Vector2f;
//import render.RenderUtil;
//import util.MathUtil;
//
//import logic.Level;
//public class ManipulareTool extends GameObject {
//	private LeftClaw leftClaw;
//	private RightClaw rightClaw;
//
//	Vec2[] vertices;
//	float angle = 0;
//
//	public ManipulareTool(Level level, Vector2f position) {
//		this.level = level;
//		this.position = position;
//		init();
//	}
//
//	@Override
//	public void init() {
//		BodyDef leftClafDef = new BodyDef();
//		leftClafDef.position.set(new Vec2(position.x / 30, position.y / 30));
//		leftClafDef.type = BodyType.DYNAMIC;
//		PolygonShape clawShape = new PolygonShape();
//		vertices = new Vec2[3];
//		vertices[0] = new Vec2(2f, -3f);
//		vertices[1] = new Vec2(0f, -1f);
//		vertices[2] = new Vec2(-2f, -3f);
//		clawShape.set(vertices, 3);
//		this.body = level.getWorld().createBody(leftClafDef);
//		this.body.m_userData = this;
//
//		FixtureDef clawFixture = new FixtureDef();
//		clawFixture.friction = 1; // trenie
//		clawFixture.density = 1f; // plotnost'
//		clawFixture.restitution = 1;
//		clawFixture.shape = clawShape;
//
//		this.body.createFixture(clawFixture);
//
//		// {// left claw
//		Vec2 pointJoint = new Vec2(new Vec2(position.x / 30 - 2f,
//				position.y / 30 - 3f));
//		leftClaw = new LeftClaw(new Vector2f(600, 450));
//		level.getNotAddedGameObjects().add(leftClaw);
//		RevoluteJointDef joint = new RevoluteJointDef();
//		// joint.collideConnected = false;
//		joint.initialize(body, leftClaw.getBody(), pointJoint);
//		joint.enableMotor = true;
//		joint.motorSpeed = -10f;
//
//		level.getWorld().createJoint(joint);
//		
//		Vec2 pointJoint2 = new Vec2(new Vec2(position.x / 30 + 2f,
//				position.y / 30 - 3f));
//		rightClaw = new RightClaw(new Vector2f(790, 450));
//		level.getNotAddedGameObjects().add(rightClaw);
//		RevoluteJointDef joint2 = new RevoluteJointDef();
//		// joint.collideConnected = false;
//		joint2.initialize(body, rightClaw.getBody(), pointJoint2);
//		level.getWorld().createJoint(joint2);
//		//
//		// }
//	}
//
//	@Override
//	public void update() {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void move() {
//		position = new Vector2f(body.getPosition().x * 30,
//				body.getPosition().y * 30);
//
//		angle = body.getAngle();
//	}
//
//	@Override
//	public void draw() {
//		RenderUtil.drawPlot(position, 3, (Color) Color.ORANGE);
//		Vector2f v2;
//		for (int i = 0; i < 3; i++) {
//			v2 = new Vector2f(position.x
//
//			+ MathUtil.newXTurn(vertices[i].x * 30, vertices[i].y * 30, angle),
//					position.y
//							+ MathUtil.newYTurn(vertices[i].x * 30,
//									vertices[i].y * 30, angle));
//			RenderUtil.drawPlot(v2, 3f, (Color) Color.GREEN);
//		}
//
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
////		level.getWorld().destroyBody(body);
//	}
//
//	class LeftClaw extends GameObject {
//		private float angle;
//		Vec2[] vertices;
//
//		public LeftClaw(Vector2f position) {
//			this.position = position;
//			this.angle = 0;
//			init();
//		}
//
//		@Override
//		public void init() {
//			BodyDef leftClafDef = new BodyDef();
//			leftClafDef.position
//					.set(new Vec2(position.x / 30, position.y / 30));
//			leftClafDef.type = BodyType.DYNAMIC;
//			PolygonShape clawShape = new PolygonShape();
//			vertices = new Vec2[3];
//			vertices[0] = new Vec2(2f, 0f);
//			vertices[1] = new Vec2(1f, -1f);
//			vertices[2] = new Vec2(2f, -5f);
//
//			clawShape.set(vertices, 3);
//			this.body = ManipulareTool.this.level.getWorld().createBody(
//					leftClafDef);
//			this.body.m_userData = this;
//
//			FixtureDef clawFixture = new FixtureDef();
//			clawFixture.friction = 1; // trenie
//			clawFixture.density = 1f; // plotnost'
//			clawFixture.restitution = 1;
//			clawFixture.shape = clawShape;
//
//			this.body.createFixture(clawFixture);
//		}
//
//		@Override
//		public void update() {
//
//		}
//
//		@Override
//		public void move() {
//			position = new Vector2f(body.getPosition().x * 30,
//					body.getPosition().y * 30);
//			angle = body.getAngle();
//		}
//
//		@Override
//		public void draw() {
//			RenderUtil.drawPlot(position, 3, (Color) Color.ORANGE);
//			Vector2f v2;
//			for (int i = 0; i < 3; i++) {
//				v2 = new Vector2f(position.x
//
//						+ MathUtil.newXTurn(vertices[i].x * 30,
//								vertices[i].y * 30, angle), position.y
//						+ MathUtil.newYTurn(vertices[i].x * 30,
//								vertices[i].y * 30, angle));
//				RenderUtil.drawPlot(v2, 3f, (Color) Color.GREEN);
//			}
//		}
//
//		@Override
//		public void playSound() {
//
//		}
//
//		@Override
//		public void destroy() {
//			ManipulareTool.this.level.getWorld().destroyBody(body);
//		}
//
//	}
//
//	class RightClaw extends GameObject {
//		private float angle;
//		Vec2[] vertices;
//
//		public RightClaw(Vector2f position) {
//			this.position = position;
//			this.angle = 0;
//			init();
//		}
//
//		@Override
//		public void init() {
//			BodyDef leftClafDef = new BodyDef();
//			leftClafDef.position
//					.set(new Vec2(position.x / 30, position.y / 30));
//			leftClafDef.type = BodyType.DYNAMIC;
//			PolygonShape clawShape = new PolygonShape();
//			vertices = new Vec2[3];
//			vertices[0] = new Vec2(-2f, 0f);
//			vertices[1] = new Vec2(-1f, -1f);
//			vertices[2] = new Vec2(-2f, -5f);
//
//			clawShape.set(vertices, 3);
//			this.body = ManipulareTool.this.level.getWorld().createBody(
//					leftClafDef);
//			this.body.m_userData = this;
//
//			FixtureDef clawFixture = new FixtureDef();
//			clawFixture.friction = 1; // trenie
//			clawFixture.density = 1f; // plotnost'
//			clawFixture.restitution = 1;
//			clawFixture.shape = clawShape;
//
//			this.body.createFixture(clawFixture);
//		}
//
//		@Override
//		public void update() {
//
//		}
//
//		@Override
//		public void move() {
//			position = new Vector2f(body.getPosition().x * 30,
//					body.getPosition().y * 30);
//			angle = body.getAngle();
//		}
//
//		@Override
//		public void draw() {
//			RenderUtil.drawPlot(position, 3, (Color) Color.ORANGE);
//			Vector2f v2;
//			for (int i = 0; i < 3; i++) {
//				v2 = new Vector2f(position.x
//
//						+ MathUtil.newXTurn(vertices[i].x * 30,
//								vertices[i].y * 30, angle), position.y
//						+ MathUtil.newYTurn(vertices[i].x * 30,
//								vertices[i].y * 30, angle));
//				RenderUtil.drawPlot(v2, 3f, (Color) Color.GREEN);
//			}
//		}
//
//		@Override
//		public void playSound() {
//
//		}
//
//		@Override
//		public void destroy() {
//			ManipulareTool.this.level.getWorld().destroyBody(body);
//		}
//
//
//	}
//
//}
