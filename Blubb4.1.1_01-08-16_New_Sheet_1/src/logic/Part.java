package logic;

import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class Part extends Polygon {
	
	public int n = npoints;
	public String name = "";
	
	private V loc = new V(Sheet.w / 2 , Sheet.h / 2);
	private V v0;
	
	private V[] edges;
	private V[] nodes;
	
	public boolean movable = true;
	public boolean fits = false;
	public boolean added = false;
	
	public Part() {
		
	}
	
	public Part(String s, int[] xp, int[] yp) {
		super(xp,yp,xp.length);
		name = s;
		n = npoints;
		V[] _nodes = new V[n];
		for(int i=0; i<n; i++) {
			_nodes[i] = new V((float)xpoints[i],(float)ypoints[i]);
		}
		nodes = _nodes;
		fillEdges();
		loc = getCenter(); //v0
	}
	
	public Part(String s, V ... _nodes){
		name = s;
		nodes = _nodes;
		fillEdges();
		loc = getCenter(); //v0
	}
	
	/*public Part(Part original) {
		super(original.xpoints,original.ypoints,original.npoints);
		n = original.n;
		name = original.name;
		loc = original.loc;
		v0 = original.v0;
		edges = original.edges;
		nodes = original.nodes;
		movable = original.movable;
		fits = original.fits;
		added = original.added;
		checked = original.checked;
	}*/
	
	//------------------------------------------------------------------------
	//-------getters-----&---setters------------------------------------------
	//------------------------------------------------------------------------
	
	public V loc(){ return loc; }
	public void setLoc(V position){ loc = position; fillNodes(); }
	public V v0() { return v0; }
	public V calcV0() { return nodes[0].sub(getCenter()); }
	public V[] edges() { return edges; }
	public V[] nodes() { return nodes; }
	
	public float width() { return getMaximumX() - getMinimumX();	}
	public float height() { return getMaximumY() - getMinimumY(); }
	
	/*private float getLeftDistanceToCenter() {
		return getLoc().getX() - getMinimumX();
	}*/
	
	public float getArea() {
		float sum = 0;
		for(int i=0; i<n; i++) {
			if(i==n-1) sum += (nodes[0].y() + nodes[i].y() )
						* ( nodes[0].x() - nodes[i].x());
			else sum += (nodes[i+1].y() + nodes[i].y() )* ( nodes[i+1].x() - nodes[i].x());
		}
		return Math.abs(sum/2);
	}
	
	//------------------------------------------------------------------------
	//-------actual--------functions------------------------------------------
	//------------------------------------------------------------------------
	
	private boolean checked = false;
	public boolean rotate(int degree, boolean clockwise) {
		v0 = v0.rotate(degree,clockwise);
		for(int i=0; i<n; i++) {
			edges[i] = edges[i].rotate(degree,clockwise);
		}
		
		V[] newNodes = fillNodes();
		if(checked) {
			checked = false;
			return false;
		}
		else if(areOnSheet(newNodes)) {
			return true;
		}
		else {
			checked = true;
			rotate(degree,!clockwise);
			return false;
		}
	}
	
	public boolean moveTo(float xpos, float ypos) {
		float xshift = xpos - getMinimumX();
		float yshift = ypos - getMinimumY();
		return shift(xshift,yshift);
	}
	
	public boolean shift(float x, float y) {
		V shift = new V(x,y);
		loc = loc.add(shift);
		
		V[] newNodes = new V[n];
		for(int i=0; i<n; i++) {
			newNodes[i] = nodes[i].add(shift);
		}
		
		if(areOnSheet(newNodes)) {
			fillNodes();
			return true;
		}
		else {
			loc = loc.sub(shift);
			return false;
		}
		
	}
	
	public boolean intersects(Part p) {
		if(this.equals(p)) {
			//System.out.println("poly doesnt intersect with itself");
			return false;
		}
		else { // polygons are different from each other
			Area thisArea = new Area(this);
			Area otherArea = new Area(p);
			
			thisArea.intersect(otherArea);
			
			if(thisArea.isEmpty()) {
				//System.out.println("poly should fit");
				return false;
			}
			else {
				//System.out.println("polys intersect");
				return true;
			}
		}
	}
	
	public void print() {
		System.out.println("------------------------");
		System.out.println("loc: "+loc.toString());
		System.out.println("v0: "+v0.toString());
		for(int i=0; i<n; i++) {
			System.out.println((i+1)+" "+nodes[i].toString());
		}
	}
	
	//------------------------------------------------------------------------
	//-------private-------functions------------------------------------------
	//------------------------------------------------------------------------
	
	private boolean areOnSheet(V[] testnodes) {
		for(int i=0; i<n; i++) {
			V t = testnodes[i];
			if(t.x() < 0 || t.x() > Sheet.width ||
					t.y() < 0 || t.y() > Sheet.height)
				return false;
		}
		return true;
	}
	
	private V[] fillNodes() {
		nodes[0] = loc.add(v0);

		for(int i=1; i<n; i++) {
			nodes[i] = nodes[i-1].add(edges[i-1]);
		}
		
		for(int i=0; i<n; i++) {
			xpoints[i] = FPoint.i(nodes[i].x());
			ypoints[i] = FPoint.i(nodes[i].y());
		}
		
		return nodes;
	}
	
	private void fillEdges() {
		edges = new V[n];
		for(int i=0; i<n; i++) {
			if(i==n-1) edges[i] = nodes[0].sub(nodes[i]);
			else edges[i] = nodes[i+1].sub(nodes[i]);
		}
	}
	
	private V getCenter() {
		float x_center = 0;
		float y_center = 0;
		
		float[] x = new float[n];
		float[] y = new float[n];
		for(int i=0; i<n; i++) {
			x[i] = nodes[i].x();
			y[i] = nodes[i].y();
		}
		
		for(int i=0; i<n; i++) {
			if(i==n-1) {
				x_center += ( x[i] + x[0] ) * (x[i] * y[0] - x[0] * y[i] );
			} else x_center += ( x[i] + x[i+1] ) * (x[i] * y[i+1] - x[i+1] * y[i] );
		}
		
		for(int i=0; i<n; i++) {
			if(i==n-1) {
				y_center += ( y[i] + y[0] ) * (x[i] * y[0] - x[0] * y[i] );
			} else y_center += ( y[i] + y[i+1] ) * (x[i] * y[i+1] - x[i+1] * y[i] );
		}
		
		double tmp = 1 / ( 6 * getArea() );
		x_center *= tmp;
		y_center *= tmp;
		
		v0 = nodes[0].sub(new V(x_center,y_center));
		
		return new V(x_center,y_center);
	}
	
	private float getMaximumX() {
		float max = 0;
		float value = 0;
		for(int i=0; i<n; i++) {
			value = nodes[i].x();
			if(value>max) max = value;
		}
		return max;
	}
	
	private float getMinimumX() {
		float min = Sheet.w;
		float value = 0;
		for(int i=0; i<n; i++) {
			value = nodes[i].x();
			if(value<min) min = value;
		}
		return min;
	}
	
	private float getMaximumY() {
		float max = 0;
		float value = 0;
		for(int i=0; i<n; i++) {
			value = nodes[i].y();
			if(value>max) max = value;
		}
		return max;
	}
	
	private float getMinimumY() {
		float min = Sheet.h;
		float value = 0;
		for(int i=0; i<n; i++) {
			value = nodes[i].y();
			if(value<min) min = value;
		}
		return min;
	}
	
	/** TODO
	 * coverting functions to convert from nodes2points or points2nodes
	 * Part.xpoints(nodes);
	 * Part.ypoints(nodes);
	 * Part.nodes(xpoints,ypoints);
	 */
	//public static int[] ...
	
	private final float rightAngleError = 20.0f; // degree
	public Float bestRightAngle() {
		HashMap<Integer, Float> angles = angles();
		
		boolean rightAngleExists = false;
		
		float result = 0;
		float error = rightAngleError;
		float tmp = 0;
		int index = angles.size(); // error source, but used to check whether there was no right angle
		for(Float f : angles.values()) {
			System.out.println("\t found angle: "+f);
			if(! ((90 - rightAngleError) < f && f < (90 + rightAngleError))) {
				if( (tmp = Math.abs(f - 90)) < error) {
					error = tmp;
					result = f;
					for (Entry<Integer, Float> e : angles.entrySet()) {
						if (e.getValue().equals(f)) { // error source (might not be truly equal -> calc)
							index = e.getKey();
			            }
					}
					rightAngleExists = true;
				}
			}
		}
		
		//if(rightAngleExists) return angles.get(index);
		if(rightAngleExists) return result;
		else return 0f;
	}
	
	private HashMap<Integer, Float> angles() {
		HashMap<Integer, Float> result = new HashMap<Integer, Float>();
		
		result.put(0, edges[0].getAngleBetween(edges[n-1].inverse(), true));
		for(int i=1; i<n; i++) {
			result.put(0, edges[i].getAngleBetween(edges[i-1].inverse(), true));
		}
		return result;
	}
}