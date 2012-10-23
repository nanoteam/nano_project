/*


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.Log;

*/
/**
 * A wrapped for all keyboard, mouse and controller input
 *
 * @author kevin
 *//*

public class Input {
    */
/** The controller index to pass to check all controllers *//*

    public static final int ANY_CONTROLLER = -1;


    */
/** True if the controllers system has been initialised *//*

    private static boolean controllersInited = false;
    */
/** The list of controllers *//*

    private static ArrayList controllers = new ArrayList();

    */
/** True if the event has been consumed *//*

    protected boolean consumed = false;
    */
/** A list of listeners to be notified of input events *//*

    protected HashSet allListeners = new HashSet();
    */
/** The listeners to notify of key events *//*

    protected ArrayList keyListeners = new ArrayList();
    */
/** The listener to add *//*

    protected ArrayList keyListenersToAdd = new ArrayList();
    */
/** The listeners to notify of mouse events *//*

    protected ArrayList mouseListeners = new ArrayList();
    */
/** The listener to add *//*

    protected ArrayList mouseListenersToAdd = new ArrayList();
    */
/** The listener to nofiy of controller events *//*

    protected ArrayList controllerListeners = new ArrayList();


    */
/** True if the display is active *//*

    private boolean displayActive = true;

    */
/**
     * Disables support for controllers. This means the jinput JAR and native libs
     * are not required.
     *//*

    public static void disableControllers() {
        controllersInited = true;
    }

    */
/**
     * Create a new input with the height of the screen
     *
     * @param height The height of the screen
     *//*

    public Input(int height) {
        init(height);
    }



    */
/**
     * Add a listener to be notified of input events
     *
     * @param listener The listener to be notified
     *//*

    public void addListener(InputListener listener) {
        addKeyListener(listener);
        addMouseListener(listener);
        addControllerListener(listener);
    }

    */
/**
     * Add a key listener to be notified of key input events
     *
     * @param listener The listener to be notified
     *//*

    public void addKeyListener(KeyListener listener) {
        keyListenersToAdd.add(listener);
    }

    */
/**
     * Add a key listener to be notified of key input events
     *
     * @param listener The listener to be notified
     *//*

    private void addKeyListenerImpl(KeyListener listener) {
        if (keyListeners.contains(listener)) {
            return;
        }
        keyListeners.add(listener);
        allListeners.add(listener);
    }

    */
/**
     * Add a mouse listener to be notified of mouse input events
     *
     * @param listener The listener to be notified
     *//*

    public void addMouseListener(MouseListener listener) {
        mouseListenersToAdd.add(listener);
    }

    */
/**
     * Add a mouse listener to be notified of mouse input events
     *
     * @param listener The listener to be notified
     *//*

    private void addMouseListenerImpl(MouseListener listener) {
        if (mouseListeners.contains(listener)) {
            return;
        }
        mouseListeners.add(listener);
        allListeners.add(listener);
    }

    */
/**
     * Add a controller listener to be notified of controller input events
     *
     * @param listener The listener to be notified
     *//*

    public void addControllerListener(ControllerListener listener) {
        if (controllerListeners.contains(listener)) {
            return;
        }
        controllerListeners.add(listener);
        allListeners.add(listener);
    }

    */
/**
     * Remove all the listeners from this input
     *//*

    public void removeAllListeners() {
        removeAllKeyListeners();
        removeAllMouseListeners();
        removeAllControllerListeners();
    }

    */
/**
     * Remove all the key listeners from this input
     *//*

    public void removeAllKeyListeners() {
        allListeners.removeAll(keyListeners);
        keyListeners.clear();
    }

    */
/**
     * Remove all the mouse listeners from this input
     *//*

    public void removeAllMouseListeners() {
        allListeners.removeAll(mouseListeners);
        mouseListeners.clear();
    }

    */
/**
     * Remove all the controller listeners from this input
     *//*

    public void removeAllControllerListeners() {
        allListeners.removeAll(controllerListeners);
        controllerListeners.clear();
    }

    */
