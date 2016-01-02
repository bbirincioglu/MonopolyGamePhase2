package domain;
import java.util.ArrayList;

import org.json.JSONObject;


public class CabSquare extends BuyableSquare{
	private static CabDescription description;
	
	public CabSquare(String name, boolean isMortgaged, int price) {
		super(name, isMortgaged, price);
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
			boolean isMortgaged = squareAsJSON.getBoolean("isMortgaged");
			int price = squareAsJSON.getInt("price");
			cabSquare = new CabSquare(name, isMortgaged, price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cabSquare;
	}
	
	@Override
	public int getMortgageValue() {
		return getDescription().getMortgageValue();
	}

	@Override
	public void applySavedData(String savedData) {
		// TODO Auto-generated method stub
		setMortgaged(Boolean.valueOf(savedData.split(";")[1]));
	}

	@Override
	public String convertToSavedData() {
		// TODO Auto-generated method stub
		return getName() + ";" + isMortgaged();
	}
}
