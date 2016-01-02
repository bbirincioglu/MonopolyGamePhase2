package domain;

public class Checker {
	public static final String NOT_OWNED_ERROR = "The square (or stock) is not yours.";
	public static final String MORTGAGED_ERROR = "The square is mortgaged. You can't build anything.";
	public static final String ALREADY_MORTGAGED_ERROR = "The square (or stock) is already mortgaged. You can't apply mortgage to an already mortgaged square (or stock).";
	public static final String ALREADY_NOT_MORTGAGED_ERROR = "The square (or stock) is not mortgaged. You can't remove mortgage from an already not-mortgaged square (or stock).";
	public static final String CANT_BUY_ANYMORE_ERROR = "The square already has a skyscraper. You can't buy anymore.";
	public static final String CANT_SELL_ANYMORE_ERROR = "The square already has 0 building. You can't sell anymore.";
	public static final String NOT_ENOUGH_MONEY_ERROR = "You don't have enough money.";
	public static final String SQUARE_HAS_BUILDING_ERROR = "The square has building, you can't sell it.";
	public static final String RESULT_HOUSE = "house";
	public static final String RESULT_HOTEL = "hotel";
	public static final String RESULT_SKYSCRAPER = "skyscraper";
	public static final String ALREADY_TRAIN_DEPOT_BUILT_ERROR = "The railroad already has a train depot. You can't build anymore.";
	public static final String ALREADY_NO_TRAIN_DEPOT_BUILT_ERROR = "The railroad doesn't have any train depot. You can't sell anymore.";
	public static final String ALREADY_OWNED_ERROR = "The square (or stock) is already yours.";
	public static final String TOO_MUCH_IMPROVEMENT_ERROR = "Too much improvement compared to other squares.";
	public static final String TOO_MUCH_DECLINE_ERROR = "Too much decline compared to other squares.";
	public static final String MONOPOLY_ERROR = "You don't have monopoly";
	public static final String MAJORITY_OWNERSHIP_ERROR = "You don't have majority ownership";
	public static final String STOCK_LIMIT_REACHED_ERROR = "You already have 6 shares of various companies. You can't buy more.";
	
	public Checker() {		
	}
	
	public String checkBuySquare(Player buyer, Object seller, BuyableSquare square, int payment) {
		String result = "true";
		
		if (seller instanceof Bank) {
			Bank sellerAsBank = (Bank) seller;
			
			if (buyer.getMoney() < payment) {
				result = NOT_ENOUGH_MONEY_ERROR;
			}
		} else {
			Player sellerAsPlayer = (Player) seller;
			
			if (buyer.equals(sellerAsPlayer)) {
				result = ALREADY_OWNED_ERROR;
			} else if (buyer.getMoney() < payment) {
				result = NOT_ENOUGH_MONEY_ERROR;
			} else {
				if (square instanceof ColorSquare) {
					ColorSquare squareAsColorSquare = (ColorSquare) square;
					
					if (squareAsColorSquare.getBuildingNum() > 0) {
						result = SQUARE_HAS_BUILDING_ERROR;
					}
				} else if (square instanceof RailRoadSquare) {
					RailRoadSquare squareAsRailroadSquare = (RailRoadSquare) square;
					
					if (squareAsRailroadSquare.isTrainDepotBuilt()) {
						result = SQUARE_HAS_BUILDING_ERROR;
					}
				}
			}
		}
		
		return result;
	}
	
	public String checkSellSquare(Player buyer, Object seller, BuyableSquare square, int payment) {
		String result = checkBuySquare(buyer, seller, square, payment);
		return result;
	}
	
