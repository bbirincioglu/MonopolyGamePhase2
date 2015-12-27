package domain;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


public  class UtilityDescription {

private ArrayList<Integer> multiplicators;
private int mortgageValue;


public UtilityDescription(){
	this.multiplicators=new ArrayList<Integer>();
}

public  ArrayList<Integer> getMultiplicators() {
	return multiplicators;
}
public  void setMultiplicators(ArrayList<Integer> multiplicators) {
	this.multiplicators = multiplicators;
}
public int getMortgageValue() {
	return mortgageValue;
}
public void setMortgageValue(int mortgageValue) {
	this.mortgageValue = mortgageValue;
}

public UtilityDescription(ArrayList<Integer> multiplicators, int mortgageValue) {
	super();
	this.multiplicators = multiplicators;
	this.mortgageValue = mortgageValue;
}

public static UtilityDescription fromJSON(JSONObject descriptionAsJSON){
	String stringMultiplicators=null;
	ArrayList<Integer> multiplicators=new ArrayList<Integer>();
	int mortgageValue=0;
	UtilityDescription desc=null; 
	try {
		stringMultiplicators = descriptionAsJSON.getString("multiplicators");
	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	String[] arr=stringMultiplicators.split(",");
	
	
	for(int i=0; i<arr.length; i++){
		multiplicators.add(Integer.parseInt(arr[i]));
	}
	try {
		mortgageValue=descriptionAsJSON.getInt("mortgageValue");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	desc=new UtilityDescription(multiplicators, mortgageValue);
	return desc;
}
}
