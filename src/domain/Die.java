package domain;
import java.util.ArrayList;
import java.util.Random;


public class Die {
	private ArrayList<DieObserver> dieObservers;
	private int[] randomValues;
	private int faceValue;
	
	/**
	 * Constructor for Die
	 */
	
	public Die() {
		//@effects: Initializes the die.
		
		this.randomValues = new int[5];
		this.faceValue = 1;
		this.dieObservers = new ArrayList<DieObserver>();
	}
	
	public void roll() {
		//@modifies: this
		//@effects: rolls the die and produces random values between 1 and 6.
		
		Random random = new Random();
		
		for (int i = 0; i < getRandomValues().length; i++) {
			getRandomValues()[i] = 1 + random.nextInt(6);
		}
	}
	
	public void notifyDieObservers() {
		//@requires: dieObservers != null
		//@effects: notifies the observers of the die.
		
		ArrayList<DieObserver> dieObservers = getDieObservers();
		
		for (int i = 0; i < dieObservers.size(); i++) {
			dieObservers.get(i).update(getFaceValue());
		}
	}
	
	public static void animate(Die die1, Die die2, Die speedDie) {
		//@requires: die1, die2 and speedDie are not null
		//@modifies: die1, die2, speedDie
		//@effects: animates the rolling of the die1, die2 and speedDie
		
		int[] randomValues1 = die1.getRandomValues();
		int[] randomValues2 = die2.getRandomValues();
		int[] randomValues3 = speedDie.getRandomValues();
		
		for (int i = 0; i < die1.getRandomValues().length; i++) {
			int randomValue1 = randomValues1[i];
			int randomValue2 = randomValues2[i];
			int randomValue3 = randomValues3[i];
			die1.setFaceValue(randomValue1);
			die2.setFaceValue(randomValue2);
			speedDie.setFaceValue(randomValue3);
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void animate(Die die1, Die die2) {
		//@requires: die1, die2 is not null
		//@modifies: die1, die2
		//@effects: animates the rolling of the die1, die2
		
		int[] randomValues1 = die1.getRandomValues();
		int[] randomValues2 = die2.getRandomValues();
		
		for (int i = 0; i < die1.getRandomValues().length; i++) {
			int randomValue1 = randomValues1[i];
			int randomValue2 = randomValues2[i];
			die1.setFaceValue(randomValue1);
			die2.setFaceValue(randomValue2);
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addDieObserver(DieObserver dieObserver) {
		//@requires: dieObserver is not null
		//@modifies: this
		//@effects: adds dieObserver to the observers of the die
		
		getDieObservers().add(dieObserver);
	}
	
	public void setFaceValue(int faceValue) {
		//@requires: 1<=faceValue<=6
		//@modifies: this
		//@effects: sets the face value of die
		
		this.faceValue = faceValue;
		notifyDieObservers();
	}
	
	public int getFaceValue() {
		//@effects: gets the face value of die
		
		return faceValue;
	}
	
	public ArrayList<DieObserver> getDieObservers() {
		//@effects: gets the observers of the die
		
		return dieObservers;
	}

	public void setDieObservers(ArrayList<DieObserver> dieObservers) {
		//@modifies: this
		//@effects: sets the observers of the die as given input
		
		this.dieObservers = dieObservers;
	}

	public void setRandomValues(int[] randomValues) {
		//@requires: randomValues is not null
		//@modifies: this
		//@effects: sets the random values of die as given input
		
		this.randomValues = randomValues;
	}
	
	public int[] getRandomValues() {
		//@effects: gets the random values of die
		
		return randomValues;
	}
}
