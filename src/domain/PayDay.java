package domain;
import org.json.JSONObject;

/**
 * 
 * @author Ali Furkan
 *	Payday square
 */
public class PayDay extends Square {
	/**
	 * Constructor with name
	 * @param name
	 */
	public PayDay(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/** 
	 * Owner gets 400 if dice total is  even
	 * it gets 300 if its odd
	 * @effects gives 400 or 300 to owner
	 * @requires piece is not null
	 * @param piece
	 * 
	 * @see domain.Square#landedOn(domain.Piece)
	 */
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
	/** 
	 * Owner gets 400 if dice total is  even
	 * it gets 300 if its odd
	 * @effects gives 400 or 300 to owner
	 * @requires piece is not null
	 * @param piece
	 * 
	 * @see domain.Square#passedOn(domain.Piece)
	 */
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
	/**
	 * Creates payday object from json format
	 * @requires squareAsJSON is not null
	 * @param squareAsJSON
	 * @return
	 */
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
