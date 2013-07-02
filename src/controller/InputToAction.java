package controller;

import resourses.configuration.SheetParse;
import util.LightInteger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: happydroidx Date: 22.09.12 Time: 13:16
 */
public final class InputToAction {
    public final static byte KEYBOARD = 0;
    public final static byte MOUSE = 1;
    public final static byte CONTROLLER = 2;

    public final static int left = 100;
    public final static int right = 101;
    public final static int up = 102;
    public final static int down = 103;

    public final static int firePrimary = 131;
    public final static int fireAlternative = 132;

    public final static int specialAction = 6;


    public final static int previousWeapon = 30;
    public final static int nextWeapon = 31;


    //not use by player

    public final static int rotateWeaponUp = 40;
    public final static int rotateWeaponDown = 41;


    //public final static int spec2 = 7;

    /*
    public final static int leftEngineOn = 50;
    public final static int rightEngineOn = 51;

    public final static int leftEngineLeft = 40;
    public final static int leftEngineRight = 41;

    public final static int rightEngineLeft = 42;
    public final static int rightEngineRight = 43;

    public final static int comboChoiseFirst = 140;
    public final static int comboChoiseSecond = 141;
    public final static int comboChoiseStop = 142;
    */

    //menu
    public final static int menu = 200;
    public final static int zoomIn = 201;
    public final static int zoomOut = 202;
    public final static int zoomCenter = 203;

    private boolean complete = false;

    // list for storage name of commands (action_name : action code)
    private final HashMap<String, LightInteger> nameToAction = new HashMap<String, LightInteger>();
    // list (key code:action code)
    private HashMap<LightInteger, LightInteger> keyboardEventToAction = new HashMap<LightInteger, LightInteger>();
    // list (button code:action code)
    private HashMap<LightInteger, LightInteger> mouseEventToAction = new HashMap<LightInteger, LightInteger>();
    // list (controller code:action code)
    private HashMap<LightInteger, LightInteger> controllerEventToAction = new HashMap<LightInteger, LightInteger>();

    private static InputToAction inputToAction;

    // name of action must be equals name action in files!!!
    private InputToAction() {
        // low level action
        nameToAction.put("Left", new LightInteger(left));
        nameToAction.put("Right", new LightInteger(right));
        nameToAction.put("Up", new LightInteger(up));
        nameToAction.put("Down", new LightInteger(down));
        /*
        nameToAction.put("LeftEngineOn", new LightInteger(leftEngineOn));
        nameToAction.put("RightEngineOn", new LightInteger(rightEngineOn));

        nameToAction.put("LeftEngineLeft", new LightInteger(leftEngineLeft));
        nameToAction.put("LeftEngineRight", new LightInteger(leftEngineRight));
        nameToAction.put("RightEngineLeft", new LightInteger(rightEngineLeft));
        nameToAction.put("RightEngineRight", new LightInteger(rightEngineRight));

        nameToAction.put("LeftEngineOn", new LightInteger(leftEngineOn));
        nameToAction.put("RightEngineOn", new LightInteger(rightEngineOn));


        nameToAction.put("Spec2", new LightInteger(spec2));

        nameToAction.put("Move", new LightInteger(move));
        nameToAction.put("Fire", new LightInteger(fire));

        nameToAction.put("ComboChoiseFirst", new LightInteger(comboChoiseFirst));
        nameToAction.put("ComboChoiseSecond", new LightInteger(comboChoiseSecond));
        nameToAction.put("ComboChoiseStop", new LightInteger(comboChoiseStop));*/

        nameToAction.put("SpecialAction", new LightInteger(specialAction));

        nameToAction.put("FirePrimary", new LightInteger(firePrimary));
        nameToAction.put("FireAlternative", new LightInteger(fireAlternative));

        nameToAction.put("Menu", new LightInteger(menu));
        nameToAction.put("ZoomIn", new LightInteger(zoomIn));
        nameToAction.put("ZoomOut", new LightInteger(zoomOut));
        nameToAction.put("ZoomCenter", new LightInteger(zoomCenter));

        nameToAction.put("PreviousWeapon", new LightInteger(previousWeapon));
        nameToAction.put("NextWeapon", new LightInteger(nextWeapon));
    }

