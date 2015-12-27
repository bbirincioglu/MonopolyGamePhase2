package domain;
import org.json.JSONObject;

/**
 * 
 * @author Ali Furkan
 *	Bonus square
 */
public class Bonus extends Square{
	/**
	 * Constructor with name
	 * @param name
	 */
	public Bonus(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/** 
	 * When piece lands, the owner gets 300
	 * @requires piece is not null
	 * @effects adds 300 to owners money
	 * @param piece
	 * @see domain.Square#landedOn(domain.Piece)
	 */
	@Override
	
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(300);
	}

	@Override
	/** 
	 * When piece passes, the owner gets 250
	 * @requires piece is not null
	 * @effects adds 250 to owners money
	 * @param piece
	 * @see domain.Square#passedOn(domain.Piece)
	 */
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(250);

	}
	/**
	 * Creates Bonus object from json format
	 * @requires sqareAsJSON is not null
	 * @param squareAsJSON
	 * @return
	 */
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
