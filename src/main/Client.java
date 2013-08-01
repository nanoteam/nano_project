package main;

import ai.AI;
import controller.Controller;
import controller.InputToAction;
import controller.StateKeyboard;
import controller.StateMouse;
import logic.Logic;
import physic.Physic;
import render.Render;
import resourses.ResourcesManager;
import resourses.configuration.ConfigsLibrary;
import sound.Sound;

import java.util.List;

public final class Client {

    private static Client client;

    private Game game;

    private Sound sound;

    private Render render;

    private Physic physic;

    private Logic logic;

    private AI ai;

    private ResourcesManager resourcesManager;

    private Controller controller;

    private InputToAction inputToAction;

    private ConfigsLibrary configsLibrary;

    private int state;

    // States

    boolean testMode;

    public static int ERROR = -1;

    public static int LOAD_RESOURCES = 1;

    public static int MAIN_MENU = 2;

    public static int GAME = 3;

    public static int GLOBAL_WORLD = 4;

    public static int EXIT = 13;

    private Client() {
        state = LOAD_RESOURCES;
        configsLibrary = ConfigsLibrary.get();
        inputToAction = InputToAction.get();
        inputToAction.init(configsLibrary.getConfig(ConfigsLibrary.pathToSetting));
        //must call first, before entity with resources
        resourcesManager = ResourcesManager.get();
        state = MAIN_MENU;
        // menu = new Menu();
        logic = new Logic(this);
        physic = new Physic();
        render = new Render();
        sound = new Sound();
        ai = new AI();
        controller = Controller.createController(this);
        state = GAME;
        game = new Game(this);

    }

    public static Client get() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    private void cleanup() {
        // TODO: save anything you want to disk here
        physic.cleanUp();
        sound.cleanUp();
        render.cleanUp();
        logic.cleanUp();
        ai.cleanUp();
    }

    void start() {
        game.start();
    }

    public int getState() {
        return state;
    }

    public void exit() {
        cleanup();
        System.exit(0);
    }

    Sound getSound() {
        return sound;
    }

    Render getRender() {
        return render;
    }

    Physic getPhysic() {
        return physic;
    }

    Logic getLogic() {
        return logic;
    }

    Controller getController() {
        return controller;
    }

    AI getAI() {
        return ai;
    }

    public void sendStatesInput(List<StateKeyboard> eventsKeyboard, List<StateMouse> eventsMouse) {
        if (state == Client.MAIN_MENU) {
            return;
        }

        if (state == Client.GAME) {
            game.sendStatesInput(eventsKeyboard, eventsMouse);
            return;
        }
    }

    public InputToAction getInputToAction() {
        return inputToAction;
    }

    // TODO add commands parametrs to start with there
    public static void main(String args[]) {
        /*if(args.length>0)  {
            for (int i = 0; i < ; i++) {
                
            }
            
            args[0]
        } */
        Client client = new Client();
        client.start();
    }
}