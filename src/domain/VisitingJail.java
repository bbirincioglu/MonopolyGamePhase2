package domain;
import org.json.JSONObject;

public class VisitingJail extends Square {

	public VisitingJail(String name) {
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
	
	public static VisitingJail fromJSON(JSONObject squareAsJSON) {
		VisitingJail visitingJail = null;
		
		try {
			String name = squareAsJSON.getString("name");
			visitingJail = new VisitingJail(name);
		} catch (Exception e) {
			
		}
		
		return visitingJail;
	}
}