
/**
* @author Arthur
* @version 1.0
*/
package logic.entity;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

public class Ship extends GameObjectPhysicMoving {
	// переменные
	float maxXSpeed;
	float maxYSpeed;
	float dx;
	float dy;

	public static final int UP_SIDE = 1;
	public static final int DOWN_SIDE = 2;
	public static final int LEFT_SIDE = 3;
	public static final int RIGHT_SIDE = 4;
	public static final float SPEED_CONTROL = 1f;

	// вариант на будущее
	// private ArrayList<ShipComponent> shipComponents;
	// Player player;
	// TODO add this class in game cycle
	public Ship(float x, float y) {
		// if(!loadParametres(..))
		// throw...;
		position.x = x;
		position.y = y;
		this.dx = 0f;
		this.dy = 0f;
		angle = 0f;
		init();
	}

	@Override
	public void init() {
		// заглушка
		maxXSpeed = 5;
		maxYSpeed = 2;
		mass = 0.02f;
	}

	@Override
	public void update() {
		position.x += dx;
		position.y += dy;
		dy -= this.mass; // гравитация
		if (dx - SPEED_CONTROL * 0.1 > 0)
			dx -= SPEED_CONTROL * 0.08;
		else
			dx += SPEED_CONTROL * 0.08;
		// System.out.println("x =" + x + ", y =" + y + ", dx =" + dx + ", dy ="
		// + dy + ", weight = " + weight);

		/*
		 * switch (1) { case 1: dy += SPEED_CONTROL; if (dy > maxYSpeed) dy =
		 * maxYSpeed; break; case 2: dy -= SPEED_CONTROL; if (dy < -maxYSpeed)
		 * dy = -maxYSpeed; break; case 3: dx -= SPEED_CONTROL; if (dx <
		 * -maxXSpeed) dx = -maxXSpeed; break; case 4: dx += SPEED_CONTROL; if
		 * (dx > maxXSpeed) dx = maxXSpeed; break; }
		 */
	}

	@Override
	public void move() {

	}

	@Override
	public void draw() {/*
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glClear(GL_COLOR_BUFFER_BIT);
		glPushMatrix();
		// glTranslatef(Display.getDisplayMode().getWidth() / 2, Display
		// .getDisplayMode().getHeight() / 2, 0.0f);
		glTranslatef(position.x, position.y, 0.0f);
		// 0.01f - angle
		glRotatef(angle, 0, 0, 1.0f);
		glBegin(GL_QUADS);
		glVertex2i(-50, -50);
		glVertex2i(50, -50);
		glVertex2i(50, 50);
		glVertex2i(-50, 50);
		glEnd();
		glPopMatrix();*/
	}

	@Override
	public void playSound() {
	}

	public void fire(int gun_id) {

	}

}
