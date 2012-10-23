/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import main.Engine;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

class Mouse implements Engine {
    private Controller controller;
    private Vector2f position;
    private Vector2f lastPosition;
    //private List<>

    Mouse(Controller controller) {
        this.controller = controller;
        createMouse();
        position = lastPosition = new Vector2f(org.lwjgl.input.Mouse.getX(),org.lwjgl.input.Mouse.getY());
    }

    @Override
    public void tick() {
        lastPosition = new Vector2f(position);
        position = new Vector2f(org.lwjgl.input.Mouse.getX(),org.lwjgl.input.Mouse.getY());
    }

    Vector2f getPosition(){
        return new Vector2f(position);
    }

    private void createMouse() {
        try {
            org.lwjgl.input.Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanUp() {
        org.lwjgl.input.Mouse.destroy();
    }

}
