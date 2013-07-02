package render;

import org.newdawn.slick.Color;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA. User: artur Date: 05.05.13 Time: 19:39 To change
 * this template use File | Settings | File Templates.
 */
public class MFont {
	private Image font;
	private Image sFont[] = new Image[256];
	private static int charWidth;
	private static int charHeight;
	public static int GIF_EXT = 0;
	public static int PNG_EXT = 1;

	public MFont(String ref, int ext) {

		font = new Image();
		if (ext == GIF_EXT) {
			try {
				font.LoadGIF(ref);
			} catch (IOException e) {
				e.printStackTrace(); // To change body of catch statement use
										// File |
										// Settings | File Templates.
			}
		}
		if (ext == PNG_EXT) {
			try {
				font.LoadPNG(ref);
			} catch (IOException e) {
				e.printStackTrace(); // To change body of catch statement use
										// File |
										// Settings | File Templates.
			}
		}
		biuldFont();
	}

	private void biuldFont() {
		int xi = font.getWidth() / 16;
		int yi = font.getHeight() / 16;
		int z = -1;
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++) {
				z++;
				if (z < 256) {
					sFont[z] = font.getSubImage(j * xi, i * yi, xi, yi);
					System.out.println(sFont[z].toffsets());
				}
			}
		charWidth = sFont[0].getWidth();
		charHeight = sFont[0].getHeight();
	}

	public static int getCharWidth() {
		return charWidth;
	}

	public static int getCharHeight() {
		return charHeight;
	}

	public void drawString(String text, float xpos, float ypos, Color color,
			float size) {
		for (int i = 0; i < text.length(); i++) {
			sFont[text.codePointAt(i)].draw(xpos + charWidth / 2.5f * size * i,
					ypos, charWidth * size, charHeight * size, 0);
		}
	}
}
