package domain;
import org.json.JSONException;
import org.json.JSONObject;


public class GameOptions {
	private static final String[] FIELD_NAMES = {"playerNumber", "isDebugging", "playerMoney", "currentPlayerIndex"};
	private int playerNum;
	private boolean isDebugging;
	private int playerMoney;
	private int currentPlayerIndex;
	
	public GameOptions(int playerNum, boolean isDebugging, int playerMoney, int currentPlayerIndex) {
		this.playerNum = playerNum;
		this.isDebugging = isDebugging;
		this.playerMoney = playerMoney;
		this.currentPlayerIndex = currentPlayerIndex;
	}
	
	public static GameOptions fromJSON(JSONObject gameOptionsAsJSON) {
		GameOptions gameOptions;
		int playerNum = 0;
		boolean isDebugging = false;
		int playerMoney = 0;
		int currentPlayerIndex = 0;
		
		try {
			playerNum = Integer.valueOf(gameOptionsAsJSON.getString(FIELD_NAMES[0]));
			isDebugging = Boolean.valueOf(gameOptionsAsJSON.getString(FIELD_NAMES[1]));
			playerMoney = Integer.valueOf(gameOptionsAsJSON.getString(FIELD_NAMES[2]));
			currentPlayerIndex = Integer.valueOf(gameOptionsAsJSON.getString(FIELD_NAMES[3]));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gameOptions = new GameOptions(playerNum, isDebugging, playerMoney, currentPlayerIndex);
		return gameOptions;
	}
	
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public boolean isDebugging() {
		return isDebugging;
	}

	public void setDebugging(boolean isDebugging) {
		this.isDebugging = isDebugging;
	}

	public int getPlayerMoney() {
		return playerMoney;
	}

	public void setPlayerMoney(int playerMoney) {
		this.playerMoney = playerMoney;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public static String[] getFieldNames() {
		return FIELD_NAMES;
	}
}