package domain;
import java.applet.Applet;
import java.util.ArrayList;

import org.json.JSONObject;

public class GameController {
	private ArrayList<ControllerObserver> observers;
	private static GameController instance;
	
	private Cup cup;
	private ArrayList<Player> players;
	private int currentPlayerIndex;
	private MonopolyBoard monopolyBoard;
	
	private Checker checker;
	private CardEvaluator cardEvaluator;
	
	private static boolean isRollButtonClicked;
	private static boolean isGameOver;
	private static boolean isLoad;
	
	private GameController() {
		setObservers(new ArrayList<ControllerObserver>());
		
		setCup(new Cup());
		setMonopolyBoard(new MonopolyBoard());
		setCardEvaluator(new CardEvaluator());
		setPlayers(new ArrayList<Player>());
		setChecker(new Checker());
		setCardEvaluator(new CardEvaluator());
		setRollButtonClicked(false);
		setGameOver(false);
		
		Reader reader = new Reader();
		GameOptions options = GameOptions.fromJSON(reader.read("options.txt").get(0));
		
		if (isLoad()) {
			System.out.println("is loading.");
			startWithNotDebugging(options);
		} else {
			if (options.isDebugging()) {
				System.out.println("is debugging");
				startWithDebugging(options, reader.read("debug.txt"));
			} else {
				System.out.println("is not debugging");
				startWithNotDebugging(options);
			}
		}
		
		new Thread(new GameLoop()).start();
	}
	
	private void startWithNotDebugging(GameOptions options) {
		int playerNum = options.getPlayerNum();
		int playerMoney = options.getPlayerMoney();
		int currentPlayerIndex = options.getCurrentPlayerIndex();
		
		ArrayList<Player> players = getPlayers();
		setCurrentPlayerIndex(currentPlayerIndex);
		
		for (int i = 0; i < playerNum; i++) {
			Player player = new Player("Player" + i, playerMoney);
			players.add(player);
		}
	}
	
	private void startWithDebugging(GameOptions options, ArrayList<JSONObject> playersAsJSON) {
		int playerNum = options.getPlayerNum();
		int currentPlayerIndex = options.getCurrentPlayerIndex();
		
		ArrayList<Player> players = getPlayers();
		setCurrentPlayerIndex(currentPlayerIndex);
		
		try {
			for (int i = 0; i < playerNum; i++) {
				JSONObject playerAsJSON = playersAsJSON.get(i);
				DebugUtil.applyDebugOptions(players, playerAsJSON, getMonopolyBoard(), getCup());
			}
			
			getCup().putDebugValuesInOrder(players.size(), getCurrentPlayerIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doRoll() {
		setRollButtonClicked(true);
	}
	
	public void doApplyMortgage(String squareName) {
		Player currentPlayer = getCurrentPlayer();
		BuyableSquare square = (BuyableSquare) getMonopolyBoard().getSquare(squareName);
		System.out.println("doApplyMortgage");
		Checker checker = getChecker();
		String result = checker.checkApplyMortgage(square);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("true")) {
			currentPlayer.applyMortgageTo(square);
		} else {
			
		}
	}
	
	public void doRemoveMortgage(String squareName) {
		Player currentPlayer = getCurrentPlayer();
		BuyableSquare square = (BuyableSquare) getMonopolyBoard().getSquare(squareName);
		System.out.println("doRemoveMortgage");
		Checker checker = getChecker();
		String result = checker.checkRemoveMortgage(square);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("true")) {
			currentPlayer.removeMortgageFrom(square);
		} else {
			
		}
	}
	
	public void doBuyBuilding(String squareName) {
		Player currentPlayer = getCurrentPlayer();
		ColorSquare square = (ColorSquare) getMonopolyBoard().getSquare(squareName);
		System.out.println("doBuyBuilding");
		Checker checker = getChecker();
		String result = checker.checkBuyBuilding(square);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("house")) {
			currentPlayer.buyHouse(square);
		} else if (result.equals("hotel")) {
			currentPlayer.buyHotel(square);
		} else if (result.equals("skyscraper")) {
			currentPlayer.buySkyscraper(square);
		} else {
			
		}
	}
	
