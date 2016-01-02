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
	
	/**
	 * Constructor for TransitStation
	 * @param name name of the square
	 */
	
	public TransitStation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Player landing on that square immediately moves to corresponding rail road if he rolled even.
	 * @param piece piece of the current player.
	 * @requires owner of piece != null
	 * @modifies piece, an instance of TransitStation class, and corresponding instance of
	 * RailRoad class
	 * @effects  player landing on that square immediately moves to corresponding rail road if he rolled even
	 */
	
	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Cup cup = GameController.getInstance().getCup();
		int diceValuesTotal = cup.getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			try {
				Thread.sleep(Piece.SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			piece.moveImmediate(getDown());
		}
	}
	
	/**
	 * Player passing from that square immediately moves to corresponding rail road if he rolled even.
	 * @param piece piece of the current player.
	 * @modifies piece, an instance of TransitStation class, and corresponding instance of
	 * RailRoad class 
	 */
	
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		Cup cup = GameController.getInstance().getCup();
		int diceValuesTotal = cup.getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			try {
				Thread.sleep(Piece.SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			piece.moveImmediate(getDown());
		}
	}
	
	/**
	 * Using json object representation of square, creates instance of TransitStation class.
	 * @param squareAsJSON json object holding the information of the square.
	 * @return an instance of TransitStation class
	 */
	
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