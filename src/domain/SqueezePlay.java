package domain;
import java.util.ArrayList;

import org.json.JSONObject;


public class SqueezePlay extends Square {

	public SqueezePlay(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		GameController gameController = GameController.getInstance();
		ArrayList<Player> players = gameController.getPlayers();
		Player currentPlayer = gameController.getCurrentPlayer();
		Cup cup = gameController.getCup();
		cup.roll2Dice();
		int diceValuesTotal = cup.getDiceValuesTotal();
		int payment;
		
		if (5 <= diceValuesTotal && diceValuesTotal <= 9) {
			payment = 50;
		} else if ((3 <= diceValuesTotal && diceValuesTotal <= 4) || (10 <= diceValuesTotal && diceValuesTotal <= 11)) {
			payment = 100;
		} else {
			payment = 200;
		}
		
		for (Player player : players) {
			player.makePayment(currentPlayer, payment);
		}	
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static SqueezePlay fromJSON(JSONObject squareAsJSON) {
		SqueezePlay squeezePlay = null;
		
		try {
			String name = squareAsJSON.getString("name");
			squeezePlay = new SqueezePlay(name);
		} catch (Exception e) {
			
		}
		
		return squeezePlay;
	}
}