	public String checkApplyMortgage(BuyableSquare square) {
		String result = "true";
		Player squareOwner = square.getOwner();
		Player currentPlayer = GameController.getInstance().getCurrentPlayer();
		
		if (!currentPlayer.equals(squareOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (square.isMortgaged()) {
			result = ALREADY_MORTGAGED_ERROR;
		}
		
		return result;
	}
	
	public String checkRemoveMortgage(BuyableSquare square) {
		String result = "true";
		Player squareOwner = square.getOwner();
		Player currentPlayer = GameController.getInstance().getCurrentPlayer();
		
		if (!currentPlayer.equals(squareOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (!square.isMortgaged()) {
			result = ALREADY_NOT_MORTGAGED_ERROR;
		} else if (currentPlayer.getMoney() < square.getMortgageValue() * 1.1) {
			result = NOT_ENOUGH_MONEY_ERROR;
		}
		
		return result;
	}
	
	public String checkBuyBuilding(ColorSquare square) {
		String result;
		Player squareOwner = square.getOwner();
		GameController gameController = GameController.getInstance();
		Player currentPlayer = gameController.getCurrentPlayer();
		
		if (!currentPlayer.equals(squareOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (square.isMortgaged()) {
			result = MORTGAGED_ERROR;
		} else if (square.getBuildingNum() == 6) {
			result = CANT_BUY_ANYMORE_ERROR;
		} else {
			int buildingNum = square.getBuildingNum();
			boolean isMajorityOwnership = square.isMajorityOwnership();
			boolean isMonopoly = square.isMonopoly();
			
			if (0 <= buildingNum && buildingNum <= 4) {
				if (isMajorityOwnership || isMonopoly) {
					if (!square.isMoreDeveloped()) {
						if (buildingNum == 4) {
							if (currentPlayer.getMoney() < square.getHotelCost()) {
								result = NOT_ENOUGH_MONEY_ERROR;
							} else {
								result = RESULT_HOTEL;
							}
 						} else {
							if (currentPlayer.getMoney() < square.getHouseCost()) {
								result = NOT_ENOUGH_MONEY_ERROR;
							} else {
								result = RESULT_HOUSE;
							}
						}
					} else {
						result = TOO_MUCH_IMPROVEMENT_ERROR;
					}
				} else {
					result = MAJORITY_OWNERSHIP_ERROR;
				}
			} else {
				if (isMonopoly) {
					if (!square.isMoreDeveloped()) {
						if (currentPlayer.getMoney() < square.getSkyscraperCost()) {
							result = NOT_ENOUGH_MONEY_ERROR;
						} else {
							result = RESULT_SKYSCRAPER;
						}
					} else {
						result = TOO_MUCH_IMPROVEMENT_ERROR;
					}
				} else {
					result = MONOPOLY_ERROR;
				}
			}
		}
		
		return result;
	}
	
	public String checkSellBuilding(ColorSquare square) {
		String result = null;
		Player squareOwner = square.getOwner();
		Player currentPlayer = GameController.getInstance().getCurrentPlayer();
		
		if (!currentPlayer.equals(squareOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (square.isMortgaged()) {
			result = MORTGAGED_ERROR;
		} else if (square.getBuildingNum() == 0) {
			result = CANT_SELL_ANYMORE_ERROR;
		} else if (square.isLessDeveloped()) {
			result = TOO_MUCH_DECLINE_ERROR;
		} else {
			int buildingNum = square.getBuildingNum();
			
			if (1 <= buildingNum && buildingNum <= 4) {
				result = RESULT_HOUSE;
			} else if (buildingNum == 5) {
				result = RESULT_HOTEL;
			} else if (buildingNum == 6) {
				result = RESULT_SKYSCRAPER;
			}
		}
		
		return result;
	}
	
	public String checkBuyTrainDepot(RailRoadSquare square) {
		String result = "true";
		Player squareOwner = square.getOwner();
		Player currentPlayer = GameController.getInstance().getCurrentPlayer();
		
		if (!currentPlayer.equals(squareOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (square.isMortgaged()) {
			result = MORTGAGED_ERROR;
		} else if (square.isTrainDepotBuilt()) {
			result = ALREADY_TRAIN_DEPOT_BUILT_ERROR;
		} else if (currentPlayer.getMoney() < 100) {
			result = NOT_ENOUGH_MONEY_ERROR;
		}
		
		return result;
	}
	
	public String checkSellTrainDepot(RailRoadSquare square) {
		String result = "true";
		Player squareOwner = square.getOwner();
		Player currentPlayer = GameController.getInstance().getCurrentPlayer();
		
		if (!currentPlayer.equals(squareOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (square.isMortgaged()) {
			result = MORTGAGED_ERROR;
		} else if (!square.isTrainDepotBuilt()) {
			result = ALREADY_NO_TRAIN_DEPOT_BUILT_ERROR;
		}
		
		return result;
	}
	
	public String checkBuyStock(Object buyer, Object seller, Stock stock, int payment) {
		String result = "true";
		
		if (buyer instanceof Player) {
			Player buyerAsPlayer = (Player) buyer;
			
			if (buyerAsPlayer.getStocks().size() == 6) {
				result = STOCK_LIMIT_REACHED_ERROR;
			} else if (buyerAsPlayer.getMoney() < payment) {
				result = NOT_ENOUGH_MONEY_ERROR;
			}
		} else {
			Player sellerAsPlayer = (Player) seller;
			
			if (stock == null) {
				result = NOT_OWNED_ERROR;
			} else if (stock.isMortgaged()) {
				result = "The stock is mortgaged. You can't sell it.";
			}
		}
		
		return result;
	}
	
	public String checkSellStock(Player seller, Bank buyer, Stock stock, int payment) {
		String result = checkBuyStock(buyer, seller, stock, payment);
		return result;
	}
	
	public String checkApplyMortgage(Stock stock) {
		String result = "true";
		Player currentPlayer = GameController.getInstance().getCurrentPlayer();
		Player stockOwner = stock.getOwner();
		
		if (!currentPlayer.equals(stockOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (stock.isMortgaged()) {
			result = ALREADY_MORTGAGED_ERROR;
		}
		
		return result;
	}
	
	public String checkRemoveMortgage(Stock stock) {
		String result = "true";
		Player currentPlayer = GameController.getInstance().getCurrentPlayer();
		Player stockOwner = stock.getOwner();
		
		if (!currentPlayer.equals(stockOwner)) {
			result = NOT_OWNED_ERROR;
		} else if (!stock.isMortgaged()) {
			result = ALREADY_NOT_MORTGAGED_ERROR;
		} else if (currentPlayer.getMoney() < stock.getLoanValue() * 1.1) {
			result = NOT_ENOUGH_MONEY_ERROR;
		}
		
		return result;
	}
}
