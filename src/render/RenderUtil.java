package render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import util.MathUtil;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class RenderUtil {
	public static void drawPlot(Vector2f position, float size, Color color) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float sizeResult = size * Render.getZoom();
		GL11.glPointSize(sizeResult);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		GL11.glVertex2f(position.x, position.y);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

	}

	public static void drawLine(Vector2f positionBegin, Vector2f positionEnd,
			float width, Color color) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		float widthResult = width * Render.getZoom();
		GL11.glLineWidth(widthResult);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		GL11.glVertex2f(positionBegin.x, positionBegin.y);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		GL11.glVertex2f(positionEnd.x, positionEnd.y);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		// GL11.glColor3d(1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

	}

	public static void drawPolyLineSmooth(Vector2f position,
			List<Vector2f> positions, float widthLine, float angle, Color color) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		if (positions.size() < 1) {
			return;
		}
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		float widthResult = widthLine * Render.getZoom();
		GL11.glLineWidth(widthResult);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());

		for (int i = 0; i < positions.size(); i++) {
			GL11.glVertex2f(
					position.x
							+ MathUtil.newXTurn(positions.get(i).x,
									positions.get(i).y, angle),
					position.y
							+ MathUtil.newYTurn(positions.get(i).x,
									positions.get(i).y, angle));
		}
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_LINE_STRIP);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

	}

	public static void drawQaud(Vector2f position, float width, float height,
			float angle, Color color) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		GL11.glVertex2f(
				position.x + MathUtil.newXTurn(-width / 2, height / 2, angle),
				position.y + MathUtil.newYTurn(-width / 2, height / 2, angle));
		GL11.glVertex2f(
				position.x + MathUtil.newXTurn(width / 2, height / 2, angle),
				position.y + MathUtil.newYTurn(width / 2, height / 2, angle));
		GL11.glVertex2f(
				position.x + MathUtil.newXTurn(width / 2, -height / 2, angle),
				position.y + MathUtil.newYTurn(width / 2, -height / 2, angle));
		GL11.glVertex2f(
				position.x + MathUtil.newXTurn(-width / 2, -height / 2, angle),
				position.y + MathUtil.newYTurn(-width / 2, -height / 2, angle));
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

	}

	public static void drawCircle(Vector2f position, float radius,
			float witdthLine, Color color) {
		int numberPointsInCircle = 100;
		Vector2f[] box = new Vector2f[numberPointsInCircle];
		float angle;
		for (int i = 0; i < numberPointsInCircle; i++) {
			angle = (float) (2 * Math.PI * i / numberPointsInCircle);
			box[i] = new Vector2f(
					(float) (Math.cos(angle) * radius + position.x),
					(float) (Math.sin(angle) * radius + position.y));
		}
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glColor3ub(color.getRedByte(), color.getGreenByte(),
				color.getBlueByte());
		for (int i = 0; i < numberPointsInCircle; i++) {
			GL11.glVertex2f(box[i].x, box[i].y);
		}
		GL11.glEnd();
	}

	public static void drawImage(float x, float y, float width, float height,
			float angle, float zoom, Image image) {
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 0f);
		float actual_width, actual_height;
		// actual_width = image.getTexture().getWidth();
		// actual_height = image.getTexture().getHeight();

		actual_width = image.getTWidth();
		actual_height = image.getHeight();

		// width = (int) (image.getTexture().getImageWidth() * zoom);
		// height = (int) (image.getTexture().getImageHeight() * zoom);

		GL11.glPushMatrix();
		// GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		org.newdawn.slick.Color.white.bind();
		image.getTexture().bind();
		// GL11.glEnable(GL11.GL_TEXTURE_2D);
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
		GL11.glEnd();
		GL11.glPopMatrix();

		// GL11.glDisable(GL11.GL_TEXTURE_2D);
		// GL11.glDisable(GL11.GL_QUADS);
	}

	public static void drawLifebar(float x, float y, float width, float height,
			float percent, float scale) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3ub(Color.GREY.getRedByte(), Color.GREY.getGreenByte(),
				Color.GREY.getBlueByte());
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, y + height * scale);
		GL11.glVertex2f(x + width * scale, y + height * scale);
		GL11.glVertex2f(x + width * scale, y);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3ub(Color.GREEN.getRedByte(), Color.GREEN.getGreenByte(),
				Color.GREEN.getBlueByte());
		GL11.glVertex2f(x + 1 * scale, y + 1 * scale);
		GL11.glVertex2f(x + 1 * scale, y + height * scale - 1 * scale);
		GL11.glVertex2f(x + width * scale * percent - 1 * scale, y + height
				* scale - 1 * scale);
		GL11.glVertex2f(x + width * scale * percent - 1 * scale, y + 1 * scale);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
