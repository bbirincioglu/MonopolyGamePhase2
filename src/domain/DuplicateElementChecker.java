package domain;

import java.util.ArrayList;

public class DuplicateElementChecker {
	public boolean hasDuplicateElements(ArrayList elements) {
		boolean control = false;
		
		int size = elements.size();
		
		for (int i = 0; i < size - 1; i++)  {
			Object currentElement = elements.get(i);
			
			for (int j = i + 1; j < size; j++) {
				if (currentElement.equals(elements.get(j))) {
					control = true;
					break;
				}
			}
			
			if (control) {
				break;
			}
		}
		
		return control;
	}
}
