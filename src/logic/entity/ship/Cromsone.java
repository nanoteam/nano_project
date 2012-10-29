package logic.entity.ship;
import java.util.Random;
public class Cromsone {
    public final static float MIN_KOOF = 0;
    public final static float MAX_KOOF = 5;
    public final static float KOOF_RANDOM = 0.5f;

    public final static int numberGen = 7;
    private float box[] = new float[numberGen];

    private static Random random = new Random();


    private float mark = -1;

    public Cromsone() {
        for (int i = 0; i < box.length; i++) {
            box[i] = random.nextFloat();
        }
    }

    public Cromsone(Cromsone cromsone) {
        for (int i = 0; i < box.length; i++) {
            this.box[i] = box[i];
        }
    }

    public Cromsone(float[] newBox) {
        box = newBox;
    }

    public float[] checkActivity(float args[]) {
        float[] output = new float[2];
        float activity = 0;
        for (int i = 0; i < box.length; i++) {
            activity += args[i] * box[i];
        }
        output[0] = activity;
        activity = 0;
        for (int i = 0; i < box.length; i++) {
            activity += args[i] * box[i];
        }
        output[1] = activity;
        return output;
    }

    public void randomize(){
        for (int i = 0; i < box.length; i++) {
            box[i]=random.nextFloat()*KOOF_RANDOM;
        }
    }
    public Cromsone newChild(Cromsone otherSex){
        float newBox[] = new float[numberGen];
        for (int i = 0; i < box.length; i++) {
            if (random.nextFloat()>0.5){
                newBox[i] = this.box[i];
            }
            else{
                newBox[i] = otherSex.box[i];
            }
        }
        return new Cromsone(newBox);
    }
    @Override
    public String toString(){
        String output =new String();
        for (int i = 0; i < box.length; i++) {
            output+=box[i] + ",";
        }
        return output;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
