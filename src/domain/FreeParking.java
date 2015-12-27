package domain;
import org.json.JSONObject;


public class FreeParking extends Square{

	public FreeParking(String name) {
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
	
	public static FreeParking fromJSON(JSONObject squareAsJSON) {
		FreeParking freeParking = null;
		
		try {
			String name = squareAsJSON.getString("name");
			freeParking = new FreeParking(name);
		} catch (Exception e) {
			
		}
		
		return freeParking;
	}
}