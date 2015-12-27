package domain;

import org.json.JSONException;
import org.json.JSONObject;

public class CardFactory {
	private static CardFactory instance;
	private CardFactory(){}
	
	public static CardFactory getInstance(){
		if(instance==null)
			instance= new CardFactory();
		return instance;
	}
	
	public CommunityCard createCommunityCard(JSONObject cardAsJSON) throws JSONException{
		String content = cardAsJSON.getString("content");
		return new CommunityCard(content);
		
	}
	public ChanceCard createChanceCard(JSONObject cardAsJSON) throws JSONException{
		String content = cardAsJSON.getString("content");
		return new ChanceCard(content);
		
	}
	/*private String[] actions(String content){
		String[] parts = content.split(".");
		String[] actions = new String[parts.length-1];
		for (int i = 0; i < actions.length; i++) {
			String[] descriptions = parts[i].split(",");
			actions[i]= descriptions[0];
		}
		return actions;
	}*/
	
}
