package domain;
import org.json.JSONObject;

public class BusTicket extends Square {

	public BusTicket(String name) {
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
	
	public static BusTicket fromJSON(JSONObject squareAsJSON) {
		BusTicket busTicket = null;
		
		try {
			String name = squareAsJSON.getString("name");
			busTicket = new BusTicket(name);
		} catch (Exception e) {
			
		}
		
		return busTicket;
	}
}
