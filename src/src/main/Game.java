/**
 *
 * This is a main Game class, he init and start game
 *
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package main;

import ai.AI;
import ai.nnga.Manager;
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
        level = new Level(this);

        level.testLevelStudyAI();
        //level.testInitLevel();

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
        byte moving = 0;
        byte firing = 0;
        //keyboard event
        //System.out.println(eventsKeyboard);

        if (eventsKeyboard != null && (!eventsKeyboard.isEmpty())) {
            for (StateKeyboard stateKeyboard : eventsKeyboard) {
                if (stateKeyboard.state == StateKeyboard.DOWN || stateKeyboard.state == StateKeyboard.DOWN_PRESSED) {
                    switch (inputToAction.getActionByDevice(stateKeyboard.keyCode, InputToAction.KEYBOARD)) {
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

                        case InputToAction.leftEngineLeft: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.leftEngineLeft);
                            break;
                        }

                        case InputToAction.leftEngineRight: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.leftEngineRight);
                            break;
                        }
                        case InputToAction.rightEngineLeft: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.leftEngineLeft);
                            break;
                        }

                        case InputToAction.rightEngineRight: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.leftEngineRight);
                            break;
                        }

                        case InputToAction.leftEngineOn: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.leftEngineOn);
                            break;
                        }

                        case InputToAction.rightEngineOn: {
                            level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.rightEngineOn);
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

                        case InputToAction.up: {
                            /*level.getPlayer().getControlledObject()
                                    .doAction(InputToAction.up);*/
                            Global.realTime = !Global.realTime;
                            break;
                        }

                        case InputToAction.fire: {
                            //firing++;
                            //System.out.println("fire");

                            //Manager.get().changeMarkCurrentChromosome(Chromosome.DIFF_MARK);
                            break;
                        }

                        case InputToAction.move: {
                            //moving++;
                            //System.out.println("move");

                            //Manager.get().changeMarkCurrentChromosome(-Chromosome.DIFF_MARK);
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
                if (stateMouse.rolickEvent) {

                }
                if (!stateMouse.rolickEvent) {
                    if (stateMouse.buttonCode == StateKeyboard.DOWN || stateMouse.stateButton == StateKeyboard.DOWN_PRESSED) {
                        switch (inputToAction.getActionByDevice(stateMouse.buttonCode, InputToAction.MOUSE)) {
                            /* case InputToAction.comboChoiseFirst: {
                       Manager.get().changeMarkCurrentCromsone(+Chromosome.DIFF_MARK);
                       //System.out.println("left");
                       break;
                   }
                   case InputToAction.comboChoiseSecond: {
                       Manager.get().changeMarkCurrentCromsone(-Chromosome.DIFF_MARK);
                       //System.out.println("right");
                       break;
                   }         */


                            /*
//                            case InputToAction.comboChoiseFirst: {
//                                if (firing!=0){
//                                    //player.get
//                                    firing = 0;
//                                }
//                                if (moving!=0){
//
//                                    moving = 0;
//                                }
//                                break;
//                            }
//                            case InputToAction.comboChoiseSecond: {
//                                if (firing!=0){
//
//                                    firing = 0;
//                                }
//                                if (moving!=0){
//
//                                    moving = 0;
//                                }
//                                break;
//                            }
//                            case InputToAction.comboChoiseStop: {
//                                if (firing!=0){
//
//                                    firing = 0;
//                                }
//                                if (moving!=0){
//
//                                    moving = 0;
//                                }
//                                break;
//                            }*/


                        }
                    }
                }
            }
        }
    }

    public Vector2f getPositionMouse() {
        return controller.getMousePosition();
    }
}