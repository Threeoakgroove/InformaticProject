package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.Timer;

import logic.Part;
import logic.Sheet;

@SuppressWarnings("serial")
public class GUISheet extends JComponent implements ActionListener {
	
	public final Timer timer = new Timer(1, this);
	
	private Sheet s;
	
	public GUISheet(Sheet _s) {
		s = _s;
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		//SHEET
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Sheet.width, Sheet.height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, Sheet.width, Sheet.height);
		
		//POLYS
		ArrayList<Part> list = s.parts();
		GUIPart[] parts = new GUIPart[list.size()];
		for(int i=0; i<parts.length; i++) {
			parts[i] = new GUIPart(list.get(i),g);
		}
	}
	
}
