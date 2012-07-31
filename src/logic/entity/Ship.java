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

import org.lwjgl.util.vector.Vector2f;
import controller.ControlledObject;
import logic.Level;
import main.Game;

public class Ship extends GameObjectPhysicMoving implements ControlledObject {
	float forceX;
	float forceY;

	float vx;
	float vy;
	// temp variable
	float ax = 0, ay = 0;
	float fx = 0, fy = 0;
	float M = 0f, e = 0f;
	//
	int width, height;

	public static final int UP_SIDE = 1;
	public static final int DOWN_SIDE = 2;
	public static final int LEFT_SIDE = 3;
	public static final int RIGHT_SIDE = 4;
	public static final int FIRE = 5;
	public static final float SPEED_CONTROL = 1f;

	// private ArrayList<ShipComponent> shipComponents;
	// Player player;
	// TODO add this class in game cycle
	public Ship(float x, float y) {
		// if(!loadParametres(..))
		// throw...;

		position = new Vector2f(x, y);
		init();
	}

	// TODO
	@Override
	public void init() {
		// magic const, I can give you help with this example of pure
		// code(Artyom)
		forceX = 1000f;
		forceY = 8000f;
		mass = 1000f;
		this.vx = 0f;
		this.vy = 0f;
		angle = 0f;
		width = 60;
		height = 30;
		// formula for moment inercia : I = m * (lenght/12);
		I = mass * 5;
	}

	@Override
	public void update() {/*
						 * switch (1) { case 1: dy += SPEED_CONTROL; if (dy >
						 * maxYSpeed) dy = maxYSpeed; break; case 2: dy -=
						 * SPEED_CONTROL; if (dy < -maxYSpeed) dy = -maxYSpeed;
						 * break; case 3: dx -= SPEED_CONTROL; if (dx <
						 * -maxXSpeed) dx = -maxXSpeed; break; case 4: dx +=
						 * SPEED_CONTROL; if (dx > maxXSpeed) dx = maxXSpeed;
						 * break; }
						 */
	}

	@Override
	public void move(int side) {
		// TODO add *dt
//
//		fx = 0;
//		fy = 0;
//		M = 0;
//
//		fy = (float) (-mass * Level.gravity);
//		fx = 0f;
//		M = 100000;
//
//		ax = fx / mass;
//		ay = fy / mass;
//		e = M / I;
//
//		vx = vx + (float) (ax * 0.0166666);
//		vy = vy + (float) (ay * 0.0166666);
//		w = w + (float) (e * 0.0166666);
//
//		position.x = position.x + (float) (vx * 0.0166666);
//		position.y = position.y + (float) (vy * 0.0166666);
//		angle = angle + (float) (w * 0.0166666);
		
		
		
		
		
		
		
		
		
		
		
	}

	@Override
	public void draw() {
		glPushMatrix();
		// glTranslatef(Display.getDisplayMode().getWidth() / 2, Display
		// .getDisplayMode().getHeight() / 2, 0.0f);

		glTranslatef(position.x, position.y, 0.0f);

		// 0.01f - angle
		glRotatef(angle, 0, 0, 1.0f);
		glBegin(GL_QUADS);

		glVertex2i(-width / 2, -height / 2);
		glVertex2i(width / 2, -height / 2);
		glVertex2i(width / 2, height / 2);
		glVertex2i(-width / 2, height / 2);

		glEnd();
		glPopMatrix();
	}

	@Override
	public void playSound() {
	}

	@Override
	public void doAction(int code) {
		// TODO Auto-generated method stub
		System.out.println(code);

	}

	void fire() {

	}

}
