package domain;
import org.json.JSONObject;


public class ReverseDirection extends Square {
	/**
	 * Constructor for ReverseDirection
	 * @param name name of the square
	 */
	
	public ReverseDirection(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Player landing on that square starts to move on previous direction.
	 * @param piece piece of the current player.
	 * @requires owner of piece != null
	 * @modifies piece
	 * @effects player landing on that square starts to move on previous direction.
	 */
	
	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		String direction = piece.getDirection();
		
		if (direction.equals(Piece.Direction.CLOCKWISE)) {
			piece.setDirection(Piece.Direction.COUNTER_CLOCKWISE);
		} else {
			piece.setDirection(Piece.Direction.CLOCKWISE);
		}
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Using json object representation of square, creates instance of ReverseDirection class.
	 * @param squareAsJSON json object holding the information of the square.
	 * @return an instance of ReverseDirection class
	 */
	
	public static ReverseDirection fromJSON(JSONObject squareAsJSON) {
		ReverseDirection reverseDirection = null;
		
		try {
			String name = squareAsJSON.getString("name");
			reverseDirection = new ReverseDirection(name);
		} catch (Exception e) {
			
		}
		
		return reverseDirection;
	}
}