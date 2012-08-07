/**
 *    
 * this class inkapsulate 2 class - keyboard and mouse;this class is singlton
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import java.util.ArrayList;
import java.util.Arrays;

import util.LightInteger;

import logic.Level;
import main.Client;
import main.Engine;

//OMG, there are singlton, in begin i think thats this is good idea.
//
public class Controller implements Engine {
	private Keyboard keyboard;
	private Mouse mouse;

	private Client client;
	private static Controller controller;

	private Controller(boolean active_keyboard, boolean active_mouse,
			Client client) {
		if (active_keyboard) {
			keyboard = new Keyboard();
		}
		if (active_mouse) {
			mouse = new Mouse();

		}
		this.client = client;

	}

	public static Controller createController(boolean active_keyboard,
			boolean active_mouse, Client client) {
		if (controller == null) {
			controller = new Controller(active_keyboard, active_mouse, client);
		}
		return controller;
	}

	public static Controller getController() {
		return controller;
	}

	@Override
	public void tick() {
		keyboard.tick();
		if (null != keyboard) {
			ArrayList<LightInteger> list_key = new ArrayList<LightInteger>();

			while (org.lwjgl.input.Keyboard.next()) {
				list_key.add(new LightInteger(org.lwjgl.input.Keyboard
						.getEventKey()));
			}
			client.keyAction(list_key);

		}
		if (null != mouse) {
			mouse.tick();
			client.updateCursor(mouse.getCursor());
			ArrayList<LightInteger> list_key = new ArrayList<LightInteger>();

			while (org.lwjgl.input.Mouse.next()) {
				list_key.add(new LightInteger(org.lwjgl.input.Mouse
						.getEventButton()));
			}
			if (list_key.size() > 0) {
				client.mouseAction(list_key);
				for(LightInteger a :list_key)
				System.out.println(a.data);
			}

		}

	}

	@Override
	public void cleanUp() {
		keyboard.cleanUp();
		mouse.cleanUp();
	}
	/*
	 * public get
	 * 
	 * Mouse.poll(); Keyboard.poll();
	 */

	/*
	 * public Keyboard getKeyboard() { return keyboard; }
	 * 
	 * public Mouse getMouse() { return mouse; }
	 */

}
