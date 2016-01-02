package domain;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Ali Furkan
 * Piece class is for the representation of the tools on the game board.
 */

public class Piece {
	//@Overview: Piece is the moving tool of the player.
	public static final int SLEEP_TIME = 250;
	
	private ArrayList<PieceObserver> pieceObservers;
	private Player owner;
	private Square currentLocation;
	private String direction;
	
	/**
	 * Piece is the moving tool of the player.
	 * Constructor of the piece
	 * @requires owner is not null
	 * @effects creates the piece of the given owner
	 * @param owner The owner player of the piece
	 * 
	 */
	
	public Piece(Player owner) {
		//@requires: owner is not null
		//@effects: initialize the piece for the given owner
		
		setPieceObservers(new ArrayList<PieceObserver>());
		setOwner(owner);
		setCurrentLocation(null);
		setDirection(Direction.CLOCKWISE);
	}
	
	/**
	 * Moves the piece on the board according the integer stepNum given
	 * @modifies this
	 * @effects The pieces location changes according to stepNum, moves one by one
	 * @param stepNum The number of steps wanted to go on the board
	 * @see move(Square) , moveImmediate(Square)
	 */
	
	public void move(int stepNum) {
		//@modifies: this
		//@effects: moves this according the stepNum given
		
		try {
			if (getDirection().equals(Direction.CLOCKWISE)) {
				while (stepNum > 0) {
					moveImmediate(getCurrentLocation().getNext());
					stepNum = stepNum - 1;
					
					if (stepNum > 0) {
						getCurrentLocation().passedOn(this);
					}
					
					Thread.sleep(SLEEP_TIME);
				}
			} else {
				while (stepNum > 0) {
					moveImmediate(getCurrentLocation().getPrevious());
					stepNum = stepNum - 1;
					
					if (stepNum > 0) {
						getCurrentLocation().passedOn(this);
					}
					
					Thread.sleep(SLEEP_TIME);
				}
			}
			
			getCurrentLocation().landedOn(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves the piece to the given square.
	 * @requires square is not null
	 * @modifies this
	 * @effects The pieces location becomes the square given, moves one by one
	 * @param square The destination square
	 * @see move(int),moveImmediate(Square)
	 */
	
	public void move(Square square) {
		//@requires: square is not null 
		//@modifies: this
		//@effects: moves this to the square.
		try {
			if (getDirection().equals(Direction.CLOCKWISE)) {
				while (!getCurrentLocation().equals(square)) {
					moveImmediate(getCurrentLocation().getNext());
					
					if (!getCurrentLocation().equals(square)) {
						getCurrentLocation().passedOn(this);
					}
					
					Thread.sleep(SLEEP_TIME);
				}
			} else {
				while (!getCurrentLocation().equals(square)) {
					moveImmediate(getCurrentLocation().getPrevious());
					
					if (!getCurrentLocation().equals(square)) {
						getCurrentLocation().passedOn(this);
					}
					
					Thread.sleep(SLEEP_TIME);
				}
			}
			
			getCurrentLocation().landedOn(this);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Moves the piece to the square directly without moving one by one
	 * @requires square is not null
	 * @modifies This
	 * @effects Changes the location of the piece directly, without moving one by one
	 * @param square The destination square
	 * @see move(int),move(Square)
	 */
	
	public void moveImmediate(Square square) {
		//@requires: square is not null 
		//@modifies: this
		//@effects: moves this to the square directly.
		
		if (getCurrentLocation() != null) {
			getCurrentLocation().removePiece(this);
		}
		
		setCurrentLocation(square);
		getCurrentLocation().addPiece(this);
	}
	
	/**
	 * Notifies the observer of the piece so that
	 * the pieces on the gui responds to the changes in the domain
	 */
	
	public void notifyPieceObservers() {
		//@effects: notifies the observer of the piece
		
		for (int i = 0; i < getPieceObservers().size(); i++) {
			PieceObserver pieceObserver = getPieceObservers().get(i);
			pieceObserver.update(this);
		}
	}
	
	/**
	 * Adds pieceObserver to the observers of the piece.
	 * @requires pieceObserver != null && pieceObservers != null 
	 * @modifies this
	 * @effects adds the parameter to the observer list
	 * @param pieceObserver
	 */
	
	public void addPieceObserver(PieceObserver pieceObserver) {
		//@requires: pieceObserver != null && pieceObservers != null 
		//@modifies: this
		//@effects: adds pieceObserver to the observers of the piece.
		
		getPieceObservers().add(pieceObserver);
	}
	
	/**
	 * returns the observers of the piece.
	 * @return pieceObservers list
	 */
	
	public ArrayList<PieceObserver> getPieceObservers() {
		//@effects: returns the observers of the piece.
		
		return pieceObservers;
	}
	
	/**
	 * sets pieceObserver as the observers of the piece.
	 * @modifies: this
	 * @effects: sets pieceObserver as the observers of the piece.
	 * @param pieceObservers list
	 */

	public void setPieceObservers(ArrayList<PieceObserver> pieceObservers) {
		//@modifies: this
		//@effects: sets pieceObserver as the observers of the piece.
		
		this.pieceObservers = pieceObservers;
	}
	
	/**
	 * returns the owner of the piece.
	 * @effects: returns the owner of the piece.
	 * @return the owner player
	 */

	public Player getOwner() {
		//@effects: returns the owner of the piece.
		
		return owner;
	}
	
	/**
	 * sets owner as the owner of the piece.
	 * @modifies: this
	 * @effects: sets owner as the owner of the piece.
	 * @param owner the new owner player
	 */
	
	public void setOwner(Player owner) {
		//@modifies: this
		//@effects: sets owner as the owner of the piece.
		this.owner = owner;
	}
	
	/**
	 * returns the current location of the piece.
	 * @effects: returns the current location of the piece.
	 * @return the current location 
	 */

	public Square getCurrentLocation() {
		//@effects: returns the current location of the piece.
		
		return currentLocation;
	}
	
	/**
	 * sets currentLocation as the current location of the piece.
	 * @modifies: this
	 * @effects: sets currentLocation as the current location of the piece.
	 * @param currentLocation 
	 * @requires currentLocation is not null 
	 */

	private void setCurrentLocation(Square currentLocation) {
		//@modifies: this
		//@effects: sets currentLocation as the current location of the piece.
		
		this.currentLocation = currentLocation;
		notifyPieceObservers();
	}
	
	/**
	 * sets direction as the movement direction of the piece.
	 * @modifies: this
	 * @effects: sets direction as the movement direction of the piece.
	 * @param direction
	 * @see Direction
	 */
	
	public void setDirection(String direction) {
		//@modifies: this
		//@effects: sets direction as the movement direction of the piece.
		
		this.direction = direction;
	}
	
	/**
	 * returns the movement direction of the piece.
	 * @effects: returns the movement direction of the piece.
	 * @return the current direction
	 * @see Direction
	 */
	
	public String getDirection() {
		//@effects: returns the movement direction of the piece.
		return direction;
	}
	public JSONObject toJSON() throws JSONException{
		JSONObject js = new JSONObject();
		js.put("pieceObversers", getPieceObservers());
		js.put("owner",getOwner());
		js.put("currentLocation", getCurrentLocation());
		js.put("direction", getDirection());
		return js;
	}
	
	public String toString(){
		try {
			return toJSON().toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public boolean repOk(){
		if(getDirection()==null || getOwner()==null || getPieceObservers()== null)
			return false;
		return true;
	}
	
	public class Direction {
		public static final String CLOCKWISE = "CLOCKWISE";
		public static final String COUNTER_CLOCKWISE = "COUNTER_CLOCKWISE";
	}
}