package domain;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * Super abstract class for all squares on board.
 */

public abstract class Square {
	private String name;
	private Square next;
	private Square previous;
	private ArrayList<SquareObserver> squareObservers;
	private ArrayList<Piece> pieces;
	
	/**
	 * Abstract square class constructor
	 * @param name
	 * @requires name name is not null
	 * @effects Initialize square with given name and other fields to be null.
	 */
	
	public Square(String name){
		//@effects: initialize the square with given name.
		
		this.name=name;
		this.previous=null;
		this.next=null;
		this.squareObservers=new ArrayList<SquareObserver>();
		this.pieces = new ArrayList<Piece>();
	}
	
	public ArrayList<Piece> getPieces() {
		//@effects: returns pieces that are on the square
		
		return pieces;
	}
	
	/**
	 * Sets pieces.
	 * @param pieces
	 */
	
	public void setPieces(ArrayList<Piece> pieces) {
		//@modifies: this
		//@effects: puts pieces on the square
		
		this.pieces = pieces;
	}
	public String getName() {
		//@effects: returns name of the square
		return name;
	}
	public void setName(String name) {
		//@requires: name is not null
		//@modifies: this
		//@effects: updates the name of the square
		this.name = name;
	}
	public Square getNext() {
		//@effects: gets the square that is next to this. 
		return next;
	}
	public void setNext(Square next) {
		//@requires: next is not null
		//@modifies: this
		//@effects: sets the square that is next to this as next
		this.next = next;
	}
	public Square getPrevious() {
		//@effects: gets the square that is previous of this. 
		return previous;
	}
	public void setPrevious(Square previous) {
		//@requires: previous is not null
		//@modifies: this
		//@effects: sets the square that is previous to this as previous	
		this.previous = previous;
	}
	public ArrayList<SquareObserver> getSquareObservers() {
		//@effects: gets observers of the square. 

		return squareObservers;
	}
	public void setSquareObservers(ArrayList<SquareObserver> squareObservers) {
		//@requires: squareObservers is not null
		//@modifies: this
		//@effects: sets the observers of the square as given input.	
		this.squareObservers = squareObservers;
	}
	
	public void addSquareObserver(SquareObserver squareObserver){
		//@requires: squareObserver is not null
		//@modifies: this
		//@effects: adds observer to the observers of the square.	
		this.squareObservers.add(squareObserver);
	}
	public void notifySquareObservers(){
		//@effects: notifies the observers of the square.	
		for(SquareObserver squareObserver : squareObservers){
			squareObserver.update(this);
		}
	}
	/**
	 * 
	 * @param piece
	 */
	public abstract void landedOn(Piece piece);
	/**
	 * 
	 * @param piece
	 */
	public abstract void passedOn(Piece piece);
	
	public void addPiece(Piece piece){
		//@requires: piece is not null
		//@modifies: this
		//@effects: adds piece to this.
		getPieces().add(piece);
	}
	public void removePiece(Piece piece){
		//@requires: piece is not null
		//@modifies: this
		//@effects: removes piece from this.
		getPieces().remove(piece);
	}
}