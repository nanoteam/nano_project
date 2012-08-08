/**
 * 
 * This is a main Game class, he init and start game
 * 
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package main;

import java.util.ArrayList;

import logic.Level;
import logic.Logic;
import logic.entity.Player;
import logic.entity.Ship;
import org.lwjgl.opengl.Display;
import physic.Physic;
import render.Render;
import sound.Sound;
import util.LightInteger;
import controller.Controller;
import controller.ControlledObject;
import controller.Cursor;

public class Game {

	private Client client;
	/** Exit the game */
	private static boolean finished;

	private Level level;

	private Sound sound;

	private Render render;

	private Physic physic;

	private Logic logic;

	private Controller controller;
	// TODO add more good code to change work Game with Player
	private Player player;

	public static boolean isFinished() {
		return finished;
	}

	public static void setFinished(boolean finished) {
		Game.finished = finished;
	}

	// TODO add mechanizm creating level with parametrs from Client
	public Game(Client client) {

		this.player = new Player();
		level = new Level(player);
		level.testInitLevel();
		this.client = client;

		// this code is WTF, but I think, thats it is not important. code work)
		this.sound = client.getSound();
		this.render = client.getRender();
		this.physic = client.getPhysic();
		this.logic = client.getLogic();
		this.controller = client.getController();
		// TODO repair this hint with object level and engines to normal code
		this.logic.setLevel(level);
		this.physic.setLevel(level);
		this.sound.setLevel(level);
		this.render.setLevel(level);
		// controller dont need to set level!

	}

	/**
	 * Runs the game (the "main loop")
	 */
	public void start() {
		while (!finished) {
			// Always call Window.update(), all the time
			render.update();

			if (Display.isCloseRequested()) {
				// Check for O/S close requests
				finished = true;
			} else if (Display.isActive()) {
				// The window is in the foreground, so we should play the game
				controller.tick();
				physic.tick();
				logic.tick();
				sound.tick();
				render.tick();

				render.syncFps(Client.FRAMERATE);
			} else {
				// The window is not in the foreground, so we can allow other
				// stuff to run and
				// infrequently update
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				physic.tick();
				logic.tick();
				sound.tick();
				if (Display.isVisible() || Display.isDirty()) {
					// Only bother rendering if the window is visible or dirty
					render.tick();
					// TODO in titurial version this method dosnt call,
					// but i think, that call method maut be
					render.syncFps(Client.FRAMERATE);
				}
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	// TODO add table of key
	public void keyAction(ArrayList<LightInteger> list_key) {
		// interpretation key code
		for (LightInteger key : list_key) {
			switch (key.data) {

			case org.lwjgl.input.Keyboard.KEY_A: {
				level.getPlayer().getControlledObject()
						.doAction(ControlledObject.LEFT_ENGINE_ACTIVE);
				break;
			}
			case org.lwjgl.input.Keyboard.KEY_S: {
				level.getPlayer().getControlledObject()
						.doAction(ControlledObject.All_ENGINE_ACTIVE);
				break;
			}
			case org.lwjgl.input.Keyboard.KEY_D: {
				level.getPlayer().getControlledObject()
						.doAction(ControlledObject.RIGHT_ENGINE_ACTIVE);
				break;
			}
			case 0:
			case org.lwjgl.input.Keyboard.KEY_J: {
				level.getPlayer().getControlledObject()
						.doAction(ControlledObject.FIRE_FIRST_WEAPON);
				break;
			}
			// number of second button of mouse is maybe esc_key
			case org.lwjgl.input.Keyboard.KEY_K: {
				level.getPlayer().getControlledObject()
						.doAction(ControlledObject.FIRE_SECOND_WEAPON);
				break;
			}
			case org.lwjgl.input.Keyboard.KEY_ESCAPE: {
				client.exit();
				break;
			}
			// case 0: {
			// level.getPlayer().getControlledObject()
			// .doAction(ControlledObject.FIRE_FIRST_WEAPON);
			// break;
			// }
			}
		}

		// call method Player -> Entity

	}

	public void updateCursor(Cursor cursor) {
		level.updateCursor(cursor);
	}
}
