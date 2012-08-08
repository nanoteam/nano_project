package logic.entity;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import render.RenderUtil;

public class Particle extends GameObjectSimpleMoving {
	private Color color;
	private int lifeTime;
	private float size;
	public Particle(Vector2f position,Vector2f speed, float size, int lifeTime, Color color){
		this.position = position;
		this.speed = speed;
		this.size = size;
		this.lifeTime = lifeTime;
		this.color = color;
	}
	@Override
	public void init() {

	}

	@Override
	public void update() {
		lifeTime--;
		if (lifeTime<0) {
			live = false;
		}
	}

	@Override
	public void move() {
		position.x +=speed.x;
		position.y +=speed.y;
//		System.out.println("Particle.move()");
	}

	@Override
	public void draw() {
		RenderUtil.drawPlot(position, size, color);
	}

	@Override
	public void playSound() {
	}

}
