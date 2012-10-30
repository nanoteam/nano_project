package logic.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;
import logic.entity.ammo.PlazmaBall;
import logic.entity.ammo.RubberBall;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;
import util.MathUtil;

public class SimpleWeapon extends ArsenalGameObject {
	// temp
	private int width;
	private int height;
	private int time = 0;

	// delete. sizeBullet != Bullet.size
	private int sizeBullet;
	private int reloadTime;

	public SimpleWeapon(GameObjectMoving gameObject, int width, int height,
			int randomizeFire, int sizeBullet, int reloadTime) {
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
			// plazma ball
			// PlazmaBall plazmaBall = new PlazmaBall(position, angle, 0,
			// level);
			// fatherObj.level.getNotAddedGameObjects().add(plazmaBall);

			// bullet
			// Bullet bullet = new Bullet(position, angle, sizeBullet,
			// randomizeFire);
			// fatherObj.level.getNotAddedGameObjects().add(bullet);

			// rubber ball
			if (random.nextFloat() > 0.9) {

				PlazmaBall plazmaBall = new PlazmaBall(position, angle, 0,
						level);
				fatherObj.level.getNotAddedGameObjects().add(plazmaBall);

			}
			RubberBall rubberBall = new RubberBall(position, angle, 0, level);

			fatherObj.level.getNotAddedGameObjects().add(rubberBall);

			onShoot = false;
		}
	}

	@Override
	public void move() {
		Vector2f fPos = fatherObj.getPosition();

		position = new Vector2f(fPos.x
				+ MathUtil.newXTurn(30, 17, fatherObj.getAngle()), fPos.y
				+ MathUtil.newYTurn(30, 17, fatherObj.getAngle()));

		Vector2f vector = level.getMousePosition();

		angle = (float) Math.atan2(vector.y - this.position.y, vector.x
				- this.position.y);
	}

	@Override
	public void draw() {
		RenderUtil.drawQaud(position.x, position.y, width, height, angle,
				(Color) Color.DKGREY);
		RenderUtil.drawPlot(
				new Vector2f(position.x
						+ MathUtil.newXTurn(width / 2, 0, angle), position.y
						+ MathUtil.newYTurn(width / 2, 0, angle)), 4,
				(Color) (Color.RED));
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}