    public static InputToAction get() {
        if (inputToAction == null) {
            inputToAction = new InputToAction();
        }
        return inputToAction;
    }

    // filling keyboardEventToAction
    public void init(SheetParse sheetParse) {
        SheetParse player = sheetParse.findSheetParseByName("Input");
        player = player.findSheetParseByName("Player1");

        SheetParse playerKeyboard = player.findSheetParseByName("Keyboard");

        if (playerKeyboard == null) {
            System.out.println("InutToAction:init - player1Keyboard == null");
            return;
        }
        ArrayList<SheetParse> listSheets = playerKeyboard.getSheets();

        for (SheetParse parse : listSheets) {
            String sheetName = parse.getName();
            String sheetValue = parse.getValue();

            if (nameToAction.containsKey(sheetName)) {
                int key = -1;
                try {
                    key = Integer.valueOf(sheetValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                keyboardEventToAction.put(new LightInteger(key), new LightInteger(
                        ((LightInteger) (nameToAction.get(sheetName))).data));
            }
        }

        SheetParse player1Mouse = player.findSheetParseByName("Mouse");

        if (player1Mouse == null) {
            System.out.println("InutToAction:init - player1Mouse == null");
            return;
        }
        listSheets = player1Mouse.getSheets();

        for (SheetParse parse : listSheets) {
            String sheetName = parse.getName();
            String sheetValue = parse.getValue();
            if (nameToAction.containsKey(sheetName)) {
                int key = -1;
                try {
                    key = Integer.valueOf(sheetValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mouseEventToAction.put(new LightInteger(key), new LightInteger(
                        ((LightInteger) (nameToAction.get(sheetName))).data));
            }
        }

        complete = true;
        System.out.println("keyboard event:");
        for (LightInteger action : keyboardEventToAction.keySet()) {
            System.out.println("key is " + action.data + ", value is "
                    + keyboardEventToAction.get(action).data);
        }

        System.out.println("mouse event:");
        for (LightInteger action : mouseEventToAction.keySet()) {
            System.out.println("button is " + action.data + ", value is "
                    + mouseEventToAction.get(action).data);
        }
    }

    public int getActionByDevice(int code, byte device) {
        if (!complete) {
            new Exception("InputToAction:getActionByDevice - not init!");
        }

        LightInteger key = new LightInteger(code);
        switch (device) {
            case KEYBOARD: {
                if (keyboardEventToAction.containsKey(key)) {
                    return keyboardEventToAction.get(key).data;
                }
                break;
            }
            case MOUSE: {
                if (mouseEventToAction.containsKey(key)) {
                    return mouseEventToAction.get(key).data;
                }
                break;
            }
            case CONTROLLER: {
                if (controllerEventToAction.containsKey(key)) {
                    return mouseEventToAction.get(key).data;
                }
                break;
            }
        }
        return -10;
    }

    //need to
    public void configKeys() {
        HashMap<String, String> list = new HashMap<String, String>();
        boolean complite;
        String inputKey = null;
        for (String nameAction : nameToAction.keySet()) {
            complite = false;
            while (!complite) {
                inputKey = JOptionPane
                        .showInputDialog("Input please key for action "
                                + nameToAction.get(nameAction));
                try {
                    if (null != Integer.getInteger(inputKey.trim())) {
                        complite = true;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            list.put(nameAction, inputKey);
        }

        // save change setting in game
        // init(list);
        // save change setting in setting.ini
        //

    }

    public List<LightInteger> getAllNeedKeybKeys() {
        if (complete) {
            //get all key, whose need in the game
            return new ArrayList<LightInteger>(keyboardEventToAction.keySet());
        } else {
            new Exception("InputToAction.getAllCodeKey - InputToAction not initialized");
        }
        return null;
    }
}
