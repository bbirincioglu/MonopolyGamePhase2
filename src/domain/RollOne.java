package domain;
import org.json.JSONObject;


public class RollOne extends Square{

	public RollOne(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static RollOne fromJSON(JSONObject squareAsJSON) {
		RollOne rollOne = null;
		
		try {
			String name = squareAsJSON.getString("name");
			rollOne = new RollOne(name);
		} catch (Exception e) {
			
		}
		
		return rollOne;
	}
}
