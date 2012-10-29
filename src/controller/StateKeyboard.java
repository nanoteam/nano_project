package controller;

public final class StateKeyboard {
    public static final int DOWN_PRESSED = 0;
    public static final int DOWN = 1;
    public static final int UP = 2;
    public static final int UP_RELEASED = 3;
   // decimal int
    public int keyCode;
    //true if pressed, false if released
    public int state;

    public StateKeyboard(int keyCode, int state){
        this.keyCode = keyCode;
        this.state = state;
    }

    @Override
    public String toString(){
        if(state==0){
            return "keycode == "+keyCode + ", key down-pressed";
        }
        if(state==1){
            return "keycode == "+keyCode + ", key down";
        }
        if(state==2){
            return "keycode == "+keyCode + ", key up";
        }
        if(state==3){
            return "keycode == "+keyCode + ", key up_released";
        }
        return null;
    }
}
