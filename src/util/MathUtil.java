package util;

public class MathUtil {
	public static float newXTurn(float x, float y, float alfa) {
		return (float) (x * Math.cos(alfa / 60f) - y * Math.sin(alfa / 60f));
	}

	public static float newYTurn(float x, float y, float alfa) {
		return (float) (x * Math.sin(alfa / 60f) + y * Math.cos(alfa / 60f));
	}

}
