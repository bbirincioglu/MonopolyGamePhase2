package domain;

import java.util.ArrayList;

import org.json.JSONArray;

public class FromArrayListToJSONArray {
	public JSONArray convert(ArrayList elements) {
		JSONArray elementsJSONArray = new JSONArray();
		
		for (Object element : elements) {
			if (element instanceof Square) {
				Square elementAsSquare = (Square) element;
				elementsJSONArray.put(elementAsSquare.toJSON());
			} else if (element instanceof Card) {
				Card elementAsCard = (Card) element;
				elementsJSONArray.put(elementAsCard.toJSON());
			} else if (element instanceof Player) {
				Player elementAsPlayer = (Player) element;
				elementsJSONArray.put(elementAsPlayer.toJSON());
			} else if (element instanceof Piece) {
				Piece elementAsPiece = (Piece) element;
				elementsJSONArray.put(elementAsPiece.toJSON());
			} else if (element instanceof Stock) {
				Stock elementAsStock = (Stock) element;
				elementsJSONArray.put(elementAsStock.toJSON());
			}
		}
		
		return elementsJSONArray;
	}
}
