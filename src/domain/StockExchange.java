package domain;
import org.json.JSONObject;


public class StockExchange extends Square {

	public StockExchange(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Player currentPlayer = piece.getOwner();
		GameController gameController = GameController.getInstance();
		Bank bank = gameController.getMonopolyBoard().getBank();
		Checker checker = gameController.getChecker();
		
		if (bank.isUnownedStockLeft()) {
			String stockName = DialogBuilder.pickAnUnownedStockDialog(bank);
			Stock stock = bank.getStock(stockName);
			
			boolean isYes = DialogBuilder.yesNoDialog("Would you like to buy the stock?");
			
			if (isYes) {
				String result = checker.checkBuyStock(currentPlayer, bank, stock, stock.getParValue());
				
				if (result.equals("true")) {
					currentPlayer.buyStock(bank, stock, stock.getParValue());
				} else {
					DialogBuilder.informativeDialog(result);
				}
			} else {
				int[] bids = DialogBuilder.auctionDialog(gameController.getPlayers(), stock);
				int maximumBid = bids[0];
				int maximumBidIndex = 0;
				int length = bids.length;
				
				for (int i = 0; i < length; i++) {
					if (maximumBid < bids[i]) {
						maximumBid = bids[i];
						maximumBidIndex = i;
					}
				}
				
				if (maximumBid >= stock.getParValue() / 2) {
					Player buyer = gameController.getPlayers().get(maximumBidIndex);
					String result = checker.checkBuyStock(buyer, bank, stock, maximumBid);
					
					if (result.equals("true")) {
						buyer.buyStock(bank, stock, maximumBid);
					} else {
						DialogBuilder.informativeDialog(result);
					}
				}
			}
		} else {
			DialogBuilder.informativeDialog("There is no unowned stock left.");
		}
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static StockExchange fromJSON(JSONObject squareAsJSON) {
		StockExchange stockExchange = null;
		
		try {
			String name = squareAsJSON.getString("name");
			stockExchange = new StockExchange(name);
		} catch (Exception e) {
			
		}
		
		return stockExchange;
	}
}
