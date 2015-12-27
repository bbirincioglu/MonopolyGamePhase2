package domain;

import java.awt.Color;
import java.util.ArrayList;

public class CardEvaluator {
	private Player currentPlayer;
	
	private void evaluateCard(Card card){
		String content = card.getContent();
		String[] actions = actions(content);
		String[] details = details(content);
		String action1 = actions[0];
		String action2 = (actions.length==2) ? actions[1] : null; 
		if(action1.equals("jail")){
			GoToJail jail=(GoToJail) GameController.getInstance().getMonopolyBoard().getOuterSquares().get(43);
			jail.landedOn(getCurrentPlayer().getPiece());
		}else if (action1.equals("free")){
			getCurrentPlayer().setInJail(false);
		}else if(action1.equals("advance all")){
			ArrayList<Player> players = GameController.getInstance().getPlayers();
			for (int i = 0; i < players.size(); i++) {
				players.get(i).move(GameController.getInstance().getMonopolyBoard().getOuterSquares().get(10));
			}
		}else if(action1.equals("advance")){
			if(details[0].equals("closest utility")){
				getCurrentPlayer().move(getCurrentPlayer().getCurrentLocation().getClosestUtility());
			}else if(details[0].equals("closest railroad")){
				new NearestRailRoad().picked(getCurrentPlayer());
			}else if (details[0].equals("3")){
				getCurrentPlayer().move(3);
			}else if(details[0].equals("stock exchange")){
				getCurrentPlayer().move(GameController.getInstance().getMonopolyBoard().getInnerSquares().get(13));
			}else if(details[0].equals("closest unowned")){
				Square closestOwned = getCurrentPlayer().getCurrentLocation().getClosestOwned();
				if(closestOwned!=null) getCurrentPlayer().move(closestOwned);
				else getCurrentPlayer().move(getCurrentPlayer().getCurrentLocation().getClosestUnowned());
			}else if(details[0].equals("roll one")){
				getCurrentPlayer().move(GameController.getInstance().getMonopolyBoard().getMiddleSquares().get(31));
			}else if(details[0].equals("black and white cab")){
				getCurrentPlayer().move(GameController.getInstance().getMonopolyBoard().getMiddleSquares().get(23));
			}else if(details[0].equals("current location")){
				getCurrentPlayer().move(getCurrentPlayer().getCurrentLocation());
			}else if(details[0].equals("tax refund")){
				getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()+GameController.getInstance().getMonopolyBoard().getBank().getPoolMoney());
				GameController.getInstance().getMonopolyBoard().getBank().setPoolMoney(0);
			}else if(details[0].equals("directly")){
				
			}
		}else if(action1.equals("pay") && action2==null){
			if(details[0].equals("per")){
				if(details[1].equals("25")){
					if(details[2].equals("house")){
						ArrayList<BuyableSquare> squares = getCurrentPlayer().getSquares();
						int totalNum = 0;
						for (int i = 0; i < squares.size(); i++) {
							if(((ColorSquare) squares.get(i))!=null)
							totalNum+= ((ColorSquare) squares.get(i)).getBuildingNum();
						}
						getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()-25*totalNum);
					}else if (details[2].equals("unmortgaged property")){
						getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()-25*getCurrentPlayer().getSquares().size());
					}else if (details[2].equals("40")){
						ArrayList<BuyableSquare> squares = getCurrentPlayer().getSquares();
						int totalNum = 0;
						for (int i = 0; i < squares.size(); i++) {
							if(((ColorSquare) squares.get(i))!=null)
							totalNum+= ((ColorSquare) squares.get(i)).getBuildingNum();
						}
						getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()-25*totalNum);
						
					}
				}
			}else if(details[0].equals("each")){
				ArrayList<Player> players = GameController.getInstance().getPlayers();
				for (int i = 0; i < players.size(); i++) {
					if(i!=GameController.getInstance().getCurrentPlayerIndex()){
						players.get(i).setMoney(players.get(i).getMoney()+50);
					}else 
						players.get(i).setMoney(players.get(i).getMoney()-50*7);
				}
			}else if(details[0].equals("50")){
				getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()-50);
				GameController.getInstance().getMonopolyBoard().getBank().receivePayment(50);
			}else if(details[0].equals("150")){
				getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()-150);
				GameController.getInstance().getMonopolyBoard().getBank().receivePayment(150);
			}
		}else if(action1.equals("downgrade")){
			ArrayList<BuyableSquare> s = getCurrentPlayer().getSquares();
			for (int i = 0; i < s.size(); i++) {
				int bn = ((ColorSquare) s.get(i)).getBuildingNum();
				((ColorSquare) s.get(i)).setBuildingNum(bn-1);
			}
		}else if(action1.equals("collect")){
			if(details[0].equals("100")){
				getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()+100);
			}else if(details[0].equals("150")){
				getCurrentPlayer().setMoney(getCurrentPlayer().getMoney()+150);
			}else if(details[0].equals("each")){
				ArrayList<Player> players = GameController.getInstance().getPlayers();
				for (int i = 0; i < players.size(); i++) {
					if(i!=GameController.getInstance().getCurrentPlayerIndex()){
						players.get(i).setMoney(players.get(i).getMoney()-10);
					}else 
						players.get(i).setMoney(players.get(i).getMoney()+10*7);
				}
			}
		}
			
			
	}
	private String[] actions(String content){
		String[] parts = content.split(".");
		String[] actions = new String[parts.length-1];
		for (int i = 0; i < actions.length; i++) {
			String[] descriptions = parts[i].split(",");
			actions[i]= descriptions[0];
		}
		return actions;
	}
	private String[] details(String content){
		String[] parts = content.split(".");
		String[] details = new String[parts.length-1];
		for (int i = 0; i < details.length; i++) {
			String part = parts[i];
			details[i]=part.substring(part.indexOf(",")+1,part.length());
		}
		return details;
	}
	
	
}
