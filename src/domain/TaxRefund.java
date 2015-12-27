package domain;
import org.json.JSONObject;


public class TaxRefund extends Square {
	/**
	 * Constructor for TaxRefund
	 * @param name name of the square
	 */
	public TaxRefund(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Player landing on that square collects the half of the money accumulated in the pool.
	 * @param piece piece of the current player.
	 * @requires owner of piece != null
	 * @modifies owner of the piece, bank.
	 * @effects player landing on that square collects the half of the money accumulated in the pool
	 */
	
	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Player currentPlayer = piece.getOwner();
		Bank bank = GameController.getInstance().getMonopolyBoard().getBank();
		int poolMoneyHalf = bank.getPoolMoney() / 2;
		currentPlayer.receivePayment(poolMoneyHalf);
		bank.receivePayment(poolMoneyHalf * -1);
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Using json object representation of square, creates instance of TaxRefund class.
	 * @param squareAsJSON json object holding the information of the square.
	 * @return an instance of TaxRefund class
	 */
	
	public static TaxRefund fromJSON(JSONObject squareAsJSON) {
		TaxRefund taxRefund = null;
		
		try {
			String name = squareAsJSON.getString("name");
			taxRefund = new TaxRefund(name);
		} catch (Exception e) {
			
		}
		
		return taxRefund;
	}
}