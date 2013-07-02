package render;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import util.MathUtil;

public class Image {

	protected Texture texture;
	protected int width;
	protected int height;
	protected float tWidth;
	protected float tHeight;
	private float tOffsetX = 0;
	private float tOffsetY = 0;
	private String ref;

	public void LoadPNG(String file) throws IOException {
		texture = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream(file));
		if (texture != null) {
			width = texture.getImageWidth();
			height = texture.getImageHeight();
			tWidth = texture.getWidth();
			tHeight = texture.getHeight();
		}
	}

	public void LoadGIF(String file) throws IOException {
		texture = TextureLoader.getTexture("GIF",
				ResourceLoader.getResourceAsStream(file));
		if (texture != null) {
			width = texture.getImageWidth();
			height = texture.getImageHeight();
			tWidth = texture.getWidth();
			tHeight = texture.getHeight();
		}
	}

	public Image() {

	}

	public Image(String ref, float x, float y) {
		try {
			LoadGIF(ref);
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		this.ref = ref;

	}

	public Texture getTexture() {
		return texture;
	}

	public Image getSubImage(int _x, int _y, int _width, int _height) {
		float newTOffsetX = ((_x / (float) this.width) * tWidth) + tOffsetX;
		float newTOffsetY = ((_y / (float) this.height) * tHeight) + tOffsetY;
		float newTWidth = ((_width / (float) this.width) * tWidth);
		float newTHeight = ((_height / (float) this.height) * tHeight);
		Image sub = new Image();
		sub.texture = this.texture;
		sub.tOffsetX = newTOffsetX;
		sub.tOffsetY = newTOffsetY;
		sub.tWidth = newTWidth;
		sub.tHeight = newTHeight;

		sub.width = _width;
		sub.height = _height;
		sub.ref = this.ref;

		return sub;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getTWidth() {
		return tWidth;
	}

	public float getTHeight() {
		return tHeight;
	}

	public String toffsets() {
		return "offsets " + tOffsetX + " x " + tOffsetY;
	}

	public void draw(float x, float y, float width, float height, float angle) {
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 0f);

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
	}

	public void fillPolygon(float x, float y, float fwidth, float fheight) {
		int scale = 1;
		GL11.glPushMatrix();
		// GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		org.newdawn.slick.Color.white.bind();
		texture.bind();
		GL11.glColor3ub(Color.GREY.getRedByte(), Color.GREY.getGreenByte(),
				Color.GREY.getBlueByte());
		for (int i = 0; i < (fwidth / width) + 1; i++) {
			for (int j = 0; j < (fheight / height) + 1; j++) {
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(tOffsetX, tOffsetY + tHeight);
				GL11.glVertex2f(x + width * i, y + height * j);
				GL11.glTexCoord2f(tOffsetX + tWidth, tOffsetY + tHeight);
				GL11.glVertex2f(x + width * i + width, y + height * j);

				GL11.glTexCoord2f(tOffsetX + tWidth, tOffsetY);
				GL11.glVertex2f(x + width * i + width, y + height * j + height);
				GL11.glTexCoord2f(tOffsetX, tOffsetY);
				GL11.glVertex2f(x + width * i, y + height * j + height);
				GL11.glEnd();

			}
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}