/**
     * Add a listener to be notified of input events. This listener
     * will get events before others that are currently registered
     *
     * @param listener The listener to be notified
     *//*

    public void addPrimaryListener(InputListener listener) {
        removeListener(listener);

        keyListeners.add(0, listener);
        mouseListeners.add(0, listener);
        controllerListeners.add(0, listener);

        allListeners.add(listener);
    }

    */
/**
     * Remove a listener that will no longer be notified
     *
     * @param listener The listen to be removed
     *//*

    public void removeListener(InputListener listener) {
        removeKeyListener(listener);
        removeMouseListener(listener);
        removeControllerListener(listener);
    }

    */
/**
     * Remove a key listener that will no longer be notified
     *
     * @param listener The listen to be removed
     *//*

    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);

        if (!mouseListeners.contains(listener) && !controllerListeners.contains(listener)) {
            allListeners.remove(listener);
        }
    }

    */
/**
     * Remove a controller listener that will no longer be notified
     *
     * @param listener The listen to be removed
     *//*

    public void removeControllerListener(ControllerListener listener) {
        controllerListeners.remove(listener);

        if (!mouseListeners.contains(listener) && !keyListeners.contains(listener)) {
            allListeners.remove(listener);
        }
    }

    */
/**
     * Remove a mouse listener that will no longer be notified
     *
     * @param listener The listen to be removed
     *//*

    public void removeMouseListener(MouseListener listener) {
        mouseListeners.remove(listener);

        if (!controllerListeners.contains(listener) && !keyListeners.contains(listener)) {
            allListeners.remove(listener);
        }
    }

    */
/**
     * Initialise the input system
     *
     * @param height The height of the window
     *//*

    void init(int height) {
        this.height = height;
        lastMouseX = getMouseX();
        lastMouseY = getMouseY();
    }



    */
/**
     * Clear the state for isControlPressed method. This will reset all
     * controls to not pressed
     *//*

    public void clearControlPressedRecord() {
        for (int i=0;i<controllers.size();i++) {
            Arrays.fill(controllerPressed[i], false);
        }
    }

    */
/**
     * Clear the state for the <code>isKeyPressed</code> method. This will
     * resort in all keys returning that they haven't been pressed, until
     * they are pressed again
     *//*

    public void clearKeyPressedRecord() {
        Arrays.fill(pressed, false);
    }

    */
/**
     * Clear the state for the <code>isMousePressed</code> method. This will
     * resort in all mouse buttons returning that they haven't been pressed, until
     * they are pressed again
     *//*

    public void clearMousePressedRecord() {
        Arrays.fill(mousePressed, false);
    }



    */
/**
     * Initialise the controllers system
     *
     * @throws SlickException Indicates a failure to use the hardware
     *//*

    public void initControllers() throws SlickException {
        if (controllersInited) {
            return;
        }

        controllersInited = true;
        try {
            Controllers.create();
            int count = Controllers.getControllerCount();

            for (int i = 0; i < count; i++) {
                Controller controller = Controllers.getController(i);

                if ((controller.getButtonCount() >= 3) && (controller.getButtonCount() < MAX_BUTTONS)) {
                    controllers.add(controller);
                }
            }

            Log.info("Found "+controllers.size()+" controllers");
            for (int i=0;i<controllers.size();i++) {
                Log.info(i+" : "+((Controller) controllers.get(i)).getName());
            }
        } catch (LWJGLException e) {
            if (e.getCause() instanceof ClassNotFoundException) {
                throw new SlickException("Unable to create controller - no jinput found - add jinput.jar to your classpath");
            }
            throw new SlickException("Unable to create controllers");
        } catch (NoClassDefFoundError e) {
            // forget it, no jinput availble
        }
    }

    */
/**
     * Notification from an event handle that an event has been consumed
     *//*

    public void consumeEvent() {
        consumed = true;
    }

    */
