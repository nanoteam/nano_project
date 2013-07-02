package render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import util.MathUtil;

public class Sprite extends Image {

	int curFrame = 0;
	int framesCount;
	int syncTimer = 0;
	float oWidth;
	float oHeight;
	private List<Vector2f> tOffsets = new ArrayList<Vector2f>();


	public Sprite() {
		tOffsets.add(new Vector2f(0, 0));
	}

	public void slit(int oWidth, int oHeight, int count) {
		framesCount = count;
		System.out.println(oWidth+" "+width);
		this.oWidth = oWidth;
		this.oHeight = oHeight;
		tWidth = oWidth/(float)width;
		tHeight = oHeight/(float)height;
		for (int i = 1; i < count; i++) {
			tOffsets.add(new Vector2f(i * tWidth, 0));
		}
		System.out.println("init. passed: "+tWidth+tHeight+tOffsets);
	}

	public void draw(float x, float y, float width, float height, float angle) {
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 0f);
		float tOffsetX = tOffsets.get(curFrame).x;
		float tOffsetY = tOffsets.get(curFrame).y;

		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		org.newdawn.slick.Color.white.bind();
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);

		// 4
		GL11.glTexCoord2f(tOffsetX, tOffsetY);
		GL11.glVertex2f(x + MathUtil.newXTurn(-width / 2, height / 2, angle), y
				+ MathUtil.newYTurn(-width / 2, height / 2, angle));

		// 3
		GL11.glTexCoord2f(tOffsetX + tWidth, tOffsetY);
		GL11.glVertex2f(x + MathUtil.newXTurn(width / 2, height / 2, angle), y
				+ MathUtil.newYTurn(width / 2, height / 2, angle));

		// 2

		GL11.glTexCoord2f(tOffsetX + tWidth, tOffsetY + tHeight);
		GL11.glVertex2f(x + MathUtil.newXTurn(width / 2, -height / 2, angle), y
				+ MathUtil.newYTurn(width / 2, -height / 2, angle));

		// 1

		GL11.glTexCoord2f(tOffsetX, tOffsetY + tHeight);
		GL11.glVertex2f(x + MathUtil.newXTurn(-width / 2, -height / 2, angle),
				y + MathUtil.newYTurn(-width / 2, -height / 2, angle));

		GL11.glEnd();
		GL11.glPopMatrix();
		
		if(++syncTimer>10){
			syncTimer = 0;
			curFrame++;
			if(curFrame==framesCount){
				curFrame = 0;
			}
		}
	}

}
