package domain;
import org.json.JSONObject;


public class CommunitySquare extends Square{

	public CommunitySquare(String name) {
		super(name);
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Player player = piece.getOwner();
		CommunityCard communityCard = player.selectCommunityCard(GameController.getInstance().getMonopolyBoard());
		String[] splitArray = communityCard.getContent().split(".");
		String content = splitArray[splitArray.length - 2];
		DialogBuilder.informativeDialog(content);
		CardEvaluator cardEvaluator = GameController.getInstance().getCardEvaluator();
		cardEvaluator.evaluateCard(player, communityCard);
	}

	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static CommunitySquare fromJSON(JSONObject squareAsJSON) {
		CommunitySquare communitySquare = null;
		
		try {
			String name = squareAsJSON.getString("name");
			communitySquare = new CommunitySquare(name);
		} catch (Exception e) {
			
		}
		
		return communitySquare;
	}
}
