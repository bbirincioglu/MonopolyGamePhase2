package domain;
import org.json.JSONObject;


public class IncomeTax extends Square{

	public IncomeTax(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Bank bank=GameController.getInstance().getMonopolyBoard().getBank();
		Player currentPlayer = piece.getOwner();
		int currentPlayerWealth = currentPlayer.getWealth();
		
		if (currentPlayerWealth / 10 > 200) {
			currentPlayer.makePayment(bank, 200);
		} else {
			currentPlayer.makePayment(bank, currentPlayerWealth / 10);
		}
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static IncomeTax fromJSON(JSONObject squareAsJSON) {
		IncomeTax incomeTax = null;
		
		try {
			String name = squareAsJSON.getString("name");
			incomeTax = new IncomeTax(name);
		} catch (Exception e) {
			
		}
		
		return incomeTax;
	}
}