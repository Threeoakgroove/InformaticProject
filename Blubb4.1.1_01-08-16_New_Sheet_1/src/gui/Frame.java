package gui;

import ga.Sorter;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import logic.Order;
import logic.Part;
import logic.Sheet;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private static SolutionPanel solPanel;
	
	public Frame(GUISheet gs) {
		super("Testing");
		
		solPanel = new SolutionPanel(gs);
		this.add(solPanel, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Sheet.width + 30, Sheet.height + 100);
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		String source = "C:\\Users\\schaefer\\workspace1\\Blubb4.1_01-08-04_solvedCalculationDelay\\Blubb4.1_01-08-04_solvedCalculationDelay\\input2.txt";
		
		Sheet sheet = new Sheet(400.0f,200.0f);
		
		Frame f = new Frame(new GUISheet(sheet));
		f.setVisible(true);
		
		Order order = new Order();
		ArrayList<Part> parts = order.readFromFile(source);
		
		
		
		
		ArrayList<Part> notAddedParts;
		
		// find new sheet
		do{
			notAddedParts = new ArrayList<Part>();
			for(int i = 0; i < parts.size(); i++) {
				if(!sheet.addPart(parts.get(i))){
					notAddedParts.add(parts.get(i));
				}
			}
			
			if(!notAddedParts.isEmpty()){
				parts = notAddedParts;
				for(int i = 0; i < parts.size(); i++){
					parts.get(i).movable = true;
				}
				sheet = new Sheet();
				solPanel.addSheet(new GUISheet(sheet));
			}
			
		} while(!notAddedParts.isEmpty());
		
		
	}
}
