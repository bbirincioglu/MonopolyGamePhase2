package domain;
import org.json.JSONObject;

/**
 * 
 * @author Ali Furkan
 * Hollandtunnel square
 * @
 */
public class HollandTunnel extends Square {
	private HollandTunnel opposite;
	/**
	 * Constructor for hollandtunnel
	 * @param name
	 */
	public HollandTunnel(String name) {
		super(name);
		setOpposite(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Moves to the opposite side of the tunnel
	 * immediately
	 * @param piece
	 */
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.moveImmediate(getOpposite());
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Creates hollandtunnel object from json format
	 * @param squareAsJSON
	 * @return
	 */
	public static HollandTunnel fromJSON(JSONObject squareAsJSON) {
		HollandTunnel hollandTunnel = null;
		
		try {
			String name = squareAsJSON.getString("name");
			hollandTunnel = new HollandTunnel(name);
		} catch (Exception e) {
			
		}
		
		return hollandTunnel;
	}

	public HollandTunnel getOpposite() {
		return opposite;
	}

	public void setOpposite(HollandTunnel opposite) {
		this.opposite = opposite;
	}
}