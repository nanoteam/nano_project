package logic.entity;

import org.lwjgl.util.vector.Vector2f;

import logic.Level;

public class AeroDynTest extends GameObject {

	private boolean isActivated = false;
	private Vector2f begin = null;
	private Vector2f end = null;

	public AeroDynTest(Level level) {
		this.level = level;

	}

	@Override
	public void init() {
		this.begin = level.getPositionMouse();
	}

	@Override
	public void update() {
		if (level.cursor.isLeftPressed())
			isActivated = true;
		else
			isActivated = false;

		if (isActivated)
			this.end = level.getPositionMouse();
		else
			this.begin = level.getPositionMouse();
	}

	@Override
	public void move() {
		if (isActivated) {
			this.end = level.getPositionMouse();
			Vector2f speed = new Vector2f((end.x - begin.x) * 2,
					(end.y - begin.y) * 2);
			level.getNotAddedGameObjects().add(new Partic(level, begin, speed));

		}
	}

	@Override
	public void draw() {

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
