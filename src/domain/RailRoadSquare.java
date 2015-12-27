package domain;
import java.util.ArrayList;

import org.json.JSONObject;


public class RailRoadSquare extends BuyableSquare {

	private static RailRoadDescription description;
	private TransitStation up;
	private boolean isTrainDepotBuilt;

	
	public RailRoadSquare(String name, boolean isMortgaged, boolean isTrainDepotBuilt, int price) {
		super(name, isMortgaged, price);
		this.isTrainDepotBuilt = isTrainDepotBuilt;
		// TODO Auto-generated constructor stub
	}

	public static RailRoadDescription getDescription() {
		return description;
	}

	public static void setDescription(RailRoadDescription description) {
		RailRoadSquare.description = description;
	}

	public TransitStation getUp() {
		return up;
	}

	public void setUp(TransitStation up) {
		this.up = up;
	}
	
	public boolean isTrainDepotBuilt() {
		return isTrainDepotBuilt;
	}

	public void setTrainDepotBuilt(boolean isTrainDepotBuilt) {
		this.isTrainDepotBuilt = isTrainDepotBuilt;
	}

	@Override
	public int getCurrentRent() {
		int currentRent;// TODO Auto-generated method stub
		int railRoadNum=0;
		ArrayList<BuyableSquare> squares=this.getOwner().getSquares();
		for(int i=0; i<squares.size(); i++){
			BuyableSquare square=squares.get(i);
			if(square instanceof RailRoadSquare){
				railRoadNum++;
			}
		}
		currentRent = description.getRents().get(railRoadNum-1);
		if(isTrainDepotBuilt())
			currentRent = currentRent *2;
		
		return currentRent;
	}
	
	public void landedOn(Piece piece) {
		super.landedOn(piece);
		int diceValuesTotal = GameController.getInstance().getCup().getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			piece.moveImmediate(getUp());
		}
	}
	
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		int diceValuesTotal = GameController.getInstance().getCup().getDiceValuesTotal();
		
		if ((diceValuesTotal % 2) == 0) {
			piece.moveImmediate(getUp());
		}
	}
	
	public String getBuildingsInfo() {
		String buildingsInfo = "trainDepotNum: ";
		
		if (isTrainDepotBuilt()) {
			buildingsInfo += 1;
		} else {
			buildingsInfo += 0;
		}
		
		return buildingsInfo;
	}
	
	public static RailRoadSquare fromJSON(JSONObject squareAsJSON) {
		RailRoadSquare railRoadSquare = null;
		
		try {
			String name = squareAsJSON.getString("name");
			boolean isMortgaged = squareAsJSON.getBoolean("isMortgaged");
			boolean isTrainDepotBuilt = squareAsJSON.getBoolean("isTrainDepotBuilt");
			int price = squareAsJSON.getInt("price");
			railRoadSquare = new RailRoadSquare(name, isMortgaged, isTrainDepotBuilt, price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return railRoadSquare;
	}
	
	public int getMortgageValue() {
		return getDescription().getMortgageValue();
	}
}