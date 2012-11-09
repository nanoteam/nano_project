package logic.entity.ship;

import logic.entity.ArsenalGameObject;
import logic.entity.GameObjectMoving;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;
import util.MathUtil;

import logic.entity.ammo.*;

public class Weapon extends ArsenalGameObject {
	// temp
	private int width;
	private int height;
	private int time = 0;
	private int reloadTime;

	public Weapon(GameObjectMoving gameObject, int width, int height,
			int randomizeFire, int reloadTime) {
		// this way or draw throw draw-method of ship
		this.position = gameObject.getPosition();
		this.fatherObj = gameObject;
		this.level = gameObject.getLevel();
		this.width = width;
		this.height = height;
		this.randomizeFire = randomizeFire;
		this.reloadTime = reloadTime;
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
				level.getNotAddedGameObjects().add(plazmaBall);

			}
			RubberBall rubberBall = new RubberBall(position, angle, 0, level);

			level.getNotAddedGameObjects().add(rubberBall);
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