package render;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import logic.entity.GameObject;

public class PopUpText extends GameObject {
	private int liveTime;
	private String text;
	private float size;
	private Color color;

	public PopUpText(float x, float y, String text, Color color, float size) {
		position = new Vector2f(x, y);
		this.text = text;
		liveTime = 60;
		this.size = size;
		// TODO split text
	}

	@Override
	public void update() {
		liveTime--;
		if (liveTime < 0) {
			live = false;
		}
	}

	@Override
	public void move() {
		position.y += 0.3f;
		size -= 0.006f;
	}

	@Override
	public void draw() {
		RenderTextUtil.getInstance().drawText(position.x, position.y, text,
				color, size);
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void toThink() {
		// TODO Auto-generated method stub

	}

}