/**
     * A null stream to clear out those horrid errors
     *
     * @author kevin
     *//*

    private class NullOutputStream extends OutputStream {
        */
/**
         * @see java.io.OutputStream#write(int)
         *//*

        public void write(int b) throws IOException {
            // null implemetnation
        }

    }

    */
/**
     * Hook to allow us to translate any key character into special key
     * codes for easier use.
     *
     * @param key The original key code
     * @param c The character that was fired
     * @return The key code to fire
     *//*

    private int resolveEventKey(int key, char c) {
        // BUG with LWJGL - equals comes back with keycode = 0
        // See: http://slick.javaunlimited.net/viewtopic.php?t=617
        if ((c == 61) || (key == 0)) {
            return KEY_EQUALS;
        }

        return key;
    }



    */
/**
     * Poll the state of the input
     *
     * @param width The width of the game view
     * @param height The height of the game view
     *//*

    public void poll(int width, int height) {
        if (paused) {
            clearControlPressedRecord();
            clearKeyPressedRecord();
            clearMousePressedRecord();

            while (Keyboard.next()) {}
            while (Mouse.next()) {}
            return;
        }

        if (!Display.isActive()) {
            clearControlPressedRecord();
            clearKeyPressedRecord();
            clearMousePressedRecord();
        }

        // add any listeners requested since last time
        for (int i=0;i<keyListenersToAdd.size();i++) {
            addKeyListenerImpl((KeyListener) keyListenersToAdd.get(i));
        }
        keyListenersToAdd.clear();
        for (int i=0;i<mouseListenersToAdd.size();i++) {
            addMouseListenerImpl((MouseListener) mouseListenersToAdd.get(i));
        }
        mouseListenersToAdd.clear();

        if (doubleClickTimeout != 0) {
            if (System.currentTimeMillis() > doubleClickTimeout) {
                doubleClickTimeout = 0;
            }
        }

        this.height = height;

        Iterator allStarts = allListeners.iterator();
        while (allStarts.hasNext()) {
            ControlledInputReciever listener = (ControlledInputReciever) allStarts.next();
            listener.inputStarted();
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                int eventKey = resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());

                keys[eventKey] = Keyboard.getEventCharacter();
                pressed[eventKey] = true;
                nextRepeat[eventKey] = System.currentTimeMillis() + keyRepeatInitial;

                consumed = false;
                for (int i=0;i<keyListeners.size();i++) {
                    KeyListener listener = (KeyListener) keyListeners.get(i);

                    if (listener.isAcceptingInput()) {
                        listener.keyPressed(eventKey, Keyboard.getEventCharacter());
                        if (consumed) {
                            break;
                        }
                    }
                }
            } else {
                int eventKey = resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                nextRepeat[eventKey] = 0;

                consumed = false;
                for (int i=0;i<keyListeners.size();i++) {
                    KeyListener listener = (KeyListener) keyListeners.get(i);
                    if (listener.isAcceptingInput()) {
                        listener.keyReleased(eventKey, keys[eventKey]);
                        if (consumed) {
                            break;
                        }
                    }
                }
            }
        }

        while (Mouse.next()) {
            if (Mouse.getEventButton() >= 0) {
                if (Mouse.getEventButtonState()) {
                    consumed = false;
                    mousePressed[Mouse.getEventButton()] = true;

                    pressedX = (int) (xoffset + (Mouse.getEventX() * scaleX));
                    pressedY =  (int) (yoffset + ((height-Mouse.getEventY()) * scaleY));

                    for (int i=0;i<mouseListeners.size();i++) {
                        MouseListener listener = (MouseListener) mouseListeners.get(i);
                        if (listener.isAcceptingInput()) {
                            listener.mousePressed(Mouse.getEventButton(), pressedX, pressedY);
                            if (consumed) {
                                break;
                            }
                        }
                    }
                } else {
                    consumed = false;
                    mousePressed[Mouse.getEventButton()] = false;

                    int releasedX = (int) (xoffset + (Mouse.getEventX() * scaleX));
                    int releasedY = (int) (yoffset + ((height-Mouse.getEventY()) * scaleY));
                    if ((pressedX != -1) &&
                            (pressedY != -1) &&
                            (Math.abs(pressedX - releasedX) < mouseClickTolerance) &&
                            (Math.abs(pressedY - releasedY) < mouseClickTolerance)) {
                        considerDoubleClick(Mouse.getEventButton(), releasedX, releasedY);
                        pressedX = pressedY = -1;
                    }

                    for (int i=0;i<mouseListeners.size();i++) {
                        MouseListener listener = (MouseListener) mouseListeners.get(i);
                        if (listener.isAcceptingInput()) {
                            listener.mouseReleased(Mouse.getEventButton(), releasedX, releasedY);
                            if (consumed) {
                                break;
                            }
                        }
                    }
                }
            } else {
                if (Mouse.isGrabbed() && displayActive) {
                    if ((Mouse.getEventDX() != 0) || (Mouse.getEventDY() != 0)) {
                        consumed = false;
                        for (int i=0;i<mouseListeners.size();i++) {
                            MouseListener listener = (MouseListener) mouseListeners.get(i);
                            if (listener.isAcceptingInput()) {
                                if (anyMouseDown()) {
                                    listener.mouseDragged(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
                                } else {
                                    listener.mouseMoved(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
                                }

                                if (consumed) {
                                    break;
                                }
                            }
                        }
                    }
                }

                int dwheel = Mouse.getEventDWheel();
                wheel += dwheel;
                if (dwheel != 0) {
                    consumed = false;
                    for (int i=0;i<mouseListeners.size();i++) {
                        MouseListener listener = (MouseListener) mouseListeners.get(i);
                        if (listener.isAcceptingInput()) {
                            listener.mouseWheelMoved(dwheel);
                            if (consumed) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (!displayActive || Mouse.isGrabbed()) {
            lastMouseX = getMouseX();
            lastMouseY = getMouseY();
        } else {
            if ((lastMouseX != getMouseX()) || (lastMouseY != getMouseY())) {
                consumed = false;
                for (int i=0;i<mouseListeners.size();i++) {
                    MouseListener listener = (MouseListener) mouseListeners.get(i);
                    if (listener.isAcceptingInput()) {
                        if (anyMouseDown()) {
                            listener.mouseDragged(lastMouseX ,  lastMouseY, getMouseX(), getMouseY());
                        } else {
                            listener.mouseMoved(lastMouseX ,  lastMouseY, getMouseX(), getMouseY());
                        }
                        if (consumed) {
                            break;
                        }
                    }
                }
                lastMouseX = getMouseX();
                lastMouseY = getMouseY();
            }
        }

        if (controllersInited) {
            for (int i=0;i<getControllerCount();i++) {
                int count = ((Controller) controllers.get(i)).getButtonCount()+3;
                count = Math.min(count, 24);
                for (int c=0;c<=count;c++) {
                    if (controls[i][c] && !isControlDwn(c, i)) {
                        controls[i][c] = false;
                        fireControlRelease(c, i);
                    } else if (!controls[i][c] && isControlDwn(c, i)) {
                        controllerPressed[i][c] = true;
                        controls[i][c] = true;
                        fireControlPress(c, i);
                    }
                }
            }
        }

        if (keyRepeat) {
            for (int i=0;i<1024;i++) {
                if (pressed[i] && (nextRepeat[i] != 0)) {
                    if (System.currentTimeMillis() > nextRepeat[i]) {
                        nextRepeat[i] = System.currentTimeMillis() + keyRepeatInterval;
                        consumed = false;
                        for (int j=0;j<keyListeners.size();j++) {
                            KeyListener listener = (KeyListener) keyListeners.get(j);

                            if (listener.isAcceptingInput()) {
                                listener.keyPressed(i, keys[i]);
                                if (consumed) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }


        Iterator all = allListeners.iterator();
        while (all.hasNext()) {
            ControlledInputReciever listener = (ControlledInputReciever) all.next();
            listener.inputEnded();
        }

        if (Display.isCreated()) {
            displayActive = Display.isActive();
        }
    }



    */
