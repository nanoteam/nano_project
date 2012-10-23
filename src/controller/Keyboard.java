/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import main.Engine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import util.LightInteger;

import java.util.ArrayList;
import java.util.List;

class Keyboard implements Engine {
    private ArrayList<StateKey> statesKeys;
    private List<LightInteger> allNeedKeys;
    private Controller controller;

    Keyboard(Controller controller , List<LightInteger> list){
        this.controller = controller;
        setAllNeedKeys(list);
        createKeyboard();
        org.lwjgl.input.Keyboard.enableRepeatEvents(false);

    }

    public List<StateKey> getEvent() {
        return statesKeys;
    }
    //TODO add support this code!
    @Override
    public void tick() {
        //if window was lost fokus, need update keyboard
        /*if (!org.lwjgl.input.Keyboard.isCreated()){
			createKeyboard();
	   	}          */

        //reset collection, must be!
        statesKeys = new ArrayList<StateKey>();

        //check keys, buffered
        org.lwjgl.input.Keyboard.poll();

        //filling output list by events data
        int count = org.lwjgl.input.Keyboard.getNumKeyboardEvents();

        while (org.lwjgl.input.Keyboard.next()) {
            StateKey keyboardEvent = null;
            if (org.lwjgl.input.Keyboard.getEventKeyState()) {
                keyboardEvent = new StateKey(
                        org.lwjgl.input.Keyboard.getEventKey(), StateKey.DOWN_PRESSED);
            } else {
                keyboardEvent = new StateKey(
                        org.lwjgl.input.Keyboard.getEventKey(), StateKey.UP_RELEASED);

            }
            statesKeys.add(keyboardEvent);
        }


        //filling output list by key with state down and up.
        if (allNeedKeys!=null&&allNeedKeys.size()!=0){
            for (LightInteger key : allNeedKeys) {
                StateKey keyboardEvent = null;
                if (org.lwjgl.input.Keyboard.isKeyDown(key.data)) {
                    keyboardEvent = new StateKey(key.data,StateKey.DOWN);
                } else {
                    keyboardEvent = new StateKey(key.data,StateKey.UP);
                }
                statesKeys.add(keyboardEvent);
            }
        }
    }

    public void setAllNeedKeys(List<LightInteger> list){
        if (list == null){
            new Exception("Keyboard.setAllNeedKeys - list must be != null");
        }
        this.allNeedKeys = new ArrayList<LightInteger>(list);
    }

    @Override
    public void cleanUp() {
        org.lwjgl.input.Keyboard.destroy();
    }

    private void createKeyboard() {
        try {
            org.lwjgl.input.Keyboard.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

}
