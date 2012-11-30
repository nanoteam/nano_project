package ai.nnga;

import main.Global;

public class Training {
    //number elite + number child = number all
    //number elite == number child
    public final static int ALL_NUMBER_CROMSONE = 30;
    public final static int CHILD_NUMBER_CROMSONE = 15;
    public final static int ELITE_NUMBER_CROMSONE = 15;
    private Chromosome[] eliteChromosomeList;
    private Chromosome[] childChromosomeList;
    public final static int[] ARHITUCTURE_NETWORK = {8, 4, 4};
    private NeuronNetwork neuronNetwork;

    private int counter = 0;

    public final static int LENGHT_EXPERIMENT_SECOND = 20000;
    private int numberTestsLeft = 0;
    private static final int NUMBER_MAX_TESTS = 5;

    private long lifeTime = 0;
    private long generation = 0;

    private long leftEngineActivity = 0;
    private long rightEngineActivity = 0;
    private float dx = 0, dy = 0;
    private long warningAngle = 0;
    private long rotateEngine = 0;

    //state test
    private int stateTest = 0;

    public static final int STATE_TEST_EASY = 0;
    public static final int STATE_TEST_MEDIUM = 1;
    public static final int STATE_TEST_HARD = 2;

    private static final float upSectorLeftAngle = 3 * 3.14159f / 4f;
    private static final float upSectorRightAngle = 1 * 3.14159f / 4f;
    private static final float downSectorLeftAngle = 5 * 3.14159f / 4f;
    private static final float downSectorRightAngle = 7 * 3.14159f / 4f;

    private int levelOfChaos = 0;
    static final int NO_CHAOS = 0;
    static final int LOW_CHAOS = 1;
    static final int HIGH_CHAOS = 2;
    Training() {
        eliteChromosomeList = new Chromosome[ALL_NUMBER_CROMSONE];
        childChromosomeList = new Chromosome[CHILD_NUMBER_CROMSONE];
        for (int i = 0; i < ALL_NUMBER_CROMSONE; i++) {
            eliteChromosomeList[i] = new Chromosome();
        }
        for (int i = 0; i < CHILD_NUMBER_CROMSONE; i++) {
            childChromosomeList[i] = new Chromosome();
        }

        //init cromsone manual
        childChromosomeList[0] = new Chromosome(new float[][]{{0.034561828f,0.10052949f,1.1510628f,-0.8274355f,-0.10797796f,0.9450654f,-0.8658619f,1.0725899f,-0.94281447f,-0.36276585f,-0.27542934f,-0.24756667f,-0.44170082f,-2.796539f,-1.0184939f,-0.90817285f,-0.29211897f,-1.3513252f,-0.28995165f,-2.095533f,-0.94059974f,-0.6902472f,0.82734936f,-1.5535111f,0.32556063f,0.892546f,-0.55046546f,0.33703983f,-0.10282214f,0.85381556f,-0.04736781f,-0.79043376f,1.301129f,0.80912685f,0.6368007f,1.0430369f,-0.6186922f,1.1637963f,-0.31017295f,0.20259333f,0.4718951f,0.33805808f,1.6523602f,0.061925314f,-0.29864308f,-1.1800032f,0.27489543f,1.9478247f},
                  {0.053834558f,-0.27571982f,-0.84302044f,-0.23583078f,0.3273043f,0.17912862f,-0.61129344f,1.5149367f,-1.5171484f,0.4979918f,-0.65166235f,-0.46115634f,-0.2749039f,-2.602311f,-0.4052808f,-0.32811582f,-1.1414247f,0.41858053f,-0.26792186f,-1.1207498f,-0.65707177f,0.6612432f,0.44465956f,-0.81730574f,-0.8175189f,0.08763003f,-0.593424f,-0.12770194f,0.11633491f,0.15685382f,-0.31800973f,-0.92779577f,1.2958231f,0.49093458f,0.64216924f,0.72988975f,-0.64547235f,0.19276078f,-0.8760685f,-0.6079672f,0.9931612f,0.33950737f,1.7258855f,0.07680867f,-0.19289458f,-0.7819583f,0.5532616f,0.5611796f}});

        neuronNetwork = new NeuronNetwork();
        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
        stateTest = STATE_TEST_EASY;
    }

