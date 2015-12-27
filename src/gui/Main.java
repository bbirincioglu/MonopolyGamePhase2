package gui;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Main {
	public static void main(String[] args) throws JSONException {
		/*ArrayList<String> outerSquares = new ArrayList<String>();
		ArrayList<String> middleSquares = new ArrayList<String>();
		ArrayList<String> innerSquares = new ArrayList<String>();
		
		for (int i = 0; i < 56; i++) {
			outerSquares.add("outer");
		}
		
		for (int i = 0; i < 40; i++) {
			middleSquares.add("middle");
		}
		
		for (int i = 0; i < 24; i++) {
			innerSquares.add("inner");
		}
		
		MonopolyBoard monopolyBoard = new MonopolyBoard(outerSquares, middleSquares, innerSquares);*/
		MonopolyGame monopolyGame = new MonopolyGame();
		
		//JSONObject object = new JSONObject("{type:\"ColorSquare\", name:\"Maria Avenue\", rents:[25, 50, 75, 100]");
	}
}
