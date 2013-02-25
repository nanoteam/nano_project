/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import main.Engine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import util.LightInteger;

import java.util.ArrayList;
import java.util.List;

final class Mouse implements Engine {
    private Controller controller;
    private Vector2f position;
    private Vector2f lastPosition;

    //private List<LightInteger> allNeedButton;
    private List<StateMouse> statesMouse;

    Mouse(Controller controller) {
        this.controller = controller;
        createMouse();
        position = lastPosition = new Vector2f(org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY());
    }

    @Override
    public void tick() {
        lastPosition = new Vector2f(position);
        position = new Vector2f(org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY());

        //reset collection, must be!
        statesMouse = new ArrayList<StateMouse>();

        //check keys, buffered
        org.lwjgl.input.Mouse.poll();

        //filling output list by events data
        //int count = org.lwjgl.input.Mouse.get();

        while (org.lwjgl.input.Mouse.next()) {
            StateMouse stateMouse = null;
            //if button event
            if (org.lwjgl.input.Mouse.getDWheel() == 0) {
                if (org.lwjgl.input.Mouse.getEventButtonState()) {
                    stateMouse = new StateMouse(
                            org.lwjgl.input.Mouse.getEventButton(), StateMouse.DOWN_PRESSED, new Vector2f(org.lwjgl.input.Mouse.getEventX(), org.lwjgl.input.Mouse.getEventY()));
                } else {
                    stateMouse = new StateMouse(
                            org.lwjgl.input.Mouse.getEventButton(), StateMouse.UP_RELEASED, new Vector2f(org.lwjgl.input.Mouse.getEventX(), org.lwjgl.input.Mouse.getEventY()));
                }
                //if wheel event
            } else {
                stateMouse = new StateMouse(
                        org.lwjgl.input.Mouse.getDWheel(), new Vector2f(org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY()));
            }
            statesMouse.add(stateMouse);
        }

        //0,1,2 button.
        StateMouse stateMouse;
        for(int i = 0; i<5;i++){
            stateMouse = null;
            if (org.lwjgl.input.Mouse.isButtonDown(i)) {
                stateMouse = new StateMouse(i, StateMouse.DOWN, new Vector2f(org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY()));
            } else {
                stateMouse = new StateMouse(i, StateMouse.UP, new Vector2f(org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY()));
            }
            statesMouse.add(stateMouse);
        }
    }

    Vector2f getPosition() {
        return new Vector2f(position);
    }
    Vector2f getDVector() {
        return new Vector2f(org.lwjgl.input.Mouse.getDX(),org.lwjgl.input.Mouse.getDY());
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

    List<StateMouse> getStatesMouse(){
        return statesMouse;
    }

}