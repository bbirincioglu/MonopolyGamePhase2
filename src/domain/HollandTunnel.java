package domain;
import org.json.JSONObject;


public class HollandTunnel extends Square {
	private HollandTunnel opposite;
	
	public HollandTunnel(String name) {
		super(name);
		setOpposite(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.moveImmediate(getOpposite());
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
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
