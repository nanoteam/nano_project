/**
 *    
 * this class inkapsulate 2 class - keyboard and mouse;singlton;
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import util.LightInteger;

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

	@Override
	public void tick() {
        if (keyboardOn) {
            keyboard.tick();
            client.sendEventsKeyboard(keyboard.getStateKeyboard());

        }

		if (mouseOn) {
			mouse.tick();
            //client.sendEventMouse(mouse.getStateMouse());
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
    public Vector2f getMousePosition(){
        return mouse.getPosition();
    }
}
