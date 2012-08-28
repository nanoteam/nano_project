package logic.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import util.MathUtil;

public class SimpleWeapon extends ArsenalGameObject {
	// temp
	private int width;
	private int height;
	private int time = 0;

	// delete. sizeBullet != Bullet.size
	private int sizeBullet;
	private int reloadTime;

	public SimpleWeapon(GameObjectPhysicMoving gameObject, int width,
			int height, int randomizeFire, int sizeBullet, int reloadTime) {
		// this way or draw throw draw-method of ship
		this.position = gameObject.getPosition();
		this.fatherObj = gameObject;
		this.level = gameObject.level;
		this.width = width;
		this.height = height;
		this.randomizeFire = randomizeFire;
		this.sizeBullet = sizeBullet;
		this.reloadTime = reloadTime;
	}

	@Override
	public void init() {
		onShoot = false;
		onReload = false;

	}

	@Override
	public void update() {
		if (onReload) {
			if (time == 0)
				onReload = false;
			time--;
			// System.out.println(time);
		} else if (onShoot) {
			onReload = true;
			time = reloadTime;
			// TODO fire from border
			PlazmaBall plazmaBall = new PlazmaBall(position, angle, 0, level);
			fatherObj.level.getNotAddedGameObjects().add(plazmaBall);
			// Bullet bullet = new Bullet(position, angle, sizeBullet,
			// randomizeFire);
			// fatherObj.level.getNotAddedGameObjects().add(bullet);

			onShoot = false;
		}
	}

	@Override
	public void move() {
		Vector2f fPos = fatherObj.getPosition();

		position = new Vector2f(fPos.x
				+ MathUtil.newXTurn(30, 17, fatherObj.getAlfa()), fPos.y
				+ MathUtil.newYTurn(30, 17, fatherObj.getAlfa()));

		Vector2f vector = level.getPositionMouse();
		angle = (float) Math.atan2(vector.y - this.position.y, vector.x
				- this.position.y) * 60;
	}

	@Override
	public void draw() {

		glPushMatrix();
		glTranslatef(position.x, position.y, 0.0f);
		// 0.01f - angle

		glRotatef(angle, 0, 0, 1.0f);
		GL11.glColor3ub(Color.GREEN.getRedByte(), Color.GREEN.getGreenByte(),
				Color.GREEN.getBlueByte());

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

	// ? funny method
	@Override
	public boolean setShootOn() {
		onShoot = true;
		return false;
	}

}
