package logic.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.util.vector.Vector2f;

public class Bullet extends GameObjectSimpleMoving {

	// temp
	static final int SIZE = 7;
	static final float SPEED = 7;

	public Bullet(Vector2f pos, float angle) {
		position = new Vector2f(pos);
		speed = new Vector2f((float) (SPEED * Math.cos(angle/60)),
				(float) (SPEED * Math.sin(angle/60)));
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		position.x += speed.x;
		position.y += speed.y;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw() {
		glPushMatrix();
		glTranslatef(position.x, position.y, 0.0f);
		// glRotatef(angle, 0, 0, 1.0f);
		glBegin(GL_QUADS);
		glVertex2i(-SIZE / 2, -SIZE / 2);
		glVertex2i(SIZE / 2, -SIZE / 2);
		glVertex2i(SIZE / 2, SIZE / 2);
		glVertex2i(-SIZE / 2, SIZE / 2);

		glEnd();
		glPopMatrix();
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

}
