package ai.nnga;

class NeuronNetwork extends Object{
    private static float T_CONST = 1f;
    private float w[][][];
    public void init(int box[], float[] wInput) {
        w = new float[box.length - 1][][];
        //last layer R neuron - output.
        for (int i = 0; i < w.length; i++) {
            if (i != w.length) {
                w[i] = new float[box[i]][box[i + 1]];
            }
        }
        int counter = 0;

        //layer
        for (int i = 0; i < w.length; i++) {
            //neuron
            for (int k = 0; k < w[i].length; k++) {
                //this neuron - next neuron d
                for (int d = 0; d < w[i][k].length; d++) {
                    w[i][k][d] = wInput[counter];
                    counter++;
                }
            }
        }
    }


    public float[] getActivity(float input[]) {
        float prevS[] = new float[w[0].length];

        for (int i = 0; i < prevS.length; i++) {
            prevS[i] = (float) (1f / (1f + Math.exp(-input[i] * T_CONST)));
        }

        float nextS[] = null;
        //layer
        for (int i = 0; i < w.length; i++) {

            //last hide layer
            if (i + 1 == w.length) {
                nextS = new float[w[0][0].length];
            } else {
                nextS = new float[w[i + 1][0].length];
            }
            //neuron
            for (int k = 0; k < w[i].length; k++) {
                //this neuron - next neuron d
                for (int d = 0; d < nextS.length; d++) {
                    nextS[d] += prevS[k] * w[i][k][d];
                }
            }
            if (i + 1 != w.length) {
                for (int z = 0; z < nextS.length; z++) {
                    nextS[z] = (float) (1f / (1f + Math.exp(-nextS[z] * T_CONST)));
                }
            }
            prevS = nextS;
        }
        input = null;
        return nextS;
    }
}
