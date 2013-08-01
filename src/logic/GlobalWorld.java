package logic;


import main.Engine;
import main.Global;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class GlobalWorld implements Engine {

    private static final int NUMBER_BY_X_LEVELS = 5;
    private static final int NUMBER_BY_Y_LEVELS = 5;
    public static final int NUMBER_LEVELS = NUMBER_BY_X_LEVELS * NUMBER_BY_Y_LEVELS;
    public static final int WORLD_RADIUS = 10;
    private static final int MAX_TIME_FOR_TIMER = 30000;
    private static final int DISTANCE_BETWEEN_WORLDS = 60;
    private static final int DISTANCE_BETWEEN_WORLDS_DEVIATION = 20;

    private static GlobalWorld globalWorld;
    private List<GlobalLevel> globalLevels = new ArrayList<GlobalLevel>();
    private ArrayDeque<GlobalLevel> dequeForTeleport = new ArrayDeque<GlobalLevel>();

    private GlobalWorld() {
        int orderNumber = 0;
        for (int i = 0; i < NUMBER_BY_X_LEVELS; i++)
            for (int j = 0; j < NUMBER_BY_Y_LEVELS; j++) {
                globalLevels.add(new GlobalLevel(i * DISTANCE_BETWEEN_WORLDS + DISTANCE_BETWEEN_WORLDS_DEVIATION / 2 - Global.random.nextInt(DISTANCE_BETWEEN_WORLDS_DEVIATION),
                                                 j * DISTANCE_BETWEEN_WORLDS + DISTANCE_BETWEEN_WORLDS_DEVIATION / 2 - Global.random.nextInt(DISTANCE_BETWEEN_WORLDS_DEVIATION),
                                                 orderNumber++, Global.random.nextInt(MAX_TIME_FOR_TIMER)));
            }
    }

    public static GlobalWorld get() {
        if (globalWorld == null) {
            globalWorld = new GlobalWorld();
        }
        return globalWorld;
    }

    public void tick() {
        for (GlobalLevel globalLevel : globalLevels) {
            globalLevel.update();
            if (globalLevel.isReadyToTeleport()) {
                dequeForTeleport.push(globalLevel);
                if (dequeForTeleport.size() > 1) {
                    exchange(dequeForTeleport.pop(), dequeForTeleport.pop());
                }
            }
        }
        for (GlobalLevel globalLevel : globalLevels) {
            globalLevel.draw();
        }

    }

    public void cleanUp() {

    }

    //add emmiter effects
    public void exchange(GlobalLevel globalLevel1, GlobalLevel globalLevel2) {
        int x = globalLevel1.getX(), y = globalLevel1.getY();

        globalLevel1.setX(globalLevel2.getX());
        globalLevel1.setY(globalLevel2.getY());
        globalLevel2.setX(x);
        globalLevel2.setY(y);
        globalLevel1.setTimeBeforeTeleport(Global.random.nextInt(MAX_TIME_FOR_TIMER));
        globalLevel2.setTimeBeforeTeleport(Global.random.nextInt(MAX_TIME_FOR_TIMER));


    }


}
