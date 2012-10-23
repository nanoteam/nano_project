/**
 *    
 * this class inkapsulate 2 class - keyboard and mouse;singlton;
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

public class Controller implements Engine {
    private Keyboard keyboard;
	private Mouse mouse;

    private Client client;
	private static Controller controller;

    private boolean keyboardOn = false;
    private boolean mouseOn = false;

	private Controller(	Client client) {
		keyboard = new Keyboard(this, client.getInputToAction().getAllNeedKeybKeys());
        keyboardOn = true;
        mouseOn = true;
		mouse = new Mouse(this);
		this.client = client;
	}

    public static Controller createController(Client client) {
        if (controller == null) {
            controller = new Controller(client);
        }
        return controller;
    }

    public Cursor getCursor(){
		return mouse.getCursor();
	}

	@Override
	public void tick() {
        if (keyboardOn) {
            keyboard.tick();
            client.sendEventsKeyboard(keyboard.getEvent());

        }

		if (mouseOn) {
			mouse.tick();
			client.updateCursor(mouse.getCursor());
			ArrayList<LightInteger> list_key = new ArrayList<LightInteger>();
			// what a format of Mouse.event key?
			// there are no capability with method client.keyAction()
			while (org.lwjgl.input.Mouse.next()) {
				list_key.add(new LightInteger(org.lwjgl.input.Mouse
						.getEventButton()));
			}
			if (list_key.size() > 0) {
				client.mouseAction(list_key);
			}
		}
	}

	@Override
	public void cleanUp() {
		keyboard.cleanUp();
		mouse.cleanUp();
	}

    public void setKeyboardOn(boolean keyboardOn){
        this.keyboardOn = keyboardOn;
    }
    public void setMouseOn(boolean mouseOn){
        this.mouseOn = mouseOn;
    }

    public boolean isKeyboardOn() {
        return keyboardOn;
    }

    public boolean isMouseOn() {
        return mouseOn;
    }
}
