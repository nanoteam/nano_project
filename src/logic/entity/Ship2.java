package logic.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import controller.ControlledObject;

public class Ship2 extends GameObjectPhysicMoving implements ControlledObject {

	private List<GameObject> listOfPrivateObjects = new ArrayList<GameObject>();

	float maxXSpeed;
	float maxYSpeed;
	float dx;
	float dy;
	int width, height;

	// to class of guns
	static final long reloadTime = 100000000;
	boolean onReload;
	long timeOfFire;

	public static final int UP_SIDE = 1;
	public static final int DOWN_SIDE = 2;
	public static final int LEFT_SIDE = 3;
	public static final int RIGHT_SIDE = 4;
	public static final int FIRE = 10;
	public static final float SPEED_CONTROL = 0.13f;

	public Ship2(float x, float y) {
		// if(!loadParametres(..))
		// throw...;

		position = new Vector2f(x, y);
		init();
	}

	@Override
	public void doAction(int code) {
		/*if (code < 10)
			this.move(code);
		else if (code == 10)
			fire();
		 */
	}

	@Override
	public void init() {

		mass = 0.2f;
		angle = 0f;
		width = 60;
		height = 30;
		maxXSpeed = 6;
		maxYSpeed = 6;
		dx = 0;
		dy = 0;
		onReload = false;
	}

	@Override
	public void update() {

		// wtfwtfwtfwtwfwtfwtw
		if (listOfPrivateObjects.size() != 0)
			for (GameObject go : listOfPrivateObjects)
				go.update();
		// twftwfwtfwtwfwtfwtwftw

		position.x += dx;
		position.y += dy;

		dy -= this.mass * SPEED_CONTROL; // гравитация
		if (dx - SPEED_CONTROL * 0.1 > 0)
			dx -= SPEED_CONTROL * 0.08;
		else
			dx += SPEED_CONTROL * 0.08;
		// System.out.println("x =" + position.x + ", y =" + position.y +
		// ", dx ="
		// + dx + ", dy =" + dy + ", weight = " + mass);
	}

	@Override
	public void move() {
		int side = 0;
		switch (side) {
		case 1:
			dy += SPEED_CONTROL;
			if (dy > maxYSpeed)
				dy = maxYSpeed;
			break;
		case 2:
			dy -= SPEED_CONTROL;
			if (dy < -maxYSpeed)
				dy = -maxYSpeed;
			break;
		case 3:
			dx -= SPEED_CONTROL;
			if (dx < -maxXSpeed)
				dx = -maxXSpeed;
			break;
		case 4:
			dx += SPEED_CONTROL;
			if (dx > maxXSpeed)
				dx = maxXSpeed;
			break;
		case 5:
			angle += 1;
			break;
		case 6:
			angle -= 1;
			break;

		}
	}

	@Override
	public void draw() {

		// wftwftwftwtft
		if (listOfPrivateObjects.size() != 0)
			for (GameObject go : listOfPrivateObjects)
				go.draw();
		// wtfwtwftwfwtfwtwfwt

		glPushMatrix();

		glTranslatef(position.x, position.y, 0.0f);
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
		// TODO Auto-generated method stub

	}

	public void fire() {
		if (!onReload) {
			onReload = true;
			timeOfFire = System.nanoTime();
			Bullet bullet = new Bullet(position, angle);
			listOfPrivateObjects.add(bullet);
		} else if (System.nanoTime() - timeOfFire > reloadTime)
			onReload = false;
	}

}
