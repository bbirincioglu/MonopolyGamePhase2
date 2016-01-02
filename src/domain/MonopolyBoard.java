package domain;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** MonopolyBoard class contains squares, chance and community cards, and bank.
 * 
 */

public class MonopolyBoard {
	private static final String[] FIELD_NAMES = new String[] {"outerSquares", "middleSquares", "innerSquares", "chanceCards", "currentChanceCardIndex", "communityCards", "currentCommunityCardIndex"};
	private ArrayList<Square> outerSquares;
	private ArrayList<Square> middleSquares;
	private ArrayList<Square> innerSquares;
	
	private ArrayList<ChanceCard> chanceCards;
	private int currentChanceCardIndex;
	
	private ArrayList<CommunityCard> communityCards;
	private int currentCommunityCardIndex;
	
	private Bank bank;
	/**
	 * Constructor for MonopolyBoard class.
	 * @effects initializes bank, chanceCard, communityCards, outerSquares, middleSquares, innerSquares
	 * currentChanceCardIndex, currentCommunityCardIndex fields. Connects squares, and shuffles cards.
	 */
	public MonopolyBoard() {
		initializeCards();
		initializeSquares();
		connectSquares(getOuterSquares(), getMiddleSquares(), getInnerSquares());
		setBank(new Bank(getOuterSquares(), getMiddleSquares(), getInnerSquares()));
		shuffleCards(getChanceCards(), getCommunityCards());
		setCurrentChanceCardIndex(0);
		setCurrentCommunityCardIndex(0);
	}
	
	private void shuffleCards(ArrayList<ChanceCard> chanceCards, ArrayList<CommunityCard> communityCards) {
		//@requires:chanceCards is not null, and communityCards is not null
		//@modifies: chanceCards, communityCards
		//@effects:shuffles the given chanceCards and communityCards
		
		Random random = new Random();
		
		for (int i = 0; i < chanceCards.size(); i++) {
			int index1 = i;
			int index2 = i + random.nextInt(chanceCards.size() - i);
			
			ChanceCard temp = chanceCards.get(index1);
			chanceCards.set(index1, chanceCards.get(index2));
			chanceCards.set(index2, temp);
		}
		
		for (int i = 0; i < communityCards.size(); i++) {
			int index1 = i;
			int index2 = i + random.nextInt(communityCards.size() - i);
			
			CommunityCard temp = communityCards.get(index1);
			communityCards.set(index1, communityCards.get(index2));
			communityCards.set(index2, temp);
		}
	}
	
