package domain;

import java.util.ArrayList;

import org.json.JSONObject;

public class Cup {
	private static final String[] FIELD_NAMES = new String[]{"die1Value", "die2Value", "speedDieValue"};
	
	private Die die1;
	private Die die2;
	private Die speedDie;
	private ArrayList<int[]> debugValues;
	
	public Cup() {
		setDie1(new Die());
		setDie2(new Die());
		setSpeedDie(new Die());
		setDebugValues(new ArrayList<int[]>());
	}
	
	public Cup(int die1Value, int die2Value, int speedDieValue) {
		setDie1(new Die());
		setDie2(new Die());
		setSpeedDie(new Die());
		setDebugValues(new ArrayList<int[]>());
		
		getDie1().setFaceValue(die1Value);
		getDie2().setFaceValue(die2Value);
		getSpeedDie().setFaceValue(speedDieValue);
	}
	
	public void roll2Dice() {
		Die die1 = getDie1();
		Die die2 = getDie2();
		
		die1.roll();
		die2.roll();
		Die.animate(die1, die2);
	}
	
	public void roll3Dice() {
		Die die1 = getDie1();
		Die die2 = getDie2();
		Die speedDie = getSpeedDie();
		
		ArrayList<int[]> debugValues = getDebugValues();
		int size = debugValues.size();
		
		if (size == 0) {
			die1.roll();
			die2.roll();
			speedDie.roll();
			
			Die.animate(die1, die2, speedDie);
		} else {
			int[] diceValues = debugValues.get(0);
			die1.setFaceValue(diceValues[0]);
			die2.setFaceValue(diceValues[1]);
			speedDie.setFaceValue(diceValues[2]);
			debugValues.remove(0);
		}
	}
	
	public int getDiceValuesTotal() {
		int diceValuesTotal;
		
		if (isMrMonopolyRolled() || isBusRolled()) {
			diceValuesTotal = getDie1().getFaceValue() + getDie2().getFaceValue();
		} else {
			diceValuesTotal = getDie1().getFaceValue() + getDie2().getFaceValue() + getSpeedDie().getFaceValue();
		}
		
		return diceValuesTotal;
	}
	
	public boolean isTripleRolled() {
		boolean isTripleRolled = false;
		
		if (!isMrMonopolyRolled() && !isBusRolled()) {
			if (getDie1().getFaceValue() == getDie2().getFaceValue() && getDie2().getFaceValue() == getSpeedDie().getFaceValue()) {
				isTripleRolled = true;
			}
		}
		
		return isTripleRolled;
	}
	
	public boolean isDoubleRolled() {
		boolean isDoubleRolled = false;
		
		int die1Value = getDie1().getFaceValue();
		int die2Value = getDie2().getFaceValue();
		int speedDieValue = getSpeedDie().getFaceValue();
		
		if (isMrMonopolyRolled() || isBusRolled()) {
			if (die1Value == die2Value) {
				isDoubleRolled = true;
			}
		} else {
			if (!isTripleRolled()) {
				if (die1Value == die2Value || die2Value == speedDieValue || die1Value == speedDieValue) {
					isDoubleRolled = true;
				}
			}
		}
		
		return isDoubleRolled;
	}
	
	public boolean isMrMonopolyRolled() {
		boolean isMrMonopolyRolled = false;
		
		if (getSpeedDie().getFaceValue() == 6) {
			isMrMonopolyRolled = true;
		}
		
		return isMrMonopolyRolled;
	}
	
	public boolean isBusRolled() {
		boolean isBusRolled = false;
		
		if (getSpeedDie().getFaceValue() == 4 || getSpeedDie().getFaceValue() == 5) {
			isBusRolled = true;
		}
		
		return isBusRolled;
	}
	
	public Die getDie1() {
		return die1;
	}

	public void setDie1(Die die1) {
		this.die1 = die1;
	}

	public Die getDie2() {
		return die2;
	}

	public void setDie2(Die die2) {
		this.die2 = die2;
	}

	public Die getSpeedDie() {
		return speedDie;
	}

	public void setSpeedDie(Die speedDie) {
		this.speedDie = speedDie;
	}

	public ArrayList<int[]> getDebugValues() {
		return debugValues;
	}

	public void setDebugValues(ArrayList<int[]> debugValues) {
		this.debugValues = debugValues;
	}
	
	public void putDebugValuesInOrder(int playerNum, int currentPlayerIndex) {
		ArrayList<int[]> debugValues = getDebugValues();
		ArrayList<int[]> newDebugValues = new ArrayList<int[]>();
		
		for (int i = 0; i < playerNum; i++) {
			newDebugValues.add(debugValues.get(currentPlayerIndex));
			currentPlayerIndex = (currentPlayerIndex + 1) % playerNum;
		}
		
		setDebugValues(newDebugValues);
	}
	
	public String toString() {
		return toJSON().toString();
	}
	
	public JSONObject toJSON() {
		JSONObject cupAsJSON = new JSONObject();
		
		try {
			cupAsJSON.put(FIELD_NAMES[0], getDie1().getFaceValue());
			cupAsJSON.put(FIELD_NAMES[1], getDie2().getFaceValue());
			cupAsJSON.put(FIELD_NAMES[2], getSpeedDie().getFaceValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cupAsJSON;
	}
	
	public static Cup fromJSON(JSONObject cupAsJSON) {
		Cup cup = null;
		
		try {
			int die1Value = cupAsJSON.getInt(FIELD_NAMES[0]);
			int die2Value = cupAsJSON.getInt(FIELD_NAMES[1]);
			int speedDieValue = cupAsJSON.getInt(FIELD_NAMES[2]);
			cup = new Cup(die1Value, die2Value, speedDieValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cup;
	}
}