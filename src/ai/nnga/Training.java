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
        childChromosomeList[0] = new Chromosome(new float[][]{{-1.0264574f,0.33840147f,-0.19527513f,-0.8789664f,-1.0069404f,-0.16960841f,-0.5167872f,2.661609f,0.13348156f,-0.5225902f,0.87313765f,-0.2777676f,0.8126172f,1.0269679f,0.09601855f,-1.1273391f,-0.008428797f,0.20543037f,1.0308602f,-0.07095447f,1.0296497f,-1.7841907f,-1.3682791f,-0.5822253f,-1.0097969f,0.6521181f,0.88702697f,-0.10111052f,-0.4433465f,-3.0739493f,0.23103213f,0.5383948f,0.36483037f,-1.1790307f,-0.55947524f,-0.52497673f,-0.53448f,-3.546376f,1.923237f,1.8463033f,0.8770224f,0.6321292f,0.12974143f,-2.9268162f,-0.550135f,-2.17369f,0.65515125f,0.53190774f},
                {-0.795827f,-0.855886f,-0.38009596f,-0.09553024f,-1.1961634f,-0.18327153f,-0.7128701f,2.2830117f,1.0202523f,0.0364659f,1.1254996f,0.31883746f,0.8551465f,1.2275025f,-0.721784f,-1.3737466f,-0.9525382f,-0.039253633f,1.2974725f,0.4956053f,0.48922595f,-1.1911857f,-0.96722287f,-0.102942765f,-0.83557206f,0.59231526f,1.4883702f,-0.010710955f,-1.2146884f,-2.5867944f,0.72053874f,1.0850867f,0.43623406f,-0.6500273f,-0.31075823f,0.15135688f,-0.9524455f,-1.4406899f,2.0050898f,2.9505324f,1.3826799f,-1.0247519f,-0.104363486f,-1.8608637f,-0.5874692f,-2.4457614f,-0.1964724f,0.46061084f}});
        neuronNetwork = new NeuronNetwork();
        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
        stateTest = STATE_TEST_MEDIUM;
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
             /*
            if (koofDxy > 0.8f) {
                koofDxy = 1f;
            }
            if (sopriagEngine > 0.8f) {
                sopriagEngine = 1f;
            }
            if (koofRotateEngine > 0.8f) {
                koofRotateEngine = 1f;
            }               */
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
                        // mainMark, timeFly, energoCoasting, sopriagEngine       , koofDxy, koofWarningAngle, koofRotateEngine
                        box[0] += box[1] + box[1] * (box[2] * box[3] * box[4] * box[5] * box[6]);
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_MEDIUM;
                        //System.out.println("test medium");
                    } else {
                        float box[] = childChromosomeList[counter].getComplexMark()[stateTest];
                        box[0] += box[1] + box[1] * (box[2] * box[3] * box[4] * box[5] * box[6]);
                        //childChromosomeList[counter].addToComplexMark(stateTest, new float[]{timeTest, timeTest, energoCoasting, sopriagEngine, koofDxy, koofWarningAngle, koofRotateEngine});
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_EASY;
                        counter++;
                        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
                    }
                    break;
                }
                case STATE_TEST_MEDIUM: {
                    if (timeTest > (float) eliteChromosomeList[0].getComplexMark()[stateTest][1] / 100f) {
                        float box[] = childChromosomeList[counter].getComplexMark()[stateTest];
                        box[0] += box[1] + box[1] * (box[2] * box[3] * box[4] * box[5] * box[6]);
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_MEDIUM;
                        //in future deleted, need patch
                        counter++;
                        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList[counter].getPrimaryGens());
                    } else {
                        childChromosomeList[counter].addToComplexMark(stateTest, new float[]{timeTest, timeTest, energoCoasting, sopriagEngine, koofDxy, koofWarningAngle, koofRotateEngine});
                        numberTestsLeft = 0;
                        stateTest = STATE_TEST_MEDIUM;
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
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.gc();
            }
            if (generation % 200 == 0) {
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
                float a1 = (cromsoneList[i].getComplexMark()[0][0] + cromsoneList[i].getComplexMark()[1][0] * 5f + cromsoneList[i].getComplexMark()[2][0]);
                float a2 = (cromsoneList[i + 1].getComplexMark()[0][0] + cromsoneList[i + 1].getComplexMark()[1][0] * 5f + cromsoneList[i + 1].getComplexMark()[2][0]);
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

        if (stateTest == STATE_TEST_MEDIUM) {
            if (lifeTime % 200 == 0 && lifeTime > 199) {
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
        if (activity[0] > 0.3f) {
            rotateEngine++;
        }
        if (activity[0] < -0.3f) {
            rotateEngine++;
        }
        if (activity[1] > 0f) {
            leftEngineActivity++;
        }
        //right engine
        if (activity[2] > 0.3f) {
            rotateEngine++;
        }
        if (activity[2] < -0.3f) {
            rotateEngine++;
        }
        if (activity[3] > 0f) {
            rightEngineActivity++;
        }
        return activity;
    }

    public int getLevelChaos() {
        if (levelOfChaos == NO_CHAOS) {
            return levelOfChaos;
        } else {
            int lol = levelOfChaos;
            levelOfChaos = NO_CHAOS;
            return lol;
        }
    }
}
