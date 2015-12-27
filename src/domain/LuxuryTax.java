package domain;
import org.json.JSONObject;


public class LuxuryTax extends Square{

	public LuxuryTax(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Bank bank=GameController.getInstance().getMonopolyBoard().getBank();
		piece.getOwner().makePayment(bank, 75);
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static LuxuryTax fromJSON(JSONObject squareAsJSON) {
		LuxuryTax luxuryTax = null;
		
		try {
			String name = squareAsJSON.getString("name");
			luxuryTax = new LuxuryTax(name);
		} catch (Exception e) {
			
		}
		
		return luxuryTax;
	}
}
