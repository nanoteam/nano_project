package controller;

import util.LightInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import resourses.configuration.SheetParse;

/**
 * Created by IntelliJ IDEA. User: happydroidx Date: 22.09.12 Time: 13:16
 */
public final class InputToAction {
    public final static byte KEYBOARD = 0;
    public final static byte MOUSE = 1;
    public final static byte CONTROLLER = 2;

    //arrow
	public final static int left = 0;
	public final static int right = 1;
	public final static int up = 2;
	public final static int down = 3;

	public final static int spec1 = 6;
	public final static int spec2 = 7;
	
	public final static int menu = 20;

	public final static int zoomIn = 21;
	public final static int zoomOut = 22;
	public final static int zoomCenter = 23;

    public final static int move = 30;
    public final static int fire = 31;

    public final static int comboChoiseFirst = 40;
    public final static int comboChoiseSecond = 41;
    public final static int comboChoiseStop = 42;

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
		nameToAction.put("ActionLeft", new LightInteger(left));
		nameToAction.put("ActionRight", new LightInteger(right));
		nameToAction.put("ActionUp", new LightInteger(up));
		nameToAction.put("ActionDown", new LightInteger(down));
		nameToAction.put("ActionSpec1", new LightInteger(spec1));
		nameToAction.put("ActionSpec2", new LightInteger(spec2));
		nameToAction.put("ActionMenu", new LightInteger(menu));
		nameToAction.put("ActionZoomIn", new LightInteger(zoomIn));
		nameToAction.put("ActionZoomOut", new LightInteger(zoomOut));
		nameToAction.put("ActionZoomCenter", new LightInteger(zoomCenter));

        nameToAction.put("ActionMove", new LightInteger(move));
        nameToAction.put("ActionFire", new LightInteger(fire));

        nameToAction.put("ActionComboChoiseFirst", new LightInteger(comboChoiseFirst));
        nameToAction.put("ActionComboChoiseSecond", new LightInteger(comboChoiseSecond));
        nameToAction.put("ActionComboChoiseStop", new LightInteger(comboChoiseStop));



	}

    public static InputToAction get(){
        if (inputToAction==null){
            inputToAction = new InputToAction();
        }
        return inputToAction;
    }

	// filling keyboardEventToAction
	public void init(SheetParse sheetParse) {
        SheetParse player1 = sheetParse.findSheetParseByName("Input").findSheetParseByName("Player1");

        SheetParse player1Keyboard = player1.findSheetParseByName("Keyboard");

		if (player1Keyboard == null) {
			System.out.println("InutToAction:init - player1Keyboard == null");
			return;
		}
		ArrayList<SheetParse> listSheets = player1Keyboard.getSheets();

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

        SheetParse player1Mouse = player1.findSheetParseByName("Mouse");

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
        switch (device){
            case KEYBOARD:{
                if (keyboardEventToAction.containsKey(key)) {
                    return keyboardEventToAction.get(key).data;
                }
                break;
            }
            case MOUSE:{
                if (mouseEventToAction.containsKey(key)) {
                    return mouseEventToAction.get(key).data;
                }
                break;
            }
            case CONTROLLER:{
                if (controllerEventToAction.containsKey(key)) {
                    return mouseEventToAction.get(key).data;
                }
                break;
            }
        }
		return -10;
	}
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

    public List<LightInteger> getAllNeedKeybKeys(){
        if (complete){
            //get all key, whose need in the game
            return new ArrayList<LightInteger>(keyboardEventToAction.keySet());
        }
        else{
            new Exception("InputToAction.getAllCodeKey - InputToAction not initialized");
        }
        return null;
    }
}
