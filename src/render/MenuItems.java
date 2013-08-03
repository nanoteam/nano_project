package render;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import logic.Level;
import logic.entity.ship.Ship;
import main.Client;
import main.Global;

public class MenuItems {

	private boolean isConsole = true;
	// private StatusBar leftBar;
	// private StatusBar rigthBar;
	private Vector2f leftBar_position;
	private Vector2f rigthBar_position;
	private Client client;
	private static Image detailedStatusbar_img;
	private static Image gameMenu_img;
	private static float CONSOLE_X = 250;
	private static float CONSOLE_Y = 450;
	private static float CONSOLE_HEIGHT = 400;
	private static float CONSOLE_WIDTH = 350;
	public static int CONSOLE_ON = 0b0000001;
	public static int STATUS_BARS_ON = 0b00000010;
	private int resolutionX = 100, resolutionY = 100;

	public MenuItems(int r_x, int r_y) {
		this.client = client;
		/*
		 * leftBar = new StatusBar(StatusBar.LEFT_BAR);
		 * leftBar.setLifeBarRelativePosition(new Vector2f(31, 36)); rigthBar =
		 * new StatusBar(StatusBar.RIGHT_BAR);
		 * rigthBar.setLifeBarRelativePosition(new Vector2f(119, 36));
		 */
		Global.consoleText.add("Console initialized.");
		Global.consoleText
				.add("If you have some problems, you should send letter to this mail: www@www.ww. Thanks.");

		resolutionX = r_x;
		resolutionY = r_y;
	}

	public void openglIntro() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, resolutionX, 0, resolutionY, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glScalef(1, 1, 1);
		glTranslatef(0, 0, 0);
	}

	public void draw(int mode) {
		openglIntro();
		if ((mode & MenuItems.CONSOLE_ON) != 0)
			drawConsole();
		if ((mode & MenuItems.STATUS_BARS_ON) != 0) {
			// leftBar.draw();
			// rigthBar.draw();
		}

	}

	public void drawConsole() {
		RenderUtil.drawQaud(new Vector2f(CONSOLE_X, CONSOLE_Y), CONSOLE_WIDTH,
				CONSOLE_HEIGHT, 0f, (Color) Color.GREY);
		// RenderTextUtil.getInstance()
		// .drawText(CONSOLE_X, CONSOLE_Y, Global.consoleText.toString(),
		// org.newdawn.slick.Color.blue, 2f);
		RenderTextUtil.getInstance().fillText(CONSOLE_X, CONSOLE_Y,
				CONSOLE_WIDTH, CONSOLE_HEIGHT, Global.consoleText,
				org.newdawn.slick.Color.white, 0.7f);
	}

	public void drawStatusBars() {
		// leftBar.draw();
		// rigthBar.draw();
	}

	public boolean isConsole() {
		return isConsole;
	}

	public void setConsole(boolean isConsole) {
		this.isConsole = isConsole;
	}

	public static void drawDetailedStatusbar(Ship ship) {
		// TODO write code which consider borders of window

		if (detailedStatusbar_img == null) {
			try {
				detailedStatusbar_img = new Image()
						.LoadPNG("res/menu/detailedStatusbar.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		float x = ship.getPosition().x - 150;
		float y = ship.getPosition().y - 20;
		List<String> info = new ArrayList<String>();
		detailedStatusbar_img.draw(x, y, detailedStatusbar_img.getWidth(),
				detailedStatusbar_img.getHeight(), 0);
		info.add("Health: ?/?");
		info.add("Money: ?");
		info.add("State: ?");
		RenderTextUtil.getInstance().fillText(
				x + detailedStatusbar_img.getWidth() / 5, y,
				detailedStatusbar_img.getWidth() / 1.5f,
				detailedStatusbar_img.getHeight(), info,
				org.newdawn.slick.Color.white, 0.5f);
		RenderUtil.drawLifebar(x - detailedStatusbar_img.width / 3, y
				- detailedStatusbar_img.height / 3, 50, 10,
				ship.getLifePercent(), 1);
	}

	public void drawGameMenu() {
		if (gameMenu_img == null) {
			try {
				gameMenu_img = new Image().LoadPNG("res/menu/gameMenu.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// gameMenu_img.draw(resolutionX / 2, resolutionY / 2,
		gameMenu_img.draw(400, 200,
				gameMenu_img.getWidth(), gameMenu_img.getHeight(), 0);
	}
}
