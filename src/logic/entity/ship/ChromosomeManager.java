package logic.entity.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChromosomeManager {
    //number elite + number child = number all
    //number elite == number child
    public final static int ALL_NUMBER_CROMSONE = 30;
    public final static int CHILD_NUMBER_CROMSONE = 15;

    public final static int LENGHT_EXPERIMENT_SECOND = 20000;
    public final static int[] ARHITUCTURE_NETWORK = {8, 4, 4};
    private static boolean realTime = true;


    private int counter = 0;
    private long lifeTime = 0;
    private long generation = 0;
    private boolean start = false;
    private List<Chromosome> eliteChromosomeList;
    private List<Chromosome> childChromosomeList;
    private Random random = new Random();
    private static ChromosomeManager chromosomeManager;

    private NeuronNetwork neuronNetwork;


    private long leftEngineActivity;
    private long rightEngineActivity;
    private float dx = 0, dy = 0;

    private boolean leftEngineOn;
    private boolean rightEngineOn;
    private boolean limitW;
    private boolean overloadAngle;

    public static final int STATE_LEFT_ENGINE_ON = 0;
    public static final int STATE_RIGHT_ENGINE_ON = 1;
    public static final int STATE_LIMIT_W = 2;
    public static final int STATE_OVERLOAD_ANGLE = 3;


    private ChromosomeManager() {

        eliteChromosomeList = new ArrayList<Chromosome>();
        for (int i = 0; i < CHILD_NUMBER_CROMSONE * 2; i++) {
            eliteChromosomeList.add(new Chromosome());
        }
        childChromosomeList = new ArrayList<Chromosome>(eliteChromosomeList);
        //init cromsone manual
        //childChromosomeList.set(0, new Chromosome(new float[][]{{-0.79756325f, 0.10298779f, 0.5770693f, 0.032388568f, 0.8251306f, 0.68639576f, -0.5469712f, -0.10162991f, -0.9287936f, -0.23749691f, 1.3780006f, -0.22003731f, -0.671055f, -0.39540097f, -1.2294183f, -0.04533696f, -0.11474764f, -0.79908615f, 0.009989977f, -0.34477508f, 0.5312138f, 0.5119039f, -0.9190956f, -1.5324372f, 0.39415926f, -0.27849406f, 0.679525f, -0.37675416f, -0.9938378f, 0.7062167f, 0.6757697f, 1.0397247f, -0.17932531f, -0.09791422f, -0.64227265f, 0.19837552f, 0.4420771f, 0.26228842f, -0.818461f, 0.83440983f, -0.9442727f, -0.02183941f, -0.13144156f, 0.4198891f, -0.33632404f, 1.070285f, -0.43511543f, -0.38308835f},
          //      {-1.0884502f, 0.055762872f, -0.30972892f, -0.21056825f, 0.0828383f, -0.4326964f, -0.15239894f, -0.38687998f, 0.6494541f, -0.14435562f, 0.73797214f, -0.22795513f, -1.1753302f, -0.2629533f, -0.06783748f, -0.76002055f, 1.0573325f, -0.35473186f, 0.7047916f, 0.0278216f, 0.6479676f, 1.2806077f, 0.28163552f, -1.3344679f, -0.503431f, 0.53702843f, 0.37634647f, -0.35197473f, -0.5869878f, 1.6453856f, 0.19549984f, 0.7313971f, -0.43593407f, -0.2823144f, -1.2554829f, 0.32751778f, 1.0197946f, 0.73579216f, 0.56714696f, -0.13759604f, -0.9918426f, 0.49023533f, -0.041372716f, 0.40818366f, -0.69787925f, 0.22706664f, -0.7518498f, -1.0891585f}}));
        neuronNetwork = NeuronNetwork.get();
    }

    public static ChromosomeManager get() {
        if (chromosomeManager == null) {
            chromosomeManager = new ChromosomeManager();
        }
        return chromosomeManager;
    }

    //main method
    public void touchWall() {
        //first call - initialezed
        if (!start) {
            counter = 0;
            lifeTime = 0;
            //for test!
            neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList.get(counter).getPrimaryGens());
            start = true;
            return;
        }


        // complex fitness
        float mainMark = 0;
        float timeFly = (float) lifeTime / LENGHT_EXPERIMENT_SECOND;
        if (timeFly > 0.005f) {
            //1 - good, 0- bad
            float energoCoasting = 1f - ((float) leftEngineActivity + rightEngineActivity) / (lifeTime * 2f);
            //1 - good, 0 - bad
            float sopriagEngine = 1f - (float) Math.abs((leftEngineActivity - rightEngineActivity)) / (leftEngineActivity + rightEngineActivity);
            //1 - good, 0 - bad
            float koofDxy = 1f - ((dx + dy) / lifeTime) / 2f;

            mainMark = timeFly;
            if (energoCoasting > 0.125f && sopriagEngine > 0.125f && timeFly > 0.125f && koofDxy > 0.125f) {
                mainMark += (timeFly * 0.125f) * (1f + energoCoasting + sopriagEngine + koofDxy);
                if (energoCoasting > 0.25f && sopriagEngine > 0.25f && timeFly > 0.25f && koofDxy > 0.25f) {
                    mainMark += (timeFly * 0.125f) * (1f + energoCoasting + sopriagEngine + koofDxy);
                    if (energoCoasting > 0.75f && sopriagEngine > 0.75f && timeFly > 0.75f && koofDxy > 0.75f) {
                        mainMark += (timeFly * 0.125f) * (1f + energoCoasting + sopriagEngine + koofDxy);
                    }
                }
            }
            childChromosomeList.get(counter).setComplexMark(new float[]{
                    mainMark, timeFly, energoCoasting, sopriagEngine, koofDxy});
        } else {
            mainMark = timeFly;
            childChromosomeList.get(counter).setComplexMark(new float[]{mainMark, 0, 0, 0, 0});
        }

        clearFlags();
//for resolution
        if (realTime)

        {
            System.out.println(childChromosomeList.get(counter).getComplexMarkToString());
        }

        lifeTime = 0;
//create generation
        if (counter + 1 == childChromosomeList.size())

        {
            counter = 0;
            generation++;
            if (generation % 50 == 0) {
                System.out.println("\n");

                for (int i = 0; i < 3; i++) {
                    System.out.println(eliteChromosomeList.get(i).getStringAllGens());
                }
                System.out.println("\n");
            }
            compareChildWithElite();
            if (realTime) {
                printBestGen(eliteChromosomeList, 0, 3);
            }
            newGeneration();
            counter++;
            neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList.get(counter).getPrimaryGens());
            return;
        }

        counter++;
        neuronNetwork.init(ARHITUCTURE_NETWORK, childChromosomeList.get(counter).getPrimaryGens());
    }

    private void compareChildWithElite() {
        eliteChromosomeList.addAll(childChromosomeList);
        sortingCromsonListByMark(eliteChromosomeList);
        deleteBadChromosome();
    }

    private void sortingCromsonListByMark(List<Chromosome> cromsoneList) {
        for (int i = 0; i < cromsoneList.size(); i++) {
            for (int k = 0; k < cromsoneList.size(); k++) {
                if (i == k) {
                    continue;
                }
                if ((cromsoneList.get(i).getComplexMark())[0] > (cromsoneList.get(k).getComplexMark())[0]) {
                    Chromosome cromsone = cromsoneList.get(k);
                    cromsoneList.set(k, cromsoneList.get(i));
                    cromsoneList.set(i, cromsone);
                }
            }
        }
    }

    private void deleteBadChromosome() {
        for (int i = eliteChromosomeList.size() - 1; i > eliteChromosomeList.size(); i--) {
            eliteChromosomeList.get(i).destroy();
            eliteChromosomeList.set(i, null);
        }
    }

    private void newGeneration() {
        //first best cromsone merge with all cromsone
        childChromosomeList = new ArrayList<Chromosome>();
        int k;

//i,k - merge cromsone
        for (int i = 0; i < CHILD_NUMBER_CROMSONE; i++) {
            k = random.nextInt(ALL_NUMBER_CROMSONE);
            while (k == i) {
                k = random.nextInt(ALL_NUMBER_CROMSONE);
            }
            Chromosome cromsone;
            cromsone = eliteChromosomeList.get(i).newChild(eliteChromosomeList.get(k));
            childChromosomeList.add(cromsone);
        }
        eliteChromosomeList = eliteChromosomeList.subList(0, ALL_NUMBER_CROMSONE - CHILD_NUMBER_CROMSONE);
    }

    private void printBestGen(List<Chromosome> cromsoneList, int start, int end) {
        System.out.println("******************");
        System.out.println("generation is " + generation);
        for (int i = start; i < end; i++) {
            System.out.println(cromsoneList.get(i).getComplexMarkToString());
        }
        System.out.println("******************");

    }

    private void printBestGen(List<Chromosome> cromsoneList) {
        printBestGen(cromsoneList, 0, cromsoneList.size());
    }

    public float[] getActivityCurrentChromosome(float[] input) {
        if (!start) {
            new Exception("not initialized!");
        }
        lifeTime++;
        if (lifeTime == 20000) {
            //System.out.println("this cromson is winner!, gens is " + childChromosomeList.get(counter).getStringAllGens());
            touchWall();
        }
        return neuronNetwork.getActivity(input);
    }

/*
public void changeMarkCurrentChromosome(float mark) {
childChromosomeList.get(counter).addToMark(mark);
}                                      */

    public void sendEvent(int event) {
        if (event == STATE_LEFT_ENGINE_ON) {
            leftEngineOn = true;
            leftEngineActivity++;
        }
        if (event == STATE_RIGHT_ENGINE_ON) {
            rightEngineOn = true;
            rightEngineActivity++;
        }

        if (event == STATE_LIMIT_W) {
            limitW = true;
        }
        if (event == STATE_OVERLOAD_ANGLE) {
            overloadAngle = true;
        }
    }

    public void incDx(float dx) {
        this.dx += Math.abs(dx);
    }

    public void incDy(float dy) {
        this.dy += Math.abs(dy);
    }


    private void clearFlags() {
        leftEngineOn = false;
        rightEngineOn = false;
        limitW = false;
        overloadAngle = false;
        leftEngineActivity = 0;
        rightEngineActivity = 0;
        dx = 0;
        dy = 0;
    }

    public static boolean getRealTime() {
        return realTime;
    }

    public static void changeRealTime() {
        realTime = !realTime;
    }
}
