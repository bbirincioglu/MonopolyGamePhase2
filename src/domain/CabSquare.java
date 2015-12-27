package domain;
import java.util.ArrayList;

import org.json.JSONObject;


public class CabSquare extends BuyableSquare{
	private static CabDescription description;
	
	public CabSquare(String name, int price) {
		super(name, price);
	}
	
	public static CabDescription getDescription() {
		return description;
	}


	public static void setDescription(CabDescription description) {
		CabSquare.description = description;
	}
	
	@Override
	public int getCurrentRent() {
		int currentRent;// TODO Auto-generated method stub
		int cabNum=0;
		ArrayList<BuyableSquare> squares=this.getOwner().getSquares();
		for(int i=0; i<squares.size(); i++){
			BuyableSquare square=squares.get(i);
			if(square instanceof CabSquare){
				cabNum++;
			}
		}
		
		currentRent = description.getRents().get(cabNum-1);
		return currentRent;
	}
	
	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
	}
	
	public static CabSquare fromJSON(JSONObject squareAsJSON) {
		CabSquare cabSquare = null;
		
		try {
			String name = squareAsJSON.getString("name");
			int price = squareAsJSON.getInt("price");
			cabSquare = new CabSquare(name, price);
		} catch (Exception e) {
			
		}
		
		return cabSquare;
	}
	
	@Override
	public int getMortgageValue() {
		return getDescription().getMortgageValue();
	}
}
