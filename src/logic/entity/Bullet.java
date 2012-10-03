package logic.entity;

import org.lwjgl.util.Color;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class Bullet extends GameObjectSimpleMoving {

	
	// temp
	private float size;
	//constant
	private final static float maxSpeed = 300;

	public Bullet(Vector2f pos, float angle, float size, float randTrajectory) {
		position = new Vector2f(pos);
		speed = new Vector2f((float) (maxSpeed * Math.cos(angle / 60)),
				(float) (maxSpeed * Math.sin(angle / 60)));
		this.size = size;
		// randomize traektori desu
		speed.x += randTrajectory * Math.random() - randTrajectory/2f;
		speed.y += randTrajectory * Math.random() - randTrajectory/2f;
		

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		speed.y+=-level.gravity*(0.016666);
		position.x += speed.x*(0.016666);
		position.y += speed.y*(0.016666);
		
	}

	@Override
	public void draw() {
		
		RenderUtil.drawPlot(new Vector2f(position), size, (Color) Color.CYAN);
		
	
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
