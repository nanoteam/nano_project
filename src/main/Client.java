package main;

import java.util.ArrayList;

import controller.Controller;
import logic.Level;
import logic.Logic;
import logic.entity.Player;
import physic.Physic;
import render.Render;
import sound.Sound;
import util.LightInteger;

public class Client {

	/** Desired frame time */
	public static final int FRAMERATE = 60;

	private Game game;

	private MainMenu mainMenu;

	private Sound sound;

	private Render render;

	private Physic physic;

	private Logic logic;

	private ResourcesManager resourcesManager;

	private Controller controller;

	private int state;

	// States

	public static int ERROR = -1;

	public static int LOAD_RESOURCES = 1;

	public static int MAIN_MENU = 2;

	public static int GAME = 3;

	public static int EXIT = 13;

	// TODO add full support menu
	public Client() {
		state = LOAD_RESOURCES;
		resourcesManager = new ResourcesManager();
		// do not delete! In progress
		state = MAIN_MENU;
		controller = Controller.createController(true, false, this);

		// menu = new Menu();
		logic = new Logic(this);
		physic = new Physic();
		render = new Render();
		sound = new Sound();

		state = GAME;

		// TODO change this line by call construktor with good parametrs - type
		// of level
		// , physic const, logic const
		game = new Game(this);

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

	public void start() {
		game.start();
	}

	public int getState() {
		return state;
	}

	public void exit() {
		cleanup();
		System.exit(0);
	}

	// TODO add command parametrs to start with there
	public static void main(String args[]) {
		Client client = new Client();
		client.start();
	}

	public Sound getSound() {
		return sound;
	}

	public Render getRender() {
		return render;
	}

	public Physic getPhysic() {
		return physic;
	}

	public Logic getLogic() {
		return logic;
	}

	public Controller getController() {
		return controller;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	// TODO add support mouse in client
	public void mouseAction() {

	}

	public void keyAction(ArrayList<LightInteger> list_key) {
		// TODO add support MainMenu
		if (state == Client.MAIN_MENU) {
			return;
		}

		if (state == Client.GAME) {
			game.keyAction(list_key);
			return;
		}
	}
}
