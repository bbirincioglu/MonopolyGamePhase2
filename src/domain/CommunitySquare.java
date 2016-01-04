package domain;
import java.util.ArrayList;

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
		String[] splitArray = mySplit(communityCard.getContent(), ".");
		String content = splitArray[splitArray.length - 1];
		DialogBuilder.informativeDialog(content);
		CardEvaluator cardEvaluator = GameController.getInstance().getCardEvaluator();
		cardEvaluator.evaluateCard(player, communityCard);
	}
	
	private String[] mySplit(String content, String splitWith) {
		String[] splittedContent;
		int length = content.length();
		int splitWithNum = 0;
		
		for (int i = 0; i < length; i++) {
			String charAtI = "" + content.charAt(i);
			
			if (charAtI.equals(splitWith)) {
				splitWithNum += 1;
			}
		}
		
		splittedContent = new String[splitWithNum + 1];
		String subString = "";
		int splittedContentIndex = 0;
		
		for (int i = 0; i < length; i++) {
			String charAtI = "" + content.charAt(i);
			
			if (charAtI.equals(splitWith)) {
				splittedContent[splittedContentIndex] = subString;
				subString = "";
				splittedContentIndex++;
			} else {
				subString += charAtI;
			}
		}
		
		if (!subString.equals("")) {
			splittedContent[splittedContentIndex] = subString;
		}
		
		return splittedContent;
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