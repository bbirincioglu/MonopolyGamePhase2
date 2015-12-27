package domain;
import org.json.JSONObject;
/**
 * 
 * @author Ali Furkan
 * Go square
 */

public class Go extends Square {
	/**
	 * Constructor with name
	 * @param name
	 */
	public Go(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	
	
	
	/** 
	 * When the piece lands, the owner gets 200 dollars
	 * @requires piece is not null
	 * @effects adds 200 to the money of the piece owner
	 * @param piece
	 * 
	 * @see domain.Square#landedOn(domain.Piece)
	 */
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(200);
	}
	/** 
	 * When the piece passes, the owner gets 200 dollars
	 * @requires piece is not null
	 * @effects adds 200 to the money of the piece owner
	 * @param piece
	 * 
	 * @see domain.Square#passedOn(domain.Piece)
	 */
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(200);
	}
	/**
	 * Creates the go object from json format
	 * @requires squareAsJSON is not null
	 * @param squareAsJSON
	 * @return
	 */
	public static Go fromJSON(JSONObject squareAsJSON) {
		Go go = null;
		
		try {
			String name = squareAsJSON.getString("name");
			go = new Go(name);
		} catch (Exception e) {
			
		}
		
		return go;
	}
}