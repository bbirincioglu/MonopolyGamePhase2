package domain;

import org.json.JSONObject;


public class BirthdayGift extends Square{

	public BirthdayGift(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Player currentPlayer = piece.getOwner();
		currentPlayer.receivePayment(100);
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static BirthdayGift fromJSON(JSONObject squareAsJSON) {
		BirthdayGift birthdayGift = null;
		
		try {
			String name = squareAsJSON.getString("name");
			birthdayGift = new BirthdayGift(name);
		} catch (Exception e) {
			
		}
		
		return birthdayGift;
	}
}
