package domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *The class for encapsulating buyable squares, and stocks that are not owned.
 *This class also stores the amount of money in the pool.
 *
 */

public class Bank {
	private static final String[] FIELD_NAMES = new String[]{"buyableSquares", "stocks", "poolMoney"};
	private ArrayList<BankObserver> bankObservers;
	private ArrayList<BuyableSquare> buyableSquares;
	private ArrayList<Stock> stocks;
	private int poolMoney;
	
	/**
	 * Constructor for Bank class.
	 * @param outerSquares ArrayList of Squares located at the outer layer of monopoly board.
	 * @param middleSquares ArrayList of Squares located at the middle layer of monopoly board.
	 * @param innerSquares ArrayList of Squares located at the inner layer of monopoly board.
	 */
	
	public Bank(ArrayList<Square> outerSquares, ArrayList<Square> middleSquares, ArrayList<Square> innerSquares) {
		//@requires outerSquares, middleSquares, and innerSquares are not null.
		//@modifies this
		//@effects gets all the squares in the game. Among them, picks the buyable ones, and populate
		//buyableSquares with those buyable ones. Initializes pool money. Read stocks' data from file
		//collect them under stocks ArrayList.
		
		setBankObservers(new ArrayList<BankObserver>());
		setBuyableSquares(new ArrayList<BuyableSquare>());
		setStocks(composeStocks());
		pickBuyableSquares(outerSquares, middleSquares, innerSquares);
		setPoolMoney(0);
	}
	/**
	 * Reads stocks' data from a file. Converts them into JSON objects. Then converts JSON objects
	 * to Stock objects. Populate all of the Stock objects in an ArrayList and returns that list.
	 * @return list of stocks
	 */
	private ArrayList<Stock> composeStocks() {
		ArrayList<Stock> stocks = new ArrayList<Stock>();
		Reader reader = new Reader();
		ArrayList<JSONObject> stocksAsJSON = reader.read("stocks.txt");
		int size = stocksAsJSON.size();
		
		for (int i = 0; i < size; i++) {
			stocks.add(Stock.fromJSON(stocksAsJSON.get(i)));
		}
		return stocks;
	}

	private void pickBuyableSquares(ArrayList<Square> outerSquares,ArrayList<Square> middleSquares, ArrayList<Square> innerSquares) {
		ArrayList<BuyableSquare> buyableSquares = getBuyableSquares();
		Square square;
		
		for (int i = 0; i < outerSquares.size(); i++) {
			square = outerSquares.get(i);
			
			if (square instanceof BuyableSquare) {
				buyableSquares.add((BuyableSquare) square);
			}
		}
		
		for (int i = 0; i < middleSquares.size(); i++) {
			square = middleSquares.get(i);
			
			if (square instanceof BuyableSquare) {
				buyableSquares.add((BuyableSquare) square);
			}
		}
		
		for (int i = 0; i < innerSquares.size(); i++) {
			square = innerSquares.get(i);
			
			if (square instanceof BuyableSquare) {
				buyableSquares.add((BuyableSquare) square);
			}
		}
	}
	/**
	 * Search through the buyableSquares ArrayList with given name, and returns corresponding
	 * square. If can't found, returns null.
	 * @param name name of the square for search.
	 * @return square with given name, or null if can't found.
	 */
	public BuyableSquare getBuyableSquare(String name) {
		//@requires name is not null.
		//@effects Search through the buyableSquares ArrayList with given name, and returns
		// corresponding square. If can't found, returns null.
		
		BuyableSquare buyableSquare = null;
		ArrayList<BuyableSquare> buyableSquares = getBuyableSquares();
		int size = buyableSquares.size();
		
		for (int i = 0; i < size; i++) {
			BuyableSquare square = buyableSquares.get(i);
			
			if (square.getName().equals(name)) {
				buyableSquare = square;
				break;
			}
		}
		
		return buyableSquare;
	}
	
