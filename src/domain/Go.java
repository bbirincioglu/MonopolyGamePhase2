package domain;
import org.json.JSONObject;


public class Go extends Square {

	public Go(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(200);
	}
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		piece.getOwner().receivePayment(200);
	}
	
	public static Go fromJSON(JSONObject squareAsJSON) {
		Go go = null;
		
		try {
			String name = squareAsJSON.getString("name");
			go = new Go(name);
		} catch (Exception e) {
			
		}
		
		return go;
	}
}