    //
    //Study
    //
    //main method
    public void nextTest() {
        float energoCoasting = 0, sopriagEngine = 0, koofDxy = 0, koofWarningAngle = 0, koofRotateEngine = 0;
        //timeFly - time fly ship in this test
        float timeTest = (float) lifeTime / LENGHT_EXPERIMENT_SECOND;
        //calc complex parametrs, else equals 0
        if (timeTest > 0.003f) {
            energoCoasting = 1f - ((float) leftEngineActivity + rightEngineActivity) / (lifeTime + lifeTime);
            if (leftEngineActivity + rightEngineActivity != 0) {
                sopriagEngine = 1f - (float) Math.abs((leftEngineActivity - rightEngineActivity)) / (leftEngineActivity + rightEngineActivity);
            } else {
                sopriagEngine = 0.125f;
            }
            //koofDxy = (1f - ((dx + dy) / lifeTime)) / 2f;
            koofDxy = 1f - ((dx + dy) / lifeTime) / 2f;
            koofWarningAngle = 1f - ((float) warningAngle / lifeTime);
            koofRotateEngine = 1f - ((float) rotateEngine / (lifeTime + lifeTime));
            childChromosomeList[counter].addToComplexMark(stateTest, new float[]{0, timeTest, energoCoasting, sopriagEngine, koofDxy, koofWarningAngle, koofRotateEngine});
        }
        if (Global.realTime) {
            System.out.println();
            System.out.println(childChromosomeList[counter].getComplexMarkToString());
            System.out.println();
        }

        numberTestsLeft--;
        //calc complex mark for chromsone and change chromsone
        if (numberTestsLeft <= 0) {
            switch (stateTest) {
                case STATE_TEST_EASY: {
                    //if (timeTest > (float) eliteChromosomeList[0].getComplexMark()[stateTest][1] / 25f) {
                    if (timeTest > 0.1f) {
                        float box[] = childChromosomeList[counter].getComplexMark()[stateTest];
                        // mainMark, timeFly, energoCoasting, sopriagEngine, koofDxy, koofWarningAngle, koofRotateEngine
                        box[0] += box[1] + box[1] * (float) Math.sqrt(box[2] * box[3] * box[4] * box[5] * box[6]);
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_MEDIUM;
                        //System.out.println("test medium");
                    } else {
                        float box[] = childChromosomeList[counter].getComplexMark()[stateTest];
                        box[0] += box[1] + box[1] * (float) Math.sqrt(box[2] * box[3] * box[4] * box[5] * box[6]) * 2f;
                        //childChromosomeList[counter].addToComplexMark(stateTest, new float[]{timeTest, timeTest, energoCoasting, sopriagEngine, koofDxy, koofWarningAngle, koofRotateEngine});
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_EASY;
                        counter++;
                        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
                    }
                    break;
                }
                case STATE_TEST_MEDIUM: {
                    if (timeTest > (float) eliteChromosomeList[0].getComplexMark()[stateTest][1]/10) {
                        float box[] = childChromosomeList[counter].getComplexMark()[stateTest];
                        box[0] += box[1] + box[1] * (float) Math.sqrt(box[2] * box[3] * box[4] * box[5] * box[6]) * 2f;
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_EASY;
                        //in future deleted, need patch
                        counter++;
                        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
                    } else {
                        childChromosomeList[counter].addToComplexMark(stateTest, new float[]{timeTest, timeTest, energoCoasting, sopriagEngine, koofDxy, koofWarningAngle, koofRotateEngine});
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_EASY;
                        counter++;
                        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
                    }
                    break;
                }
                case STATE_TEST_HARD: {
                    break;
                }
            }
        }

        clearFlags();

        //create generation
        if (counter + 1 == CHILD_NUMBER_CROMSONE) {
            counter = 0;
            generation++;
            if (generation % 50 == 0) {
                System.runFinalization();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.gc();
            }
            if (generation % 50 == 0) {
                System.out.println("\n");

                for (int i = 0; i < 3; i++) {
                    System.out.println(eliteChromosomeList[i].getStringAllGens());
                }
                System.out.println("\n");
            }
            //add child and sort by mark
            compareChildWithElite();
            if (Global.realTime) {
                printBestGen(eliteChromosomeList, 0, 3);
            }
            newGeneration();
            //delete garbage from elite
            deleteBadChromosome();
            counter++;
            neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
            return;
        }

    }

    private void compareChildWithElite() {
        //add child to elite
        for (int i = ELITE_NUMBER_CROMSONE; i < ALL_NUMBER_CROMSONE; i++) {
            eliteChromosomeList[i] = null;
            eliteChromosomeList[i] = childChromosomeList[i - ELITE_NUMBER_CROMSONE];
        }
        sortingCromsonListByMark(eliteChromosomeList);
    }

