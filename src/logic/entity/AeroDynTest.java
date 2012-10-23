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
	}

	@Override
	public void update() {
        //arthur, may be i delete your good code(. but this code work to!
        /*
		if ((level.cursor.isLeftPressed())&&(!isActivated)){
			isActivated = true;
            begin = level.getPositionMouse();

        }
        if (isActivated){
            end = level.getPositionMouse();
        }

        if ((!level.cursor.isLeftPressed())&&(isActivated)){
            isActivated = false;
        } */
	}

	@Override
	public void move() {
		if (isActivated) {
			/*this.end = level.getPositionMouse();
			Vector2f speed = new Vector2f((end.x - begin.x) * 2,
					(end.y - begin.y) * 2);
			level.getNotAddedGameObjects().add(new Partic(level, begin, speed));
              */
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
