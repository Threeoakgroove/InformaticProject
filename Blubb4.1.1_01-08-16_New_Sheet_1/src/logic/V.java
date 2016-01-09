package logic;

public class V {
	
	private float x = 0.0f;
	private float y = 0.0f;
	
	public V(float _x, float _y) {
		x = _x;
		y = _y;
	}
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}
	
	private float getLength() {
		return (float) Math.sqrt( (double) Math.pow(x, 2) + (double) Math.pow(y, 2) );
	}
	
	/**
	 * returns the angle (degree) going clockwise the right horizontal line
	 * @return
	 */
	public float getDirection() {
		float dir = (float) Math.acos( x / getLength() );
		dir = (float) Math.toDegrees(dir);
		if( y >= 0) return dir;
		else return 360.0f - dir;
	}
	
	public V add(V v) {
		float rx = x + v.x();
		float ry = y + v.y();
		
		return new V(rx,ry);
	}
	
	public V sub(V v) {
		float rx = x - v.x();
		float ry = y - v.y();
		
		return new V(rx,ry);
	}
	
	public V inverse() {
		return new V(-x,-y);
	}
	
	public float getAngleBetween(V v, boolean clockwise) {
		float a1 = this.getDirection();
		float a2 = v.getDirection();
		
		float result = 0;
		
		if( a1>0 && a2==0 ) result = 360 - a1;
		else result = (360 + (a2 - a1) ) % 360;
		
		if(clockwise) return result;
		else return 360 - result;
	}
	
	public V rotate(int d_angle, boolean clockwise) {
		float dir = getDirection();
		if(!clockwise) d_angle = 360 - d_angle;
		double angle = (dir + d_angle) % 360.0;
		
		angle = Math.toRadians(angle);
		float rx = (float) ( getLength() * Math.cos(angle) );
		float ry = (float) ( getLength() * Math.sin(angle) );
		
		return new V(rx,ry);
	}
	
	@Override
	public String toString() {
		return "[\t"+x+",\t"+y+"]";
	}
	
}
