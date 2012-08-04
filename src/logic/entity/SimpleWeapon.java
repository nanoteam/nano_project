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

public class SimpleWeapon extends ArsenalGameObject {

	private static final int RELOAD_TIME = 10;
	// temp
	int width = 50;
	int height = 5;

	public SimpleWeapon(GameObjectPhysicMoving gameObject) {
		// this way or draw throw draw-method of ship
		this.position = gameObject.getPosition();
		this.fatherObj = gameObject;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		onShoot = false;
		onReload = false;
		randomizeFire = 2;
		position.x += 10;
		position.y += 10;

	}

	@Override
	public void update() {
		if (onReload) {
			if (reloadTime == 0)
				onReload = false;
			reloadTime--;
			System.out.println(reloadTime);
		} else if (onShoot) {
			onReload = true;
			reloadTime = RELOAD_TIME;
			// TODO fire from border
			Bullet bullet = new Bullet(position, angle, 2, 2);
			// level.getGameObjects().add(0, bullet);
			fatherObj.level.getNotAddedGameObjects().add(bullet);
			onShoot = false;
		}
	}

	@Override
	public void move() {
		// angle = fatherObj.getAngle();
		Vector2f t = fatherObj.getPosition();
		position = new Vector2f(t.x + 30, t.y + 35);
		angle = fatherObj.getAngle();
	}

	@Override
	public void draw() {
		glPushMatrix();

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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setShootOn() {
		onShoot = true;
		return false;
	}

}
