package ga;

import java.util.ArrayList;

import logic.Part;

public class Sorter {

	public Sorter() {
		
	}
	
	/**
	 * searches for the best free right angle (edit: only has to search through distinct parts)
	 * puts corresponding part on front of the list with the right rotation
	 * @param parts
	 */
	public ArrayList<Part> findFirstPart(ArrayList<Part> parts) {
		
		// finding best angle and saving index
		float max = 0;
		int bestIndex = 0;
		float tmp = 0;
		
		Part p = null;
		for(int i=0; i<parts.size(); i++) {
			p = parts.get(i);
			if((tmp = p.bestRightAngle()) > max) {
				
				max = tmp;
				bestIndex = i;
			}
			System.out.println("best angle #"+i+" "+tmp);
		}
		
		//rotate... 
		Part bestPart = parts.get(bestIndex);
		
		//put on front of list
		parts.remove(bestIndex);
		parts.add(0, bestPart);
		
		return parts;
	}
	
	//sort should sort parts depending on size, complexity, random / as they come
	
}
