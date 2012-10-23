/**
 *
 * This is a main Game class, he init and start game
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package main;

import controller.*;
import logic.Level;
import logic.Logic;
import logic.entity.Player;
import org.lwjgl.opengl.Display;
import physic.Physic;
import render.Render;
import sound.Sound;

import java.util.List;

public class Game {

    private Client client;
    /**
     * Exit the game
     */
    private static boolean finished;

    private Level level;

    private Sound sound;

    private Render render;

    private Physic physic;

    private Logic logic;

    private Controller controller;
    // TODO add more good code to change work Game with Player
    private Player player;

    private Cursor cursor;

    private InputToAction inputToAction;

    public static boolean isFinished() {
        return finished;
    }

    public static void setFinished(boolean finished) {
        Game.finished = finished;
    }

    // TODO add mechanizm creating level with parametrs from Client
    public Game(Client client) {

        this.player = new Player();
        level = new Level(player, client.getController().getCursor());

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
        this.inputToAction = client.getInputToAction();
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
                //go sleep game! game go to menu!
                //or sleep all resourses
                //in multi game newtwork must work!
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                /*
				physic.tick();
				logic.tick();
				sound.tick();
				*/
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

    public void sendEventsKeyboard(List<StateKey> eventsKeyboard) {

        if (eventsKeyboard == null) {
            return;

        }

        for (StateKey stateKey : eventsKeyboard) {
            System.out.println(stateKey.toString());
        }

        for (StateKey stateKey : eventsKeyboard) {
            if (stateKey.state == StateKey.DOWN || stateKey.state == StateKey.DOWN_PRESSED) {
                switch (inputToAction.getAction(stateKey.keyCode)) {
                    case InputToAction.left: {
                        level.getPlayer().getControlledObject()
                                .doAction(ControlledObject.LEFT_ENGINE_ACTIVE);
                        break;
                    }
                    case InputToAction.right: {
                        level.getPlayer().getControlledObject()
                                .doAction(ControlledObject.RIGHT_ENGINE_ACTIVE);
                        break;
                    }

                    case InputToAction.down: {
                        level.getPlayer().getControlledObject()
                                .doAction(ControlledObject.All_ENGINE_ACTIVE);
                        break;
                    }

                    case InputToAction.up: {
                        level.getPlayer().getControlledObject()
                                .doAction(ControlledObject.All_ENGINE_ACTIVE);
                        break;
                    }

                    case InputToAction.fire1: {
                        level.getPlayer().getControlledObject()
                                .doAction(ControlledObject.FIRE_FIRST_WEAPON);
                        break;
                    }

                    case InputToAction.engineLeft: {
                        level.getPlayer().getControlledObject()
                                .doAction(ControlledObject.TURN_ENGINES_LEFT);
                        break;
                    }

                    case InputToAction.engineRight: {
                        level.getPlayer().getControlledObject()
                                .doAction(ControlledObject.TURN_ENGINES_RIGHT);
                        break;
                    }
                    case InputToAction.zoomIn: {
                        render.setZoom((float) (render.getZoom() + 0.05));
                        break;
                    }
                    case InputToAction.zoomOut: {
                        render.setZoom((float) (render.getZoom() - 0.05));
                        break;
                    }
                }
            }


            if (stateKey.state == StateKey.DOWN_PRESSED) {
                switch (inputToAction.getAction(stateKey.keyCode)) {

                    case InputToAction.zoomCenter: {
                        if (render.getStateViewPort() == Render.VIEWPORT_ON_PLAYER) {
                            render.setStateViewPort(Render.VIEWPORT_GLOBAL_WORLD);
                            break;
                        }
                        if (render.getStateViewPort() == Render.VIEWPORT_GLOBAL_WORLD) {
                            render.setStateViewPort(Render.VIEWPORT_ON_PLAYER);
                            break;
                        }

                    }
                    case InputToAction.menu: {
                        client.exit();
                        break;
                    }

                }

            }
        }
    }

    public void updateCursor(Cursor cursor) {
        this.cursor = cursor;
    }
}
