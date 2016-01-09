package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logic.Sheet;

@SuppressWarnings("serial")
public class SolutionPanel extends JPanel {
	
	private JTabbedPane sheetPane;
	private int sheetCounter = 0;
	
	private ArrayList<GUISheet> sheets = new ArrayList<GUISheet>();
	
	public SolutionPanel(GUISheet s) {
		this.setLayout(new BorderLayout());	
		sheetPane = new JTabbedPane();		
		this.add(sheetPane, BorderLayout.CENTER);
		addSheet(s);
	}
	
	public void addSheet(GUISheet s) {		
		sheetCounter++;
		sheetPane.addTab("Blech "+sheetCounter,s);
		sheets.add(s);
		//Switch to the new Panel by default
		sheetPane.setSelectedIndex(sheetCounter-1);
		System.out.println("#sheets: "+sheetCounter);
	}
}
