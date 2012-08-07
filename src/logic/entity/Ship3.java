/**
 * @author Arthur
 * @version 1.3
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
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;
import controller.ControlledObject;
import logic.Level;
import main.Game;

public class Ship3 extends GameObjectPhysicMoving implements ControlledObject {
	float forceX;
	float forceY;

	boolean onShoot;
	boolean onReload;
	int reloadTime;

	private float Ft = 300000f;
	
	private boolean leftEngineActive = false;
	private boolean rightEngineActive = false;
	private boolean allEngineActive = false;
	int width, height;
	private List<ArsenalGameObject> arsenalList = new ArrayList<ArsenalGameObject>();

	// private ArrayList<ShipComponent> shipComponents;
	public Ship3(Level level, float x, float y) {
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
		width = 120;
		height = 30;
		// formula for moment inercia : I = m * (lenght/12);
		I = mass * 5;

		SimpleWeapon weap1 = new SimpleWeapon(this,20,3,1,3,20);
		SimpleWeapon weap2 = new SimpleWeapon(this,10,5,2,15,70);
		arsenalList.add(weap1);
		arsenalList.add(weap2);
		level.getGameObjects().add(weap1);
		level.getGameObjects().add(weap2);
	}

	@Override
	public void update() {

	}

	// TODO add *dt
	@Override
	public void move() {

		float ft = 0;
		float ax = 0, ay = 0;
		float fx = 0, fy = 0;
		float M = 0f, e = 0f;

		if (allEngineActive) {
			ft = Ft * 2;
			w /= 8;

		}
		if (rightEngineActive) {
			ft += Ft;
			M += Ft * width / 20;
		}
		if (leftEngineActive) {
			ft += Ft;
			M += -Ft * width / 20;
		}

		fx = (float) (-ft * Math.sin(angle / 60));

		fy = (float) (ft * Math.cos(angle / 60) - mass * Level.gravity);

		ax = fx / mass;
		ay = fy / mass;

		// System.out.println("ax = " + ax + " ay = "+ ay);
		e = M / I;

		speed.x += (float) (ax * 0.0166666);
		speed.y += (float) (ay * 0.0166666);
		w += (float) (e * 0.0166666);

		position.x += (float) (speed.x * 0.0166666);
		position.y += (float) (speed.y * 0.0166666);

		angle = angle + (float) (w * 0.0166666);

		allEngineActive = false;
		rightEngineActive = false;
		leftEngineActive = false;

	}

	@Override
	public void draw() {/*
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
		glPopMatrix();*/
		ArrayList<Vector2f> vector = new ArrayList<Vector2f>();
		for (int i = 0;i<random.nextInt(10)+5;i++){
			vector.add(new Vector2f(random.nextInt(1000),random.nextInt(700)));
		}
			//RenderUtil.drawLine(new Vector2f(100,100), 
				//	new Vector2f(300,300), (float) (5f), (Color) Color.BLUE);
		
		RenderUtil.drawPolyLineSmooth(vector, 5, (Color)Color.ORANGE);
			
		
				
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
		case ControlledObject.FIRE_FIRST_WEAPON: {
			// TODO more situat. check
			if (!arsenalList.isEmpty())
				arsenalList.get(0).setShootOn();
			System.out.println("weapon is empty");
			break;
		}
		case ControlledObject.FIRE_SECOND_WEAPON: {
			// TODO more situat. check
			if (!arsenalList.isEmpty())
				arsenalList.get(1).setShootOn();
			System.out.println("weapon is empty");
			break;
		}
		}
	}
}