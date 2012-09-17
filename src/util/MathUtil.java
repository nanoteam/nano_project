package util;

public class MathUtil {
	public static float newXTurn(float x, float y, float alfa) {
		return (float) (x * Math.cos(alfa) - y * Math.sin(alfa));
	}

	public static float newYTurn(float x, float y, float alfa) {
		return (float) (x * Math.sin(alfa) + y * Math.cos(alfa));
	}

}