    private void sortingCromsonListByMark(Chromosome[] cromsoneList) {
        for (int k = cromsoneList.length - 1; k >= 0; k--) {
            for (int i = 0; i < k; i++) {
                float a1 = (cromsoneList[i].getComplexMark()[0][0] + cromsoneList[i].getComplexMark()[1][0] + cromsoneList[i].getComplexMark()[2][0]);
                float a2 = (cromsoneList[i + 1].getComplexMark()[0][0] + cromsoneList[i + 1].getComplexMark()[1][0] + cromsoneList[i + 1].getComplexMark()[2][0]);
                if (a1 < a2) {
                    Chromosome cromsone = new Chromosome(cromsoneList[i + 1]);
                    cromsoneList[i + 1] = null;
                    cromsoneList[i + 1] = new Chromosome(cromsoneList[i]);
                    cromsoneList[i] = null;
                    cromsoneList[i] = cromsone;
                }

            }
        }
    }

    private void deleteBadChromosome() {

        for (int i = ELITE_NUMBER_CROMSONE; i < ALL_NUMBER_CROMSONE; i++) {
            eliteChromosomeList[i].destroy();
            eliteChromosomeList[i] = null;
        }
    }

    private void newGeneration() {
        //first best cromsone merge with all cromsone
        //childChromosomeList = new ArrayList<Chromosome>();
        int k;
        for (int i = 0; i < CHILD_NUMBER_CROMSONE; i++) {
            //i,k - parents chromosome
            k = Global.random.nextInt(ALL_NUMBER_CROMSONE);
            while (k == i) {
                k = Global.random.nextInt(ALL_NUMBER_CROMSONE);
            }
            Chromosome cromsone;
            cromsone = Chromosome.newChild(eliteChromosomeList[i], eliteChromosomeList[k]);
            childChromosomeList[i] = null;
            childChromosomeList[i] = cromsone;
        }
    }

    private void printBestGen(Chromosome[] cromsoneList, int start, int end) {
        System.out.println("******************");
        System.out.println("generation is " + generation);
        for (int i = start; i < end; i++) {
            System.out.println(cromsoneList[i].getComplexMarkToString());
        }
        System.out.println("******************");

    }

    private void printBestGen(Chromosome[] cromsoneList) {
        printBestGen(cromsoneList, 0, cromsoneList.length);
    }


    //replace this in getActivityChromsone
    private void clearFlags() {
        lifeTime = 0;
        leftEngineActivity = 0;
        rightEngineActivity = 0;
        warningAngle = 0;
        rotateEngine = 0;
        dx = 0;
        dy = 0;
    }

    public int getState() {
        return stateTest;
    }

    public float[] getActivityCurrentChromosome(float[] input) {

        lifeTime++;
        if (lifeTime > 20000) {
            //System.out.println("this cromson is winner!, gens is " + childChromosomeList.get(counter).getStringAllGens());
            nextTest();
        }

        if (stateTest == STATE_TEST_MEDIUM){

                if (lifeTime%200==0){
                //System.out.println("@");
                levelOfChaos = LOW_CHAOS;
            }
        }

        //rules with input
        if ((input[0] < upSectorLeftAngle) && (input[0] > upSectorRightAngle)) {
            warningAngle++;
        }
        if ((input[0] > downSectorLeftAngle) && (input[0] < downSectorRightAngle)) {
            warningAngle++;
        }
        dx += Math.abs(input[3]);
        dy += Math.abs(input[4]);

        float[] activity = neuronNetwork.getActivity(input);
        //rules with output

        //left engine
        if (activity[0] > 0.8f) {
            rotateEngine++;
        }
        if (activity[0] < 0.2f) {
            rotateEngine++;
        }
        if (activity[1] > 0.5f) {
            leftEngineActivity++;
        }
        //right engine
        if (activity[2] > 0.8f) {
            rotateEngine++;
        }
        if (activity[2] < 0.2f) {
            rotateEngine++;
        }
        if (activity[3] > 0.5f) {
            rightEngineActivity++;
        }
        return activity;
    }
    public int getLevelChaos() {
        if (levelOfChaos==NO_CHAOS){
            return levelOfChaos;
        }
        else{
            int lol = levelOfChaos;
            levelOfChaos = NO_CHAOS;
            return lol;
        }
    }
}
