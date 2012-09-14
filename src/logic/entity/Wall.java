package logic.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;
import logic.Level;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class Wall extends GameObject {
	float height, width;
	float wallRestitution;
	float angle;

	public Wall(Level level, float x, float y, float width, float height) {
		this.level = level;
		this.position = new Vector2f(x, y);
		this.height = height;
		this.width = width;
		wallRestitution = 0;
		init();
	}

	@Override
	public void init() {

		BodyDef wallDef = new BodyDef();
		wallDef.position.set(new Vec2(position.x / 30, position.y / 30));
		wallDef.type = BodyType.STATIC;
		PolygonShape downWallShape = new PolygonShape();
		downWallShape.setAsBox(width / 30 / 2, height / 30 / 2);
		this.body = level.getWorld().createBody(wallDef);
		this.body.m_userData = this;
		FixtureDef wallFixture = new FixtureDef();
		wallFixture.density = 1f;
		wallFixture.friction = 1f;
		wallFixture.restitution = wallRestitution;
		wallFixture.shape = downWallShape;
		body.createFixture(wallFixture);
	}

	@Override
	public void update() {

	}

	@Override
	public void move() {

	}

	@Override
	public void draw() {
		angle = body.getAngle();
		glPushMatrix();

		glTranslatef(position.x, position.y, 0.0f);
		GL11.glColor3ub(Color.WHITE.getRedByte(), Color.WHITE.getGreenByte(),
				Color.WHITE.getBlueByte());

		// 0.01f - angle
		glRotatef(angle, 0, 0, 1.0f);
		glBegin(GL_QUADS);

		glVertex2i((int) -width / 2, (int) -height / 2);
		glVertex2i((int) width / 2, (int) -height / 2);
		glVertex2i((int) width / 2, (int) height / 2);
		glVertex2i((int) -width / 2, (int) height / 2);

		glEnd();
		glPopMatrix();
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
