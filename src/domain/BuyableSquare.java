package domain;
public abstract class BuyableSquare extends Square {
	
	private int price;
	private boolean isMortgaged;
	private Player owner;
	
	public BuyableSquare(String name, int price) {
		super(name);
		this.price=price;
		this.isMortgaged=false;
		this.owner=null;	
		// TODO Auto-generated constructor stub
	}
	
	public Player getOwner() {
		return owner;
	}


	public void setOwner(Player owner) {
		this.owner = owner;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}
	
	public boolean isMortgaged() {
		return isMortgaged;
	}
	
	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}
	
	public abstract int getMortgageValue();
	public abstract int getCurrentRent();
	
	public void landedOn(Piece piece) {
		Player pieceOwner = piece.getOwner();
		Player squareOwner = getOwner();
		GameController gameController = GameController.getInstance();
		Bank bank = gameController.getMonopolyBoard().getBank();
		
		if (squareOwner == null) {
			if (pieceOwner.getMoney() >= getPrice()) {
				String choice = DialogBuilder.buyOrAuctionDialog(pieceOwner, this);
				
				if (choice.equals("Buy")) {
					pieceOwner.buySquare(bank, this, getPrice());
				} else if (choice.equals("Auction")) {
					int[] bids = DialogBuilder.auctionDialog(gameController.getPlayers(), this);
					int maximumBid = bids[0];
					int maximumBidIndex = 0;
					
					for (int i = 1; i < bids.length; i++) {
						if (bids[i] > maximumBid) {
							maximumBid = bids[i];
							maximumBidIndex = i;
						}
					}
					
					if (maximumBid >= getPrice() / 2) {
						Player buyer = gameController.getPlayers().get(maximumBidIndex);
						buyer.buySquare(bank, this, maximumBid);
					}
				}
			}
		} else {
			if (!pieceOwner.equals(squareOwner)) {
				if (!isMortgaged()) {
					pieceOwner.makePayment(squareOwner, getCurrentRent());
				}
			}
		}
	}
}
