package domain;

import org.json.JSONObject;
/**
 * 
 * @author Ali Furkan
 * Subway square class
 */
public class Subway extends Square {
	/**
	 * Constructor with name
	 * @param name
	 */
	public Subway(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Create subway object from json format
	 * @param squareAsJSON
	 * @return
	 */
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
