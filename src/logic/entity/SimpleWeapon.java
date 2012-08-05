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
<<<<<<< HEAD

	private static final int RELOAD_TIME = 10;
	// temp
	int width = 50;
	int height = 5;

	public SimpleWeapon(GameObjectPhysicMoving gameObject) {
		// this way or draw throw draw-method of ship
		this.position = gameObject.getPosition();
		this.fatherObj = gameObject;
<<<<<<< HEAD
		relativePosition = new Vector2f(30f,35f);

=======
>>>>>>> 669492cf06c928d5da40c86143b6b710fcb5b6be
=======
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
>>>>>>> master
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		onShoot = false;
		onReload = false;
<<<<<<< HEAD
		randomizeFire = 2;
		position.x += 10;
		position.y += 10;
<<<<<<< HEAD
		System.out.println(relativePosition.toString());
=======

>>>>>>> 669492cf06c928d5da40c86143b6b710fcb5b6be
=======

>>>>>>> master
	}

	@Override
	public void update() {
		if (onReload) {
<<<<<<< HEAD
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
=======
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
>>>>>>> master
			fatherObj.level.getNotAddedGameObjects().add(bullet);
			onShoot = false;
		}
	}

	@Override
	public void move() {
		// angle = fatherObj.getAngle();
		Vector2f t = fatherObj.getPosition();
<<<<<<< HEAD
<<<<<<< HEAD
		angle = fatherObj.getAngle();

		position = new Vector2f((float) (t.x - relativePosition.x
				* Math.sin(angle / 60)),
				(float) (t.y + relativePosition.y * Math.cos(angle / 60)));
=======
		position = new Vector2f(t.x + 30, t.y + 35);
		angle = fatherObj.getAngle();
>>>>>>> 669492cf06c928d5da40c86143b6b710fcb5b6be
=======
		position = new Vector2f(t.x + 30, t.y + 35);
		angle = fatherObj.getAngle();
>>>>>>> master
	}

	@Override
	public void draw() {
		glPushMatrix();
<<<<<<< HEAD

		glTranslatef(position.x, position.y, 0.0f);

		// 0.01f - angle
		glRotatef(angle, 0, 0, 1.0f);
		glBegin(GL_QUADS);

=======
		glTranslatef(position.x, position.y, 0.0f);
		// 0.01f - angle
		glRotatef(angle, 0, 0, 1.0f);
		glBegin(GL_QUADS);
>>>>>>> master
		glVertex2i(-width / 2, -height / 2);
		glVertex2i(width / 2, -height / 2);
		glVertex2i(width / 2, height / 2);
		glVertex2i(-width / 2, height / 2);
<<<<<<< HEAD

=======
>>>>>>> master
		glEnd();
		glPopMatrix();
	}

	@Override
	public void playSound() {
<<<<<<< HEAD
		// TODO Auto-generated method stub

=======
>>>>>>> master
	}

	@Override
	public boolean setShootOn() {
		onShoot = true;
		return false;
	}

}
