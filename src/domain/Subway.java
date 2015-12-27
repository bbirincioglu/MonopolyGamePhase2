package domain;

import org.json.JSONObject;

public class Subway extends Square {

	public Subway(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public static Subway fromJSON(JSONObject squareAsJSON) {
		Subway subway = null;
		
		try {
			String name = squareAsJSON.getString("name");
			subway = new Subway(name);
		} catch (Exception e) {
			
		}
		
		return subway;
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
}
