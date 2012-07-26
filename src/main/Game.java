/**
 * 
 * This is a main Game class, he init and start game
 * 
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package main;

import logic.Level;
import sound.Sound;
import render.Render;
import physic.Physic;
import logic.Logic;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;



public class Game {

	/** Desired frame time */
	private static final int FRAMERATE = 60;

	/** Exit the game */
	private static boolean finished;

	private Level level;

	private Sound sound;

	private Render render;

	private Physic physic;

	private Logic logic;

	public Game() {
		level = new Level();
		logic = new Logic(level);
		physic = new Physic(level);
		render = new Render(level);
		sound = new Sound(level);
	}

	public static void main(String[] args) {
		Game game = new Game();
		try {
			game.start();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert("lol", "An error occured and the game will exit.");
		} finally {
			game.cleanup();
		}
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
				physic.tick();
				logic.tick();
				sound.tick();
				render.tick();

				render.syncFps(FRAMERATE);
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
					render.syncFps(FRAMERATE);
				}
			}
		}
	}

	/**
	 * Do any game-specific cleanup
	 */
	private void cleanup() {
		// TODO: save anything you want to disk here
		physic.cleanUp();
		sound.cleanUp();
		render.cleanUp();
		logic.cleanUp();
	}
}