	public void doSellBuilding(String squareName) {
		Player currentPlayer = getCurrentPlayer();
		ColorSquare square = (ColorSquare) getMonopolyBoard().getSquare(squareName);
		System.out.println("doSellBuilding");
		Checker checker = getChecker();
		String result = checker.checkSellBuilding(square);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("house")) {
			currentPlayer.sellHouse(square);
		} else if (result.equals("hotel")) {
			currentPlayer.sellHotel(square);
		} else if (result.equals("skyscraper")) {
			currentPlayer.sellSkyscraper(square);
		} else {
			
		}
	}
	
	public void doBuyTrainDepot(String squareName) {
		Player currentPlayer = getCurrentPlayer();
		RailRoadSquare square = (RailRoadSquare) getMonopolyBoard().getSquare(squareName);
		System.out.println("doBuyTrainDepot");
		Checker checker = getChecker();
		String result = checker.checkBuyTrainDepot(square);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("true")) {
			currentPlayer.buyTrainDepot(square);
		} else {
			
		}
	}
	
	public void doSellTrainDepot(String squareName) {
		Player currentPlayer = getCurrentPlayer();
		RailRoadSquare square = (RailRoadSquare) getMonopolyBoard().getSquare(squareName);
		System.out.println("doSellTrainDepot");
		Checker checker = getChecker();
		String result = checker.checkSellTrainDepot(square);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("true")) {
			currentPlayer.sellTrainDepot(square);
		} else {
			
		}
	}
	
	public void doSellStock(String stockName) {
		Player currentPlayer = getCurrentPlayer();
		Bank bank = getMonopolyBoard().getBank();
		Checker checker = getChecker();
		
		Stock stock = currentPlayer.getStock(stockName);
		String result = checker.checkSellStock(currentPlayer, bank, stock, stock.getParValue());
		
		if (result.equals("true")) {
			currentPlayer.sellStock(bank, stock, stock.getParValue() / 2);
		} else {
			DialogBuilder.informativeDialog(result);
		}
	}
	
	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		
		return instance;
	}
	
	public class GameLoop implements Runnable {
		public GameLoop() {
		}
		
		public void run() {
			while (!isGameOver()) {
				if (isRollButtonClicked()) {
					Player currentPlayer = getPlayers().get(getCurrentPlayerIndex());
					
					if (currentPlayer.isInJail()) {
						if (currentPlayer.getRoundNumInJail() == 3) {
							if (currentPlayer.getMoney() >= 50) {
								currentPlayer.makePayment(getMonopolyBoard().getBank(), 50);
								DialogBuilder.informativeDialog(currentPlayer.getName() + " is forced to pay $50 to the bank to get out of jail.");
								// no continuity.
							} else {
								if (currentPlayer.getWealth() < 50) {
									// check or mortgage.
								} else {
									DialogBuilder.forcePlayerToSellPropertyToGetOutOfJailDialog(currentPlayer);
								}
							}
						} else {
							// would you like to pay 50 dollar or roll 2 dice.
						}
					} else {
						Cup cup = getCup();
						cup.roll3Dice();
						int diceValuesTotal = cup.getDiceValuesTotal();
						
						if (cup.isMrMonopolyRolled()) {
							currentPlayer.move(diceValuesTotal);
							Bank bank = getMonopolyBoard().getBank();
							int unownedSquareSize = bank.getBuyableSquares().size();
							if (unownedSquareSize == 0) {
								currentPlayer.move(getClosestSquareToPayRent(currentPlayer));
								System.out.println("mr monopoly 1");
							} else {
								System.out.println("mr monopoly 2");
								currentPlayer.move(getClosestSquareToBuy(currentPlayer));
							}
						} else if (cup.isBusRolled()) {
							int die1Value = cup.getDie1().getFaceValue();
							int die2Value = cup.getDie2().getFaceValue();
							int choice = DialogBuilder.busDialog(die1Value, die2Value);
							System.out.println("choice" +choice);
							currentPlayer.move(choice);
						} else {
							currentPlayer.move(diceValuesTotal);
						}
					}
					
					updateCurrentPlayerIndex();
					setRollButtonClicked(false);
				} else {
					try {
						//System.out.println("in the if");
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void updateCurrentPlayerIndex() {
		setCurrentPlayerIndex((getCurrentPlayerIndex() + 1) % getPlayers().size());
		notifyObservers();
	}
	
	public void setCup(Cup cup) {
		this.cup = cup;
	}
	
	public Cup getCup() {
		return cup;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public MonopolyBoard getMonopolyBoard() {
		return monopolyBoard;
	}

	public void setMonopolyBoard(MonopolyBoard monopolyBoard) {
		this.monopolyBoard = monopolyBoard;
	}

	public Checker getChecker() {
		return checker;
	}

	public void setChecker(Checker checker) {
		this.checker = checker;
	}
	
	public static void setInstance(GameController instance) {
		GameController.instance = instance;
	}
	
	public static boolean isRollButtonClicked() {
		return isRollButtonClicked;
	}

	public static void setRollButtonClicked(boolean isRollButtonClicked) {
		GameController.isRollButtonClicked = isRollButtonClicked;
	}

	public static boolean isGameOver() {
		return isGameOver;
	}

	public static void setGameOver(boolean isGameOver) {
		GameController.isGameOver = isGameOver;
	}
	
	public static void setLoad(boolean isLoad) {
		GameController.isLoad = isLoad;
	}
	
	public static boolean isLoad() {
		return isLoad;
	}

	public ArrayList<ControllerObserver> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<ControllerObserver> observers) {
		this.observers = observers;
	}
	
	public void addObserver(ControllerObserver observer) {
		getObservers().add(observer);
	}
	
	public void notifyObservers() {
		for (int i = 0; i < getObservers().size(); i++) {
			getObservers().get(i).update(this);
		}
	}

	public CardEvaluator getCardEvaluator() {
		return cardEvaluator;
	}

	public void setCardEvaluator(CardEvaluator cardEvaluator) {
		this.cardEvaluator = cardEvaluator;
	}
	
	public Player getCurrentPlayer() {
		return getPlayers().get(getCurrentPlayerIndex());
	}
	
	public Square getClosestSquareToPayRent(Player currentPlayer) {
		Square currentPlayerLocation = currentPlayer.getCurrentLocation();
		int diceValuesTotal = getCup().getDiceValuesTotal();
		
		if (currentPlayer.getDirection().equals(Piece.Direction.CLOCKWISE)) {
			currentPlayerLocation = currentPlayerLocation.getNext();
		} else {
			currentPlayerLocation = currentPlayerLocation.getPrevious();
		}
		
		while (!(currentPlayerLocation instanceof BuyableSquare) || (((BuyableSquare) currentPlayerLocation).getOwner() == null) || (((BuyableSquare) currentPlayerLocation).getOwner().equals(currentPlayer))) {
			if (diceValuesTotal % 2 == 0) {
				if (currentPlayerLocation instanceof RailRoadSquare) {
					currentPlayerLocation = ((RailRoadSquare) currentPlayerLocation).getUp();
				} else if (currentPlayerLocation instanceof TransitStation) {
					currentPlayerLocation = ((TransitStation) currentPlayerLocation).getDown();
				} else {
					if (currentPlayer.getDirection().equals(Piece.Direction.CLOCKWISE)) {
						currentPlayerLocation = currentPlayerLocation.getNext();
					} else {
						currentPlayerLocation = currentPlayerLocation.getPrevious();
					}
				}
			} else {
				if (currentPlayer.getDirection().equals(Piece.Direction.CLOCKWISE)) {
					currentPlayerLocation = currentPlayerLocation.getNext();
				} else {
					currentPlayerLocation = currentPlayerLocation.getPrevious();
				}
			}
		}
		
		return currentPlayerLocation;
	}
	
	public Square getClosestSquareToBuy(Player currentPlayer) {
		Square currentPlayerLocation = currentPlayer.getCurrentLocation();
		int diceValuesTotal = getCup().getDiceValuesTotal();
		
		if (currentPlayer.getDirection().equals(Piece.Direction.CLOCKWISE)) {
			currentPlayerLocation = currentPlayerLocation.getNext();
		} else {
			currentPlayerLocation = currentPlayerLocation.getPrevious();
		}
		
		while (!(currentPlayerLocation instanceof BuyableSquare) || ((BuyableSquare) currentPlayerLocation).getOwner() != null) {
			if (diceValuesTotal % 2 == 0) {
				if (currentPlayerLocation instanceof TransitStation) {
					currentPlayerLocation = ((TransitStation) currentPlayerLocation).getDown();
				} else if (currentPlayerLocation instanceof RailRoadSquare) {
					currentPlayerLocation = ((RailRoadSquare) currentPlayerLocation).getUp();
				} else {
					if (currentPlayer.getDirection().equals(Piece.Direction.CLOCKWISE)) {
						currentPlayerLocation = currentPlayerLocation.getNext();
					} else {
						currentPlayerLocation = currentPlayerLocation.getPrevious();
					}
				}
			} else {
				if (currentPlayer.getDirection().equals(Piece.Direction.CLOCKWISE)) {
					currentPlayerLocation = currentPlayerLocation.getNext();
				} else {
					currentPlayerLocation = currentPlayerLocation.getPrevious();
				}
			}
		}
		
		return currentPlayerLocation;
	}
	
	public void doApplyMortgage(String stockName, int dummy) {
		Player currentPlayer = getCurrentPlayer();
		Stock stock = null;
		ArrayList<Player> players = getPlayers();
		int size = players.size();
		
		for (int i = 0; i < size; i++) {
			Player player = players.get(i);
			ArrayList<Stock> stocks = player.getStocks();
			int stocksSize = stocks.size();
			
			for (int j = 0; j < stocksSize; j++) {
				if (stocks.get(j).getName().equals(stockName)) {
					stock = stocks.get(j);
					break;
				}
			}
			
			if (stock != null) {
				break;
			}
		}
		
		Checker checker = getChecker();
		String result = checker.checkApplyMortgage(stock);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("true")) {
			currentPlayer.applyMortgageTo(stock);
		} else {
			
		}
	}
	
	public void doRemoveMortgage(String stockName, int dummy) {
		Player currentPlayer = getCurrentPlayer();
		Stock stock = null;
		ArrayList<Player> players = getPlayers();
		int size = players.size();
		
		for (int i = 0; i < size; i++) {
			Player player = players.get(i);
			ArrayList<Stock> stocks = player.getStocks();
			int stocksSize = stocks.size();
			
			for (int j = 0; j < stocksSize; j++) {
				if (stocks.get(j).getName().equals(stockName)) {
					stock = stocks.get(j);
					break;
				}
			}
			
			if (stock != null) {
				break;
			}
		}
		
		Checker checker = getChecker();
		String result = checker.checkRemoveMortgage(stock);
		DialogBuilder.informativeDialog(result);
		
		if (result.equals("true")) {
			currentPlayer.removeMortgageFrom(stock);
		} else {
			
		}
	}
	
	public void doSaveGame() {
		SaveLoadUtil.saveGame(this);
	}
	
	public void doLoadGame() {
		SaveLoadUtil.loadGame(this);
	}
	
	public static GameController getNewInstanceEachTime() {
		instance = new GameController();
		return instance;
	}
}