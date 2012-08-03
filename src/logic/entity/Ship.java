/**
 * @author Arthur
 * @version 1.0
 */
package logic.entity;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.util.vector.Vector2f;
import controller.ControlledObject;
import logic.Level;
import main.Game;

public class Ship extends GameObjectPhysicMoving implements ControlledObject {
	float forceX;
	float forceY;

	boolean onShoot;
	boolean onReload;
	int reloadTime;

	private boolean leftEngineActive = false;
	private boolean rightEngineActive = false;
	private boolean allEngineActive = false;
	// temp variable
	float ft = 0;
	float ax = 0, ay = 0;
	float fx = 0, fy = 0;
	float M = 0f, e = 0f, m1=0, m2=0;
	//
	private float Ft = 1000000f;
	int width, height;

	// private ArrayList<ShipComponent> shipComponents;
	// Player player;
	// TODO add this class in game cycle
	public Ship(Level level, float x, float y) {
		// if(!loadParametres(..))
		// throw...;
		this.level = level;
		position = new Vector2f(x, y);
		init();
	}

	// TODO
	@Override
	public void init() {
		// magic const, I can give you help with this example of pure
		// code(Artyom)
		forceX = 100000f;
		forceY = 50000f;
		mass = 1000f;
		speed = new Vector2f(0, 0);
		angle = 0f;
		width = 60;
		height = 30;
		// formula for moment inercia : I = m * (lenght/12);
		I = mass * 5;

		onShoot = false;
		onReload = false;
	}

	@Override
	public void update() {
		if (onShoot) {
			System.out.println("fire!!!");
			if (!onReload) {
				onReload = true;
				reloadTime = 1000;
				Bullet bullet = new Bullet(position, angle);
				level.getGameObjects().add(0, bullet);
				onShoot = false;
			} else
				reloadTime--;
		}
	}

	@Override
	public void move() {
		// TODO add *dt
		ft = 0;
		fx = 0;
		fy = 0;
		M = 0;
		
		if (allEngineActive) {
			ft = Ft*2;
			//M = 0;
		}
		if (rightEngineActive){
			ft+= Ft;
			M+=Ft*width/2;
		}
		if (leftEngineActive){
			ft+= Ft;
			M+=-Ft*width/2;
		}
		
		fx = (float) (ft * Math.cos(angle));
		
		fy = (float) (ft * Math.sin(angle) - mass * Level.gravity);
		
		ax = fx / mass;
		ay = fy / mass;
		
		//System.out.println("ax = " + ax + " ay = "+ ay);
		e = M / I;

		speed.x += (float) (ax * 0.0166666);
		speed.y += (float) (ay * 0.0166666);
		w += (float) (e * 0.0166666);
		
		
		position.x += (float) (speed.x * 0.0166666);
		position.y += (float) (speed.y * 0.0166666);
		
		angle = angle + (float) (w * 0.0166666);
		//System.out.println();
		
		allEngineActive = false;
		rightEngineActive = false;
		leftEngineActive = false;
		
	}

	@Override
	public void draw() {
		glPushMatrix();
		// glTranslatef(Display.getDisplayMode().getWidth() / 2, Display
		// .getDisplayMode().getHeight() / 2, 0.0f);

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
	public void doAction(int code) {
		System.out.println("Ship.doAction()" + code);
		switch (code) {
		case ControlledObject.LEFT_ENGINE_ACTIVE: {
			leftEngineActive = true;
			break;
		}
		case ControlledObject.RIGHT_ENGINE_ACTIVE: {
			rightEngineActive = true;
			break;
		}
		case ControlledObject.All_ENGINE_ACTIVE: {
			allEngineActive = true;
			break;
		}
		}
	}
}
