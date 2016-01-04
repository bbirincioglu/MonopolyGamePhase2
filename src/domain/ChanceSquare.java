package domain;
import org.json.JSONObject;


public class ChanceSquare extends Square {

	public ChanceSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Piece piece) {
		// TODO Auto-generated method stub
		Player player = piece.getOwner();
		ChanceCard chanceCard = player.selectChanceCard(GameController.getInstance().getMonopolyBoard());
		String[] splitArray = mySplit(chanceCard.getContent(), ".");
		String content = splitArray[splitArray.length - 1];
		DialogBuilder.informativeDialog(content);
		CardEvaluator cardEvaluator = GameController.getInstance().getCardEvaluator();
		cardEvaluator.evaluateCard(player, chanceCard);
	}
	
	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	public static ChanceSquare fromJSON(JSONObject squareAsJSON) {
		ChanceSquare chanceSquare = null;
		
		try {
			String name = squareAsJSON.getString("name");
			chanceSquare = new ChanceSquare(name);
		} catch (Exception e) {
			
		}
		
		return chanceSquare;
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
}