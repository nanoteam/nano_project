package util;

import java.util.Random;

public class MathUtil {
    public static float newXTurn(float x, float y, float alfa) {
        return (float) (x * Math.cos(alfa) - y * Math.sin(alfa));
    }

    public static float newYTurn(float x, float y, float alfa) {
        return (float) (x * Math.sin(alfa) + y * Math.cos(alfa));
    }

    public static Random random = new Random();
}
