package domain;
import java.util.ArrayList;

import org.json.JSONObject;


public class AuctionSquare extends Square {

	public AuctionSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		GameController controller = GameController.getInstance();
		Bank bank = controller.getMonopolyBoard().getBank();
		
		if (bank.isUnownedBuyableSquareLeft()) {
			String squareName = DialogBuilder.pickAnUnownedSquareDialog(bank);
			BuyableSquare buyableSquare = bank.getBuyableSquare(squareName);
			int[] bids = DialogBuilder.auctionDialog(controller.getPlayers(), buyableSquare);
			int maximumBid = bids[0];
			int maximumBidIndex = 0;
			
			for (int i = 0; i < bids.length; i++) {
				if (bids[i] > maximumBid) {
					maximumBid = bids[i];
					maximumBidIndex = i;
				}
			}
			
			if (maximumBid >= buyableSquare.getPrice() / 2) {
				Checker checker = controller.getChecker();
				Player buyer = controller.getPlayers().get(maximumBidIndex);
				String result = checker.checkBuySquare(buyer, bank, buyableSquare, maximumBid);
				
				if (result.equals("true")) {
					buyer.buySquare(bank, buyableSquare, maximumBid);
				} else {
					DialogBuilder.informativeDialog(result);
				}
			}		
		} else {
			DialogBuilder.informativeDialog("There is no unowned square left.");
			Player currentPlayer = piece.getOwner();
			BuyableSquare squareWithHighestRent = findSquareWithHighestRent(currentPlayer, controller.getMonopolyBoard());
			currentPlayer.move(squareWithHighestRent);
		}
	}
	
	private BuyableSquare findSquareWithHighestRent(Player currentPlayer, MonopolyBoard monopolyBoard) {
		BuyableSquare squareWithHighestRent = null;
		Square currentLocation = currentPlayer.getCurrentLocation();
		ArrayList<Square> outerSquares = monopolyBoard.getOuterSquares();
		ArrayList<Square> middleSquares = monopolyBoard.getMiddleSquares();
		int totalDiceValue = GameController.getInstance().getCup().getDiceValuesTotal();
		BuyableSquare outerSquareHighestRent;
		BuyableSquare middleSquareHighestRent;
		
		if (totalDiceValue % 2 == 0) {
			if (currentPlayer.getDirection().equals(Piece.Direction.CLOCKWISE)) {
				outerSquareHighestRent = highestRentHelper(outerSquares, 16, 36, currentPlayer);
			} else {
				outerSquareHighestRent = highestRentHelper(outerSquares, 8, 15, currentPlayer);
			}
			
			middleSquareHighestRent = highestRentHelper(middleSquares, 0, middleSquares.size(), currentPlayer);
			
			if (outerSquareHighestRent == null || middleSquareHighestRent == null) {
				if (outerSquareHighestRent == null) {
					squareWithHighestRent = middleSquareHighestRent;
				} else {
					squareWithHighestRent = outerSquareHighestRent;
				}
			} else {
				if (outerSquareHighestRent.getCurrentRent() >= middleSquareHighestRent.getCurrentRent()) {
					squareWithHighestRent = outerSquareHighestRent;
				} else {
					squareWithHighestRent = middleSquareHighestRent;
				}
			}		
		} else {
			squareWithHighestRent = highestRentHelper(outerSquares, 0, outerSquares.size(), currentPlayer);
		}
		
		return squareWithHighestRent;
	}
	
	private BuyableSquare highestRentHelper(ArrayList<Square> squares, int start, int end, Player currentPlayer) {
		BuyableSquare squareWithHighestRent = null;
		
		for (int i = start; i < end; i++) {
			Square square = squares.get(i);
			
			if (square instanceof BuyableSquare) {
				BuyableSquare buyableSquare = (BuyableSquare) square;
				
				if (squareWithHighestRent == null && (buyableSquare.getOwner() != null) && !(buyableSquare.getOwner().equals(currentPlayer))) {
					squareWithHighestRent = buyableSquare;
				} else {
					if (buyableSquare.getCurrentRent() > squareWithHighestRent.getCurrentRent() && (buyableSquare.getOwner() != null) && !(buyableSquare.getOwner().equals(currentPlayer))) {
						squareWithHighestRent = buyableSquare;
					}
				}
			}
		}
		
		return squareWithHighestRent;
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static AuctionSquare fromJSON(JSONObject squareAsJSON) {
		AuctionSquare auctionSquare = null;
		
		try {
			String name = squareAsJSON.getString("name");
			auctionSquare = new AuctionSquare(name);
		} catch (Exception e) {
			
		}
		
		return auctionSquare;
	}
}
