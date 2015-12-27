package domain;
import org.json.JSONObject;

public class GoToJail extends Square {
	private VisitingJail visitingJail;
	
	/**
	 * Constructor for GoToJail
	 * @param name name of the square
	 */
	public GoToJail(String name) {
		super(name);
		setVisitingJail(null);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Player landing on that square immediately moves to VisitingJail square, and he is considered
	 * as in jail.
	 * @param piece piece of the current player.
	 * @requires owner of piece != null
	 * @modifies this, player, instance of VisitingJail class.
	 * @effects player landing on that square immediately moves to VisitingJail square, and he is considered
	 * as in jail
	 */

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Player player = piece.getOwner();
		player.moveImmediate(getVisitingJail());
		player.setInJail(true);
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Using json object representation of square, creates instance of GoToJail class.
	 * @param squareAsJSON json object holding the information of the square.
	 * @return an instance of GoToJail class
	 */
	
	public static GoToJail fromJSON(JSONObject squareAsJSON) {
		GoToJail goToJail = null;
		
		try {
			String name = squareAsJSON.getString("name");
			goToJail = new GoToJail(name);
		} catch (Exception e) {
			
		}
		
		return goToJail;
	}

	public VisitingJail getVisitingJail() {
		return visitingJail;
	}

	public void setVisitingJail(VisitingJail visitingJail) {
		this.visitingJail = visitingJail;
	}
}