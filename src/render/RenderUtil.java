package render;

import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.GL11;
import java.util.List;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

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

	public static void drawLine(Vec2 positionBegin, Vec2 positionEnd,
			float width, Color color) {
		drawLine(new Vector2f(positionBegin.x, positionBegin.y), new Vector2f(
				positionEnd.x, positionEnd.y), width, color);
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

	/*
	 * public static void drawCircle(Vector2f position, float width, int radius,
	 * Color color) { glEnable(GL_LINE_SMOOTH); glLineWidth(width);
	 * glBegin(GL_LINES); glColor3ub(color.getRedByte(), color.getGreenByte(),
	 * color.getBlueByte()); glVertex2f((int) positionBegin.x, (int)
	 * positionBegin.y); glEnd(); glDisable(GL_LINE_SMOOTH); glColor3d(1, 1, 1);
	 * }
	 */
}
