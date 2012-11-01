package logic.entity.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CromsonManager {
    public final static int NUMBER_CROMSONE = 10;
    public final static int LENGHT_EXPERIMENT_SECOND = 300;

    private long timeStartExperiment;
    private long timeEndExperiment;
    private int counter = 0;
    private int generation = 0;
    private boolean start = false;
    private List<Cromsone> cromsoneList = new ArrayList<Cromsone>();
    private Random random = new Random();

    public CromsonManager() {
        for (int i = 0; i < NUMBER_CROMSONE; i++) {
            cromsoneList.add(new Cromsone());
        }
    }

    //main method
    public void touchWall() {
        //first call - initialezed
        if (!start) {
            counter = 0;
            timeStartExperiment = System.currentTimeMillis() / 1000;
            start = true;
            counter++;
            return;
        }

        timeEndExperiment = System.currentTimeMillis() / 1000;
        cromsoneList.get(counter).setMark((timeEndExperiment-timeStartExperiment)/LENGHT_EXPERIMENT_SECOND);
        //create generation
        if (counter + 1 == NUMBER_CROMSONE) {
            counter = 0;
            generation++;
            sortingCromsonListByMark();
            printBestGen();
            newGeneration();
            timeStartExperiment = System.currentTimeMillis() / 1000;
            counter++;
            return;
        }
        counter++;
    }

    public float[] getActivityCurrentCromsone(float[] input){
        if(!start){
            new Exception("not initialized!");
        }
        return cromsoneList.get(counter).checkActivity(input);
    }
    
    private void sortingCromsonListByMark() {
        for (int i = 0; i < cromsoneList.size(); i++) {
            for (int k = i; k < cromsoneList.size(); k++) {
                if (i == k) {
                    continue;
                }
                if (cromsoneList.get(i).getMark() < cromsoneList.get(k).getMark()) {
                    Cromsone cromsone = cromsoneList.get(k);
                    cromsoneList.set(k, cromsoneList.get(i));
                    cromsoneList.set(i, cromsone);
                }

            }

        }

    }

    private void newGeneration() {
        //0-1-2 has x2 sex, 3-4-5 has x1 sex, rand 6-7-8-9 has one sex.
        List<Cromsone> newCromsoneList = new ArrayList<Cromsone>();
        int k;
        Cromsone newCromsone;
        for (int i = 0; i < 6; i++) {
            if (i == 0 || i == 1 || i == 2) {
                k = random.nextInt(10);
                while (k == i) {
                    k = random.nextInt(10);
                }
                newCromsone = cromsoneList.get(i).newChild(cromsoneList.get(k));
                newCromsoneList.add(newCromsone);

                k = random.nextInt(10);
                while (k == i) {
                    k = random.nextInt(10);
                }
                newCromsone = cromsoneList.get(i).newChild(cromsoneList.get(k));
                newCromsoneList.add(newCromsone);
            }

            if (i == 3 || i == 4 || i == 5) {
                k = random.nextInt(10);
                while (k == i) {
                    k = random.nextInt(10);
                }
                newCromsone = cromsoneList.get(i).newChild(cromsoneList.get(k));
                newCromsoneList.add(newCromsone);
            }
        }
        int i = random.nextInt(4) + 5;
        k = random.nextInt(10);
        while (k == i) {
            k = random.nextInt(10);
        }
        newCromsone = cromsoneList.get(i).newChild(cromsoneList.get(k));
        newCromsoneList.add(newCromsone);
        cromsoneList = newCromsoneList;
    }

    private void printBestGen(){

    }
}
