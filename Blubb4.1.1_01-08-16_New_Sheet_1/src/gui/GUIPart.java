package gui;

import java.awt.Color;
import java.awt.Graphics;

import logic.Part;
import logic.V;

public class GUIPart {
	
	private Part p;
	
	public GUIPart(Part _p, Graphics g) {
		p = _p;
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(p.added) {
			g.setColor(Color.MAGENTA);
			drawUsingVectors(g);
			
			g.setColor(Color.ORANGE);
			drawUsingNodes(g);
	
			g.setColor(Color.BLUE);
			g.drawPolygon(p.xpoints, p.ypoints, p.npoints);
		}
		else {
			if(p.fits) g.setColor(Color.GREEN);
			else g.setColor(Color.RED);
			
			if(p.movable) g.drawPolygon(p.xpoints,p.ypoints,p.npoints);
		}
	}
	
	private void drawUsingVectors(Graphics g) {
		V start = p.calcV0();
		V[] v = p.edges();
		float lx = p.loc().x();
		float ly = p.loc().y();
		
		// line from center to first point
		g.drawLine(i(lx), i(ly), i((lx) += i(start.x())), i((ly) += i(start.y())));
		
		for(int i=0; i<p.n; i++) {
			g.drawLine(i(lx), i(ly), i((lx) += i(v[i].x())), i((ly) += i(v[i].y())));
		}
	}
	
	private void drawUsingNodes(Graphics g) {
		V[] n = p.nodes();
		int[] x = new int[p.n];
		int[] y = new int[p.n];
		
		for(int i=0; i<p.n; i++) {
			x[i] = i(n[i].x());
			y[i] = i(n[i].y());
		}
		g.drawPolygon(x, y, p.n);
	}
		
	private static int i(float f) {
		return (int) Math.rint(f);
	}
	
}