/**
     * Fire an event indicating that a control has been pressed
     *
     * @param index The index of the control pressed
     * @param controllerIndex The index of the controller on which the control was pressed
     *//*

    private void fireControlPress(int index, int controllerIndex) {
        consumed = false;
        for (int i=0;i<controllerListeners.size();i++) {
            ControllerListener listener = (ControllerListener) controllerListeners.get(i);
            if (listener.isAcceptingInput()) {
                switch (index) {
                    case LEFT:
                        listener.controllerLeftPressed(controllerIndex);
                        break;
                    case RIGHT:
                        listener.controllerRightPressed(controllerIndex);
                        break;
                    case UP:
                        listener.controllerUpPressed(controllerIndex);
                        break;
                    case DOWN:
                        listener.controllerDownPressed(controllerIndex);
                        break;
                    default:
                        // assume button pressed
                        listener.controllerButtonPressed(controllerIndex, (index - BUTTON1) + 1);
                        break;
                }
                if (consumed) {
                    break;
                }
            }
        }
    }

    */
/**
     * Fire an event indicating that a control has been released
     *
     * @param index The index of the control released
     * @param controllerIndex The index of the controller on which the control was released
     *//*

    private void fireControlRelease(int index, int controllerIndex) {
        consumed = false;
        for (int i=0;i<controllerListeners.size();i++) {
            ControllerListener listener = (ControllerListener) controllerListeners.get(i);
            if (listener.isAcceptingInput()) {
                switch (index) {
                    case LEFT:
                        listener.controllerLeftReleased(controllerIndex);
                        break;
                    case RIGHT:
                        listener.controllerRightReleased(controllerIndex);
                        break;
                    case UP:
                        listener.controllerUpReleased(controllerIndex);
                        break;
                    case DOWN:
                        listener.controllerDownReleased(controllerIndex);
                        break;
                    default:
                        // assume button release
                        listener.controllerButtonReleased(controllerIndex, (index - BUTTON1) + 1);
                        break;
                }
                if (consumed) {
                    break;
                }
            }
        }
    }

    */
