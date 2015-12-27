package domain;
import org.json.JSONObject;


public class RollOne extends Square{
	/**
	 * Constructor for RollOne
	 * @param name name of the square
	 */
	
	public RollOne(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Player landing on that square picks a card on which a number is written, and rolls a dice.
	 * If dice value is the same as number written on the card, he receives $100.
	 * @param piece piece of the current player.
	 * @requires owner of piece != null
	 * @modifies owner of the piece
	 * @effects player landing on that square picks a card on which a number is written, and rolls a dice.
	 * If dice value is the same as number written on the card, he receives $100
	 */
	
	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Using json object representation of square, creates instance of RollOne class.
	 * @param squareAsJSON json object holding the information of the square.
	 * @return an instance of RollOne class
	 */
	
	public static RollOne fromJSON(JSONObject squareAsJSON) {
		RollOne rollOne = null;
		
		try {
			String name = squareAsJSON.getString("name");
			rollOne = new RollOne(name);
		} catch (Exception e) {
			
		}
		
		return rollOne;
	}
}