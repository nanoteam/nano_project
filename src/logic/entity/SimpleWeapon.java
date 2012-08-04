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
	//temp
	private int width;
	private int height;
	private int time = 0;
	
	private int sizeBullet;
	private int reloadTime;
	
	public SimpleWeapon(GameObjectPhysicMoving gameObject, 
	int width, int height, int randomizeFire, int sizeBullet, int reloadTime) {
		// this way or draw throw draw-method of ship
		this.position = gameObject.getPosition();
		this.fatherObj = gameObject;
		this.width = width;
		this.height = height;
		this.randomizeFire = randomizeFire;
		this.sizeBullet = sizeBullet;
		this.reloadTime = reloadTime;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		onShoot = false;
		onReload = false;

	}

	@Override
	public void update() {
		if (onReload) {
			if (time == 0)
				onReload = false;
			time--;
			//System.out.println(time);
		} else if (onShoot) {
			onReload = true;
			time = reloadTime;
			// TODO fire from border
			Bullet bullet = new Bullet(position, angle, sizeBullet, (int) randomizeFire);
			// level.getGameObjects().add(0, bullet);
			//WTF??????????????? getNotAddedGameObjects().add(bullet) ???
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
	}

	@Override
	public boolean setShootOn() {
		onShoot = true;
		return false;
	}

}
