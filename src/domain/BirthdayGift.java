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
		boolean isYes = DialogBuilder.yesNoDialog("Would you like to take $100?");
		
		if (isYes) {
			currentPlayer.receivePayment(100);
		} else {
			currentPlayer.move(findNearestCabSquare(currentPlayer.getCurrentLocation(), currentPlayer.getDirection()));
		}
	}
	
	private Square findNearestCabSquare(Square currentLocation, String direction) {
		Square nearestCabSquare;
		
		while (!(currentLocation instanceof CabSquare)) {
			if (direction.equals(Piece.Direction.CLOCKWISE)) {
				currentLocation = currentLocation.getNext();
			} else {
				currentLocation = currentLocation.getPrevious();
			}
		}
		
		nearestCabSquare = currentLocation;
		return nearestCabSquare;
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
