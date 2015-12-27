package domain;
import java.util.ArrayList;

import org.json.JSONObject;


public class UtilitySquare extends BuyableSquare{

	private static UtilityDescription description;
	
	public UtilitySquare(String name, int price) {
		super(name, price);
		// TODO Auto-generated constructor stub
	}

	public static UtilityDescription getDescription() {
		return description;
	}
	
	public static void setDescription(UtilityDescription description) {
		UtilitySquare.description = description;
	}
	
	public int getCurrentRent(){
		int currentRent;
		int utilityNum=0;
		ArrayList<BuyableSquare> squares=this.getOwner().getSquares();
		for(int i=0; i<squares.size(); i++){
			BuyableSquare square=squares.get(i);
			if(square instanceof UtilitySquare){
				utilityNum++;
			}
		}
		
		int diceValuesTotal = GameController.getInstance().getCup().getDiceValuesTotal();
		currentRent = diceValuesTotal * description.getMultiplicators().get(utilityNum-1);
		return currentRent;
	}
	
	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static UtilitySquare fromJSON(JSONObject squareAsJSON) {
		UtilitySquare utilitySquare = null;
		
		try {
			String name = squareAsJSON.getString("name");
			int price = squareAsJSON.getInt("price");
			utilitySquare = new UtilitySquare(name, price);
		} catch (Exception e) {
			
		}
		
		return utilitySquare;
	}
	
	public int getMortgageValue() {
		return getDescription().getMortgageValue();
	}
}
