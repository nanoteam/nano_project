package logic.entity;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import render.RenderUtil;

public class Particle extends GameObjectMoving {
	private Color color;
	private int lifeTime;
	private float size;

	public Particle(Vector2f position, Vector2f speed, float size,
			int lifeTime, Color color) {
		this.position = position;
		this.speed = speed;
		this.size = size;
		this.lifeTime = lifeTime;
		this.color = color;
	}

	@Override
	public void update() {
		lifeTime--;
		if (lifeTime < 0) {
			live = false;
		}
	}

	@Override
	public void move() {
		position.x += speed.x * 0.016666;
		position.y += speed.y * 0.016666;
		// System.out.println("Particle.move()");
	}

	@Override
	public void draw() {
		RenderUtil.drawPlot(position, size, color);

	}

	@Override
	public void playSound() {
	}
	
	@Override
	public void destroy() {
		color = null;
        speed = null;
        physicObject = null;
        position = null;
        renderObject = null;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
}
