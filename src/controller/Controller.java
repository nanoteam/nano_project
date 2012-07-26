/**
 *    
 * this class inkapsulate 2 class - keyboard and mouse;this class is singlton
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import main.Engine;

public class Controller implements Engine {
	private Keyboard keyboard;
	private Mouse mouse;

	private static Controller controller;

	private Controller(boolean active_keyboard, boolean active_mouse) {
		if (active_keyboard) {
			keyboard = new Keyboard();
		}
		if (active_mouse) {
			mouse = new Mouse();
		}
	}

	public static Controller createController(boolean active_keyboard,
			boolean active_mouse) {
		if (controller == null) {
			controller = new Controller(active_keyboard, active_mouse);
		}
		return controller;
	}

	public static Controller getController() {
		return controller;
	}

	@Override
	public void tick() {
		keyboard.tick();
		mouse.tick();
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
