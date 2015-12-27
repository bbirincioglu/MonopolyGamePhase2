package domain;
import org.json.JSONObject;


public class TransitStation extends Square {
	private RailRoadSquare down;

	public RailRoadSquare getDown() {
		return down;
	}

	public void setDown(RailRoadSquare down) {
		this.down = down;
	}

	public TransitStation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Cup cup = GameController.getInstance().getCup();
		int diceValuesTotal = cup.getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			piece.moveImmediate(getDown());
		}
	}
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		Cup cup = GameController.getInstance().getCup();
		int diceValuesTotal = cup.getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			piece.moveImmediate(getDown());
		}
	}
	
	public static TransitStation fromJSON(JSONObject squareAsJSON) {
		TransitStation transitStation = null;
		
		try {
			String name = squareAsJSON.getString("name");
			transitStation = new TransitStation(name);
		} catch (Exception e) {
			
		}
		
		return transitStation;
	}
}
