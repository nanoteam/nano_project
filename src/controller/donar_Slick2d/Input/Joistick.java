/*
package controller.donar_Slick2d.Input;

import org.lwjgl.input.Controller;

public class Joistick {

    private static final int MAX_BUTTONS = 100;
    */
/** THe state of the controller buttons *//*

    private boolean[][] controllerPressed = new boolean[100][MAX_BUTTONS];

    */
/** The controller index to pass to check all controllers *//*

    public static final int ANY_CONTROLLER = -1;



    */
/** Control index *//*

    private static final int LEFT = 0;
    */
/** Control index *//*

    private static final int RIGHT = 1;
    */
/** Control index *//*

    private static final int UP = 2;
    */
/** Control index *//*

    private static final int DOWN = 3;
    */
/** Control index *//*

    private static final int BUTTON1 = 4;
    */
/** Control index *//*

    private static final int BUTTON2 = 5;
    */
/** Control index *//*

    private static final int BUTTON3 = 6;
    */
/** Control index *//*

    private static final int BUTTON4 = 7;
    */
/** Control index *//*

    private static final int BUTTON5 = 8;
    */
/** Control index *//*

    private static final int BUTTON6 = 9;
    */
/** Control index *//*

    private static final int BUTTON7 = 10;
    */
/** Control index *//*

    private static final int BUTTON8 = 11;
    */
/** Control index *//*

    private static final int BUTTON9 = 12;
    */
/** Control index *//*

    private static final int BUTTON10 = 13;

    */
/** True if the input is currently paused *//*

    private boolean paused;

    */
/**
     * Check if a controller button has been pressed since last
     * time
     *
     * @param button The button to check for (note that this includes directional controls first)
     * @return True if the button has been pressed since last time
     *//*

    public boolean isControlPressed(int button) {
        return isControlPressed(button, 0);
    }

    */
/**
     * Check if a controller button has been pressed since last
     * time
     *
     * @param controller The index of the controller to check
     * @param button The button to check for (note that this includes directional controls first)
     * @return True if the button has been pressed since last time
     *//*

    public boolean isControlPressed(int button, int controller) {
        if (controllerPressed[controller][button]) {
            controllerPressed[controller][button] = false;
            return true;
        }

        return false;
    }


    */
/**
     * Get a count of the number of controlles available
     *
     * @return The number of controllers available
     *//*

    public int getControllerCount() {
        try {
            initControllers();
        } catch (SlickException e) {
            throw new RuntimeException("Failed to initialise controllers");
        }

        return controllers.size();
    }

    */
/**
     * Get the number of axis that are avaiable on a given controller
     *
     * @param controller The index of the controller to check
     * @return The number of axis available on the controller
     *//*

    public int getAxisCount(int controller) {
        return ((Controller) controllers.get(controller)).getAxisCount();
    }

    */
/**
     * Get the value of the axis with the given index
     *
     * @param controller The index of the controller to check
     * @param axis The index of the axis to read
     * @return The axis value at time of reading
     *//*

    public float getAxisValue(int controller, int axis) {
        return ((Controller) controllers.get(controller)).getAxisValue(axis);
    }

    */
/**
     * Get the name of the axis with the given index
     *
     * @param controller The index of the controller to check
     * @param axis The index of the axis to read
     * @return The name of the specified axis
     *//*

    public String getAxisName(int controller, int axis) {
        return ((Controller) controllers.get(controller)).getAxisName(axis);
    }

    */
/**
     * Check if the controller has the left direction pressed
     *
     * @param controller The index of the controller to check
     * @return True if the controller is pressed to the left
     *//*

    public boolean isControllerLeft(int controller) {
        if (controller >= getControllerCount()) {
            return false;
        }

        if (controller == ANY_CONTROLLER) {
            for (int i=0;i<controllers.size();i++) {
                if (isControllerLeft(i)) {
                    return true;
                }
            }

            return false;
        }

        return ((Controller) controllers.get(controller)).getXAxisValue() < -0.5f
                || ((Controller) controllers.get(controller)).getPovX() < -0.5f;
    }

    */
/**
     * Check if the controller has the right direction pressed
     *
     * @param controller The index of the controller to check
     * @return True if the controller is pressed to the right
     *//*

    public boolean isControllerRight(int controller) {
        if (controller >= getControllerCount()) {
            return false;
        }

        if (controller == ANY_CONTROLLER) {
            for (int i=0;i<controllers.size();i++) {
                if (isControllerRight(i)) {
                    return true;
                }
            }

            return false;
        }

        return ((Controller) controllers.get(controller)).getXAxisValue() > 0.5f
                || ((Controller) controllers.get(controller)).getPovX() > 0.5f;
    }

    */
/**
     * Check if the controller has the up direction pressed
     *
     * @param controller The index of the controller to check
     * @return True if the controller is pressed to the up
     *//*

    public boolean isControllerUp(int controller) {
        if (controller >= getControllerCount()) {
            return false;
        }

        if (controller == ANY_CONTROLLER) {
            for (int i=0;i<controllers.size();i++) {
                if (isControllerUp(i)) {
                    return true;
                }
            }

            return false;
        }
        return ((Controller) controllers.get(controller)).getYAxisValue() < -0.5f
                || ((Controller) controllers.get(controller)).getPovY() < -0.5f;
    }

    */
/**
     * Check if the controller has the down direction pressed
     *
     * @param controller The index of the controller to check
     * @return True if the controller is pressed to the down
     *//*

    public boolean isControllerDown(int controller) {
        if (controller >= getControllerCount()) {
            return false;
        }

        if (controller == ANY_CONTROLLER) {
            for (int i=0;i<controllers.size();i++) {
                if (isControllerDown(i)) {
                    return true;
                }
            }

            return false;
        }

        return ((Controller) controllers.get(controller)).getYAxisValue() > 0.5f
                || ((Controller) controllers.get(controller)).getPovY() > 0.5f;

    }

    */
/**
     * Check if controller button is pressed
     *
     * @param controller The index of the controller to check
     * @param index The index of the button to check
     * @return True if the button is pressed
     *//*

    public boolean isButtonPressed(int index, int controller) {
        if (controller >= getControllerCount()) {
            return false;
        }

        if (controller == ANY_CONTROLLER) {
            for (int i=0;i<controllers.size();i++) {
                if (isButtonPressed(index, i)) {
                    return true;
                }
            }

            return false;
        }

        return ((Controller) controllers.get(controller)).isButtonPressed(index);
    }

    */
/**
     * Check if button 1 is pressed
     *
     * @param controller The index of the controller to check
     * @return True if the button is pressed
     *//*

    public boolean isButton1Pressed(int controller) {
        return isButtonPressed(0, controller);
    }

    */
/**
     * Check if button 2 is pressed
     *
     * @param controller The index of the controller to check
     * @return True if the button is pressed
     *//*

    public boolean isButton2Pressed(int controller) {
        return isButtonPressed(1, controller);
    }

    */
/**
     * Check if button 3 is pressed
     *
     * @param controller The index of the controller to check
     * @return True if the button is pressed
     *//*

    public boolean isButton3Pressed(int controller) {
        return isButtonPressed(2, controller);
    }
}
*/
