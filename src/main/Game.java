/**
 *
 * This is a main Game class, he init and start game
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package main;

import ai.AI;
import controller.Controller;
import controller.InputToAction;
import controller.StateKeyboard;
import controller.StateMouse;
import logic.Level;
import logic.Logic;
import logic.entity.Player;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import physic.Physic;
import render.Render;
import sound.Sound;

import java.util.List;

public class Game {

    private Client client;

    private boolean finished;

    private Level level;

    private Sound sound;

    private Render render;

    private Physic physic;

    private Logic logic;

    private Controller controller;

    private AI ai;
    // TODO add more good code to change work Game with Player
    private Player player;

    private InputToAction inputToAction;

    public boolean isFinished() {
        return finished;
    }

    void setFinished(boolean finished) {
        this.finished = finished;
    }

    // TODO add mechanizm creating level with parametrs from Client
    public Game(Client client) {
        this.player = new Player();
        level = Level.getLevel(this,"any");

        //level.testLevelStudyAI();
        level.testInitLevel();

        this.client = client;
        // this code is WTF, but I think, thats it is not important. code work)
        sound = client.getSound();
        render = client.getRender();
        physic = client.getPhysic();
        logic = client.getLogic();
        controller = client.getController();
        ai = client.getAI();
        // TODO repair this hint with object level and engines to normal code
        logic.setLevel(level);
        physic.setLevel(level);
        sound.setLevel(level);
        render.setLevel(level);
        ai.setLevel(level);
        inputToAction = client.getInputToAction();
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
                ai.tick();
                sound.tick();
                render.tick();

                if (Global.realTime) {
                    render.syncFps(Global.FPS);
                }
            } else {
                //go sleep game! game go to menu!
                //or sleep all resourses
                //in multi game newtwork must work!

                physic.tick();
                logic.tick();
                ai.tick();
                sound.tick();
                if (Global.realTime) {
                    render.syncFps(Global.FPS);
                }
                /*
         if (Display.isVisible() || Display.isDirty()) {
             //Only bother rendering if the window is visible or dirty
             render.tick();
             //TODO in titurial version this method dosnt call,
             //       but i think, that call method maut be
             render.syncFps(Client.FRAMERATE);
         }       */
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void sendStatesInput(List<StateKeyboard> eventsKeyboard, List<StateMouse> eventsMouses) {
        //keyboard event
        //System.out.println(eventsKeyboard);

        if (eventsKeyboard != null && (!eventsKeyboard.isEmpty())) {
            for (StateKeyboard stateKeyboard : eventsKeyboard) {
                if (stateKeyboard.state == StateKeyboard.DOWN || stateKeyboard.state == StateKeyboard.DOWN_PRESSED) {
                    switch (inputToAction.getActionByDevice(stateKeyboard.keyCode, InputToAction.KEYBOARD)) {
                        case InputToAction.up: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.up);
                            break;
                        }

                        case InputToAction.left: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.left);
                            break;
                        }
                        case InputToAction.right: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.right);
                            break;
                        }

                        case InputToAction.down: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.down);
                            break;
                        }
                        case InputToAction.firePrimary: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.firePrimary);
                            break;
                        }

                        case InputToAction.fireAlternative: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.fireAlternative);
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

                if (stateKeyboard.state == StateKeyboard.DOWN_PRESSED) {
                    switch (inputToAction.getActionByDevice(stateKeyboard.keyCode, InputToAction.KEYBOARD)) {


                        case InputToAction.specialAction: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.specialAction);
                            break;
                        }

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

        //mouse event
        if (eventsMouses != null && (!eventsMouses.isEmpty())) {
            for (StateMouse stateMouse : eventsMouses) {
                if (!stateMouse.rolickEvent) {
                    if (stateMouse.stateButton == StateMouse.DOWN || stateMouse.stateButton == StateMouse.DOWN_PRESSED) {
                        switch (inputToAction.getActionByDevice(stateMouse.buttonCode, InputToAction.MOUSE)) {
                            case InputToAction.firePrimary: {
                                level.getPlayer().getControlledObject()
                                        .doAction(InputToAction.firePrimary);
                                break;
                            }
                            case InputToAction.fireAlternative: {
                                level.getPlayer().getControlledObject()
                                        .doAction(InputToAction.fireAlternative);
                                break;
                            }

                        }
                    }
                }

                if (stateMouse.rolickEvent) {

                }
            }
        }
    }

    public Vector2f getPositionMouse() {
        return controller.getMousePosition();
    }

    public float getAngleBetweenShipAndCursor() {
        if (render.getStateViewPort() == Render.VIEWPORT_ON_PLAYER) {
            return (float) (Math.PI + Math.atan2(Global.RESOLUTION_Y / 2 - controller.getMousePosition().y, Global.RESOLUTION_X / 2
                    - controller.getMousePosition().x));
        }

        if (render.getStateViewPort() == Render.VIEWPORT_GLOBAL_WORLD) {
            return (float) (Math.PI + Math.atan2(player.getControlledObject().getPosition().y - controller.getMousePosition().y, player.getControlledObject().getPosition().x
                    - controller.getMousePosition().x));
        }
        return 0;
    }
}