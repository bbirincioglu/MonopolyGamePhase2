package domain;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class ColorSquare extends BuyableSquare {
	private static final String[] FIELD_NAMES = {"name", "isMortgaged", "buildingNum", "price", "rents", "houseCost", "hotelCost", "skyscraperCost", "mortgageValue", "color"};
	
	private ArrayList<Integer> rents;
	private int houseCost;
	private int hotelCost;
	private int skyscraperCost;
	private int mortgageValue;
	private String color;
	private int buildingNum;
	/**
	 * 
	 * @param name
	 * @param price
	 * @param rents
	 * @param houseCost
	 * @param hotelCost
	 * @param skyscraperCost
	 * @param mortgageValue
	 * @param color
	 */
	
	public ColorSquare(String name, boolean isMortgaged, int buildingNum, int price, ArrayList<Integer> rents,int houseCost,
			int hotelCost, int skyscraperCost,int mortgageValue,String color) {
		super(name, isMortgaged, price);
		this.rents=rents;
		this.houseCost=houseCost;
		this.hotelCost=hotelCost;
		this.skyscraperCost=skyscraperCost;
		this.mortgageValue=mortgageValue;
		this.color=color;
		this.buildingNum = buildingNum;
		// TODO Auto-generated constructor stub
	}
	

	public ArrayList<Integer> getRents() {
		return rents;
	}


	public void setRents(ArrayList<Integer> rents) {
		this.rents = rents;
	}


	public int getHouseCost() {
		return houseCost;
	}


	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}


	public int getHotelCost() {
		return hotelCost;
	}


	public void setHotelCost(int hotelCost) {
		this.hotelCost = hotelCost;
	}


	public int getSkyscraperCost() {
		return skyscraperCost;
	}


	public void setSkyscraperCost(int skyscraperCost) {
		this.skyscraperCost = skyscraperCost;
	}


	public int getMortgageValue() {
		return mortgageValue;
	}


	public void setMortgageValue(int mortgageValue) {
		this.mortgageValue = mortgageValue;
	}


	public int getBuildingNum() {
		return buildingNum;
	}


	public void setBuildingNum(int buildingNum) {
		this.buildingNum = buildingNum;
		notifySquareObservers();
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public void passedOn(Piece piece) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getCurrentRent() {
		int currentRent = 0;
		
		boolean isMonopoly = isMonopoly();
		boolean isMajorityOwnership = isMajorityOwnership();
		
		int buildingNum = getBuildingNum();
		
		if (buildingNum == 0) {
			if (isMonopoly) {
				currentRent = getRents().get(buildingNum) * 3;
			} else if (isMajorityOwnership) {
				currentRent = getRents().get(buildingNum) * 2;
			} else {
				currentRent = getRents().get(buildingNum) * 1;
			}
		} else {
			currentRent = getRents().get(buildingNum);
		}
		
		return currentRent;
	}
	
	public boolean isMajorityOwnership() {
		boolean isMajorityOwnership= false;
		
		int squareNumForColorGroup = findSquareNumForColorGroup(getColor(), GameController.getInstance().getMonopolyBoard());
		int squareNum = findSquareNumAccordingTo(getColor(), getOwner());
		
		if (squareNumForColorGroup == 3) {
			if (squareNum == 2) {
				isMajorityOwnership = true;
			}
		} else if (squareNumForColorGroup == 4) {
			if (squareNum == 3) {
				isMajorityOwnership = true;
			}
		}
		
		return isMajorityOwnership;
	}
	
	public boolean isMonopoly() {
		boolean isMonopoly = false;
		int squareNumForColorGroup = findSquareNumForColorGroup(getColor(), GameController.getInstance().getMonopolyBoard());
		int squareNum = findSquareNumAccordingTo(getColor(), getOwner());
		
		if (squareNum == squareNumForColorGroup) {
			isMonopoly = true;
		}
		
		return isMonopoly;
	}
	
	private int findSquareNumAccordingTo(String color, Player player) {
		int squareNum = 0;
		ArrayList<BuyableSquare> squares = player.getSquares();
		
		for (int i = 0; i < squares.size(); i++) {
			BuyableSquare square = squares.get(i);
			
			if (square instanceof ColorSquare) {
				if (((ColorSquare) square).getColor().equals(color)) {
					squareNum = squareNum + 1;
				}
			}
		}
		
		return squareNum;
	}
	
	private int findSquareNumForColorGroup(String color, MonopolyBoard monopolyBoard) {
		int squareNumForColorGroup = 0;
		
		ArrayList<Square> outerSquares = monopolyBoard.getOuterSquares();
		ArrayList<Square> middleSquares = monopolyBoard.getMiddleSquares();
		ArrayList<Square> innerSquares = monopolyBoard.getInnerSquares();
		
		squareNumForColorGroup += findSquareNumForColorGroupHelper(color, outerSquares);
		squareNumForColorGroup += findSquareNumForColorGroupHelper(color, middleSquares);
		squareNumForColorGroup += findSquareNumForColorGroupHelper(color, innerSquares);
		
		return squareNumForColorGroup;
	}
	
	private int findSquareNumForColorGroupHelper(String color, ArrayList<Square> squares) {
		int squareNum = 0;
		
		for (int i = 0; i < squares.size(); i++) {
			Square square = squares.get(i);
			
			if (square instanceof ColorSquare) {
				if (((ColorSquare) square).getColor().equals(color)) {
					squareNum = squareNum + 1;
				}
			}
		}
		
		return squareNum;
	}
	
	public boolean isMoreDeveloped() {
		boolean result = false;
		Player owner = getOwner();
		ArrayList<BuyableSquare> ownerSquares = owner.getSquares();
		int size = ownerSquares.size();
		
		for (int i = 0; i < size; i++) {
			BuyableSquare square = ownerSquares.get(i);
			
			if (square instanceof ColorSquare) {
				ColorSquare colorSquare = (ColorSquare) square;
				
				if (colorSquare.getColor().equals(getColor())) {
					if (getBuildingNum() > colorSquare.getBuildingNum()) {
						result = true;
						break;
					}
				}
			}
		}
		
		return result;
	}
	
	public boolean isLessDeveloped() {
		boolean result = false;
		Player owner = getOwner();
		ArrayList<BuyableSquare> squares = owner.getSquares();
		int size = squares.size();
		
		for (int i = 0; i < size; i++) {
			BuyableSquare square = squares.get(i);
			
			if (square instanceof ColorSquare) {
				ColorSquare colorSquare = (ColorSquare) square;
				
				if (colorSquare.getColor().equals(getColor())) {
					if (getBuildingNum() < colorSquare.getBuildingNum()) {
						result = true;
						break;
					}
				}
			}
		}
		
		return result;
	}
	
	public static ColorSquare fromJSON(JSONObject squareAsJSON) {
		ColorSquare colorSquare = null;
		
		try {		
			String name = squareAsJSON.getString(FIELD_NAMES[0]);
			boolean isMortgaged = squareAsJSON.getBoolean(FIELD_NAMES[1]);
			int buildingNum = squareAsJSON.getInt(FIELD_NAMES[2]);
			int price = squareAsJSON.getInt(FIELD_NAMES[3]);
			String rentsString = squareAsJSON.getString(FIELD_NAMES[4]);
			ArrayList<Integer> rents = parse(rentsString);
			int houseCost = squareAsJSON.getInt(FIELD_NAMES[5]);
			int hotelCost = squareAsJSON.getInt(FIELD_NAMES[6]);
			int skyscraperCost = squareAsJSON.getInt(FIELD_NAMES[7]);
			int mortgageValue = squareAsJSON.getInt(FIELD_NAMES[8]);
			String color = squareAsJSON.getString(FIELD_NAMES[9]);
			
			colorSquare = new ColorSquare(name, isMortgaged, buildingNum, price, rents, houseCost, hotelCost, skyscraperCost, mortgageValue, color);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return colorSquare; 
	}
	
	private static ArrayList<Integer> parse(String rentsString) {
		ArrayList<Integer> rents = new ArrayList<Integer>();
		String[] rentsArray = rentsString.split(",");
		
		for (int i = 0; i < rentsArray.length; i++) {
			rents.add(Integer.valueOf(rentsArray[i]));
		}
		
		return rents;
	}
	
	public int getBuildingWealth() {
		int buildingWealth;
		int buildingNum = getBuildingNum();
		
		if (0 <= buildingNum && buildingNum <= 4) {
			buildingWealth = buildingNum * getHouseCost();
		} else if (buildingNum == 5) {
			buildingWealth = getHotelCost();
		} else {
			buildingWealth = getSkyscraperCost();
		}
		
		return buildingWealth;
	}
	
	public String getBuildingsInfo() {
		String buildingsInfo;
		int buildingNum = getBuildingNum();
		
		if (0 <= buildingNum && buildingNum <= 4) {
			buildingsInfo = "houseNum: " + buildingNum;
		} else if (buildingNum == 5) {
			buildingsInfo = "hotelNum: " + 1;
		} else {
			buildingsInfo = "skyscraperNum: " + 1;
		}
		
		return buildingsInfo;
	}
	
	public void applySavedData(String savedData) {
		String[] savedDataArray = savedData.split(";");
		String name = savedDataArray[0];
		boolean isMortgaged = Boolean.valueOf(savedDataArray[1]);
		int buildingNum = Integer.valueOf(savedDataArray[2]);
		setMortgaged(isMortgaged);
		setBuildingNum(buildingNum);
	}
	
	public String convertToSavedData() {
		String savedData = getName() + ";" + isMortgaged() + ";" + getBuildingNum();
		return savedData;
	}
}
