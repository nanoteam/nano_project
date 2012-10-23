/*
package controller.donar_Slick2d.Input;
public class Mouse {
    */
/** The controller index to pass to check all controllers *//*

    public static final int ANY_CONTROLLER = -1;
    private static final int MAX_BUTTONS = 100;
    */
/** The left mouse button indicator *//*

    public static final int MOUSE_LEFT_BUTTON = 0;
    */
/** The right mouse button indicator *//*

    public static final int MOUSE_RIGHT_BUTTON = 1;
    */
/** The middle mouse button indicator *//*

    public static final int MOUSE_MIDDLE_BUTTON = 2;

    */
/** The last recorded mouse x position *//*

    private int lastMouseX;
    */
/** The last recorded mouse y position *//*

    private int lastMouseY;
    */
/** THe state of the mouse buttons *//*

    protected boolean[] mousePressed = new boolean[10];

    */
/** The current value of the wheel *//*

    private int wheel;
    */
/** The height of the display *//*

    private int height;


    */
/** True if the input is currently paused *//*

    private boolean paused;
    */
/** The scale to apply to screen coordinates *//*

    private float scaleX = 1;
    */
/** The scale to apply to screen coordinates *//*

    private float scaleY = 1;
    */
/** The offset to apply to screen coordinates *//*

    private float xoffset = 0;
    */
/** The offset to apply to screen coordinates *//*

    private float yoffset = 0;

    */
/** The clicked x position *//*

    private int clickX;
    */
/** The clicked y position *//*

    private int clickY;
    */
/** The clicked button *//*

    private int clickButton;

    */
/** The delay before determining a single or double click *//*

    private int doubleClickDelay = 250;
    */
/** The timer running out for a single click *//*

    private long doubleClickTimeout = 0;



    */
/** The x position location the mouse was pressed *//*

    private int pressedX = -1;

    */
/** The x position location the mouse was pressed *//*

    private int pressedY = -1;

    */
/** The pixel distance the mouse can move to accept a mouse click *//*

    private int mouseClickTolerance = 5;


    */
/**
     * Set the double click interval, the time between the first
     * and second clicks that should be interpreted as a
     * double click.
     *
     * @param delay The delay between clicks
     *//*

    public void setDoubleClickInterval(int delay) {
        doubleClickDelay = delay;
    }


    */
/**
     * Set the scaling to apply to screen coordinates
     *
     * @param scaleX The scaling to apply to the horizontal axis
     * @param scaleY The scaling to apply to the vertical axis
     *//*

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }


    */
/**
     * Set the offset to apply to the screen coodinates
     *
     * @param xoffset The offset on the x-axis
     * @param yoffset The offset on the y-axis
     *//*

    public void setOffset(float xoffset, float yoffset) {
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }


    */
/**
     * Reset the transformation being applied to the input to the default
     *//*

    public void resetInputTransform() {
        setOffset(0, 0);
        setScale(1, 1);
    }





    */
/**
     * Check if a mouse button has been pressed since last call
     *
     * @param button The button to check
     * @return True if the button has been pressed since last call
     *//*

    public boolean isMousePressed(int button) {
        if (mousePressed[button]) {
            mousePressed[button] = false;
            return true;
        }

        return false;
    }

    */
/**
     * Get the absolute x position of the mouse cursor within the container
     *
     * @return The absolute x position of the mouse cursor
     *//*

    public int getAbsoluteMouseX() {
        return org.lwjgl.input.Mouse.getX();
    }

    */
/**
     * Get the absolute y position of the mouse cursor within the container
     *
     * @return The absolute y position of the mouse cursor
     *//*

    public int getAbsoluteMouseY() {
        return height - org.lwjgl.input.Mouse.getY();
    }

    */
/**
     * Get the x position of the mouse cursor
     *
     * @return The x position of the mouse cursor
     *//*

    public int getMouseX() {
        return (int) ((org.lwjgl.input.Mouse.getX() * scaleX)+xoffset);
    }

    */
/**
     * Get the y position of the mouse cursor
     *
     * @return The y position of the mouse cursor
     *//*

    public int getMouseY() {
        return (int) (((height- org.lwjgl.input.Mouse.getY()) * scaleY)+yoffset);
    }

    */
/**
     * Check if a given mouse button is down
     *
     * @param button The index of the button to check (starting at 0)
     * @return True if the mouse button is down
     *//*

    public boolean isMouseButtonDown(int button) {
        return org.lwjgl.input.Mouse.isButtonDown(button);
    }

    */
/**
     * Check if any mouse button is down
     *
     * @return True if any mouse button is down
     *//*

    private boolean anyMouseDown() {
        for (int i=0;i<3;i++) {
            if (org.lwjgl.input.Mouse.isButtonDown(i)) {
                return true;
            }
        }

        return false;
    }


    */
/**
     * Notification that the mouse has been pressed and hence we
     * should consider what we're doing with double clicking
     *
     * @param button The button pressed/released
     * @param x The location of the mouse
     * @param y The location of the mouse
     *//*

    public void considerDoubleClick(int button, int x, int y) {
        if (doubleClickTimeout == 0) {
            clickX = x;
            clickY = y;
            clickButton = button;
            doubleClickTimeout = System.currentTimeMillis() + doubleClickDelay;
            fireMouseClicked(button, x, y, 1);
        } else {
            if (clickButton == button) {
                if ((System.currentTimeMillis() < doubleClickTimeout)) {
                    fireMouseClicked(button, x, y, 2);
                    doubleClickTimeout = 0;
                }
            }
        }
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
