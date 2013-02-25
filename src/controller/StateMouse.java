package controller;

import org.lwjgl.util.vector.Vector2f;

public class StateMouse {
    //button code
    public static final int DOWN_PRESSED = 0;
    public static final int DOWN = 1;
    public static final int UP = 2;
    public static final int UP_RELEASED = 3;
    public static final int LEFT_BUTTON = 0;
    public static final int MIDDLE_BUTTON = 1;
    public static final int RIGHT_BUTTON = 2;
    public static final int FOUR_BUTTON = 3;
    public static final int FIVE_BUTTON = 4;

    public static final int EVENT_CLICK = 1;
    public static final int EVENT_ROLICK = 2;

    public int stateEvent = 0;


    // decimal int
    public int buttonCode;

    //true if pressed, false if released
    public int stateButton;

    public boolean rolickEvent;

    public int xEventPosition;
    public int yEventPosition;
    public int dRolick;

    //if button event
    public StateMouse(int keyCode, int stateButton, Vector2f eventPosition) {
        this.buttonCode = keyCode;
        this.stateButton = stateButton;
        xEventPosition = (int) eventPosition.x;
        yEventPosition = (int) eventPosition.y;
        stateEvent = EVENT_CLICK;
    }

    //if relock event
    public StateMouse(int dRolick, Vector2f eventPosition) {
        this.dRolick = dRolick;
        xEventPosition = (int) eventPosition.x;
        yEventPosition = (int) eventPosition.y;
        stateEvent = EVENT_ROLICK;
    }

    @Override
    public String toString() {
        if (stateEvent == EVENT_CLICK) {
            if (stateButton == DOWN_PRESSED) {
                return "keycode == " + buttonCode + ", button down-pressed," + "position is x = "+xEventPosition+", y = "+yEventPosition;
            }
            if (stateButton == DOWN) {
                return "keycode == " + buttonCode + ", button down," + "position is x = "+xEventPosition+", y = "+yEventPosition;
            }
            if (stateButton == UP) {
                return "keycode == " + buttonCode + ", button up," + "position is x = "+xEventPosition+", y = "+yEventPosition;
            }
            if (stateButton == UP_RELEASED) {
                return "keycode == " + buttonCode + ", button up_released," + "position is x = "+xEventPosition+", y = "+yEventPosition;
            }
        }
        if (stateEvent == EVENT_ROLICK) {
            return "dRolick == " + dRolick  + "position is x = "+xEventPosition+", y = "+yEventPosition;
        }

        return null;
    }
}
