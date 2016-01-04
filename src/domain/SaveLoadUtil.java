package domain;

import gui.MonopolyGame;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SaveLoadUtil {
	private static final String[] FIELD_NAMES = new String[]{"players", "currentPlayerIndex", "cup", "poolMoney"};
	private static final String saveLoadFileName = "saveLoad.txt";
	
	public static void saveGame(GameController gameController) {
		Writer writer = new Writer();
		
		ArrayList<Player> players = gameController.getPlayers();
		int currentPlayerIndex = gameController.getCurrentPlayerIndex();
		Cup cup = gameController.getCup();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		try {
			JSONObject gameAsJSON = new JSONObject();
			gameAsJSON.put(FIELD_NAMES[0], fromArrayListToJSONArray(players));
			gameAsJSON.put(FIELD_NAMES[1], currentPlayerIndex);
			gameAsJSON.put(FIELD_NAMES[2], cup.toJSON());
			gameAsJSON.put(FIELD_NAMES[3], bank.getPoolMoney());
			writer.writeGameData(saveLoadFileName, gameAsJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadGame(GameController gameController) {
		Reader reader = new Reader();
		JSONObject gameAsJSON = reader.readGameData(saveLoadFileName);
		
		try {
			ArrayList<Player> players = composePlayers(gameAsJSON.getJSONArray(FIELD_NAMES[0]), gameController);
			int currentPlayerIndex = gameAsJSON.getInt(FIELD_NAMES[1]);
			Cup cup = Cup.fromJSON(gameAsJSON.getJSONObject(FIELD_NAMES[2]));
			int poolMoney = gameAsJSON.getInt(FIELD_NAMES[3]);
			
			gameController.setPlayers(players);
			gameController.setCurrentPlayerIndex(currentPlayerIndex);
			gameController.setCup(cup);
			gameController.getMonopolyBoard().getBank().receivePayment(poolMoney);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<Player> composePlayers(JSONArray playersData, GameController gameController) {
		ArrayList<Player> players = new ArrayList<Player>();
		String[] FIELD_NAMES = Player.FIELD_NAMES;
		
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		try {
			int size = playersData.length();
			
			for (int i = 0; i < size; i++) {
				JSONObject playerData = playersData.getJSONObject(i);
				
				String name = playerData.getString(FIELD_NAMES[0]);
				int money = playerData.getInt(FIELD_NAMES[1]);			
				ArrayList<String> squaresData = decomposeString(playerData.getString(FIELD_NAMES[2]), ':');
				ArrayList<String> stocksData = decomposeString(playerData.getString(FIELD_NAMES[3]), ':');
				ArrayList<String> cardContents = decomposeString(playerData.getString(FIELD_NAMES[4]), ':');				
				String currentLocation = playerData.getString(FIELD_NAMES[5]);
				String direction = playerData.getString(FIELD_NAMES[6]);
				boolean isInJail = playerData.getBoolean(FIELD_NAMES[7]);
				int roundNumInJail = playerData.getInt(FIELD_NAMES[8]);
				
				Player player = new Player(name, money);
				
				for (String squareData : squaresData) {
					ArrayList<String> subStrings = decomposeString(squareData, ';');
					System.out.println(subStrings.size() + subStrings.toString());
					BuyableSquare buyableSquare = bank.getBuyableSquare(subStrings.get(0));
					buyableSquare.applySavedData(squareData);
					player.buySquare(bank, buyableSquare, 0);
				}
				
				for (String stockData : stocksData) {
					ArrayList<String> subStrings = decomposeString(stockData, ';');
					System.out.println(subStrings.size());
					Stock stock = bank.getStock(subStrings.get(0));
					stock.applySavedData(stockData);
					player.buyStock(bank, stock, 0);
				}
				
				for (String content : cardContents) {
					ChanceCard card = new ChanceCard(content);
					player.getCards().add(card);
				}
				
				player.moveImmediate(monopolyBoard.getSquare(currentLocation));
				player.setDirection(direction);
				player.setInJail(isInJail);
				player.setRoundNumInJail(roundNumInJail);
				players.add(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return players;
	}
	
	public static JSONArray fromArrayListToJSONArray(ArrayList elements) {
		JSONArray elementsJSONArray = new JSONArray();
		
		for (Object element : elements) {
			if (element instanceof Player) {
				Player player = (Player) element;
				elementsJSONArray.put(player.toJSON());
			}
		}
		
		return elementsJSONArray;
	}
	
	public static String composeString(ArrayList elements, char composeWith) {
		String composedString = "";
		int size = elements.size();
		
		for (int i = 0; i < size; i++) {
			Object element = elements.get(i);
			
			if (element instanceof BuyableSquare) {
				BuyableSquare square = (BuyableSquare) element;
				composedString = composedString + square.convertToSavedData() + composeWith;
			} else if (element instanceof Card) {
				Card card = (Card) element;
				composedString = composedString + card.getContent() + composeWith;
			} else if (element instanceof Stock) {
				Stock stock = (Stock) element;
				composedString = composedString + stock.convertToSavedData() + composeWith;
			}
		}
		
		if (!composedString.equals("")) {
			composedString = composedString.substring(0, composedString.length() - 1);
		}
		
		return composedString;
	}
	
	public static ArrayList<String> decomposeString(String composedString, char decomposeWith) {
		ArrayList<String> subStrings = new ArrayList<String>();
		
		int length = composedString.length();
		String temp = "";
		
		for (int i = 0; i < length; i++) {
			char charAtI = composedString.charAt(i);
			
			if (charAtI == decomposeWith) {
				subStrings.add(temp);
				temp = "";
			} else {
				temp += charAtI;
			}
		}
		
		if (temp != null && temp != "") {
			subStrings.add(temp);
		}
		
		return subStrings;
	}
	
	public static String getSaveLoadFileName() {
		return saveLoadFileName;
	}
}
