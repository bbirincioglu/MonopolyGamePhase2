package domain;

import java.awt.Color;
import java.util.ArrayList;

public class CardEvaluator {
	private Player currentPlayer;
	
	public void evaluateCard(Player currentPlayer, Card card){
		String content = card.getContent();
		String[] actions = actions(content);
		String[] details = details(content);
		String action1 = actions[0];
		String action2 = (actions.length==2) ? actions[1] : null; 
		if(action1.equals("jail")){
			GoToJail jail=(GoToJail) GameController.getInstance().getMonopolyBoard().getOuterSquares().get(42);
			jail.landedOn(currentPlayer.getPiece());
		}else if (action1.equals("free")){
			currentPlayer.setInJail(false);
		}else if(action1.equals("advance all")){
			ArrayList<Player> players = GameController.getInstance().getPlayers();
			for (int i = 0; i < players.size(); i++) {
				players.get(i).move(GameController.getInstance().getMonopolyBoard().getOuterSquares().get(9));
			}
		}else if(action1.equals("advance")){
			if(details[0].equals("closest utility")){
				//currentPlayer.move(currentPlayer.getCurrentLocation().getClosestUtility());
			}else if(details[0].equals("closest railroad")){
				new NearestRailRoad().picked(currentPlayer);
			}else if (details[0].equals("3")){
				currentPlayer.move(3);
			}else if(details[0].equals("stock exchange")){
				currentPlayer.move(GameController.getInstance().getMonopolyBoard().getInnerSquares().get(13));
			}else if(details[0].equals("closest unowned")){
				Square closestOwned = GameController.getInstance().getClosestSquareToPayRent(currentPlayer);
				if(closestOwned!=null) currentPlayer.move(closestOwned);
				else currentPlayer.move(GameController.getInstance().getClosestSquareToBuy(currentPlayer));
			}else if(details[0].equals("roll one")){
				currentPlayer.move(GameController.getInstance().getMonopolyBoard().getMiddleSquares().get(30));
			}else if(details[0].equals("black and white cab")){
				currentPlayer.move(GameController.getInstance().getMonopolyBoard().getOuterSquares().get(22));
			}else if(details[0].equals("current location")){
				currentPlayer.move(currentPlayer.getCurrentLocation());
			}else if(details[0].equals("tax refund")){
				currentPlayer.setMoney(currentPlayer.getMoney()+GameController.getInstance().getMonopolyBoard().getBank().getPoolMoney());
				GameController.getInstance().getMonopolyBoard().getBank().receivePayment(GameController.getInstance().getMonopolyBoard().getBank().getPoolMoney() * -1);
			}else if(details[0].equals("directly")){
				
			}
		}else if(action1.equals("pay") && action2==null){
			if(details[0].equals("per")){
				if(details[1].equals("25")){
					if(details[2].equals("house")){
						ArrayList<BuyableSquare> squares = currentPlayer.getSquares();
						int totalNum = 0;
						for (int i = 0; i < squares.size(); i++) {
							if(((ColorSquare) squares.get(i))!=null)
							totalNum+= ((ColorSquare) squares.get(i)).getBuildingNum();
						}
						currentPlayer.setMoney(currentPlayer.getMoney()-25*totalNum);
					}else if (details[2].equals("unmortgaged property")){
						currentPlayer.setMoney(currentPlayer.getMoney()-25*currentPlayer.getSquares().size());
					}else if (details[2].equals("40")){
						ArrayList<BuyableSquare> squares = currentPlayer.getSquares();
						int totalNum = 0;
						for (int i = 0; i < squares.size(); i++) {
							if(((ColorSquare) squares.get(i))!=null)
							totalNum+= ((ColorSquare) squares.get(i)).getBuildingNum();
						}
						currentPlayer.setMoney(currentPlayer.getMoney()-25*totalNum);
						
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
				currentPlayer.setMoney(currentPlayer.getMoney()-50);
				GameController.getInstance().getMonopolyBoard().getBank().receivePayment(50);
			}else if(details[0].equals("150")){
				currentPlayer.setMoney(currentPlayer.getMoney()-150);
				GameController.getInstance().getMonopolyBoard().getBank().receivePayment(150);
			}else if(details[0].equals("move")){
				if(details[1].equals("50")){
					//doldur
				}
			}
		}else if(action1.equals("downgrade")){
			ArrayList<BuyableSquare> s = currentPlayer.getSquares();
			for (int i = 0; i < s.size(); i++) {
				int bn = ((ColorSquare) s.get(i)).getBuildingNum();
				((ColorSquare) s.get(i)).setBuildingNum(bn-1);
			}
		}else if(action1.equals("collect")){
			if(details[0].equals("100")){
				currentPlayer.setMoney(currentPlayer.getMoney()+100);
			}else if(details[0].equals("150")){
				currentPlayer.setMoney(currentPlayer.getMoney()+150);
			}else if(details[0].equals("each")){
				ArrayList<Player> players = GameController.getInstance().getPlayers();
				for (int i = 0; i < players.size(); i++) {
					if(i!=GameController.getInstance().getCurrentPlayerIndex()){
						players.get(i).setMoney(players.get(i).getMoney()-10);
					}else 
						players.get(i).setMoney(players.get(i).getMoney()+10*7);
				}
			}
		}else if(action1.equals("bus ticket")){
			currentPlayer.getCards().clear();
			Square s = DialogBuilder.busTicketDialog(currentPlayer, GameController.getInstance().getMonopolyBoard());
			currentPlayer.move(s);
		}else if(action1.equals("stock")){
			if(details[0].equals("collect")){
				if(details[1].equals("radio")){
					ArrayList<Player> players = GameController.getInstance().getPlayers();
					for (int i = 0; i < players.size(); i++) {
						ArrayList<Stock> stocks = players.get(i).getStocks();
						int generalNum = 0;
						int motionNum = 0;
						for (int j = 0; j < stocks.size(); j++) {
							if(stocks.get(j).getName().contains("Motion")) motionNum++;
							if(stocks.get(j).getName().contains("General")) generalNum++;
						}
						players.get(i).receivePayment((int) (Math.pow(motionNum, 2)*10));
						players.get(i).receivePayment((int) (Math.pow(generalNum, 2)*10));
					}
				}
			}
		}else if(action1.equals("move")){
			if(details[0].equals("above")){
				MonopolyBoard mp =GameController.getInstance().getMonopolyBoard();
				Square s = currentPlayer.getCurrentLocation();
				if(mp.getInnerSquares().contains(s))
					//break;
				if(mp.getMiddleSquares().contains(s)){
					int index = mp.getMiddleSquares().indexOf(s);
					int q = index/10;
					int r = index%10;
					int newR = 0;
					int newQ = 0;
					int newIndex = 0;
					
					newR = r-2;
					newQ = q;
					if(r==0 || r==1 || r==2) newR = 0;
					
					if(r==8 || r==9){
						newR = 0;
						newQ = q+1;
					}
					newIndex = newQ*6+newR;
					Square newSquare = mp.getInnerSquares().get(newIndex);
					currentPlayer.move(newSquare);
				}
				if(mp.getOuterSquares().contains(s)){
					int index = mp.getOuterSquares().indexOf(s);
					int q = index/14;
					int r = index%14;
					int newR = 0;
					int newQ = 0;
					int newIndex = 0;
					
					newR = r-2;
					newQ = q;
					if(r==0 || r==1 || r==2) newR = 0;
					
					if(r==12 || r==13){
						newR = 0;
						newQ = q+1;
					}
					newIndex = newQ*10+newR;
					Square newSquare = mp.getMiddleSquares().get(newIndex);
					currentPlayer.move(newSquare);
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
