/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package controller;

import main.Engine;
import org.lwjgl.LWJGLException;
import util.LightInteger;

import java.util.ArrayList;
import java.util.List;

final class Keyboard implements Engine {
    private ArrayList<StateKeyboard> statesKeyboards;
    private List<LightInteger> allNeedKeys;
    private Controller controller;

    Keyboard(Controller controller , List<LightInteger> list){
        this.controller = controller;
        setAllNeedKeys(list);
        createKeyboard();
        org.lwjgl.input.Keyboard.enableRepeatEvents(false);

    }

    public List<StateKeyboard> getStatesKeyboard() {
        return statesKeyboards;
    }
    //TODO add support this code!
    @Override
    public void tick() {
        //if window was lost fokus, need update keyboard
        /*if (!org.lwjgl.input.Keyboard.isCreated()){
			createKeyboard();
	   	}          */

        //reset collection, must be!
        statesKeyboards = new ArrayList<StateKeyboard>();

        //check keys, buffered
        org.lwjgl.input.Keyboard.poll();

        while (org.lwjgl.input.Keyboard.next()) {
            StateKeyboard keyboardEvent = null;
            if (org.lwjgl.input.Keyboard.getEventKeyState()) {
                keyboardEvent = new StateKeyboard(
                        org.lwjgl.input.Keyboard.getEventKey(), StateKeyboard.DOWN_PRESSED);
            } else {
                keyboardEvent = new StateKeyboard(
                        org.lwjgl.input.Keyboard.getEventKey(), StateKeyboard.UP_RELEASED);

            }
            statesKeyboards.add(keyboardEvent);
        }
        //filling output list by key with state down and up.
        if (allNeedKeys!=null&&allNeedKeys.size()!=0){
            for (LightInteger key : allNeedKeys) {
                StateKeyboard keyboardEvent = null;
                if (org.lwjgl.input.Keyboard.isKeyDown(key.data)) {
                    keyboardEvent = new StateKeyboard(key.data, StateKeyboard.DOWN);
                } else {
                    keyboardEvent = new StateKeyboard(key.data, StateKeyboard.UP);
                }
                statesKeyboards.add(keyboardEvent);
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
