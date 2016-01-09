package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Order {
	
	public Order() {	
	}
	
	public void readFromCommandLine() {
        read(System.in);
    }
	
	
    public ArrayList<Part> readFromFile(String path) {
        File f = new File(path);
        
        ArrayList<Part> returnValues = null;
        
        try {
            FileInputStream fis = new FileInputStream(f);
            returnValues = read(fis);
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return returnValues;
    }
	
	public ArrayList<Part> read(InputStream in) {
		Scanner sc = new Scanner(in);
        int num_elements;
                
        ArrayList<Part> partsList = new ArrayList<>();
         
        /*
        // sheet
        float width =  (float) sc.nextDouble();
        float height = (float) sc.nextDouble();
        */
        
        num_elements = sc.nextInt();
 
        // polygons
        for(int i = 0; i < num_elements; i++){
             
            String name = sc.next();
            int quantity = sc.nextInt();
             
            int num_points = sc.nextInt();
           
            int[] xpoints = new int[num_points];
            int[] ypoints = new int[num_points];
            
            sc.nextLine();
            // points
            for(int j=0; j<num_points; j++) {
            	String edgeInput = sc.nextLine();
            	String[] split = edgeInput.split(" ");
            	
            	xpoints[j] = (int)(100 * Float.parseFloat(split[0]));
            	ypoints[j] = (int)(100 * Float.parseFloat(split[1]));
            }            
             
            for(int q = 0; q < quantity; q++){
            	partsList.add(new Part(name, xpoints, ypoints));
            }
        }
        sc.close();
		
		
		return partsList;
	}
}

