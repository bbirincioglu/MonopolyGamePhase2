package domain;
import org.json.JSONObject;

public class GoToJail extends Square {
	private VisitingJail visitingJail;
	
	public GoToJail(String name) {
		super(name);
		setVisitingJail(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Player player = piece.getOwner();
		player.moveImmediate(getVisitingJail());
		player.setInJail(true);
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static GoToJail fromJSON(JSONObject squareAsJSON) {
		GoToJail goToJail = null;
		
		try {
			String name = squareAsJSON.getString("name");
			goToJail = new GoToJail(name);
		} catch (Exception e) {
			
		}
		
		return goToJail;
	}

	public VisitingJail getVisitingJail() {
		return visitingJail;
	}

	public void setVisitingJail(VisitingJail visitingJail) {
		this.visitingJail = visitingJail;
	}
}
