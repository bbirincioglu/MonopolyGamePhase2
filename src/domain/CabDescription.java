package domain;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class CabDescription {
	private ArrayList<Integer> rents;
	private int mortgageValue;
	
	
	
	public CabDescription(ArrayList<Integer> rents, int mortgageValue) {
		this.rents = rents;
		this.mortgageValue = mortgageValue;
	}
	public ArrayList<Integer> getRents() {
		return rents;
	}
	public void setRents(ArrayList<Integer> rents) {
		this.rents = rents;
	}
	public int getMortgageValue() {
		return mortgageValue;
	}
	public void setMortgageValue(int mortgageValue) {
		this.mortgageValue = mortgageValue;
	}
	public static CabDescription fromJSON(JSONObject descriptionAsJSON){
		String stringRents=null;
		int mortgageValue=0;
		try {
			stringRents = descriptionAsJSON.getString("rents");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] arr=stringRents.split(",");
		ArrayList<Integer> rents=new ArrayList<Integer>();
		for(int i=0; i<arr.length; i++){
			rents.add(Integer.parseInt(arr[i]));
		}
		try {
			int morgageValue=(descriptionAsJSON.getInt("mortgageValue"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CabDescription desc=new CabDescription(rents, mortgageValue);
		return desc;
	}
	

}
