package domain;
import org.json.JSONObject;


public class Bonus extends Square{

	public Bonus(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(300);
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(250);

	}
	
	public static Bonus fromJSON(JSONObject squareAsJSON) {
		Bonus bonus = null;
		
		try {
			String name = squareAsJSON.getString("name");
			bonus = new Bonus(name);
		} catch (Exception e) {
			
		}
		
		return bonus;
	}
}
