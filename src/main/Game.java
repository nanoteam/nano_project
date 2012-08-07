/**
 * 
 * This is a main Game class, he init and start game
 * 
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package main;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;
import logic.Level;
import sound.Sound;
import render.Render;
import physic.Physic;
import logic.Logic;
import logic.entity.Player;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class Game {

	private Client client;
	/** Exit the game */
	private static boolean finished;

	private Level level;

	private Sound sound;

	private Render render;

	private Physic physic;

	private Logic logic;
	//TODO add more good code to change work Game with Player
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

		// TODO repair this hint with object level and engines to normal code
		this.logic.setLevel(level);
		this.physic.setLevel(level);
		this.sound.setLevel(level);
		this.render.setLevel(level);
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
}