	public void initializeCards() {
		//@requires:chanceCards is not null, and communityCards is not null
		//@modifies: chanceCards, communityCards
		//@effects:shuffles the given chanceCards and communityCards
		setChanceCards(new ArrayList<ChanceCard>());
		setCommunityCards(new ArrayList<CommunityCard>());
		setCurrentChanceCardIndex(0);
		setCurrentCommunityCardIndex(0);
		
		ArrayList<JSONObject> chanceCardsAsJSON = Reader.read("chance.txt");
		ArrayList<JSONObject> communityCardsAsJSON = Reader.read("community.txt");
		CardFactory cardFactory = CardFactory.getInstance();
		
		for (int i = 0; i < chanceCardsAsJSON.size(); i++) {
			try {
				getChanceCards().add(cardFactory.createChanceCard(chanceCardsAsJSON.get(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < communityCardsAsJSON.size(); i++) {
			try {
				getCommunityCards().add(cardFactory.createCommunityCard(communityCardsAsJSON.get(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void initializeSquares() {
		setOuterSquares(new ArrayList<Square>());
		setMiddleSquares(new ArrayList<Square>());
		setInnerSquares(new ArrayList<Square>());
		
		ArrayList<Square> outerSquares = getOuterSquares();
		ArrayList<Square> middleSquares = getMiddleSquares();
		ArrayList<Square> innerSquares = getInnerSquares();
		
		ArrayList<JSONObject> outerSquaresAsJSON = Reader.read("outerSquares.txt");
		ArrayList<JSONObject> middleSquaresAsJSON = Reader.read("middleSquares.txt");
		ArrayList<JSONObject> innerSquaresAsJSON = Reader.read("innerSquares.txt");
		SquareFactory squareFactory = SquareFactory.getInstance();
		
		for (int i = 0; i < outerSquaresAsJSON.size(); i++) {
			outerSquares.add(squareFactory.createSquare(outerSquaresAsJSON.get(i)));
		}
		
		for (int i = 0; i < middleSquaresAsJSON.size(); i++) {
			middleSquares.add(squareFactory.createSquare(middleSquaresAsJSON.get(i)));
		}
		
		for (int i = 0; i < innerSquaresAsJSON.size(); i++) {
			innerSquares.add(squareFactory.createSquare(innerSquaresAsJSON.get(i)));
		}
	}
	
	private void printNames(ArrayList<JSONObject> list) {
		for (int i = 0; i < list.size(); i++) {
			try {
				System.out.println(list.get(i).getString("name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void connectSquares(ArrayList<Square> outerSquares, ArrayList<Square> middleSquares, ArrayList<Square> innerSquares) {
		connectRegularly(outerSquares);
		connectRegularly(middleSquares);
		connectRegularly(innerSquares);
		connectHollandTunnels(outerSquares, innerSquares);
		connectRailRoadsWithStations(outerSquares, middleSquares, innerSquares);
		connectGoToJailWithVisitingJail();
	}
	
	private void connectRailRoadsWithStations(ArrayList<Square> outerSquares, ArrayList<Square> middleSquares, ArrayList<Square> innerSquares) {
		RailRoadSquare readingRailRoad = (RailRoadSquare) getSquare("READING RAILROAD");
		RailRoadSquare bAndORailRoad = (RailRoadSquare) getSquare("B&0 RAILROAD");
		RailRoadSquare pennsylvaniaRailRoad = (RailRoadSquare) getSquare("PENNSYLVANIA RAILROAD");
		RailRoadSquare shortLineRailRoad = (RailRoadSquare) getSquare("SHORT LINE");
		
		TransitStation[] stations = new TransitStation[4];	
		int index = 0;
		
		for (int i = 0; i < middleSquares.size(); i++) {
			if (middleSquares.get(i).getName().equals("TransitStation")) {
				stations[index] = (TransitStation) middleSquares.get(i);
				index = index + 1;
			}
		}
		
		for (int i = 0; i < innerSquares.size(); i++) {
			if (innerSquares.get(i).getName().equals("TransitStation")) {
				stations[index] = (TransitStation) innerSquares.get(i);
				index = index + 1;
			}
		}
		
		readingRailRoad.setUp(stations[0]);
		stations[0].setDown(readingRailRoad);
		bAndORailRoad.setUp(stations[1]);
		stations[1].setDown(bAndORailRoad);
		pennsylvaniaRailRoad.setUp(stations[2]);
		stations[2].setDown(pennsylvaniaRailRoad);
		shortLineRailRoad.setUp(stations[3]);
		stations[3].setDown(shortLineRailRoad);
	}
	
	private void connectGoToJailWithVisitingJail() {
		GoToJail goToJail = (GoToJail) getSquare("GoToJail");
		VisitingJail visitingJail = (VisitingJail) getSquare("VisitingJail");
		goToJail.setVisitingJail(visitingJail);
	}
	
	private void connectHollandTunnels(ArrayList<Square> outerSquares, ArrayList<Square> innerSquares) {
		HollandTunnel outerHollandTunnel = (HollandTunnel) getSquareHelper("HollandTunnel", outerSquares);
		HollandTunnel innerHollandTunnel = (HollandTunnel) getSquareHelper("HollandTunnel", innerSquares);
		outerHollandTunnel.setOpposite(innerHollandTunnel);
		innerHollandTunnel.setOpposite(outerHollandTunnel);
	}
	
	private void connectRegularly(ArrayList<Square> squares) {
		int size = squares.size();
		
		for (int i = 0; i < size; i++) {
			Square current = squares.get(i);
			int previousIndex = i - 1;
			
			if (previousIndex < 0) {
				previousIndex = previousIndex + size;
			}
			
			int nextIndex = (i + 1) % size;
			
			Square previous = squares.get(previousIndex);
			Square next = squares.get(nextIndex);
			
			current.setNext(next);
			current.setPrevious(previous);
		}
	}
	
	/**
	 * Returns the square with specified name by searching all layers of monopoly board.
	 * @param name name of the square to search for.
	 * @requires name != null
	 * @effects returns the square with given name, or null if can't found.
	 * @return square with specified name, or null if can't found.
	 */
	
	public Square getSquare(String name) {
		Square square = null;
		ArrayList<Square> outerSquares = getOuterSquares();
		ArrayList<Square> middleSquares = getMiddleSquares();
		ArrayList<Square> innerSquares = getInnerSquares();
		
		square = getSquareHelper(name, outerSquares);
		
		if (square == null) {
			square = getSquareHelper(name, middleSquares);
			
			if (square == null) {
				square = getSquareHelper(name, innerSquares);
			}
		}
		
		return square;
	}
	
	private Square getSquareHelper(String name, ArrayList<Square> squares) {
		Square square = null;
		
		for (int i = 0; i < squares.size(); i++) {
			if (squares.get(i).getName().equals(name)) {
				square = squares.get(i);
				break;
			}
		}
		
		return square;
	}
	
	/**
	 * Returns a chance card according to currentChanceCardIndex.
	 * @requires chanceCards != null
	 * @effects returns the chance card according to currentChanceCardIndex.
	 * @return a chance card according to currentChanceCardIndex.
	 */
	
	public ChanceCard getChanceCard() {
		ChanceCard chanceCard = getChanceCards().get(getCurrentChanceCardIndex());
		setCurrentChanceCardIndex((getCurrentChanceCardIndex() + 1) % getChanceCards().size());
		return chanceCard;
	}
	
	/**
	 * Returns a community card according to community card index.
	 * @requires communityCards != null
	 * @effects returns the community card according to currentCommunityCardIndex.
	 * @return a community card according to community card index
	 */
	
	public CommunityCard getCommunityCard() {
		CommunityCard communityCard = getCommunityCards().get(getCurrentCommunityCardIndex());
		setCurrentCommunityCardIndex((getCurrentCommunityCardIndex() + 1) % getCommunityCards().size());
		return communityCard;
	}
	
	/**
	 * Returns the bank instance stored as a field.
	 * @effects returns the bank instance stored as a field.
	 * @return the bank instance stored as a field.
	 */
	
	public Bank getBank() {
		return bank;
	}
	
	/**
	 * Sets bank field to the specified bank instance.
	 * @effects sets bank field to the specified bank instance.
	 * @modifies this
	 * @param bank bank instance to be referenced.
	 */

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	/**
	 * Returns the squares located at the outer most layer of the board as a list.
	 * @effects returns the squares located at the outer most layer of the board as a list.
	 * @return squares that are in outer most layer.
	 */
	
	public ArrayList<Square> getOuterSquares() {
		return outerSquares;
	}
	
	/**
	 * Sets outerSquares field to the specified list.
	 * @modifies this
	 * @effects sets outerSquares field to the specified list.
	 * @param outerSquares a list of squares in outer most layer to be referenced.
	 */
	
	public void setOuterSquares(ArrayList<Square> outerSquares) {
		this.outerSquares = outerSquares;
	}
	
	/**
	 * Gets the list of squares located at the middle layer of monopoly board.
	 * @return the list of squares located at the middle layer of monopoly board.
	 */
	
	public ArrayList<Square> getMiddleSquares() {
		return middleSquares;
	}
	
	/**
	 * Sets the middle squares field to the specified ArrayList instance.
	 * @param middleSquares list of squares located in middle layer to be referenced.
	 */

	public void setMiddleSquares(ArrayList<Square> middleSquares) {
		this.middleSquares = middleSquares;
	}
	
	/**
	 * Gets the list of squares located at the inner most layer of monopoly board.
	 * @return the list of squares located at the inner most layer of monopoly board.
	 */
	
	public ArrayList<Square> getInnerSquares() {
		return innerSquares;
	}
	
	/**
	 * Sets the inner squares field to the specified ArrayList instance.
	 * @param innerSquares list of squares located in inner most layer to be referenced.
	 */

	public void setInnerSquares(ArrayList<Square> innerSquares) {
		this.innerSquares = innerSquares;
	}
	/**
	 * Gets the list of chance cards that are not taken by the players.
	 * @return the list of chance cards that are not taken by the players.
	 */
	
	public ArrayList<ChanceCard> getChanceCards() {
		return chanceCards;
	}
	
	/**
	 * Sets the chance cards field to the specified ArrayList instance.
	 * @param chanceCards the list of chance cards to be referenced.
	 */

	public void setChanceCards(ArrayList<ChanceCard> chanceCards) {
		this.chanceCards = chanceCards;
	}
	
	/**
	 * Gets the list of community cards that are not taken by the players.
	 * @return the list of community cards that are not taken by the players.
	 */

	public ArrayList<CommunityCard> getCommunityCards() {
		return communityCards;
	}
	
	/**
	 * Sets the community cards field to the specified ArrayList instance.
	 * @param communityCards the list of community cards to be referenced.
	 */

	public void setCommunityCards(ArrayList<CommunityCard> communityCards) {
		this.communityCards = communityCards;
	}
	
	/**
	 * Gets the current chance card index which is used to pick a chance card from the chance cards ArrayList. 
	 * @return chance card index
	 */
	public int getCurrentChanceCardIndex() {
		return currentChanceCardIndex;
	}
	
	/**
	 * Sets the current chance card index with specified argument.
	 * @param currentChanceCardIndex current chance card index to be set.
	 */
	public void setCurrentChanceCardIndex(int currentChanceCardIndex) {
		this.currentChanceCardIndex = currentChanceCardIndex;
	}
	
	/**
	 * Gets the current community card index which is used to pick a community card from the community cards ArrayList. 
	 * @return community card index
	 */
	public int getCurrentCommunityCardIndex() {
		return currentCommunityCardIndex;
	}
	
	/**
	 * Sets the current community card index with specified argument.
	 * @param currentCommunityCardIndex current community card index to be set.
	 */
	
	public void setCurrentCommunityCardIndex(int currentCommunityCardIndex) {
		this.currentCommunityCardIndex = currentCommunityCardIndex;
	}
	
	/**
	 * Checks whether concrete representation of this object is correct.
	 * @effects return true if concrete representation of this object is correct, otherwise false
	 * @return true if concrete representation of this object is correct, otherwise false
	 */
	public boolean repOK() {
		boolean control = true;
		DuplicateElementChecker dec = new DuplicateElementChecker();
		boolean has1 = dec.hasDuplicateElements(getOuterSquares());
		boolean has2 = dec.hasDuplicateElements(getMiddleSquares());
		boolean has3 = dec.hasDuplicateElements(getInnerSquares());
		boolean has4 = dec.hasDuplicateElements(getChanceCards());
		boolean has5 = dec.hasDuplicateElements(getCommunityCards());
		boolean isChanceCardIndexNegative = getCurrentChanceCardIndex() < 0 ? true : false;
		boolean isCommunityCardIndexNegative = getCurrentCommunityCardIndex() < 0 ? true : false;
		
		if (has1 || has2 || has3 || has4 || has5 || !getBank().repOK() || isChanceCardIndexNegative || isCommunityCardIndexNegative) {
			control = false;
		}
		
		return control;
	}
	
	/**
	 * @effects returns json object representation of this object.
	 * @return json object representation of this object.
	 */
	
	/*public JSONObject toJSON() {
		JSONObject monopolyBoardAsJSON = new JSONObject();
		
		try {
			FromArrayListToJSONArray converter = new FromArrayListToJSONArray();
			
			monopolyBoardAsJSON.put(FIELD_NAMES[0], converter.convert(getOuterSquares()));
			monopolyBoardAsJSON.put(FIELD_NAMES[1], converter.convert(getMiddleSquares()));
			monopolyBoardAsJSON.put(FIELD_NAMES[2], converter.convert(getInnerSquares()));
			monopolyBoardAsJSON.put(FIELD_NAMES[3], converter.convert(getChanceCards()));
			monopolyBoardAsJSON.put(FIELD_NAMES[4], getCurrentChanceCardIndex());
			monopolyBoardAsJSON.put(FIELD_NAMES[5], converter.convert(getCommunityCards()));
			monopolyBoardAsJSON.put(FIELD_NAMES[6], getCurrentCommunityCardIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return monopolyBoardAsJSON;
	}*/
	
	/**
	 * Returns string representation of this object.
	 * @effects returns string representation of this object.
	 * @return string representation of this object.
	 */
	
	/*public String toString() {
		return toJSON().toString();
	}*/
	
	public ArrayList<Square> getSquaresAtSameLevel(Square square) {
		ArrayList<Square> outerSquares = getOuterSquares();
		ArrayList<Square> middleSquares = getMiddleSquares();
		ArrayList<Square> innerSquares = getInnerSquares();
		
		for (Square squareInList : outerSquares) {
			if (square.equals(squareInList)) {
				return outerSquares;
			}
		}
		
		for (Square squareInList : middleSquares) {
			if (square.equals(squareInList)) {
				return middleSquares;
			}
		}
		
		for (Square squareInList : innerSquares) {
			if (square.equals(squareInList)) {
				return innerSquares;
			}
		}
		
		return null;
	}
}