/**
     * Check if a particular control is currently pressed
     *
     * @param index The index of the control
     * @param controllerIndex The index of the control to which the control belongs
     * @return True if the control is pressed
     *//*

    private boolean isControlDwn(int index, int controllerIndex) {
        switch (index) {
            case LEFT:
                return isControllerLeft(controllerIndex);
            case RIGHT:
                return isControllerRight(controllerIndex);
            case UP:
                return isControllerUp(controllerIndex);
            case DOWN:
                return isControllerDown(controllerIndex);
        }

        if (index >= BUTTON1) {
            return isButtonPressed((index-BUTTON1), controllerIndex);
        }

        throw new RuntimeException("Unknown control index");
    }


    */
/**
     * Pauses the polling and sending of input events.
     *//*

    public void pause() {
        paused = true;

        // Reset all polling arrays
        clearKeyPressedRecord();
        clearMousePressedRecord();
        clearControlPressedRecord();
    }

    */
/**
     * Resumes the polling and sending of input events.
     *//*

    public void resume() {
        paused = false;
    }

    */
/**
     * Notify listeners that the mouse button has been clicked
     *
     * @param button The button that has been clicked
     * @param x The location at which the button was clicked
     * @param y The location at which the button was clicked
     * @param clickCount The number of times the button was clicked (single or double click)
     *//*

    private void fireMouseClicked(int button, int x, int y, int clickCount) {
        consumed = false;
        for (int i=0;i<mouseListeners.size();i++) {
            MouseListener listener = (MouseListener) mouseListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.mouseClicked(button, x, y, clickCount);
                if (consumed) {
                    break;
                }
            }
        }
    }
}
*/
