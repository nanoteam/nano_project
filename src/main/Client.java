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
import resourses.configuration.SheetParse;
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

    boolean testMode = true;

    public static int STATE_ERROR = -1;

    public static int STATE_LOAD_RESOURCES = 1;

    public static int STATE_MAIN_MENU = 2;

    public static int STATE_GAME = 3;

    public static int STATE_EXIT = 13;

    private Client() {
        //by this line singleton must work as planed
        //if delete, there are two instance of Client
        client = this;
        state = STATE_LOAD_RESOURCES;
        configsLibrary = ConfigsLibrary.get();
        inputToAction = InputToAction.get();

        //must call first, before entity with resources
        resourcesManager = ResourcesManager.get();
        state = STATE_MAIN_MENU;
        // menu = new Menu();
        logic = new Logic();
        physic = new Physic();
        sound = new Sound();
        ai = new AI();
        render = new Render();
        controller = Controller.get();

    }

    public void start() {
        if (isTestMode()) {
            state = STATE_GAME;
            game = new Game(this, true);
            game.start();
        } else {
            new MainMenu();
        }
    }


    public static Client get() {
        if (client == null) {
            System.out.println("!!!!!!!");
            client = new Client();

        }
        return client;
    }

    private boolean isTestMode() {
        SheetParse setting = ConfigsLibrary.get().getConfig(ConfigsLibrary.pathToSetting);
        try {
            return Boolean.parseBoolean(setting.findSheetParseByName("TestMode").getValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void cleanup() {
        // TODO: save anything you want to disk here
        physic.cleanUp();
        sound.cleanUp();
        render.cleanUp();
        logic.cleanUp();
        ai.cleanUp();
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
        if (state == Client.STATE_MAIN_MENU) {
            return;
        }

        if (state == Client.STATE_GAME) {
            game.sendStatesInput(eventsKeyboard, eventsMouse);
            return;
        }
    }

    public InputToAction getInputToAction() {
        return inputToAction;
    }

    public static void main(String args[]) {
        new Client().start();
    }
}