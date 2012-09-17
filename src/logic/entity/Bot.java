package logic.entity;

import org.lwjgl.util.vector.Vector2f;

import controller.ControlledObject;

public class Bot extends GameObject {

	static private float V_ERROR = 0.1f;
	private Vector2f prevPosition;
	private float prevSpeedY;
	private float curSpeedY;
	private Ship gameObject;
	private float ay;
	private boolean calibrated;
	private float minY = 300;
	private float normY = 400;
	private boolean inProcess;
	private float processTime;
	private float[] actionTimes = { 0 }; // massive of times to action for any
											// actions

	public Bot(Ship gameObject) {
		this.gameObject = gameObject;
		init();
	}

	@Override
	public void init() {
		this.position = new Vector2f(300f, 300f);
		curSpeedY = prevSpeedY = 0;
		ay = 0;
		this.prevPosition = gameObject.getPosition();
		inProcess = false;
		calibrated = false;
	}

	@Override
	public void update() {
		if (!inProcess) {
			if (calibrated) {
				if (gameObject.getBody().getPosition().y * 30 < minY) {
					moveUp();
					inProcess = true;
				}
			} else {
				calibrate();

			}

		} else {
			if (actionTimes[0]-- > 0)
				gameObject.doAction(ControlledObject.All_ENGINE_ACTIVE);
			processTime--;
			if (processTime < 0)
				inProcess = false;
		}
		this.prevPosition = new Vector2f(gameObject.getPosition());
		
	}

	private void moveUp() {

		float distance = normY - minY;
		// actionTimes is time for engines action, processTime is time to get up
		// speed
		actionTimes[0] = (float) ((float) Math.sqrt(distance * level.gravity
				/ (ay * (ay + level.gravity))) + Math.abs(curSpeedY
				/ level.gravity));
		processTime = (float) (actionTimes[0] + curSpeedY / level.gravity) + 10f;
		System.out.println("Command to move up details\n" + "dsitanse = "
				+ distance);
		System.out.println("gravity = " + level.gravity);
		System.out.println("ax = " + ay);
		System.out.println("time is = " + actionTimes[0]);

	}

	private boolean checkParameters() {
		if (curSpeedY == 0 || prevSpeedY == 0)
			return false;
		float ay1 = (curSpeedY - prevSpeedY) / 0.016666f;
		System.out.println("compare : " + ay1 + "  " + ay);
		if (ay1 < ay + V_ERROR && ay1 > ay - V_ERROR)
			return true;
		else
			return false;
		// TODO check other parameters

	}

	private void calibrate() {
		gameObject.doAction(ControlledObject.All_ENGINE_ACTIVE);
		System.out.println(gameObject.getPosition().y + " -_- "
				+ prevPosition.y);
		if (prevSpeedY == 0) {
			prevSpeedY = (gameObject.getPosition().y - prevPosition.y) / 0.016666f;
			return;
		}

		curSpeedY = (gameObject.getPosition().y - prevPosition.y) / 0.016666f;
		ay = (curSpeedY - prevSpeedY) / 0.016666f;
		System.out.println("new ax = " + ay + " vx1 and vx2 = " + curSpeedY
				+ " " + prevSpeedY);
		if (checkParameters()) {
			System.out.println("calibrate is ok!\nAx = " + ay);
			calibrated = true;
		}
		this.prevSpeedY = curSpeedY;

	}

	@Override
	public void move() {
		// System.out.println("proctime " + processTime);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}