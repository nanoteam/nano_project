package ai.nnga;

import util.MathUtil;

class Chromosome {
    public final static float MIN_KOOF = -3;
    public final static float MAX_KOOF = 3;
    //public final static float KOOF_RANDOM = 0.5f;
    public final static int NUMBER_GEN = 48;
    //3+1
    public final static int NUMBER_COMPLEX_MARK = 6 + 1;
    public final static int NUMBER_TEST = 3;
    // n - arnost
    public final static int NUMBER_N = 2;
    public final static float DIFF_MARK = 0.01f;
    private float gens[][] = new float[NUMBER_N][NUMBER_GEN];
    private float complexMark[][] = new float[NUMBER_TEST][NUMBER_COMPLEX_MARK];


    public Chromosome() {
        for (int k = 0; k < NUMBER_N; k++) {
            for (int i = 0; i < NUMBER_GEN; i++) {
                gens[k][i] = MIN_KOOF + MathUtil.random.nextFloat() * (MAX_KOOF - MIN_KOOF);
            }
        }
        complexMark = new float[NUMBER_TEST][NUMBER_COMPLEX_MARK];
    }

    public Chromosome(float[][] newBox) {
        for (int k = 0; k < NUMBER_N; k++) {
            for (int i = 0; i < NUMBER_GEN; i++) {
                gens[k][i] = newBox[k][i];
            }
        }
    }

    public Chromosome(Chromosome chromosome) {
        this(chromosome.gens);
        complexMark = chromosome.getComplexMark().clone();
    }

    public static Chromosome newChild(Chromosome parents1, Chromosome parents2) {
        float newBox[][] = new float[NUMBER_N][NUMBER_GEN];
        for (int k = 0; k < NUMBER_N; k++) {
            for (int i = 0; i < NUMBER_GEN; i++) {
                //crossover
                if (MathUtil.random.nextFloat() > 0.5f) {
                    if (parents1.getGen(k, i) > parents2.gens[k][i]) {
                        newBox[k][i] = parents2.gens[k][i] + (parents1.gens[k][i] - parents2.gens[k][i]) * MathUtil.random.nextFloat();
                    } else {
                        newBox[k][i] = parents1.gens[k][i] + (parents2.gens[k][i] - parents1.gens[k][i]) * MathUtil.random.nextFloat();
                    }
                    continue;
                }
                //easy turn gen
                else {
                    if (MathUtil.random.nextFloat() > 0.5f) {
                        newBox[0][i] = parents1.gens[0][i];
                        newBox[1][i] = parents1.gens[1][i];
                    } else {
                        newBox[0][i] = parents2.gens[0][i];
                        newBox[1][i] = parents2.gens[1][i];
                    }
                }
                //passive gen to active
                if (MathUtil.random.nextFloat() > 0.3) {
                    float t = newBox[1][i];
                    newBox[1][i] = newBox[0][i];
                    newBox[0][i] = t;
                }

                //mutation
                if (MathUtil.random.nextFloat() > 0.3) {
                    newBox[0][i] += (MathUtil.random.nextFloat() - 0.5f);
                    newBox[1][i] += (MathUtil.random.nextFloat() - 0.5f);
                }
            }

        }
        return new Chromosome(newBox);
    }

    public float[][] getComplexMark() {
        return complexMark;
    }

    public void setComplexMark(float mark[][]) {
        complexMark = mark;
    }

    public void addToComplexMark(int row, float mark[]) {
        for (int i = 0; i < NUMBER_COMPLEX_MARK; i++) {
            complexMark[row][i] += mark[i];
        }
    }

    public float getGen(int branch, int pointer) {
        return gens[branch][pointer];
    }

    public float[] getPrimaryGens() {
        return gens[0];
    }


    public String getComplexMarkToString() {
        return ("test1 main mark = " + complexMark[0][0] + " " + complexMark[1][0] + " " + complexMark[2][0] + "\n" +
                " timeFly = " + complexMark[0][1] + " " + complexMark[1][1] + " " + complexMark[2][1] + "\n" +
                " energoCoast = " + complexMark[0][2] + " " + complexMark[1][2] + " " + complexMark[2][2] + "\n" +
                " sopriagenie = " + complexMark[0][3] + " " + complexMark[1][3] + " " + complexMark[2][3] + "\n" +
                " dxy = " + complexMark[0][4] + " " + complexMark[1][4] + " " + complexMark[2][4] + "\n" +
                " warningAngle = " + complexMark[0][5] + " " + complexMark[1][5] + " " + complexMark[2][5] + "\n" + " " +
                "rotateAngle = " + complexMark[0][6] + " " + complexMark[1][6] + " " + complexMark[2][6] + "\n");
    }


    public String getStringAllGens() {
        StringBuffer output = new StringBuffer();
        output.append(getComplexMarkToString());
        for (int i = 0; i < NUMBER_N; i++) {
            for (int k = 0; k < NUMBER_GEN; k++) {
                output.append(gens[i][k] + "f,");
            }
            output.append(" || ");
        }
        return output.toString();
    }


    public void destroy() {
        gens = null;
        complexMark = null;
    }
}
