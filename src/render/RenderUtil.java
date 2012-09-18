package render;

import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.GL11;
import java.util.List;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;

import util.MathUtil;

import util.MathUtil;

public class RenderUtil {
	public static void drawPlot(Vector2f position, float size, Color color) {
		GL11.glPointSize(size);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		GL11.glVertex2f(position.x, position.y);
		GL11.glEnd();
	}

	public static void drawLine(Vector2f positionBegin, Vector2f positionEnd,
			float width, Color color) {
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(width);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		GL11.glVertex2f(positionBegin.x, positionBegin.y);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		GL11.glVertex2f(positionEnd.x, positionEnd.y);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glColor3d(1, 1, 1);
	}

	public static void drawPolyLineSmooth(List<Vector2f> positions,
			float width, Color color) {
		if (positions.size() < 1) {
			return;
		}
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(width);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		for (int i = 0; i < positions.size(); i++) {
			GL11.glVertex2f(positions.get(i).x, positions.get(i).y);
		}
		GL11.glEnd();
	}

	public static void drawQaud(float x, float y, float width, float height,
			float angle, Color color) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());

		GL11.glVertex2f(x + MathUtil.newXTurn(-width / 2, height / 2, angle), y
				+ MathUtil.newYTurn(-width / 2, height / 2, angle));
		GL11.glVertex2f(x + MathUtil.newXTurn(width / 2, height / 2, angle), y
				+ MathUtil.newYTurn(width / 2, height / 2, angle));
		GL11.glVertex2f(x + MathUtil.newXTurn(width / 2, -height / 2, angle), y
				+ MathUtil.newYTurn(width / 2, -height / 2, angle));
		GL11.glVertex2f(x + MathUtil.newXTurn(-width / 2, -height / 2, angle),
				y + MathUtil.newYTurn(-width / 2, -height / 2, angle));

		GL11.glEnd();

	}

	public static void drawImage(float x, float y, float width, float height,
			float angle, float zoom, Image image) {

		float actual_width, actual_height;
		actual_width = image.getTexture().getWidth();
		actual_height = image.getTexture().getHeight();

		// width = (int) (image.getTexture().getImageWidth() * zoom);
		// height = (int) (image.getTexture().getImageHeight() * zoom);

		GL11.glPushMatrix();

		image.getTexture().bind();
		// Color.white.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
		// 1
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x + MathUtil.newXTurn(-width / 2, -height / 2, angle),
				y + MathUtil.newYTurn(-width / 2, -height / 2, angle));
		// 2
		GL11.glTexCoord2f(actual_width, 0);
		GL11.glVertex2f(x + MathUtil.newXTurn(width / 2, -height / 2, angle), y
				+ MathUtil.newYTurn(width / 2, -height / 2, angle));
		// 3
		GL11.glTexCoord2f(actual_width, actual_height);
		GL11.glVertex2f(x + MathUtil.newXTurn(width / 2, height / 2, angle), y
				+ MathUtil.newYTurn(width / 2, height / 2, angle));
		// 4
		GL11.glTexCoord2f(0, actual_height);
		GL11.glVertex2f(x + MathUtil.newXTurn(-width / 2, height / 2, angle), y
				+ MathUtil.newYTurn(-width / 2, height / 2, angle));

		GL11.glPopMatrix();
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
