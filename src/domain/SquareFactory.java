package domain;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class SquareFactory {
	private static SquareFactory instance;
	
	private SquareFactory() {
		
	}
	
	public static SquareFactory getInstance() {
		if (instance == null) {
			instance = new SquareFactory();
		}
		
		return instance;
	}
	
	public Square createSquare(JSONObject squareAsJSON) {
		Square square = null;
		ArrayList<JSONObject> descriptionsAsJSON = Reader.read("descriptions.txt");
		
		try {
			String type = squareAsJSON.getString("type");
			
			if (type.equals("ColorSquare")) {
				square = ColorSquare.fromJSON(squareAsJSON);
			} else if (type.equals("UtilitySquare")) {
				UtilitySquare.setDescription(UtilityDescription.fromJSON(descriptionsAsJSON.get(0)));
				square = UtilitySquare.fromJSON(squareAsJSON);
			} else if (type.equals("CabSquare")) {
				CabSquare.setDescription(CabDescription.fromJSON(descriptionsAsJSON.get(1)));
				square = CabSquare.fromJSON(squareAsJSON);
			} else if (type.equals("RailRoadSquare")) {
				RailRoadSquare.setDescription(RailRoadDescription.fromJSON(descriptionsAsJSON.get(2)));
				square = RailRoadSquare.fromJSON(squareAsJSON);
			} else if (type.equals("VisitingJail")) {
				square = VisitingJail.fromJSON(squareAsJSON);
			} else if (type.equals("TransitStation")) {
				square = TransitStation.fromJSON(squareAsJSON);
			} else if (type.equals("TaxRefund")) {
				square = TaxRefund.fromJSON(squareAsJSON);
			} else if (type.equals("StockExchange")) {
				square = StockExchange.fromJSON(squareAsJSON);
			} else if (type.equals("SqueezePlay")) {
				square = SqueezePlay.fromJSON(squareAsJSON);
			} else if (type.equals("RollOne")) {
				square = RollOne.fromJSON(squareAsJSON);
			} else if (type.equals("ReverseDirection")) {
				square = ReverseDirection.fromJSON(squareAsJSON);
			} else if (type.equals("PayDay")) {
				square = PayDay.fromJSON(squareAsJSON);
			} else if (type.equals("LuxuryTax")) {
				square = LuxuryTax.fromJSON(squareAsJSON);
			} else if (type.equals("IncomeTax")) {
				square = IncomeTax.fromJSON(squareAsJSON);
			} else if (type.equals("HollandTunnel")) {
				square = HollandTunnel.fromJSON(squareAsJSON);
			} else if (type.equals("GoToJail")) {
				square = GoToJail.fromJSON(squareAsJSON);
			} else if (type.equals("Go")) {
				square = Go.fromJSON(squareAsJSON);
			} else if (type.equals("FreeParking")) {
				square = FreeParking.fromJSON(squareAsJSON);
			} else if (type.equals("CommunitySquare")) {
				square = CommunitySquare.fromJSON(squareAsJSON);
			} else if (type.equals("ChanceSquare")) {
				square = ChanceSquare.fromJSON(squareAsJSON);
			} else if (type.equals("BusTicket")) {
				square = BusTicket.fromJSON(squareAsJSON);
			} else if (type.equals("Bonus")) {
				square = Bonus.fromJSON(squareAsJSON);
			} else if (type.equals("BirthdayGift")) {
				square = BirthdayGift.fromJSON(squareAsJSON);
			} else if (type.equals("AuctionSquare")) {
				square = AuctionSquare.fromJSON(squareAsJSON);
			} else if (type.equals("Subway")) {
				square = Subway.fromJSON(squareAsJSON);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(square.getName());
		return square;
	}
}
