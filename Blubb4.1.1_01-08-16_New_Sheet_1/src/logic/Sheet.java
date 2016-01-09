package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;


public class Sheet implements ActionListener {
	
	public static float w;
	public static float h;
	public static int width = FPoint.i(w);
	public static int height = FPoint.i(h);
	
	private ArrayList<Part> parts = new ArrayList<Part>();
	
	public Sheet() {
		
	}
	
	public Sheet(float _w, float _h) {
		w = _w;
		h = _h;
		width = FPoint.i(_w);
		height = FPoint.i(_h);
	}
	
	public ArrayList<Part> parts() { return parts; } 
	
	public boolean addPart(Part p) {
		p.moveTo(0,0);
		
		//should be added at *
		parts.add(p);
		
		while(p.fits == false) {
			if(polyFits(p)) {
				p.fits = true;
				p.added = true; //TODO comment out for green and red
			}
			else if(p.movable) {
				// do some moving action
				Random r = new Random();
				if(!p.shift(1,0)) p.movable = false;
				else {
					p.shift(-1, 0);
					p.shift(1, 0);
					//p.shift(r.nextInt(Sheet.width) - Sheet.width/2,r.nextInt(2*Sheet.height) - Sheet.height/2);
					//p.rotate(r.nextInt(360)+1, false);
					p.rotate(1, false);
					//if(!p.shift(0, 1)) if(!p.shift(1, 0));
					
				}
			}
			calculationDelay.start();
			while(calculationDelay.isRunning()) { } 
		}
		
		if(p.added) {
			//*
			//parts.add(p);
			return true;
		}
		else {
			p = new Part();
			return false;
		}
	}
	
	public boolean polyFits(Part p) {
		for (Part sp : parts) {
			if(sp.intersects(p)) return false;
		}
		return true;
	}

	
	private Timer calculationDelay = new Timer(5,this);
	/**
	 * The calculationDelay timer decides how long to wait until the next movement is calculated.
	 * It should be set to a much longer delay than the timer of the GUISheet that draws the parts to be able to watch.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		calculationDelay.stop();
	}
	
	/*private final float epsilon = 5;
	private boolean areEqual(float a, float b) {
		if(Math.abs(a-b) < epsilon) return true;
		else return false;
	}*/
}
