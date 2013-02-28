package ai.nnga;

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
                {-1.0264574f,0.33840147f,-0.19527513f,-0.8789664f,-1.0069404f,-0.16960841f,-0.5167872f,2.661609f,0.13348156f,-0.5225902f,0.87313765f,-0.2777676f,0.8126172f,1.0269679f,0.09601855f,-1.1273391f,-0.008428797f,0.20543037f,1.0308602f,-0.07095447f,1.0296497f,-1.7841907f,-1.3682791f,-0.5822253f,-1.0097969f,0.6521181f,0.88702697f,-0.10111052f,-0.4433465f,-3.0739493f,0.23103213f,0.5383948f,0.36483037f,-1.1790307f,-0.55947524f,-0.52497673f,-0.53448f,-3.546376f,1.923237f,1.8463033f,0.8770224f,0.6321292f,0.12974143f,-2.9268162f,-0.550135f,-2.17369f,0.65515125f,0.53190774f});
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
        if (gameObjectMoving.getClassName().equals(Ship.getClassName())) {
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
                    /*if (levelChaos == Training.LOW_CHAOS) {
                        ((Ship) gameObjectMoving).addChaosInMovement();
                    }       */
                }
            }
            /*
            if (box[0] > 0.3f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.leftEngineLeft);
            }
            if (box[0] < -0.3f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.leftEngineRight);
            }
            if (box[1] > 0f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.leftEngineOn);
            }
            if (box[2] > 0.3f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.rightEngineLeft);
            }
            if (box[2] < -0.3f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.rightEngineRight);
            }
            if (box[3] > 0f) {
                ((Ship) gameObjectMoving).doAction(InputToAction.rightEngineOn);
            }                                                               */
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
        box[3] = (ship.getPosition().x - 800f) / 800f;
        box[4] = (ship.getPosition().y - 800f) / 800f;

        /*
        //left engine
        float angleEngine = ship.getAngleLeftEngine() + ship.getAngle();
        while (angleEngine > 2f * 3.14159f) {
            angleEngine = angleEngine - 2f * 3.14159f;
        }
        while (angleEngine < -2f * 3.14159f) {
            angleEngine = angleEngine + 2f * 3.14159f;
        }
        box[5] = angleEngine / (2f * 3.14159f);
        //right engine
        angleEngine = ship.getAngleRightEngine() + ship.getAngle();
        while (angleEngine > 2f * 3.14159f) {
            angleEngine = angleEngine - 2f * 3.14159f;
        }
        while (angleEngine < -2f * 3.14159f) {
            angleEngine = angleEngine + 2f * 3.14159f;
        }
        box[6] = angleEngine / (2f * 3.14159f);
        box[7] = ship.getAngularVelocity() / 10f;
        return box;                                                           */
        return null;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
