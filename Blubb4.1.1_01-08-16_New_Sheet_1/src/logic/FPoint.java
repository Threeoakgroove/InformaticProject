package logic;

public class FPoint {
	
	public float x = 0.0f;
	public float y = 0.0f;
	
	public FPoint(float _x, float _y) {
		x = _x;
		y = _y;
	}
	
	public static float min(float f1, float f2) {
		if(f1 < f2) return f1;
		else return f2;
	}
	
	public static float max(float f1, float f2) {
		if(f1 > f2) return f1;
		else return f2;
	}
	
	public static int i(float f) {
		return (int) Math.rint(f);
	}
	
	public static int[] toIntArray(float[] f) {
		int[] result = new int[f.length];
		for(int i=0; i<f.length; i++) {
			result[i] = FPoint.i(f[i]);
		}
		return result;
	}
}
