package domain;

import java.util.ArrayList;

import org.json.JSONObject;

public class DebugUtil {
	public static void applyDebugOptions(ArrayList<Player> players, JSONObject playerAsJSON, MonopolyBoard monopolyBoard, Cup cup) {
		Bank bank = monopolyBoard.getBank();
		
		try {
			String name = playerAsJSON.getString("name");
			int money = playerAsJSON.getInt("money");
			
			String squares = playerAsJSON.getString("squares");
			String cards = playerAsJSON.getString("cards");
			String stocks = playerAsJSON.getString("stocks");
			String currentLocation = playerAsJSON.getString("currentLocation");
			
			int die1Value = playerAsJSON.getInt("die1Value");
			int die2Value = playerAsJSON.getInt("die2Value");
			int speedDieValue = playerAsJSON.getInt("speedDieValue");
			
			Player player = new Player(name, money);
			player.moveImmediate(monopolyBoard.getSquare(currentLocation));
			buySquares(player, bank, squares);
			buyStocks(player, bank, stocks);
			//pickCards(player, monopolyBoard, cards);
			putDiceValues(cup, die1Value, die2Value, speedDieValue);
			
			players.add(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void buyStocks(Player player, Bank bank, String stocks) {
		ArrayList<String> stockNames = mySplit(stocks, ':');
		
		for (String stockName : stockNames) {
			player.buyStock(bank, bank.getStock(stockName), 0);
		}
	}
	
	private static void buySquares(Player player, Bank bank, String squares) {
		ArrayList<String> squareNames = mySplit(squares, ':');
		
		for (String squareName : squareNames) {
			ArrayList<String> splittedSquareName = mySplit(squareName, ',');
			int buildingNum = 0;
			
			if (splittedSquareName.size() != 1) {
				buildingNum = Integer.valueOf(splittedSquareName.get(1));
			}
			
			BuyableSquare buyableSquare = bank.getBuyableSquare(splittedSquareName.get(0));
			
			if (buyableSquare instanceof ColorSquare) {
				((ColorSquare) buyableSquare).setBuildingNum(buildingNum);
			} else if (buyableSquare instanceof RailRoadSquare) {
				if (buildingNum == 1) {
					((RailRoadSquare) buyableSquare).setTrainDepotBuilt(true);
				}
			}
			
			System.out.println(splittedSquareName.toString());
			player.buySquare(bank, buyableSquare, 0);
		}
	}
	
	private static void putDiceValues(Cup cup, int die1Value, int die2Value, int speedDieValue) {
		int[] diceValues = new int[] {die1Value, die2Value, speedDieValue};
		cup.getDebugValues().add(diceValues);
	}
	
	private static ArrayList<String> mySplit(String text, char splitChar) {
		ArrayList<String> splittedText = new ArrayList<String>();
		String temp = "";
		int length = text.length();
		
		for (int i = 0; i < length; i++) {
			char charAtI = text.charAt(i);
			
			if (charAtI == splitChar) {
				splittedText.add(temp);
				temp = "";
			} else {
				temp += charAtI;
			}
		}
		
		if (temp != null && !temp.equals("")) {
			splittedText.add(temp);
		}
		
		return splittedText;
	}
	
	private static void pickCards(Player player, MonopolyBoard monopolyBoard, String cards) {
		
	}
}