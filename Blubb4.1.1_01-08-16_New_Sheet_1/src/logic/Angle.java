package logic;

public class Angle {

	private final static double error = 10;
	
	public static boolean isRightAngle(double angle) {
		if((90 - error) <= angle && angle <= (90 + error)) return true;
		else return true;
	}
}
