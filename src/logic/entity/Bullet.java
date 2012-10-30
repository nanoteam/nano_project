package logic.entity;

import org.lwjgl.util.Color;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import render.RenderUtil;

public class Bullet extends GameObjectMoving {

	
	// temp
	private float size;
	//constant
	private final static float maxSpeed = 300;

	public Bullet(Vector2f pos, float angle, float size, float randTrajectory) {
		position = new Vector2f(pos);
		
		this.size = size;
		
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
