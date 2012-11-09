package logic.entity.ship;

import main.Global;

public class Chromosome {
    public final static float MIN_KOOF = -1;
    public final static float MAX_KOOF = 1;
    //public final static float KOOF_RANDOM = 0.5f;
    public final static int NUMBER_GEN = 48;
    //3+1
    public final static int NUMB_COMPLEX_MARK = 5;
    // n - arnost
    public final static int NUMBER_N = 2;
    public final static float DIFF_MARK = 0.01f;
    private float gens[][] = new float[NUMBER_N][NUMBER_GEN];
    private float complexMark[] = new float[NUMB_COMPLEX_MARK];


    public Chromosome() {
        for (int k = 0; k < NUMBER_N; k++) {
            for (int i = 0; i < NUMBER_GEN; i++) {
                gens[k][i] = MIN_KOOF + Global.random.nextFloat() * (MAX_KOOF - MIN_KOOF);
            }
        }
    }

    public Chromosome(float[][] newBox) {
        for (int k = 0; k < NUMBER_N; k++) {
            for (int i = 0; i < NUMBER_GEN; i++) {
                gens[k][i] = newBox[k][i];
            }
        }
    }

    public Chromosome newChild(Chromosome otherSex) {
        float newBox[][] = new float[NUMBER_N][NUMBER_GEN];
        for (int k = 0; k < NUMBER_N; k++) {
            for (int i = 0; i < NUMBER_GEN; i++) {
                //crossover
                if (Global.random.nextFloat() > 0.5f) {
                    if (gens[k][i] > otherSex.getGen(k, i)) {
                        newBox[k][i] = otherSex.getGen(k, i) + (gens[k][i] - otherSex.getGen(k, i)) * Global.random.nextFloat();
                    } else {
                        newBox[k][i] = gens[k][i] + (otherSex.getGen(k, i) - gens[k][i]) * Global.random.nextFloat();
                    }
                    continue;
                }
                //easy turn gen
                else {
                    if (Global.random.nextFloat() > 0.5f) {
                        newBox[0][i] = this.gens[0][i];
                        newBox[1][i] = this.gens[1][i];
                    } else {
                        newBox[0][i] = otherSex.gens[0][i];
                        newBox[1][i] = otherSex.gens[1][i];
                    }
                }
                //passive gen to active
                if (Global.random.nextFloat() > 0.3) {
                    float t = newBox[1][i];
                    newBox[1][i] = newBox[0][i];
                    newBox[0][i] = t;
                }

                //mutation
                if (Global.random.nextFloat() > 0.3) {
                    newBox[0][i] += (Global.random.nextFloat() - 0.5f);
                    newBox[1][i] += (Global.random.nextFloat() - 0.5f);
                }
            }

        }
        return new Chromosome(newBox);
    }

    public float[] getComplexMark() {
        return complexMark;
    }

    public void setComplexMark(float mark[]) {
        complexMark = mark;
    }

    public float getGen(int branch, int pointer) {
        return gens[branch][pointer];
    }

    public float[] getPrimaryGens() {
        return gens[0];
    }


    public String getComplexMarkToString() {
        return ("main mark = " + complexMark[0] +"\n"+ " timeFly = " + complexMark[1] +"\n"+ " energoCoast = "
                + complexMark[2] +"\n"+ " sopriagenie = " + complexMark[3]+"\n"+ " dxy = " + complexMark[4]+"\n");
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
    }
}
