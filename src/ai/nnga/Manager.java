package ai.nnga;

import controller.InputToAction;
import logic.entity.GameObjectMoving;
import logic.entity.ship.Ship;
import main.Global;

public class Manager {
    public static final int TRAINING = 1;
    public static final int WORKING = 2;

    public static final int STATE_TEST_EASY = 1;
    public static final int STATE_TEST_MEDIUM = 2;
    public static final int STATE_TEST_HARD = 3;

    private int state = 0;

    private static Manager manager;
    private static NeuronNetwork nnShipFly = new NeuronNetwork();


    //init gens
    static {
        nnShipFly.init(Training.ARHITUCTURE_NETWORK, new float[]
                {0.034561828f, 0.10052949f, 1.1510628f, -0.8274355f, -0.10797796f, 0.9450654f, -0.8658619f, 1.0725899f, -0.94281447f, -0.36276585f, -0.27542934f, -0.24756667f, -0.44170082f, -2.796539f, -1.0184939f, -0.90817285f, -0.29211897f, -1.3513252f, -0.28995165f, -2.095533f, -0.94059974f, -0.6902472f, 0.82734936f, -1.5535111f, 0.32556063f, 0.892546f, -0.55046546f, 0.33703983f, -0.10282214f, 0.85381556f, -0.04736781f, -0.79043376f, 1.301129f, 0.80912685f, 0.6368007f, 1.0430369f, -0.6186922f, 1.1637963f, -0.31017295f, 0.20259333f, 0.4718951f, 0.33805808f, 1.6523602f, 0.061925314f, -0.29864308f, -1.1800032f, 0.27489543f, 1.9478247f});
    }

    private Manager() {
        training = new Training();
        if (Global.studyOn) {
            state = TRAINING;
        } else {
            state = WORKING;
        }
    }

    public static Manager get() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    private Training training;

    public void getReaction(GameObjectMoving gameObjectMoving) {
        if (gameObjectMoving.getName().equals(Ship.getName())) {
            //get need information
            float box[] = getReactionFlyShip((Ship) gameObjectMoving);

            if (state == WORKING) {
                box = nnShipFly.getActivity(box);
            }
            if (state == TRAINING) {
                box = training.getActivityCurrentChromosome(box);
                int levelChaos = training.getLevelChaos();
                if (levelChaos == Training.NO_CHAOS) {
                } else {
                    if (levelChaos == Training.LOW_CHAOS) {
                        ((Ship) gameObjectMoving).addChaosInMovement();
                    }
                }
            }

            if (box[0] > 0.8f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.leftEngineLeft);
            }
            if (box[0] < 0.2f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.leftEngineRight);
            }
            if (box[1] > 0.5f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.leftEngineOn);
            }
            if (box[2] > 0.8f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.rightEngineLeft);
            }
            if (box[2] < 0.2f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.rightEngineRight);
            }
            if (box[3] > 0.5f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.rightEngineOn);
            }
        }
    }

    public void nextTest() {
        training.nextTest();
    }

    private float[] getReactionFlyShip(Ship ship) {
        float box[] = new float[8];

        box[0] = ship.getAngle() / (2f * 3.14159f);
        box[1] = ship.getSpeed().x / 200f;
        box[2] = ship.getSpeed().y / 200f;
        box[3] = (ship.getPosition().x - 800) / 800f;
        box[4] = (ship.getPosition().y - 800) / 800f;
        //left engine
        float angleEngine = ship.getAngleLeftEngine() + ship.getAngle();
        while (angleEngine > 2 * 3.14159f) {
            angleEngine = angleEngine - 2f * 3.14159f;
        }
        while (angleEngine < -2 * 3.14159f) {
            angleEngine = angleEngine + 2f * 3.14159f;
        }
        box[5] = angleEngine / (2f * 3.14159f);
        //right engine
        angleEngine = ship.getAngleRightEngine() + ship.getAngle();
        while (angleEngine > 2f * 3.14159f) {
            angleEngine = angleEngine - 2f * 3.14159f;
        }
        while (angleEngine < -2 * 3.14159f) {
            angleEngine = angleEngine + 2f * 3.14159f;
        }
        box[6] = angleEngine / (2f * 3.14159f);
        box[7] = ship.getAngularVelocity() / 10f;
        return box;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
