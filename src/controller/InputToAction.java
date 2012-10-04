package controller;

import util.LightInteger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import resourses.configuration.SheetParse;

/**
 * Created by IntelliJ IDEA. User: happydroidx Date: 22.09.12 Time: 13:16
 */
public final class InputToAction {
	public final static int left = 0;
	public final static int right = 1;
	public final static int up = 2;
	public final static int down = 3;

	public final static int fire1 = 4;
	public final static int fire2 = 5;

	public final static int spec1 = 6;
	public final static int spec2 = 7;

	public final static int menu = 20;

	public final static int zoomIn = 21;
	public final static int zoomOut = 22;
	public final static int zoomCenter = 23;

	private boolean complete = false;

	// list for storage name of commands (action_name : action code)
	private final HashMap<String, LightInteger> nameToAction = new HashMap<String, LightInteger>();
	// list (key code:action code)
	private HashMap<LightInteger, LightInteger> keyToAction = new HashMap<LightInteger, LightInteger>();

	// name of action must be equals name action in files!!!
	public InputToAction() {
		nameToAction.put("ActionLeft", new LightInteger(left));
		nameToAction.put("ActionRight", new LightInteger(right));
		nameToAction.put("ActionUp", new LightInteger(up));
		nameToAction.put("ActionDown", new LightInteger(down));
		nameToAction.put("ActionFire1", new LightInteger(fire1));
		nameToAction.put("ActionFire2", new LightInteger(fire2));
		nameToAction.put("ActionSpec1", new LightInteger(spec1));
		nameToAction.put("ActionSpec2", new LightInteger(spec2));
		nameToAction.put("ActionMenu", new LightInteger(menu));

		nameToAction.put("ActionZoomIn", new LightInteger(zoomIn));
		nameToAction.put("ActionZoomOut", new LightInteger(zoomOut));
		nameToAction.put("ActionZoomCenter", new LightInteger(zoomCenter));

	}

	// filling keyToAction
	public void init(SheetParse sheetParse) {

		sheetParse = sheetParse.findSheetParseByName("Input");
		if (sheetParse == null) {
			System.out.println("InutToAction:init - sheetParse == null");
			return;
		}
		ArrayList<SheetParse> listSheets = sheetParse.getSheets();

		for (SheetParse parse : listSheets) {
			String sheetName = parse.getName().trim();
			String sheetValue = parse.getValue().trim();
			if (nameToAction.containsKey(sheetName)) {
				int key = -1;
				try {
					key = Integer.valueOf(sheetValue);
				} catch (Exception e) {
					e.printStackTrace();
				}

				keyToAction.put(new LightInteger(key), new LightInteger(
						((LightInteger) (nameToAction.get(sheetName))).data));
			}
		}

		/*
		 * for (String action : nameToAction.keySet()) {
		 * 
		 * 
		 * if (list.containsKey(action)) { int key = -1; try { key =
		 * Integer.valueOf((String) (list.get(action))); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * keyToAction.put(new LightInteger(key), new LightInteger(
		 * ((LightInteger) (nameToAction.get(action))).data)); } }
		 */

		// TODO Add validating key value, if key == -1;
		// if valid true

		complete = true;
		for (LightInteger action : keyToAction.keySet()) {
			System.out.println("key is " + action.data + ", value is "
					+ keyToAction.get(action).data);

		}
	}

	public int getAction(int code) {
		if (!complete) {
			new Exception("InputToAction:getAction - not init!");
		}
		LightInteger key = new LightInteger(code);
		System.out.println(keyToAction);
		System.out.println("InpuToAction.getAction " + code);
		if (keyToAction.containsKey(key)) {
			System.out.println("yes");
			return keyToAction.get(key).data;
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

	// TODO add this future\/
	/*
	 * private void saveChangeInFile(){ FileWriter fileWriter; try { fileWriter
	 * = new FileWriter(new File("D:/setting.ini")); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */

}
