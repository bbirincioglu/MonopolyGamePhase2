package domain;
import org.json.JSONObject;


public class LuxuryTax extends Square{
	/**
	 * Constructor for LuxuryTax
	 * @param name name of the square
	 */
	
	public LuxuryTax(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Player landing on that square make $75 payment to the bank.
	 * @param piece piece of the current player.
	 * @requires owner of piece != null
	 * @modifies player, bank
	 * @effects player landing on that square make $75 payment to the bank
	 */
	
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
	
	/**
	 * Using json object representation of square, creates instance of LuxuryTax class.
	 * @param squareAsJSON json object holding the information of the square.
	 * @return an instance of LuxuryTax class
	 */
	
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