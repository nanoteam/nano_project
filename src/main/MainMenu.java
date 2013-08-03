package main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import render.Image;
import render.MenuItems;
import render.Render;

public class MainMenu {
	private Client client;
	private boolean finished;
	private MenuItems menuItems;
	private ArrayList<MenuButton> buttonList;
	private int resolutionX = 100, resolutionY = 100;

	public MainMenu(Client client) {
		this.client = client;
		menuItems = new MenuItems(resolutionX, resolutionY);
		finished = false;
		System.out.println("menu has been initialized");
		buttonList = new ArrayList<MenuButton>();
		addButton(new Vector2f(700, 350), "res/menu/start_button.png", "start");
		addButton(new Vector2f(700, 150), "res/menu/exit_button.png", "exit");
		addButton(new Vector2f(700, 550), "res/menu/resume_button.png",
				"resume");
		resolutionX = client.getRender().getResolutionX();
		resolutionY = client.getRender().getResolutionY();

	}

	public void start() {
		while (!finished) {

			if (Display.isCloseRequested()) {
				finished = true;
			} else if (Display.isActive()) {
				Display.update();
				glViewport(0, 0, resolutionX, resolutionY);
				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				glOrtho(0, resolutionX, 0, resolutionY, -1, 1);
				glMatrixMode(GL_MODELVIEW);
				glLoadIdentity();
				glScalef(1, 1, 1);
				glTranslatef(0, 0, 0);

				// clear buffer and add image to buffer, display been do good
				glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
				glClear(GL_COLOR_BUFFER_BIT);

				glPushMatrix();
				for (MenuButton mb : buttonList) {
					mb.render();
				}
				glPopMatrix();
				menuItems.draw(MenuItems.CONSOLE_ON);
				Display.sync(60);
				inputTick();

			} else {
				System.out.println("quit");
				break;
			}
		}
	}

	void addButton(Vector2f position, String imagesrc, String action) {
		Image image = new Image();
		try {
			image.LoadPNG(imagesrc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buttonList.add(new MenuButton(image, position, action));

	}

	String inputTick() {

		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();
			String action = null;
			System.out.println("press on " + x + " " + y);
			for (MenuButton mb : buttonList) {
				if (mb.check(x, y)) {
					action = mb.getAction();
				} else {
					System.out.println("not on button");
				}
			}
			if (action != null) {
				switch (action) {
				case "start":
					client.setState(Client.STATE_GAME);
					client.start();
					break;
				case "exit":
					finished = true;
					client.exit();
					break;

				case "resume":
					if (client.getState() == Client.STATE_MAIN_MENU)
						break;
					client.setState(Client.STATE_MAIN_MENU);
					client.start();
					break;
				default:
					break;
				}
			}
		}
		return null;
	}

	class MenuButton {
		// TODO splitttttt
		private Image image;
		private Vector2f position;
		private String action;
		private boolean visible;

		public MenuButton(Image image, Vector2f position, String action) {
			this.action = action;
			this.image = image;
			this.position = position;
		}

		void render() {
			image.draw(position.x, position.y, image.getWidth(),
					image.getHeight(), 0);
			System.out.println(position + " " + image.getWidth());
		}

		String getAction() {
			return action;
		}

		boolean check(int x, int y) {
			if ((x >= position.x - image.getWidth() / 2 && x <= position.x
					+ image.getWidth() / 2)
					&& (y >= position.y - image.getHeight() / 2 && y <= position.y
							+ image.getHeight() / 2))
				return true;
			else
				return false;
		}

		void setVisible(boolean visible) {
			this.visible = visible;
		}
	}
}
