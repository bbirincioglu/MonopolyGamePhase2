package domain;
import org.json.JSONObject;


public class PayDay extends Square {

	public PayDay(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		Player currentPlayer = piece.getOwner();
		Cup cup = GameController.getInstance().getCup();
		int diceValuesTotal = cup.getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			currentPlayer.receivePayment(400);
		} else {
			currentPlayer.receivePayment(300);
		}
	}
	public void passedOn(Piece piece) {
		Player currentPlayer = piece.getOwner();
		Cup cup = GameController.getInstance().getCup();
		int diceValuesTotal = cup.getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			currentPlayer.receivePayment(400);
		} else {
			currentPlayer.receivePayment(300);
		}	
	}
	
	public static PayDay fromJSON(JSONObject squareAsJSON) {
		PayDay payDay = null;
		
		try {
			String name = squareAsJSON.getString("name");
			payDay = new PayDay(name);
		} catch (Exception e) {
			
		}
		
		return payDay;
	}
}
