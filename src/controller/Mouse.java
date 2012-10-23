/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import main.Client;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector2f;
import main.Engine;

class Mouse implements Engine {
    private Controller controller;
	private Cursor cursor;

	Mouse(Controller controller) {
        this.controller = controller;
        createMouse();
        cursor = new Cursor();

	}

	@Override
	public void tick() {
		cursor.setPosition(new Vector2f(org.lwjgl.input.Mouse.getX(),
				org.lwjgl.input.Mouse.getY()));
		cursor.setIsPressed(org.lwjgl.input.Mouse.isButtonDown(0), false);
	}

	@Override
	public void cleanUp() {

	}

	public Cursor getCursor() {
		return cursor;
	}

    private void createMouse(){
        try {
            org.lwjgl.input.Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
}