	/**
	 * Returns true if buyableSquare left in the buyableSquares ArrayList.
	 * @return true if buyableSquare left in the buyableSquares ArrayList.
	 */
	public boolean isUnownedBuyableSquareLeft() {
		//@requires buyableSquares != null
		//@effects returns false if size of the buyableSquares is zero, else returns true.
		
		boolean result = true;
		
		if (getBuyableSquares().size() == 0) {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Adds the specified square to buyableSquares ArrayList.
	 * @param square square which will be added to buyableSquares ArrayList.
	 */
	
	public void addBuyableSquare(BuyableSquare square) {
		//@requires buyableSquares != null && square != null
		//@modifies this
		//@effects adds the square to buyableSquares ArrayList if square doesn't exist in the list.
		
		if (!getBuyableSquares().contains(square)) {
			square.setOwner(null);
			getBuyableSquares().add(square);
		}
	}
	
	/**
	 * Removes the specified square from buyableSquares ArrayList.
	 * @param square square which will be removed from the buyableSquares ArrayList.
	 */
	
	public void removeBuyableSquare(BuyableSquare square) {
		//@requires buyableSquares != null && square != null
		//@modifies this
		//@effects removes the square from buyableSquares ArrayList.
		
		getBuyableSquares().remove(square);
	}
	
	public ArrayList<BuyableSquare> getBuyableSquares() {
		return buyableSquares;
	}

	private void setBuyableSquares(ArrayList<BuyableSquare> buyableSquares) {
		this.buyableSquares = buyableSquares;
	}
	
	private void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public ArrayList<Stock> getStocks() {
		return stocks;
	}
	
	/**
	 * Returns true if Stock object left in the stocks ArrayList.
	 * @return true if Stock object left in the stocks ArrayList.
	 */
	
	public boolean isUnownedStockLeft() {
		//@requires stocks != null
		//@effects Returns false if size of stocks ArrayList is zero, otherwise returns true.
		
		boolean result = true;
		
		if (getStocks().size() == 0) {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Returns Stock object with the given name, or null if can't found.
	 * @param name name of the Stock object.
	 * @return Stock object with the given name, or null if can't found.
	 */
	
	public Stock getStock(String name) {
		//@requires name != null && stocks != null
		//@effects Returns Stock object with given name, or null if can't found.
		
		Stock stockWanted = null;
		ArrayList<Stock> stocks = getStocks();
		int size = stocks.size();
		
		for (int i = 0; i < size; i++) {
			Stock stock = stocks.get(i);
			
			if (stock.getName().equals(name)) {
				stockWanted = stock;
				break;
			}
		}
		
		return stockWanted;
	}
	
	/**
	 * Adds specified Stock object to stocks ArrayList.
	 * @param stock Stock object to be added.
	 */
	
	public void addStock(Stock stock) {
		//@requires stock != null && stocks != null
		//@effects Adds stock into stocks ArrayList if stock doesn't exist in the list.
		
		if (!getStocks().contains(stock)) {
			stock.setOwner(null);
			getStocks().add(stock);
		}
	}
	
	/**
	 * Removes specified Stock object from stocks ArrayList.
	 * @param stock Stock object to be removed.
	 */
	
	public void removeStock(Stock stock) {
		//@requires: stock != null && stocks != null
		//@effects: removes stock from stocks ArrayList.
		
		getStocks().remove(stock);
	}
	
	private void setPoolMoney(int poolMoney) {
		this.poolMoney = poolMoney;
		notifyBankObservers();
	}
	
	public void notifyBankObservers() {
		ArrayList<BankObserver> bankObservers = getBankObservers();
		int size = bankObservers.size();
		
		for (int i = 0; i < size; i++) {
			bankObservers.get(i).update(this);
		}
	}
	
	public void addBankObserver(BankObserver bankObserver) {
		getBankObservers().add(bankObserver);
	}

	public ArrayList<BankObserver> getBankObservers() {
		return bankObservers;
	}

	public void setBankObservers(ArrayList<BankObserver> bankObservers) {
		this.bankObservers = bankObservers;
	}
	
	/**
	 * Returns the money accumulated in the pool.
	 * @return the money accumulated in the pool.
	 */
	
	public int getPoolMoney() {
		//@effects: returns the money accumulated in the pool.
		
		return poolMoney;
	}
	
	/**
	 * Increase the money in the pool by specified payment.
	 * @param payment Money received and will be added to the pool.
	 */
	
	public void receivePayment(int payment) {
		//@effects: increase the money in the pool by the amount of payment.
		
		setPoolMoney(getPoolMoney() + payment);
	}
	
	public String toString() {
		return toJSON().toString();
	}
	
	public JSONObject toJSON() {
		JSONObject bankAsJSON = new JSONObject();
		
		try {
			FromArrayListToJSONArray converter = new FromArrayListToJSONArray();
			bankAsJSON.put(FIELD_NAMES[0], converter.convert(getBuyableSquares()));
			bankAsJSON.put(FIELD_NAMES[1], converter.convert(getStocks()));
			bankAsJSON.put(FIELD_NAMES[2], getPoolMoney());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bankAsJSON;
	}
	
	public boolean repOK() {
		boolean result = true;
		DuplicateElementChecker checker = new DuplicateElementChecker();
		boolean has1 = checker.hasDuplicateElements(getBuyableSquares());
		boolean has2 = checker.hasDuplicateElements(getStocks());
		boolean isPoolMoneyNegative = getPoolMoney() < 0 ? true : false;
		
		if (has1 || has2 || isPoolMoneyNegative) {
			result = false;
		}
		
		return result;
	}
}
