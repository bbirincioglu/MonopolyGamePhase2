package domain;
import org.json.JSONObject;
/**
 * 
 * @author Ali Furkan
 *	Busticket square
 */
public class BusTicket extends Square {
	/**
	 * Constructor with name
	 * @param name
	 */
	public BusTicket(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Owner gets a busticket card
	 * @param piece
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
	 * Create Busticket object from json format
	 * @param squareAsJSON
	 * 
	 */
	public static BusTicket fromJSON(JSONObject squareAsJSON) {
		BusTicket busTicket = null;
		
		try {
			String name = squareAsJSON.getString("name");
			busTicket = new BusTicket(name);
		} catch (Exception e) {
			
		}
		
		return busTicket;
	}
}
