package render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA. User: artur Date: 05.05.13 Time: 19:29 To change
 * this template use File | Settings | File Templates.
 */
public class RenderTextUtil {
	private static RenderTextUtil renderTextUtil;
	private static MFont font;

	public static RenderTextUtil getInstance() {
		if (renderTextUtil == null) {
			renderTextUtil = new RenderTextUtil();
		}
		return renderTextUtil;
	}

	private RenderTextUtil() {
		font = new MFont("res/font2.png", MFont.PNG_EXT);
	}

	public static void init() {

	}

	public void drawText(float x, float y, String text, Color color, float size) {
		font.drawString(text, x, y, color, size);
	}

	public void fillText(float x, float y, float width, float height,
			List<String> text, Color color, float size) {
		int lineNumber = 0;
		float left_x = x - width / 2;
		float top_y = y + height / 2;
		// System.out.println(text);
		// System.out.println(x + " " + y);
		for (String textLine : text) {

			// System.out.println(textLine);

			String[] words = textLine.split(" ");
			// System.out.println(Arrays.toString(words));
			int curWord = 0;
			int prevWord = 0;
			while (curWord < words.length) {
				String curLine = "";
				if (++lineNumber * font.getCharHeight() * size > height) {
					text.remove(0);
					return;
				}
				prevWord = curWord;
				while ((curLine.length() + words[curWord].length() + 1)
						* font.getCharWidth() * size / 2.5f < width) {
					curLine += ' ' + words[curWord];
					// System.out.println("Length of '" + curLine + "' is "
					// + (curLine.length()) * font.getCharWidth() * size);
					// System.out.println("curWord is " + curWord
					// + ", words length is " + words.length);
					if (++curWord == words.length) {
						// System.out.println("its break");
						break;
					}
				}
				drawText(left_x, top_y - lineNumber * font.getCharHeight()
						* size, curLine, color, size);
				if (curWord == prevWord) {
					drawText(left_x, top_y - lineNumber * font.getCharHeight()
							* size, words[curWord++], color, size / 2);
				}

			}

		}

	}
}
